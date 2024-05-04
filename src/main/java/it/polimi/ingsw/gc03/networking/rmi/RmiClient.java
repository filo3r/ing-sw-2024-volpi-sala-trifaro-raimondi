package it.polimi.ingsw.gc03.networking.rmi;

import it.polimi.ingsw.gc03.controller.GameController;
import it.polimi.ingsw.gc03.model.Codex;
import it.polimi.ingsw.gc03.model.Desk;
import it.polimi.ingsw.gc03.model.Game;
import it.polimi.ingsw.gc03.model.enumerations.GameStatus;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class RmiClient extends UnicastRemoteObject implements VirtualView{

    final VirtualServer server;
    private Game game;
    private GameController gameController;
    private String nickname;

    public RmiClient(VirtualServer server) throws RemoteException{
        this.server = server;
    }

    private void run() throws Exception{
        this.server.connectClient(this);
        this.runCli();
    }

    private void runCli() throws Exception {
        Scanner scan = new Scanner(System.in);
        boolean nicknameChosen = false;
        if(!nicknameChosen){
            do{
                System.out.println("Choose your Nickname\n");
                nickname = scan.nextLine();
            } while(!server.checkNicknameValidity(nickname));
            nicknameChosen = true;
            gameController = server.addPlayerToGame(nickname, this);
            game = gameController.getGame();
            System.out.println("HI "+nickname+" :You have joined the game "+game.getIdGame()+"\n");
        }
        if(game.getSize()==1){
            boolean validSize = false;
            int gameSize;
            do {
                System.out.println("The game size is 1, for allowing other players to join, choose the game's size:\n");
                gameSize = scan.nextInt();
                try {
                    server.updateSize(gameSize, game.getIdGame());
                    validSize = true;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } while (!validSize);
        }
        String nextAction;
        while(!game.getStatus().equals(GameStatus.ENDED)){
            nextAction = scan.nextLine();
            switch(nextAction){
                // Simulate an eternal action, to develop the multithreading
                case "action":
                    while(true){
                        System.out.println("Working...");
                        TimeUnit.SECONDS.sleep(5);
                    }
            }
        }
    }
    public static void main(String[] args) throws Exception {
        final String serverName = "Server";
        Registry registry = LocateRegistry.getRegistry("localhost",2222);
        VirtualServer server = (VirtualServer) registry.lookup(serverName);

        new RmiClient(server).run();
    }

    @Override
    public void updatePlayerJoined(String newPlayer) throws RemoteException {
        System.err.println("[EVENT] "+newPlayer+" joined the game");
    }

    @Override
    public void updateSizeChanged(int size) throws RemoteException {
        System.err.println("[EVENT] game size changed to "+size);
    }

    @Override
    public void updateGame(Game game){
        System.err.println("[EVENT] GameModel has changed");
    }

    @Override
    public void updateDesk(Desk desk) throws RemoteException {
        System.err.println("[EVENT] DeskModel has changed");
    }


    @Override
    public void updateCodex(Codex codex) throws RemoteException {
        System.err.println("[EVENT] CodexModel has changed");
    }

    @Override
    public void reportError(String details) throws RemoteException {

    }
}
