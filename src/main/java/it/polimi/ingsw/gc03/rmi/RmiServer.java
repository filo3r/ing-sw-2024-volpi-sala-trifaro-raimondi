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

// Mancano observer e notifica dei metodi a tutti i client,riguardare bene la gestione di MainController che non sono sicuro
// di aver usato nel modo giusto

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
        Registry registry = LocateRegistry.createRegistry(1234);
        registry.rebind(serverName,stub);
        System.out.println("server bound");

    }

    @Override
    public void connect(VirtualView client) throws RemoteException {
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

    @Override
    public GameController addPlayerToGame(String playerNickName) throws RemoteException, PlayerAlreadyJoinedException, DeskIsFullException, CannotJoinGameException{
        System.err.println("add request received");
        GameController gc = this.mainController.joinGame(playerNickName);
        for(VirtualView client: clients){
            client.updatePlayerJoined(playerNickName);
        }
        return gc;
    }

    @Override
    public void updateSize(int size) throws Exception{
        System.err.println("updateSize request received");
        this.mainController.getGameControllers().get(0).updateSize(size);
        for(VirtualView client: clients){
            client.updateSizeChanged(size);
        }
    }

    @Override
    public boolean checkNicknameValidity(String nickname){
        if(nickname == null || nickname.equals("") || nickname.equals("\n")){
            return false;
        }
        for(GameController g: mainController.getGameControllers()){
            if(g.getGame().getPlayers().stream().filter(x->x.getNickname().equals(nickname)).toList().isEmpty()){
                return false;
            }
        }
        return true;
    }
}
