package it.polimi.ingsw.gc03;

/**
 * This class represents a game.
 */
public class Game {
    /**
     * Game's desk.
     */
    private Desk desk;

    /**
     * Game's id.
     */
    private int idGame;

    /**
     * Current player's turn
     */
    private Player currPlayer;

    /**
     * Chat of the game.
     */
    private Message[] chat;

    /**
     * Game's status.
     * (active (0): game's running;
     *  idle (1): game's waiting for players;
     *  end (2): game's ended)
     */
    private int status;

    /**
     * Number of players this game will handle
     */
    private int size;

    /**
     * Constructor for the Game class.
     * @param desk The game's desk.
     * @param idGame The unique game's id.
     * @param chat The game's chat.
     * @param status The game's status
     * @param size The game's size: how many players will play in this game.
     */
    public Game(Desk desk, int idGame, Message[] chat, int status, int size, Player currPlayer) {
        this.desk = desk;
        this.idGame = idGame;
        this.chat = chat;
        this.status = status;
        this.size = size;
        this.currPlayer = currPlayer;
    }

    /**
     * Getter method for retrieving the game's desk.
     * @return The game's desk.
     */
    public Desk getDesk() {
        return desk;
    }

    /**
     * Setter method for setting the game's desk.
     * @param desk The game's desk to set.
     */
    public void setDesk(Desk desk) {
        this.desk = desk;
    }

    /**
     * Getter method for retrieving the unique game's id.
     * @return The unique game's id.
     */
    public int getIdGame() {
        return idGame;
    }

    /**
     * Setter method for setting the unique game's id.
     * @param idGame The unique game's id to set.
     */
    public void setIdGame(int idGame) {
        this.idGame = idGame;
    }

    /**
     * Getter method for retrieving the game's chat.
     * @return The game's chat.
     */
    public Message[] getChat() {
        return chat;
    }

    /**
     * Setter method for setting the game's chat.
     * @param chat The game's chat to set.
     */
    public void setChat(Message[] chat) {
        this.chat = chat;
    }

    /**
     * Getter method for retrieving the game's status.
     * @return The game's status.
     */
    public int getStatus() {
        return status;
    }

    /**
     * Setter method for setting the game's status.
     * @param status The game's status to set.
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * Getter method for retrieving the game's size.
     * @return The game's size.
     */
    public int getSize() {
        return size;
    }

    /**
     * Setter method for setting the game's size.
     * @param size The game's size to set.
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Getter method for the current plauer.
     * @return The current player.
     */
    public Player getCurrPlayer() {
        return currPlayer;
    }

    /**
     * Setter method for the current player.
     * @param currPlayer The current player.
     */
    public void setCurrPlayer(Player currPlayer) {
        this.currPlayer = currPlayer;
    }
}
