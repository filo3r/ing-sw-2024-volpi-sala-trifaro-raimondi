package it.polimi.ingsw.gc03.rmi;

import it.polimi.ingsw.gc03.controller.GameController;
import it.polimi.ingsw.gc03.controller.MainController;
import it.polimi.ingsw.gc03.model.Game;
import it.polimi.ingsw.gc03.model.Player;
import it.polimi.ingsw.gc03.model.exceptions.CannotJoinGameException;
import it.polimi.ingsw.gc03.model.exceptions.DeskIsFullException;
import it.polimi.ingsw.gc03.model.exceptions.PlayerAlreadyJoinedException;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class RmiServer implements VirtualServer {
    final MainController mainController;

    final List<VirtualView> clients = new ArrayList<>();

    public RmiServer(MainController mainController){
        this.mainController = MainController.getInstance();
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
        synchronized(this.clients){
            this.clients.add(client);
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
        System.err.println("add request received");
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
}
