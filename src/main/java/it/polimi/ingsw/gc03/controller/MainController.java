package it.polimi.ingsw.gc03.controller;

import it.polimi.ingsw.gc03.model.enumerations.GameStatus;
import it.polimi.ingsw.gc03.model.exceptions.CannotJoinGameException;
import it.polimi.ingsw.gc03.model.exceptions.DeskIsFullException;
import it.polimi.ingsw.gc03.model.exceptions.NoSuchGameException;
import it.polimi.ingsw.gc03.model.exceptions.PlayerAlreadyJoinedException;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;


/**
 * Manages the lifecycle and interactions of multiple game sessions within the application.
 * This class follows the Singleton design pattern to ensure only one instance manages the game controllers globally.
 */
public class MainController implements Serializable {

    /**
     * Instance of the MainController, needed for the singleton design pattern.
     */
    private static MainController instance = null;

    /**
     * List of GameController instances that manage the individual games within the application.
     */
    private List<GameController> gameControllers;

    /**
     * Singleton's instance.
     */
    private MainController() {
        gameControllers = new ArrayList<>();
    }


    /**
     * Retrieves the singleton instance of MainController, creating it if it does not exist.
     * @return The singleton instance of MainController.
     */
    public synchronized static MainController getInstance() {
        if (instance == null) {
            instance = new MainController();
        }
        return instance;
    }


    /**
     * Creates a new game and attempts to add the first player to it.
     * @param firstPlayerNickname The nickname of the first player.
     */
    public synchronized void createGame(String firstPlayerNickname) {
        GameController controller = new GameController();
        gameControllers.add(controller);
    }


    /**
     * Attempts to join a player to an available game that is waiting for players. If no such game exists, creates a
     * new game.
     * @param playerNickname The nickname of the player attempting to join a game.
     */
    public synchronized GameController joinGame(String playerNickname) throws RemoteException {
        //First of all check if there is any game where is possible to join (GCs stands for gameControllers
        List<GameController> GCs = gameControllers.stream().filter(x -> (x.getGame().getStatus().equals(GameStatus.WAITING))).toList();
        if (!GCs.isEmpty()) {
            //If there are some (at least 1) available games to join, join the last.
            try {
                gameControllers.getLast().addPlayerToGame(playerNickname);
                return gameControllers.getLast();
            } catch (CannotJoinGameException e) {
                throw new RuntimeException(e);
            } catch (PlayerAlreadyJoinedException e) {
                throw new RuntimeException(e);
            } catch (DeskIsFullException e) {
                throw new RuntimeException(e);
            }
        } else {
            // If there are no available games to join, create a new game.
            createGame(playerNickname);
            joinGame(playerNickname);
            return gameControllers.getLast();
        }
    }


    /**
     * The method would allow players to reconnect to a game in progress.
     * @param playerNickname The nickname of the player who needs to reconnect.
     */
    public synchronized void reconnect(String playerNickname) {

    }


    /**
     * Deletes a game from the list of active games based on its ID.
     * @param idGame The ID of the game to be deleted.
     * @throws NoSuchGameException if no game with the given ID exists.
     */
    public synchronized void deleteGame(int idGame) throws NoSuchGameException {
        List<GameController> gameToRemove = gameControllers.stream().filter(x -> (x.getGame().getIdGame() == idGame)).toList();
        if(!gameToRemove.isEmpty()){
            gameControllers.remove(gameToRemove.getFirst());
        } else {
            throw new NoSuchGameException();
        }
    }


    /**
     * Returns the list of game controllers currently managed by this MainController.
     * @return A list of GameController instances.
     */
    public List<GameController> getGameControllers() {
        return gameControllers;
    }


    // Resets the singleton instance of MainController, mainly used for testing purposes.
    public static void resetInstance() {
        instance = null;
    }


}
