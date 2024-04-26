package it.polimi.ingsw.gc03.rmi;

import it.polimi.ingsw.gc03.controller.GameController;
import it.polimi.ingsw.gc03.model.Game;
import it.polimi.ingsw.gc03.model.Player;
import it.polimi.ingsw.gc03.model.enumerations.GameStatus;
import it.polimi.ingsw.gc03.model.exceptions.CannotJoinGameException;
import it.polimi.ingsw.gc03.model.exceptions.DeskIsFullException;
import it.polimi.ingsw.gc03.model.exceptions.PlayerAlreadyJoinedException;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
//Manca parte di Scanner input e gestione observer
public class RmiClient extends UnicastRemoteObject implements VirtualView{

    final VirtualServer server;
    private Game game;
    public RmiClient(VirtualServer server) throws RemoteException{
        this.server = server;
    }

    private void run() throws Exception{
        this.server.connect(this);
        this.runCli();
    }

    private void runCli() throws Exception {
        Scanner scan = new Scanner(System.in);
        do{
            String nickname;
            do{
                System.out.println("Choose your Nickname\n");
                nickname = scan.nextLine();
            } while(!server.checkNicknameValidity(nickname));
            List<GameController> gameControllers = server.getGameControllers();
            if(gameControllers==null){
                GameController gc = server.addPlayerToGame(nickname);
                game = gc.getGame();
                System.out.println("No available games to join, the server made a new game, the game id is "+game.getIdGame());
            } else {
                if(!gameControllers.stream().filter(x->x.getGame().getPlayers().size()<x.getGame().getSize()).toList().isEmpty()){
                    GameController gc = server.addPlayerToGame(nickname);
                    game = gc.getGame();
                    System.out.println("A game is available, you joined it, the game id is "+game.getIdGame());
                } else {
                    // There is already a player with this nickname, choose whether he could reconnect.
                }
            }
            boolean validSize = false;
            int gameSize;
            do {
                System.out.println("The game size is 1, for allowing other players to join, choose the game's size:\n");
                gameSize = scan.nextInt();

                try {
                    server.updateSize(gameSize);
                    validSize = true;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } while (!validSize);


        }while (!game.getStatus().equals(GameStatus.ENDED));
    }
    public static void main(String[] args) throws Exception {
        final String serverName = "Server";
        Registry registry = LocateRegistry.getRegistry("localhost",1234);
        VirtualServer server = (VirtualServer) registry.lookup(serverName);

        new RmiClient(server).run();
    }

    @Override
    public void updatePlayerJoined(String newPlayer) throws RemoteException {
        System.out.println("[EVENT] "+newPlayer+" joined the game");
    }

    public void updateSizeChanged(int size) throws RemoteException {
        System.out.println("[EVENT] game size changed to "+size);
    }

    @Override
    public void reportError(String details) throws RemoteException {

    }
}
