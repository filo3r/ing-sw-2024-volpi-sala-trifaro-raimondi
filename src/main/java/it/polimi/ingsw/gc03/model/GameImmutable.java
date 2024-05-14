package it.polimi.ingsw.gc03.model;

import it.polimi.ingsw.gc03.model.enumerations.GameStatus;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * This class represents the Game class, but made immutable.
 * It is used to provide clients with all the game information so they can view it but not modify it.
 * This approach maintains the integrity and flow of the game as managed by the server.
 */
public class GameImmutable implements Serializable {

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
    public GameImmutable(Game game) {
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



}
