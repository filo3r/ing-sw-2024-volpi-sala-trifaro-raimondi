package it.polimi.ingsw.gc03.controller;

import it.polimi.ingsw.gc03.listeners.GameListener;
import it.polimi.ingsw.gc03.model.GameImmutable;
import it.polimi.ingsw.gc03.model.enumerations.GameStatus;
import it.polimi.ingsw.gc03.model.exceptions.CannotJoinGameException;
import it.polimi.ingsw.gc03.model.exceptions.DeskIsFullException;
import it.polimi.ingsw.gc03.model.exceptions.NoSuchGameException;
import it.polimi.ingsw.gc03.model.exceptions.PlayerAlreadyJoinedException;
import it.polimi.ingsw.gc03.networking.rmi.MainControllerInterface;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Manages the lifecycle and interactions of multiple game sessions within the application.
 * This class follows the Singleton design pattern to ensure only one instance manages the game controllers globally.
 */
public class MainController implements MainControllerInterface, Serializable {

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
     * @param gameListener The game listener of the first player.
     * @param firstPlayerNickname The player who created the game.
     * @return The created GameController.
     * @throws RemoteException If there is an issue with remote communication.
     */
    public synchronized GameController createGame(GameListener gameListener, String firstPlayerNickname) throws RemoteException {
        // Creates a new GameController, which will create an empty Game.
        GameController controller = new GameController();
        // add the controller to the list of controllers
        gameControllers.add(controller);
        // add the player to the newly created game and add his listener to every component and vice versa
        joinSpecificGame(gameListener, firstPlayerNickname, controller.getGame().getIdGame());
        controller.addListener(gameListener, controller.getGame().getPlayers().getFirst());
        return controller;
    }


    /**
     * Attempts to join a player to an available game that is waiting for players. If no such game exists, creates a
     * new game.
     * @param playerNickname The nickname of the player attempting to join a game.
     * @param listener The game listener of the player.
     * @return The GameController of the joined game.
     * @throws RemoteException If there is an issue with remote communication.
     */
    public synchronized GameController joinFirstAvailableGame(GameListener listener, String playerNickname) throws RemoteException {
        // joinGame will try to make the player join the first available game, if none exists, it will create a new game
        List<GameController> GCs = gameControllers.stream().filter(x -> (x.getGame().getStatus().equals(GameStatus.WAITING))).toList();
        return addPlayerToGame(playerNickname, listener, GCs);
    }


    /**
     * Attempts to join a player to a specific game identified by its ID.
     * @param listener The game listener of the player.
     * @param playerNickname The nickname of the player attempting to join the game.
     * @param id The ID of the game to join.
     * @return The GameController of the joined game.
     * @throws RemoteException If there is an issue with remote communication.
     */
    public synchronized GameController joinSpecificGame(  GameListener listener, String playerNickname, int id) throws RemoteException {
        List<GameController> GCs = gameControllers.stream().filter(x -> (x.getGame().getIdGame() == id)).toList();
        return addPlayerToGame(playerNickname, listener, GCs);
    }


    /**
     * Adds a player to a game from a list of available games.
     * @param playerNickname The nickname of the player to add.
     * @param listener The game listener of the player.
     * @param GCs The list of available GameController instances.
     * @return The GameController of the game the player joined or created.
     * @throws RemoteException If there is an issue with remote communication.
     */
    private GameController addPlayerToGame(String playerNickname, GameListener listener, List<GameController> GCs) throws RemoteException {
        if(!GCs.isEmpty()) {
            try {
                GCs.getFirst().addPlayerToGame(playerNickname, listener);
                GCs.getFirst().addListener(listener, GCs.getFirst().getGame().getPlayers().stream().filter(x->x.getNickname().equals(playerNickname)).findFirst().get());
                return gameControllers.get(0);
            } catch (Exception e) {}
        } else {
            createGame(listener, playerNickname);
            return gameControllers.getLast();
        }
        return null;
    }


    /**
     * Allows players to reconnect to a game in progress.
     * @param gameListener The game listener of the player who needs to reconnect.
     * @param playerNickname The nickname of the player who needs to reconnect.
     * @return The GameController of the game the player reconnected to.
     * @throws RemoteException If there is an issue with remote communication.
     */
    public synchronized GameController reconnectToGame(GameListener gameListener, String playerNickname) throws RemoteException {
        // Search for a game which has a player named playerNickname
        List<GameController> GCs = gameControllers.stream()
                .filter(gc -> gc.getGame().getPlayers().stream()
                        .anyMatch(p -> p.getNickname().equals(playerNickname)))
                .toList();
        // If any player with playerNickname exists in some game
        if(!GCs.isEmpty()){
            //If the player is actually offline
            if(!GCs.getFirst().getGame().getPlayers().stream().filter(p->p.getNickname().equals(playerNickname)).toList().getFirst().getOnline()){
                try {
                    GCs.getFirst().reconnectPlayer(playerNickname, gameListener);
                    GCs.getFirst().addListener(gameListener, GCs.getFirst().getGame().getPlayers().stream().filter(x->x.getNickname().equals(playerNickname)).findFirst().get());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                // Call the listener from the controller, it's not ideal but there aren't controls in the model for this
                gameListener.playerReconnected(new GameImmutable(GCs.getFirst().getGame()), playerNickname);
                return gameControllers.getFirst();
            }
        }
        return null;
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


    /**
     * Resets the singleton instance of MainController, mainly used for testing purposes.
     */
    public static void resetInstance() {
        instance = null;
    }


}
