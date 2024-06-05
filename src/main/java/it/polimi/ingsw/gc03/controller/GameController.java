package it.polimi.ingsw.gc03.controller;

import it.polimi.ingsw.gc03.listeners.GameListener;
import it.polimi.ingsw.gc03.model.*;
import it.polimi.ingsw.gc03.model.card.Card;
import it.polimi.ingsw.gc03.model.card.CardGold;
import it.polimi.ingsw.gc03.model.card.CardResource;
import it.polimi.ingsw.gc03.model.enumerations.GameStatus;
import it.polimi.ingsw.gc03.model.enumerations.PlayerAction;
import it.polimi.ingsw.gc03.model.exceptions.*;
import it.polimi.ingsw.gc03.model.side.Side;
import it.polimi.ingsw.gc03.networking.rmi.GameControllerInterface;

import java.awt.*;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * This class controls the gameplay flow of a match, from start to finish.
 */
public class GameController implements GameControllerInterface, Runnable, Serializable {

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
     * Map to keep track of player ping timestamps.
     */
    private final Map<Player, Long> playerPingTimestamps = new ConcurrentHashMap<>();

    /**
     * Executor for handling periodic ping checks.
     */
    private final ScheduledExecutorService pingExecutor = Executors.newSingleThreadScheduledExecutor();

    /**
     * Timeout period for player pings.
     */
    private static final long TIMEOUT_MILLIS = 3000; // Timeout period

    /**
     * Last game's status before going to "halted" status
     */
    private GameStatus lastStatus;

    /**
     * Constructor of the GameController class.
     * @throws RemoteException If there is an issue with remote communication.
     */
    public GameController() throws RemoteException {
        game = new Game(random.nextInt(2147483647));
        startPingThread();
        new Thread(this).start();
    }


    /**
     * Starts a thread to periodically check player pings.
     */
    private void startPingThread() {
        pingExecutor.scheduleAtFixedRate(this::checkPings, 0, 2, TimeUnit.SECONDS);
    }


    /**
     * Checks player ping timestamps and handles timeouts.
     */
    private void checkPings() {
        long currentTime = System.currentTimeMillis();
        for (Player player : game.getPlayers()) {
            Long lastPingTime = playerPingTimestamps.get(player);
            if (lastPingTime != null && currentTime - lastPingTime > TIMEOUT_MILLIS) {
                handlePlayerTimeout(player);
            }
        }
    }


    /**
     * Handles player timeout by setting the player to offline and removing their listener.
     * @param player The player who timed out.
     */
    private void handlePlayerTimeout(Player player) {
        if(player.getOnline()){
            player.setOnline(this.getGame(), false, this, null);
        }
    }


    /**
     * Updates the ping timestamp for a player.
     * @param player The player who sent the ping.
     */
    public void ping(String player) throws RemoteException {
        List<Player> playerWhoPinged = this.getGame().getPlayers().stream().filter(p->p.getNickname().equals(player)).toList();
        if(!playerWhoPinged.isEmpty()){
            playerPingTimestamps.put(playerWhoPinged.getFirst(), System.currentTimeMillis());
        }
    }


    /**
     * Method for adding a player to the game.
     * @param playerNickname The player's nickname.
     * @param listener The game listener for the player.
     * @throws CannotJoinGameException This exception is thrown when the game has already started, is paused, or has
     *                                 ended, thus preventing the addition of new players.
     * @throws DeskIsFullException This exception prevents additional players from being added when the game has
     *                             already reached the maximum number of players allowed.
     * @throws PlayerAlreadyJoinedException This exception is thrown to prevent duplicate addition of a player in the
     *                                      same game.
     * @throws RemoteException If there is an issue with remote communication.
     */
    public void addPlayerToGame(String playerNickname, GameListener listener) throws CannotJoinGameException, DeskIsFullException, PlayerAlreadyJoinedException, RemoteException {
        // It's possible to add new players only if the game's status is WAITING
        // When the game is in WAITING status, the players.size < game.size, so
        // new players can join.
        if (game.getStatus().equals(GameStatus.WAITING)) {
            game.addPlayer(playerNickname, listener);

            // If enough players joined the game, initialize the game
            if (game.getPlayers().size() == game.getSize() && game.getNumPlayer() != 1) {
                game.setStatus(GameStatus.STARTING);
                lastStatus = GameStatus.STARTING;
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
        if (game.getStatus() == GameStatus.HALTED) {
            if (timer == null && timerTask == null) { // Check if a timer is already running
                timer = new Timer();
                timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        game.setStatus(GameStatus.ENDED);
                    }
                };
                timer.schedule(timerTask, 60*1000); // 60 seconds
            }
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
        }
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
    }


