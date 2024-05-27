package it.polimi.ingsw.gc03.controller;

import it.polimi.ingsw.gc03.listeners.GameListener;
import it.polimi.ingsw.gc03.model.GameImmutable;
import it.polimi.ingsw.gc03.model.Player;
import it.polimi.ingsw.gc03.model.enumerations.GameStatus;
import it.polimi.ingsw.gc03.model.exceptions.CannotJoinGameException;
import it.polimi.ingsw.gc03.model.exceptions.DeskIsFullException;
import it.polimi.ingsw.gc03.model.exceptions.NoSuchGameException;
import it.polimi.ingsw.gc03.model.exceptions.PlayerAlreadyJoinedException;
import it.polimi.ingsw.gc03.networking.rmi.GameControllerInterface;
import it.polimi.ingsw.gc03.networking.rmi.MainControllerInterface;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MainController implements MainControllerInterface, Serializable {

    private static MainController instance = null;
    private List<GameController> gameControllers;

    private MainController() {
        gameControllers = new ArrayList<>();
    }

    public synchronized static MainController getInstance() {
        if (instance == null) {
            instance = new MainController();
        }
        return instance;
    }


    public synchronized GameControllerInterface createGame(GameListener gameListener, String firstPlayerNickname) throws RemoteException {
        try{
            GameController controller = new GameController();
            gameControllers.add(controller);
            joinSpecificGame(gameListener, firstPlayerNickname, controller.getGame().getIdGame());
            return controller;
        } catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    public synchronized GameControllerInterface joinFirstAvailableGame(GameListener listener, String playerNickname) throws RemoteException {
        try{
            List<GameController> GCs = gameControllers.stream().filter(x -> (x.getGame().getStatus().equals(GameStatus.WAITING))).toList();
            return addPlayerToGame(playerNickname, listener, GCs);
        } catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    public synchronized GameControllerInterface joinSpecificGame(GameListener listener, String playerNickname, int id) throws RemoteException {
        try{
            List<GameController> GCs = gameControllers.stream().filter(x -> (x.getGame().getIdGame() == id)).toList();
            return addPlayerToGame(playerNickname, listener, GCs);
        } catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    private GameControllerInterface addPlayerToGame(String playerNickname, GameListener listener, List<GameController> GCs) throws RemoteException {
        if(!GCs.isEmpty()) {
            try {
                GCs.get(0).addPlayerToGame(playerNickname, listener);
                return GCs.get(0);
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            return createGame(listener, playerNickname);
        }
        return null;
    }

    public synchronized GameControllerInterface reconnectToGame(GameListener gameListener, String playerNickname) throws RemoteException {
        List<GameController> GCs = gameControllers.stream()
                .filter(gc -> gc.getGame().getPlayers().stream()
                        .anyMatch(p -> p.getNickname().equals(playerNickname)))
                .toList();

        if(!GCs.isEmpty()){
            if(!GCs.get(0).getGame().getPlayers().stream().filter(p->p.getNickname().equals(playerNickname)).toList().get(0).getOnline()){
                try {
                    GCs.get(0).reconnectPlayer(playerNickname, gameListener);
                    GCs.get(0).getGame().getListener().addListener(gameListener);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                gameListener.playerReconnected(new GameImmutable(GCs.get(0).getGame()), playerNickname);
                return GCs.get(0);
            }
        }
        return null;
    }

    public synchronized void deleteGame(int idGame) throws NoSuchGameException {
        List<GameController> gameToRemove = gameControllers.stream().filter(x -> (x.getGame().getIdGame() == idGame)).toList();
        if(!gameToRemove.isEmpty()){
            gameControllers.remove(gameToRemove.get(0));
        } else {
            throw new NoSuchGameException();
        }
    }

    public List<GameController> getGameControllers() {
        return gameControllers;
    }

    public static void resetInstance() {
        instance = null;
    }
}
