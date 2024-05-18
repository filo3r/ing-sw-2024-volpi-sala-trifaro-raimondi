package it.polimi.ingsw.gc03.networking.rmi.old;

import it.polimi.ingsw.gc03.controller.GameController;
import it.polimi.ingsw.gc03.controller.MainController;
import it.polimi.ingsw.gc03.model.Game;
import it.polimi.ingsw.gc03.model.exceptions.CannotJoinGameException;
import it.polimi.ingsw.gc03.model.exceptions.DeskIsFullException;
import it.polimi.ingsw.gc03.model.exceptions.PlayerAlreadyJoinedException;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RmiServer implements VirtualServer {
    final MainController mainController;
    private Thread pingThread;
    final List<VirtualView> clients = new ArrayList<>();
    private final Map<VirtualView, Long> clientPingTimestamps = new ConcurrentHashMap<>();
    private final Object clientPingTimestampsLock = new Object();
    private final long TIMEOUT_MILLIS  = 5000;

    public RmiServer(MainController mainController){
        this.mainController = MainController.getInstance();
        startPongThread();
    }

    public static void main(String[] args) throws RemoteException {
        final String serverName = "Server";
        VirtualServer server = new RmiServer(MainController.getInstance());
        VirtualServer stub = (VirtualServer) UnicastRemoteObject.exportObject(server,0);
        Registry registry = LocateRegistry.createRegistry(2222);
        registry.rebind(serverName,stub);
        System.out.println("server bound");

    }

    @Override
    public void connectClient(VirtualView client) throws RemoteException {
        synchronized (clients) {
            clients.add(client);
            long currentTime = System.currentTimeMillis();
            synchronized (clientPingTimestampsLock) {
                clientPingTimestamps.put(client, currentTime);
            }
        }
    }

    @Override
    public Game getGame() throws RemoteException {
        return null;
    }

    @Override
    public List<GameController> getGameControllers() throws RemoteException {
        return null;
    }

    @Override
    public void resetInstance() throws RemoteException {

    }

    /**
     *
     * @param playerNickName
     * @return The gameController of the game where the player joined
     * @throws RemoteException
     * @throws PlayerAlreadyJoinedException
     * @throws DeskIsFullException
     * @throws CannotJoinGameException
     */
    @Override
    public GameController addPlayerToGame(String playerNickName, VirtualView listener) throws RemoteException, PlayerAlreadyJoinedException, DeskIsFullException, CannotJoinGameException{
        System.err.println("addPlayerToGame request received");
        GameController gc = this.mainController.joinGame(playerNickName, listener);
        return gc;
    }

    @Override
    public void updateSize(int size, int gameID) throws Exception{
        System.err.println("updateSize request received");
        // Get the gameController of the game with such gameID
        List<GameController> gc = this.mainController.getGameControllers().stream()
                .filter(x->(x.getGame().getIdGame()==gameID)).toList();
        // If some exists, update its size
        if(!gc.isEmpty()){
            gc.getFirst().updateSize(size);
        }
    }

    @Override
    public boolean checkNicknameValidity(String nickname){
        if(nickname == null || nickname.equals("") || nickname.equals("\n")){
            return false;
        }
        for(GameController g: mainController.getGameControllers()){
            if(!g.getGame().getPlayers().stream().filter(x->x.getNickname().equals(nickname)).toList().isEmpty()){
                return false;
            }
        }
        return true;
    }

    @Override
    public void addPlayerToSpecificGame(String nickname, int id, VirtualView listener) throws RemoteException {
        System.err.println("addPlayerToSpecificGame request received");
        GameController gc = this.mainController.joinSpecificGame(nickname, id, listener);
    }

    // For testing purposes, no real case use
    @Override
    public void infiniteTask(int id, String p) throws Exception {
        List<GameController> gc = this.mainController.getGameControllers().stream()
                .filter(x->(x.getGame().getIdGame()==id)).toList();
        if(!gc.isEmpty()){
            gc.getFirst().infiniteTask(p);
        }
    }

    private void startPongThread() {
        pingThread = new Thread(() -> {
            while (!Thread.interrupted()) {
                try {
                    long currentTime = System.currentTimeMillis();
                    List<VirtualView> disconnectedClients = new ArrayList<>();
                    synchronized (clients) {
                        synchronized (clientPingTimestampsLock) {
                            for (VirtualView client : clients) {
                                Long lastPingTime = clientPingTimestamps.get(client);
                                if (lastPingTime != null && currentTime - lastPingTime > TIMEOUT_MILLIS) {
                                    disconnectedClients.add(client);
                                    System.err.println("Server: Client " + client + " disconnected due to timeout");
                                }
                            }
                            for (VirtualView disconnectedClient : disconnectedClients) {
                                clients.remove(disconnectedClient);
                                clientPingTimestamps.remove(disconnectedClient);
                            }
                        }
                        clients.wait(TIMEOUT_MILLIS);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        pingThread.start();
    }

    @Override
    public void ping(VirtualView client) {
        synchronized (clientPingTimestampsLock) {
            if (clients.contains(client)) {
                long currentTime = System.currentTimeMillis();
                clientPingTimestamps.put(client, currentTime);
            }
        }
        synchronized (clients) {
            clients.notifyAll();
        }
    }
}
