package it.polimi.ingsw.gc03.model;

import it.polimi.ingsw.gc03.model.enumerations.GameStatus;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * This class represents the Game class, but made immutable.
 * It is used to provide clients with all the game information so they can view it but not modify it.
 * This approach maintains the integrity and flow of the game as managed by the server.
 */
public class Model implements Serializable {

    /**
     * Game's ID.
     */
    private final int idGame;

    /**
     * Game's size: number of players participating in the game.
     */
    private final int size;

    /**
     * Game's status.
     */
    private final GameStatus status;

    /**
     * Game's desk.
     */
    private final Desk desk;

    /**
     * Number of players in the game.
     */
    private final int numPlayer;

    /**
     * List of players taking part in the game.
     */
    private final List<Player> players;

    /**
     * Current player.
     */
    private final int currPlayer;

    /**
     * Game's chat
     */
    private final List<ChatMessage> chat;

    /**
     * Winner or winners of the game.
     */
    private final List<Player> winner;


    /**
     * GameImmutable class constructor.
     * @param game The game instance to copy from.
     */
    public Model(Game game) throws RemoteException {
        this.idGame = game.getIdGame();
        this.size = game.getSize();
        this.status = game.getStatus();
        this.desk = game.getDesk();
        this.numPlayer = game.getNumPlayer();
        this.players = Collections.unmodifiableList(new ArrayList<>(game.getPlayers()));
        this.currPlayer = game.getCurrPlayer();
        this.chat = Collections.unmodifiableList(new ArrayList<>(game.getChat()));
        this.winner = Collections.unmodifiableList(new ArrayList<>(game.getWinner()));
    }

    /**
     * Method to get the ID of the game.
     * @return The ID of the game.
     */
    public int getIdGame() {
        return idGame;
    }

    /**
     * Method to get the size of the game.
     * @return The size of the game.
     */
    public int getSize() {
        return size;
    }

    /**
     * Method to get the status of the game.
     * @return The status of the game.
     */
    public GameStatus getStatus() {
        return status;
    }

    /**
     * Method to get the desk of the game.
     * @return The desk of the game.
     */
    public Desk getDesk() {
        return desk;
    }

    /**
     * Method to get the number of players in the game.
     * @return The number of players in the game.
     */
    public int getNumPlayer() {
        return numPlayer;
    }

    /**
     * Method to get the list of players in the game.
     * @return The list of players in the game.
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Method to get the current player in the game.
     * @return The current player in the game.
     */
    public int getCurrPlayer() {
        return currPlayer;
    }

    /**
     * Method to get the chat messages in the game.
     * @return The chat messages in the game.
     */
    public List<ChatMessage> getChat() {
        return chat;
    }

    /**
     * Method to get the winner of the game.
     * @return The winner or the winners of the game.
     */
    public List<Player> getWinner() {
        return winner;
    }
}
