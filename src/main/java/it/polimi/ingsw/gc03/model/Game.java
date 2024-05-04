package it.polimi.ingsw.gc03.model;

import it.polimi.ingsw.gc03.controller.MainController;
import it.polimi.ingsw.gc03.model.enumerations.GameStatus;
import it.polimi.ingsw.gc03.model.exceptions.DeskIsFullException;
import it.polimi.ingsw.gc03.model.exceptions.PlayerAlreadyJoinedException;
import it.polimi.ingsw.gc03.rmi.VirtualView;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.time.LocalTime;
import java.util.ArrayList;


/**
 * This class represents a game.
 */
public class Game extends Observable implements Serializable {

    /**
     * Game's ID.
     */
    private int idGame;

    /**
     * Game's size: number of players participating in the game.
     */
    private int size;

    /**
     * Game's status.
     */
    private GameStatus status;

    /**
     * Game's desk.
     */
    private Desk desk;

    /**
     * Number of players in the game.
     */
    private int numPlayer;

    /**
     * List of players taking part in the game.
     */
    private ArrayList<Player> players;

    /**
     * Current player.
     */
    private int currPlayer;

    /**
     * Game's chat
     */
    private ArrayList<Message> chat;

    /**
     * Winner or winners of the game.
     */
    private ArrayList<Player> winner;

    /**
     * Maximum number of players that can participate in a game.
     */
    private static final int MAX_NUM_PLAYERS = 4;

    /**
     * Points needed for a player to stop the game.
     */
    private static final int STOP_POINT_GAME = 20;


    /**
     * Game class constructor.
     * @param idGame The game's ID.
     */
    public Game(int idGame) throws RemoteException {
        this.idGame = idGame;
        this.size = 1;
        this.status = GameStatus.WAITING;
        this.desk = new Desk();
        this.numPlayer = 0;
        this.players = new ArrayList<>(MAX_NUM_PLAYERS);
        this.currPlayer = 0;
        this.chat = new ArrayList<>();
        this.winner = new ArrayList<>(MAX_NUM_PLAYERS);
    }


    /**
     * Method for adding a player to the game.
     * @param nickname The player's nickname.
     * @return A boolean indicating whether a player has been added to the game or not.
     */
    public boolean addPlayer(String nickname, VirtualView listener) throws DeskIsFullException, PlayerAlreadyJoinedException, RemoteException {
        // The game is full
        if (this.numPlayer >= this.size || this.numPlayer >= MAX_NUM_PLAYERS) {
            throw new DeskIsFullException();
        } else {
            // Check that the nickname is different from other players who have already entered
            for (Player player : this.players) {
                if (nickname.equals(player.getNickname()))
                    throw new PlayerAlreadyJoinedException();
            }
            // The player can be added
            this.numPlayer++;
            Player player = new Player(nickname, this.numPlayer, this.desk);
            this.players.add(player);
            this.players.forEach(p->p.addObserver(listener));
            this.players.forEach(p->p.getCodex().addObserver(listener));
            this.getDesk().addObserver(listener);
            addObserver(listener);
            notifyObservers(this);
            return true;
        }
    }


    /**
     * Method for removing a player from the game.
     * @param nickname The player's nickname.
     * @return A Boolean value indicating whether a player has been removed from the game.
     */
    public boolean removePlayer(String nickname) throws RemoteException {
        for (int i = 0; i < this.players.size(); i++) {
            if (nickname.equals(this.players.get(i).getNickname())) {
                this.players.remove(i);
                this.numPlayer--;
                notifyObservers(this);
                return true;
            }
        }
        notifyObservers(this);
        return false;
    }


    /**
     * Method to add a message to the game chat.
     * @param sender The nickname of the player who wrote the message.
     * @param text The text of the message.
     */
    public void addMessage(Player sender, String text) throws RemoteException {
        LocalTime time = LocalTime.now();
        Message message = new Message(sender, text, time);
        this.chat.add(message);
        notifyObservers(this);
    }


    /**
     * Method to update the current player.
     */
    public void updateCurrPlayer() throws RemoteException {
        if (this.currPlayer < this.numPlayer - 1)
            this.currPlayer++;
        else
            this.currPlayer = 0;
        notifyObservers(this);
    }


    /**
     * Method for returning a list of players who are online.
     * @return A list of online players.
     */
    public ArrayList<Player> getOnlinePlayers() {
        ArrayList<Player> onlinePlayers = new ArrayList<>(MAX_NUM_PLAYERS);
        for (Player player : this.players) {
            if (player.getOnline()) {
                onlinePlayers.add(player);
            }
        }
        return onlinePlayers;
    }


