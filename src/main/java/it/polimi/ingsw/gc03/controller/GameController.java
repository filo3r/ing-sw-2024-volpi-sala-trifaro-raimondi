package it.polimi.ingsw.gc03.controller;

import it.polimi.ingsw.gc03.model.*;
import it.polimi.ingsw.gc03.model.card.Card;
import it.polimi.ingsw.gc03.model.card.CardGold;
import it.polimi.ingsw.gc03.model.card.CardResource;
import it.polimi.ingsw.gc03.model.enumerations.GameStatus;
import it.polimi.ingsw.gc03.model.enumerations.PlayerAction;
import it.polimi.ingsw.gc03.model.exceptions.*;
import it.polimi.ingsw.gc03.model.side.Side;
import it.polimi.ingsw.gc03.networking.rmi.VirtualView;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.*;


/**
 * This class controls the gameplay flow of a match, from start to finish.
 */
public class GameController implements Runnable, Serializable {

    /**
     * Instance of the game on which the control takes place.
     */
    private Game game;

    /**
     * Attribute to manage timed operations.
     */
    private Timer timer;

    /**
     * Attribute used to generate random choices.
     */
    private final Random random = new Random();

    /**
     * Specific task for operations that must be performed periodically.
     */
    private TimerTask timerTask;


    /**
     * Constructor of the GameController class.
     */
    public GameController() throws RemoteException {
        game = new Game(random.nextInt(2147483647));
        new Thread(this).start();
    }


    /**
     * Method for adding a player to the game.
     * @param playerNickname The player's nickname.
     * @throws CannotJoinGameException This exception is thrown when the game has already started, is paused, or has
     *                                 ended, thus preventing the addition of new players.
     * @throws DeskIsFullException This exception prevents additional players from being added when the game has
     *                             already reached the maximum number of players allowed.
     * @throws PlayerAlreadyJoinedException This exception is thrown to prevent duplicate addition of a player in the
     *                                      same game.
     */
    public void addPlayerToGame(String playerNickname, VirtualView listener) throws CannotJoinGameException, DeskIsFullException, PlayerAlreadyJoinedException, RemoteException {
        // It's possible to add new players only if the game's status is WAITING
        // When the game is in WAITING status, the players.size < game.size, so
        // new players can join.
        if (game.getStatus().equals(GameStatus.WAITING)) {
            try {
                game.addPlayer(playerNickname, listener);
            } catch (DeskIsFullException e) {
                throw new DeskIsFullException();
            } catch (PlayerAlreadyJoinedException e) {
                throw new PlayerAlreadyJoinedException();
            }
            // If enough players joined the game, initialize the game
            if (game.getPlayers().size() == game.getSize() && game.getNumPlayer() != 1) {
                game.setStatus(GameStatus.STARTING);
                // Randomly choose the player who starts the game with the first turn
                game.setCurrPlayer(random.nextInt(game.getSize()));
            }
        } else {
         throw new CannotJoinGameException();
        }
    }


    /**
     * The method handles a specific scenario where the game is stopped and only one player remains online.
     * @return True if the game cannot continue or False if the game can resume.
     */

    private boolean startTimer() {
        if (game.getStatus() == GameStatus.HALTED && game.getOnlinePlayers().size() == 1) {
            timer = new Timer();
            timerTask = new TimerTask() {
                @Override
                public void run() {

                }
            };
            timer.schedule(timerTask, 90 * 1000);
            return true; // Nobody reconnected in time, the player left in the game won.
        }
    return false; // Someone reconnected in time, the game resumes.
    }


