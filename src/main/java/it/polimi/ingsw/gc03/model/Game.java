package it.polimi.ingsw.gc03.model;

import it.polimi.ingsw.gc03.model.enumerations.GameStatus;
import it.polimi.ingsw.gc03.model.exceptions.DeskIsFullException;
import it.polimi.ingsw.gc03.model.exceptions.PlayerAlreadyJoinedException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    private int currPlayer;

    /**
     * Chat of the game.
     */
    private List<Message> chat;

    /**
     * Game's status.
     */
    private GameStatus status;

    /**
     * Number of players this game will handle
     */
    private int size;

    /**
     * List of players sitting at the desk
     */
    private ArrayList<Player> players;

    /**
     * Constructor for the Game class.
     */
    public Game() {
        desk = new Desk();
        idGame = new Random().nextInt(999999999);
        chat = new ArrayList<>();
        players = new ArrayList<Player>();
        currPlayer = -1;
        status = GameStatus.WAITING;
        size = 1; // Default game size is 1, the first player will have to change it
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
    public List<Message> getChat() {
        return chat;
    }

    /**
     * Setter method for setting the game's chat.
     * @param chat The game's chat to set.
     */
    public void setChat(List<Message> chat) {
        this.chat = chat;
    }

    /**
     * Getter method for retrieving the game's status.
     * @return The game's status.
     */
    public GameStatus getStatus() {
        return status;
    }

    /**
     * Setter method for setting the game's status.
     * @param status The game's status to set.
     */
    public void setStatus(GameStatus status) {
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
    public int getCurrPlayer() {
        return currPlayer;
    }

    /**
     * Setter method for the current player.
     * @param currPlayer The current player.
     */
    public void setCurrPlayer(int currPlayer) {
        this.currPlayer = currPlayer;
    }

    public void addPlayer(Player player)throws PlayerAlreadyJoinedException, DeskIsFullException {
        if(this.players.stream().filter(x->(x.getNickname().equals(player.getNickname()))).toList().isEmpty()){
            if(this.players.size()<4){
                this.players.add(player);
            } else throw new PlayerAlreadyJoinedException("Error:Player already joined the desk");
        } else throw new DeskIsFullException("Error:The Desk is already full");
    }

    /**
     Method for getting th List of players at the desk.
     @return List of players
     */
    public ArrayList<Player> getPlayers(){return this.players;}

    /**
     * Method for setting the list of players at the desk.
     * @param players List of players
     */
    public void setPlayers(ArrayList<Player> players){this.players=players;}


    public List<Player> getOnlinePlayers(){
        return players.stream().filter(Player::isOnline).toList();
    }
}