    /**
     * Method for determining who won the game.
     */
    private void decideWinner() throws RemoteException {
        int maxScore = 0;
        // Determine which score is the highest
        for (Player player : this.players) {
            if (player.getScore() > maxScore)
                maxScore = player.getScore();
        }
        // Add all players with maximum score to the temporary winners array
        ArrayList<Player> tempWinners = new ArrayList<>(MAX_NUM_PLAYERS);
        for (Player player : this.players) {
            if (player.getScore() == maxScore)
                tempWinners.add(player);
        }
        // If there are multiple players with the same score, compare the points obtained from the Objective cards
        if (tempWinners.size() > 1) {
            int maxPointObjective = 0;
            // Determine which pointObjective is the highest
            for (Player tempWinner : tempWinners) {
                if (tempWinner.getPointObjective() > maxPointObjective)
                    maxPointObjective = tempWinner.getPointObjective();
            }
            // Add all players with maximum pointObjective to the final winners array
            ArrayList<Player> winners = new ArrayList<>(MAX_NUM_PLAYERS);
            for (Player tempWinner : tempWinners) {
                if (tempWinner.getPointObjective() == maxPointObjective)
                    winners.add(tempWinner);
            }
            this.winner.addAll(winners);
        } else {
            this.winner.addAll(tempWinners);
        }
        notifyObservers(this);
    }


    /**
     * Method for stopping the game if the two decks are exhausted or if a player has reached the established points.
     * @return A boolean indicating whether the game must end or not.
     */
    public boolean stopGame() {
        int scoreStop = 0;
        for (Player player : this.players) {
            if (player.getCodex().getPointCodex() >= STOP_POINT_GAME)
                scoreStop++;
        }
        if (scoreStop != 0 || (this.desk.getDeckResource().isEmpty() && this.desk.getDeckGold().isEmpty()))
            return true;
        else
            return false;
    }


    /**
     * Method to get the ID of the game.
     * @return The ID of the game.
     */
    public int getIdGame() {
        return idGame;
    }


    /**
     * Method to set the ID of the game.
     * @param idGame The ID of the game.
     */
    public void setIdGame(int idGame) {
        this.idGame = idGame;
    }


    /**
     * Method to set the size of the game.
     * @param size The size of the game.
     */
    public void setSize(int size) throws RemoteException {
        this.size = size;
        notifyObservers(this);
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
     * Method to set the status of the game.
     * @param status The status of the game.
     */
    public void setStatus(GameStatus status) {
        this.status = status;
    }


    /**
     * Method to get the desk of the game.
     * @return The desk of the game.
     */
    public Desk getDesk() {
        return desk;
    }


    /**
     * Method to set the desk of the game.
     * @param desk The desk of the game.
     */
    public void setDesk(Desk desk) {
        this.desk = desk;
    }


    /**
     * Method to get the number of players in the game.
     * @return The number of players in the game.
     */
    public int getNumPlayer() {
        return numPlayer;
    }


    /**
     * Method to set the number of players in the game.
     * @param numPlayer The number of players in the game.
     */
    public void setNumPlayer(int numPlayer) {
        this.numPlayer = numPlayer;
    }


    /**
     * Method to get the list of players in the game.
     * @return The list of players in the game.
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }


    /**
     * Method to set the list of players in the game.
     * @param players The list of players in the game.
     */
    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }


    /**
     * Method to get the current player in the game.
     * @return The current player in the game.
     */
    public int getCurrPlayer() {
        return currPlayer;
    }


    /**
     * Method to set the current player in the game.
     * @param currPlayer The current player in the game.
     */
    public void setCurrPlayer(int currPlayer) {
        this.currPlayer = currPlayer;
    }


    /**
     * Method to get the chat messages in the game.
     * @return The chat messages in the game.
     */
    public ArrayList<Message> getChat() {
        return chat;
    }


    /**
     * Method to set the chat messages in the game.
     * @param chat The chat messages in the game.
     */
    public void setChat(ArrayList<Message> chat) {
        this.chat = chat;
    }


    /**
     * Method to get the winner of the game.
     * @return The winner or the winners of the game.
     */
    public ArrayList<Player> getWinner() throws RemoteException {
        // If the winner has not yet been determined
        if (this.winner.isEmpty()) {
            decideWinner();
            return this.winner;
        } else {
            return this.winner;
        }
    }


    /**
     * Method to set the winner of the game.
     * @param winner The winner or the winners of the game.
     */
    public void setWinner(ArrayList<Player> winner) {
        this.winner = winner;
    }


}