    /**
     * Method for stopping the previously started timer and canceling any associated tasks.
     */

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
            timerTask = null;
        }
    }
    /**
     * Method for managing a player's reconnection to the game.
     * @param playerNickname Nickname of the player you want to reconnect.
     */

    public synchronized void reconnectPlayer(String playerNickname) throws RemoteException {
        // Check if there is any game with a player with "playerNickname" as nickname.
        List<Player> result = game.getPlayers().stream().filter(x -> (x.getNickname().equals(playerNickname))).toList();
        if (!result.isEmpty()) {
            // The found player is set to online
            result.getFirst().setOnline(true);
            // If the game was halted, it is set to running
            if (game.getStatus().equals(GameStatus.HALTED)) {
                stopTimer();
                game.setStatus(GameStatus.RUNNING);
            }
        }
        this.getGame().notifyObservers(this.getGame());
    }


    /**
     * The method handles the transition and updating of player actions in the game.
     */
    private synchronized void updateCurrPlayer() throws Exception {
        if(!game.getStatus().equals(GameStatus.LASTROUND)){
            game.getPlayers().get(game.getCurrPlayer()).setAction(PlayerAction.DRAW);
        } else {
            game.getPlayers().get(game.getCurrPlayer()).setAction(PlayerAction.WAIT);
        }
        game.updateCurrPlayer();
        Player currPlayer = game.getPlayers().get(game.getCurrPlayer());
        try{
            currPlayer.checkSkipTurn();
        } catch (Exception e){
            throw new Exception(e);
        }
        if(currPlayer.getSkipTurn()){
            currPlayer.setAction(PlayerAction.ENDED);
            updateCurrPlayer();
        }
        if(!currPlayer.getOnline()){
            currPlayer.setAction(PlayerAction.DISCONNECTED);
            updateCurrPlayer();
        }
        if(currPlayer.getAction().equals(PlayerAction.WAIT)){
            currPlayer.setAction(PlayerAction.PLACE);
        }
    }


    /**
     * Places the starter card on the Codex of the given player at the beginning of the game.
     * This method ensures that the game is in the correct phase and the player is eligible to place the starter card.
     * It also updates the game state and player's action based on the completion of this initial move.
     * @param player The player who is placing the starter card. This player should be currently set to FIRSTMOVES action.
     * @param side The side of the starter card to be placed into the Codex.
     * @throws Exception If the game is not in the STARTING phase or if the player has already placed their starter card.
     */
    public synchronized void placeStarterOnCodex(Player player, Side side) throws Exception {
        // Check if the game is in the STARTING phase
        if (!game.getStatus().equals(GameStatus.STARTING)) {
            throw new Exception("The current game is not in the starting phase.");
        }
        // Check if the player action is FIRSTMOVES
        if (!player.getAction().equals(PlayerAction.FIRSTMOVES)) {
            throw new Exception("The player has already placed his starter card");
        }
        // Proceed with inserting the starting card into the player's Codex
        player.getCodex().insertStarterIntoCodex(side);
        // Check if the objective cards are in the correct number
        if (player.getCardObjective().size() == Player.FINAL_CARD_OBJECTIVE) {
            // The player's action is updated to WAIT
            player.setAction(PlayerAction.WAIT);
            // Check whether all players have completed their initial moves
            List<Player> firstMovers = game.getPlayers().stream()
                    .filter(x -> x.getAction().equals(PlayerAction.FIRSTMOVES))
                    .toList();
            // If there are no more players they must make their initial move
            if (firstMovers.isEmpty()) {
                // The game switches to RUNNING state
                game.setStatus(GameStatus.RUNNING);
                // The current player's action is set to PLACE
                game.getPlayers().get(game.getCurrPlayer()).setAction(PlayerAction.PLACE);
            }
        }
        this.getGame().notifyObservers(this.getGame());
    }

    /**
     * Selects a personal objective card for a player at the start of the game.
     * This method is called during the game's starting phase where players choose their personal objective cards.
     * It ensures that the player is in the correct state to make a selection and updates the game state accordingly.
     * If the player successfully selects a card and all players have finished their initial moves, the game transitions
     * to the RUNNING state.
     * @param player The player who is selecting the objective card. The player must be in the FIRSTMOVES state.
     * @param cardObjective The index of the card in the player's list of objective cards that the player wishes to select.
     * @throws Exception if the game is not in the STARTING phase, or if the player has already made their selection
     *                   or if the player's current action is not set to DRAW.
     */
    public synchronized void selectCardObjective(Player player, int cardObjective) throws Exception {
        if (!game.getStatus().equals(GameStatus.STARTING)) {
            throw new Exception("The current game is not in the starting phase.");
        }
        if (!player.getAction().equals(PlayerAction.FIRSTMOVES)) {
            throw new Exception("The player has already chosen his personal objective");
        }
        // The player can choose his Objective card
        player.selectObjectiveCard(cardObjective);
        if (player.getCodex().getCardStarterInserted()) {
            player.setAction((PlayerAction.WAIT));
            List<Player> firstMovers = game.getPlayers().stream().
                    filter(x->(x.getAction().equals(PlayerAction.FIRSTMOVES)))
                    .toList();

            if(firstMovers.isEmpty()){
                game.setStatus(GameStatus.RUNNING);
                game.getPlayers().get(game.getCurrPlayer()).setAction(PlayerAction.PLACE);
            }
        }
        this.getGame().notifyObservers(this.getGame().getDesk());
    }

    /**
     * Checks and updates the action of the given player based on the current game status.
     * If the game is in the ENDING state, it sets the player's action to ENDED and checks if all players have ended
     * their actions.
     * If all players have finished their actions, it declares the game ended and determines the winner.
     * If the game is not in the ENDING state, it sets the player's action to WAIT.
     * @param player The player whose action is to be updated based on the game's state.
     */
    private synchronized void checkFinalAction(Player player) {
        if (player.getScore() >= Game.STOP_POINT_GAME) {
            if(game.getStatus().equals(GameStatus.RUNNING) || game.getStatus().equals(GameStatus.HALTED)){
                game.setStatus(GameStatus.ENDING);
            }
        }
        if(game.getDesk().getDeckResource().isEmpty()) {
            if (game.getDesk().getDeckGold().isEmpty()){
                if(game.getStatus().equals(GameStatus.RUNNING) || game.getStatus().equals(GameStatus.HALTED)){
                    game.setStatus(GameStatus.ENDING);
                }
            }
        }
        if(game.getStatus().equals(GameStatus.ENDING)){
            if(game.getPlayers().indexOf(player)==game.getSize()-1){
                game.setStatus(GameStatus.LASTROUND);
            }
        }
        if(!player.getAction().equals(PlayerAction.ENDED)){
            player.setAction(PlayerAction.WAIT);
        }
    }


    /**
     * Allows a player to draw a card from a specified deck if the player's action is set to DRAW and the game is in the
     * RUNNING or ENDING state.
     * After drawing a card, the method updates the game and player states accordingly, setting the next action for the
     * current player to PLACE.
     * @param player The player who is drawing the card. This player must have their action set to DRAW.
     * @param deck The deck from which the card is drawn.
     * @throws Exception If the player's current action is not DRAW or if the game state is not suitable for drawing a card.
     */
    public synchronized void drawCardFromDeck(Player player,ArrayList<? extends Card> deck) throws Exception {
        // Check that the player is authorized to draw
        if (player.getAction().equals(PlayerAction.DRAW) && (game.getStatus().equals(GameStatus.RUNNING) || game.getStatus().equals(GameStatus.ENDING))) {
            player.addCardToHand(game.getDesk().drawCardDeck(deck));
            checkFinalAction(player);
            game.getPlayers().get(game.getCurrPlayer()).setAction(PlayerAction.PLACE);
        } else {
            throw new Exception("Player's action is not draw.");
        }
        this.getGame().notifyObservers(this.getGame().getDesk());
    }


    /**
     * Allows a player to draw a specific card from a displayed deck, given the player's action is set to DRAW and the
     * game is either in the RUNNING or ENDING state.
     * This method ensures that the player draws the specified card and updates the game and player states accordingly.
     * The action of the current player is then set to PLACE, preparing for the next phase of the game.
     * @param player The player who is drawing the card.
     * @param deck The displayed deck from which the card is drawn.
     * @param index The index of the card in the displayed deck that the player wishes to draw.
     * @throws Exception If the player's current action is not DRAW or if the game state does not allow drawing a card.
     */
    public synchronized void drawCardDisplayed(Player player,ArrayList<? extends Card> deck, int index) throws Exception {
        // Check that the player is authorized to draw
        if (player.getAction().equals(PlayerAction.DRAW) && (game.getStatus().equals(GameStatus.RUNNING) || game.getStatus().equals(GameStatus.ENDING))) {
            player.addCardToHand(game.getDesk().drawCardDisplayed(deck, index));
            checkFinalAction(player);
            game.getPlayers().get(game.getCurrPlayer()).setAction(PlayerAction.PLACE);
        } else {
            throw new Exception("Player's action is not draw.");
        }
        this.getGame().notifyObservers(this.getGame().getDesk());
    }

    /**
     * Method that retrieves a specific side of a card from a player's hand based on the index provided.
     * @param player The player whose hand is to be queried.
     * @param index The index of the card in the player's hand.
     * @param frontCard If true, retrieves the front side of the card; if false, retrieves the back side.
     * @return The required side of the card or null if the index in the hand is not available.
     */
    private static Side getSide(Player player, int index, boolean frontCard) {
        // Check the index
        if (index < 0 || index >= player.getHand().size())
            return null;
        // Index available
        Card card = player.getHand().get(index);
        Side side = null;
        // Resource card
        if (card instanceof CardResource) {
            if (frontCard)
                side = ((CardResource) card).getFrontResource();
            else
                side = ((CardResource) card).getBackResource();
            // Gold card
        } else if (card instanceof CardGold) {
            if (frontCard)
                side = ((CardGold) card).getFrontGold();
            else
                side = ((CardGold) card).getBackGold();
        }
        return side;
    }

    public void updateSize(int size) throws Exception {
        if(game.getSize() != 1 || size<=1 || size>4){
            throw new Exception("Game size is not valid");
        } else {
            game.setSize(size);
        }
    }

    /**
     * Places a card from the player's hand onto a specified position in their Codex.
     * This action is permitted only when the game's status is either RUNNING or ENDING, and the player's current action
     * must be PLACE.
     * The method validates the game state and player's action, retrieves the specified side of the card, and attempts
     * to place it in the Codex.
     * If the placement is successful, the player's current state is updated and the card is removed from their hand.
     * If the player's score reaches or exceeds a defined ENDING_SCORE after placing the card, the game status is set to
     * ENDING.
     * @param player The player who is placing the card.
     * @param index The index of the card in the player's hand to be placed.
     * @param frontCard A boolean indicating whether to place the front (true) or back (false) side of the card.
     * @param row The row in the Codex where the card is to be placed.
     * @param col The column in the Codex where the card is to be placed.
     * @throws Exception If the game's status is not RUNNING or ENDING, if the player's action is not PLACE, or if there
     *                   is an error in placing the card in the Codex.
     */
    public synchronized void placeCardOnCodex(Player player, int index, boolean frontCard, int row, int col) throws Exception {
        if (game.getStatus().equals(GameStatus.RUNNING) || game.getStatus().equals(GameStatus.ENDING) || game.getStatus().equals(GameStatus.LASTROUND)) {
            if (player.getAction().equals(PlayerAction.PLACE)) {
                Side side = getSide(player, index, frontCard);
                if (player.getCodex().insertIntoCodex(side, row, col)) {
                    player.removeCardFromHand(index);
                    updateCurrPlayer();
                    if (game.getStatus().equals(GameStatus.LASTROUND)) {
                        player.setAction(PlayerAction.ENDED);
                        boolean allPlayersEnded = game.getPlayers().stream()
                                .filter(x->(!x.getAction().equals(PlayerAction.ENDED)))
                                .toList()
                                .isEmpty();
                        if (allPlayersEnded) {
                            game.getWinner();
                            game.setStatus(GameStatus.ENDED);
                        }
                    }
                }
            } else {
                throw new Exception("The player is not the current player or the game is not running or he's current action is not place");
            }
        } else {
            throw new Exception("The current GameStatus is not either RUNNING or ENDING");
        }
        this.getGame().notifyObservers(this.getGame());
    }

    // For testing purposes, no real case use
    public void infiniteTask(String p) throws Exception {
        int i = 0;
        while(true){
            Thread.sleep(1000);
            System.out.println("seq: "+i+" gameID: "+getGame().getIdGame()+" client: "+p);
            i+=1;
        }
    }
    /**
     * Retrieves the current game instance associated with this object.
     * @return The current instance of Game associated with this class.
     */
    public Game getGame(){
        return game;
    }

    /**
     * The main game loop that handles different game statuses and manages online player interactions.
     * This method runs continuously until the thread is interrupted, checking game status and adjusting the game flow
     * accordingly.
     * @throws RuntimeException if an error occurs during the game deletion process, encapsulating the original
     *                          NoSuchGameException.
     */
    @Override
    public void run() {
        while (!Thread.interrupted()){
            if (game.getStatus().equals(GameStatus.STARTING) || game.getStatus().equals(GameStatus.RUNNING) ||
                    game.getStatus().equals(GameStatus.ENDING ) || game.getStatus().equals(GameStatus.HALTED)) {
                List<Player> onlinePlayers = game.getOnlinePlayers();
                // If there are no players online, delete the game
                if (onlinePlayers.isEmpty()) {
                    try {
                        MainController.getInstance().deleteGame(getGame().getIdGame());
                    } catch (NoSuchGameException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    if (onlinePlayers.size() == 1) {
                        // If there is only one player and the status isn't WAITING
                        // then a timer start and if nobody reconnect before the timer's end
                        // the only player left is the winner
                        game.setStatus(GameStatus.HALTED);
                    }
                }
            }
            if (game.getStatus().equals(GameStatus.ENDED)) {
                try {
                    // The game has ended, I should update the view and announce the winner.
                    // then I'll delete the game and close this thread.
                    MainController.getInstance().deleteGame(game.getIdGame());
                    return;
                } catch (NoSuchGameException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }


}