    /**
     * Method for managing a player's reconnection to the game.
     * @param playerNickname Nickname of the player you want to reconnect.
     * @param gameListener The game listener for the player.
     * @throws Exception If the player cannot be reconnected or the game is not in a suitable state.
     */
    public synchronized void reconnectPlayer(String playerNickname, GameListener gameListener) throws Exception {
        // Check if there is any game with a player with "playerNickname" as nickname.
        List<Player> result = game.getPlayers().stream().filter(x -> (x.getNickname().equals(playerNickname))).toList();
        if (!result.isEmpty()) {
            // If the game was halted, it is set to running
            if (game.getStatus().equals(GameStatus.HALTED)) {
                stopTimer();
                game.setStatus(lastStatus);
                playerPingTimestamps.put(game.getPlayers().stream().filter(p->p.getNickname().equals(playerNickname)).toList().getFirst(), System.currentTimeMillis());
            }
            // The found player is set to online
            result.getFirst().setOnline(this.getGame(), true, this, gameListener);
        } else {
            throw new Exception("No previous game to reconnect with that username");
        }
    }

    /**
     * The method handle the player leaving the game
     * @param playerNickname The nickname of the player who left.
     */
    public synchronized void leaveGame(String playerNickname) throws RemoteException {
        // check if the player is actually in the game
        if(!game.getPlayers().stream().filter(p->p.getNickname().equals(playerNickname)).toList().isEmpty()){
            GameListener gameListener = game.getPlayers().stream().filter(p->p.getNickname().equals(playerNickname)).toList().getFirst().getSelfListener();
            gameListener.playerLeft(new GameImmutable(this.game), playerNickname);
            game.removeListener(gameListener);
            game.removePlayer(playerNickname);
        }
    }


