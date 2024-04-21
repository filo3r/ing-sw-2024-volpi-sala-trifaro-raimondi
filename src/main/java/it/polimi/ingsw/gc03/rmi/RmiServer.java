package it.polimi.ingsw.gc03.rmi;

import it.polimi.ingsw.gc03.controller.GameController;
import it.polimi.ingsw.gc03.controller.MainController;
import it.polimi.ingsw.gc03.model.Game;
import it.polimi.ingsw.gc03.model.Player;
import it.polimi.ingsw.gc03.model.card.Card;
import it.polimi.ingsw.gc03.model.exceptions.CannotJoinGameException;
import it.polimi.ingsw.gc03.model.exceptions.DeskIsFullException;
import it.polimi.ingsw.gc03.model.exceptions.NoSuchGameException;
import it.polimi.ingsw.gc03.model.exceptions.PlayerAlreadyJoinedException;
import it.polimi.ingsw.gc03.model.side.Side;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

// Mancano observer e notifica dei metodi a tutti i client,riguardare bene la gestione di MainController che non sono sicuro
// di aver usato nel modo giusto

public class RmiServer implements VirtualServer {

    final GameController gameController;
    final MainController mainController;

    final List<VirtualView> clients = new ArrayList<>();

   // final BlockingQueue<String> updates = new ArrayBlockingQueue<>(10);

   /* private void broadcastUpdateThread() throws RemoteException, InterruptedException {
        while(true){
            String update =updates.take();
            synchronized (this.clients){
                for(var c: clients ){
                    c.showUpdate();
                }
            }
        }
    }
    */

    public RmiServer(GameController gameController, MainController mainController){
        this.gameController = gameController;
        this.mainController = mainController;
    }



    public static void main(String[] args) throws RemoteException {
        final String serverName = "Server";

        VirtualServer server = new RmiServer(new GameController(),MainController.getInstance());
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
    public void addPlayerToGame(String playerNickName) throws RemoteException, PlayerAlreadyJoinedException, DeskIsFullException, CannotJoinGameException {
        System.err.println("add request received");
        this.gameController.addPlayerToGame(playerNickName);

    }

    @Override
    public boolean startTimer() throws RemoteException {
        return false;
    }

    @Override
    public void stopTimer() throws RemoteException {

    }

    @Override
    public void reconnectPlayer(String playerNickname) throws RemoteException {
        System.err.println("reconnect request received");
        this.gameController.reconnectPlayer(playerNickname);

    }

    @Override
    public void updateCurrPlayer() throws RemoteException {
        System.err.println("updateCurrPlayer request received");
        this.gameController.updateCurrPlayer();

    }

    @Override
    public void placeStarterOnCodex(Player player, Side side) throws RemoteException,Exception {
        System.err.println("placeStarter request received");
        this.gameController.placeStarterOnCodex(player,side);

    }

    @Override
    public void drawCardFromDeck(Player player, ArrayList<? extends Card> deck) throws RemoteException,Exception {
        System.err.println("drawCard request received");
        this.gameController.drawCardFromDeck(player,deck);

    }

    @Override
    public void drawCardDisplayed(Player player, ArrayList<? extends Card> deck, int index) throws RemoteException,Exception {
        System.err.println("drawCard request received");
        this.gameController.drawCardDisplayed(player,deck,index);
    }

    @Override
    public void selectCardObjective(Player player, int cardObjective) throws RemoteException,Exception {
        System.err.println("selectCard request received");
        this.gameController.selectCardObjective(player,cardObjective);

    }

    @Override
    public Game getGame() throws RemoteException {
        System.err.println("getGame request received");
        return this.gameController.getGame();
    }

    @Override
    public void placeCardOnCodex(Player player, int index, boolean frontCard, int row, int col) throws RemoteException,Exception {
        System.err.println("placeCard request received");
        this.gameController.placeCardOnCodex(player,index,frontCard,row,col);
    }

    @Override
    public void run() throws RemoteException {
        System.err.println("run request received");
        this.gameController.run();

    }


    //Main Controller
    @Override
    public MainController getInstance() throws RemoteException {
        System.err.println("getInstance request received");
        return this.mainController.getInstance();

    }

    @Override
    public void createGame(String firstPlayerNickname) throws RemoteException {
        System.err.println("createGame request received");
        this.mainController.createGame(firstPlayerNickname);

    }

    @Override
    public void joinGame(String playerNickname) throws RemoteException {
        System.err.println("joinGame request received");
        this.mainController.joinGame(playerNickname);
    }

    @Override
    public void reconnect(String playerNickname) throws RemoteException {
        System.err.println("reconnect request received");
        this.mainController.reconnect(playerNickname);
    }

    @Override
    public void deleteGame(int idGame) throws RemoteException, NoSuchGameException {
        System.err.println("delete request received");
        this.mainController.deleteGame(idGame);

    }

    @Override
    public List<GameController> getGameControllers() throws RemoteException {
        return this.mainController.getGameControllers();
    }

    @Override
    public void resetInstance() throws RemoteException {
        this.mainController.resetInstance();
    }
}