    /**
     * The method handles the transition and updating of player actions in the game.
     * @throws Exception If there is an error during the transition.
     */
    private synchronized void updateCurrPlayer() throws Exception {
        if(!game.getStatus().equals(GameStatus.LASTROUND)){
            if(game.getPlayers().get(game.getCurrPlayer()).getAction().equals(PlayerAction.PLACE)){
                game.getPlayers().get(game.getCurrPlayer()).setAction(PlayerAction.DRAW, this.game);
            } else if(game.getPlayers().get(game.getCurrPlayer()).getAction().equals(PlayerAction.DRAW)){
                game.getPlayers().get(game.getCurrPlayer()).setAction(PlayerAction.WAIT, this.game);
                game.updateCurrPlayer();
                Player currPlayer = game.getPlayers().get(game.getCurrPlayer());
                try{
                    currPlayer.checkSkipTurn();
                } catch (Exception e){
                    throw new Exception(e);
                }
                if(currPlayer.getSkipTurn()){
                    currPlayer.setAction(PlayerAction.ENDED, this.game);
                    updateCurrPlayer();
                } else if(!currPlayer.getOnline()) {
                    currPlayer.setAction(PlayerAction.DISCONNECTED, this.game);
                    updateCurrPlayer();
                } else {
                    currPlayer.setAction(PlayerAction.PLACE, this.game);
                }
            }
        } else {
            game.getPlayers().get(game.getCurrPlayer()).setAction(PlayerAction.WAIT, this.game);
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
        Player playerFromController = this.game.getPlayers().stream().filter(p->p.getNickname().equals(player.getNickname())).toList().getFirst();
        playerFromController.getCodex().insertStarterIntoCodex(side, this.game, player.getNickname());
        // Check if the objective cards are in the correct number
        if (player.getCardObjective().size() == Player.FINAL_CARD_OBJECTIVE) {
            // The player's action is updated to WAIT
            player.setAction(PlayerAction.WAIT, this.game);
            // Check whether all players have completed their initial moves
            List<Player> firstMovers = game.getPlayers().stream()
                    .filter(x -> x.getAction().equals(PlayerAction.FIRSTMOVES))
                    .toList();
            // If there are no more players they must make their initial move
            if (firstMovers.isEmpty()) {
                // The game switches to RUNNING state
                game.setStatus(GameStatus.RUNNING);
                lastStatus = GameStatus.RUNNING;
                // The current player's action is set to PLACE
                game.getPlayers().get(game.getCurrPlayer()).setAction(PlayerAction.PLACE, this.game);
            }
        }
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
        Player playerFromController = this.game.getPlayers().stream().filter(p->p.getNickname().equals(player.getNickname())).toList().getFirst();
        playerFromController.selectObjectiveCard(cardObjective, this.game);
        if (playerFromController.getCodex().getCardStarterInserted()) {
            playerFromController.setAction(PlayerAction.WAIT, this.game);
            List<Player> firstMovers = game.getPlayers().stream().
                    filter(x->(x.getAction().equals(PlayerAction.FIRSTMOVES)))
                    .toList();

            if(firstMovers.isEmpty()){
                game.setStatus(GameStatus.RUNNING);
                lastStatus = GameStatus.RUNNING;
                game.getPlayers().get(game.getCurrPlayer()).setAction(PlayerAction.PLACE, this.game);
            }
        }
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
                lastStatus = GameStatus.ENDING;
            }
        }
        if(game.getDesk().getDeckResource().isEmpty()) {
            if (game.getDesk().getDeckGold().isEmpty()){
                if(game.getStatus().equals(GameStatus.RUNNING) || game.getStatus().equals(GameStatus.HALTED)){
                    game.setStatus(GameStatus.ENDING);
                    lastStatus = GameStatus.ENDING;
                }
            }
        }
        if(game.getStatus().equals(GameStatus.ENDING)){
            if(game.getPlayers().indexOf(player)==game.getSize()-1){
                game.setStatus(GameStatus.LASTROUND);
                lastStatus = GameStatus.LASTROUND;
            }
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
    public synchronized void drawCardFromDeck(Player player, ArrayList<? extends Card> deck) throws Exception {
        // Check that the player is authorized to draw
        Player playerFromController = this.game.getPlayers().stream().filter(p->p.getNickname().equals(player.getNickname())).toList().getFirst();

        if (playerFromController.getAction().equals(PlayerAction.DRAW) && (game.getStatus().equals(GameStatus.RUNNING) || game.getStatus().equals(GameStatus.ENDING))) {
            playerFromController.addCardToHand(game.getDesk().drawCardDeck(deck));
            checkFinalAction(playerFromController);
            updateCurrPlayer();
        } else {
            throw new Exception("Player's action is not draw.");
        }
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
        Player playerFromController = this.game.getPlayers().stream().filter(p->p.getNickname().equals(player.getNickname())).toList().getFirst();

        if (playerFromController.getAction().equals(PlayerAction.DRAW) && (game.getStatus().equals(GameStatus.RUNNING) || game.getStatus().equals(GameStatus.ENDING))) {
            playerFromController.addCardToHand(game.getDesk().drawCardDisplayed(deck, index));
            checkFinalAction(playerFromController);
            updateCurrPlayer();
        } else {
            throw new Exception("Player's action is not draw.");
        }
    }


    /**
     * Allows a player to send a message to the chat.
     * @param chatMessage The message for the chat.
     * @throws RemoteException If there is an issue with remote communication.
     */
    @Override
    public void sendChatMessage(ChatMessage chatMessage) throws RemoteException {
        this.game.addMessage(chatMessage);
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


    /**
     * Updates the size of the game.
     * @param size The new size of the game.
     * @throws Exception If the game size is not valid.
     */
    public void updateGameSize(int size) throws Exception {
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
        Player playerFromController = this.game.getPlayers().stream().filter(p->p.getNickname().equals(player.getNickname())).toList().getFirst();
        if (game.getStatus().equals(GameStatus.RUNNING) || game.getStatus().equals(GameStatus.ENDING) || game.getStatus().equals(GameStatus.LASTROUND)) {
            if (playerFromController.getAction().equals(PlayerAction.PLACE)) {
                Side side = getSide(playerFromController, index, frontCard);
                if (playerFromController.getCodex().insertIntoCodex(this.game, side, row, col)) {
                    playerFromController.removeCardFromHand(index);
                    updateCurrPlayer();
                    if (game.getStatus().equals(GameStatus.LASTROUND)) {
                        playerFromController.setAction(PlayerAction.ENDED, this.game);
                        boolean allPlayersEnded = game.getPlayers().stream()
                                .filter(x->(!x.getAction().equals(PlayerAction.ENDED)))
                                .toList()
                                .isEmpty();
                        if (allPlayersEnded) {
                            game.getWinner();
                            game.setStatus(GameStatus.ENDED);
                            lastStatus = GameStatus.ENDED;
                        }
                    }
                }
            } else {
                throw new Exception("The player is not the current player or the game is not running or he's current action is not place");
            }
        } else {
            throw new Exception("The current GameStatus is not either RUNNING or ENDING");
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
                    game.getStatus().equals(GameStatus.ENDING ) || game.getStatus().equals(GameStatus.HALTED) || game.getStatus().equals(GameStatus.LASTROUND)) {
                List<Player> onlinePlayers = game.getOnlinePlayers();
                // If there are no players online, delete the game
                if (onlinePlayers.isEmpty()) {
                    game.setStatus(GameStatus.ENDED);
                } else {
                    if (onlinePlayers.size() == 1 && game.getPlayers().size()>1) {
                        // If there is only one player and the status isn't WAITING
                        // then a timer start and if nobody reconnect before the timer's end
                        // the only player left is the winner
                        game.setStatus(GameStatus.HALTED);
                        startTimer();
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
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }


}
