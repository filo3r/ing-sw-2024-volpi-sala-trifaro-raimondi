package it.polimi.ingsw.gc03.controller;

import it.polimi.ingsw.gc03.model.*;
import it.polimi.ingsw.gc03.model.card.Card;
import it.polimi.ingsw.gc03.model.card.CardResource;
import it.polimi.ingsw.gc03.model.card.CardStarter;
import it.polimi.ingsw.gc03.model.card.card.objective.CardObjective;
import it.polimi.ingsw.gc03.model.enumerations.GameStatus;
import it.polimi.ingsw.gc03.model.enumerations.PlayerAction;
import it.polimi.ingsw.gc03.model.exceptions.*;
import it.polimi.ingsw.gc03.model.side.Side;

import java.util.List;
import java.util.TimerTask;
import java.util.Timer;


public class GameController implements Runnable {
    private Game game;

    private Timer timer;

    private TimerTask timerTask;


    public GameController() {
        game = new Game();
        new Thread(this).start();
    }

    private void initGame(){
        // Check if enough players are ready to play
        if(game.getPlayers().size() == game.getSize() && game.getStatus().equals(GameStatus.STARTING)){
            game.setStatus(GameStatus.RUNNING);
        }
        // Give every player a starting card, a personal objective, and 3 cards (2 CardResource and 1 CardGold)
        game.getPlayers().forEach(player -> {
            player.setCardStarter((CardStarter) game.getDesk().drawCardDeck(game.getDesk().getDeckStarter()));
            player.setCardObjective((CardObjective) game.getDesk().drawCardDeck(game.getDesk().getDeckObjective()));
            player.addCardToHand(game.getDesk().drawCardDeck(game.getDesk().getDeckResource()));
            player.addCardToHand(game.getDesk().drawCardDeck(game.getDesk().getDeckResource()));
            player.addCardToHand(game.getDesk().drawCardDeck(game.getDesk().getDeckGold()));
        });
    }

    public void addPlayerToGame(Player player) throws CannotJoinGameException {
        // It's possible to add new players only if the game's status is WAITING
        // When the game is in WAITING status, the players.size < game.size, so
        // new players can join.
        if(game.getStatus().equals(GameStatus.WAITING)){
            try {
                game.addPlayer(player);
                // If enough players joined the game, initialize the game
                if(game.getPlayers().size() == game.getSize()){
                    game.setStatus(GameStatus.STARTING);
                    initGame();
                }
            } catch (PlayerAlreadyJoinedException e) {
                throw new RuntimeException(e);
            } catch (DeskIsFullException e) {
                throw new RuntimeException(e);
            }
        } else {
         throw new CannotJoinGameException();
        }
    }

    public boolean startTimer() {
        if (game.getStatus() == GameStatus.ALTED && game.getOnlinePlayers().size() == 1) {
            timer = new Timer();
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    Player winner = game.getOnlinePlayers().getFirst();
                    stopTimer();
                }
            };
            timer.schedule(timerTask, 60*1000);
            return true; // Nobody reconnected in time, the player left in the game won.
        }
    return false; // Someone reconnected in time, the game resumes.
    }

    public void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
            timerTask = null;
        }
    }

    public synchronized void reconnectPlayer(Player player){
        stopTimer();
        game.getPlayers().stream().filter(x -> (x.equals(player))).toList().getFirst().setOnline(true);
        if(game.getStatus().equals(GameStatus.ALTED)){
            game.setStatus(GameStatus.RUNNING);
        }
    }

    /**
     *
     * @param player  is the player that will place the card
     * @param side is the side of the card that will be placed.
     * @param col is the column in the codex where it will be placed.
     * @param row is the row in the codex where it will be placed.
     */
    public synchronized void placeCardOnCodex(Player player, Side side, int col, int row) throws Exception {
        // Check:
        // - if the player who is trying to place a card on the codex is the current player
        // - if this game's status is RUNNING
        int currPlayerIndex = game.getPlayers().indexOf(player);
        if(game.getCurrPlayer() == currPlayerIndex && game.getStatus().equals(GameStatus.RUNNING) && (player.getAction().equals(PlayerAction.PLACESTARTER) || player.getAction().equals(PlayerAction.PLACE))){
            // Check whether the player has or hasn't placed the starter card
            // and if the card that is trying to place is the starter.
            if(player.getAction().equals(PlayerAction.PLACESTARTER)){
                throw new Exception("Player must place the starter card first.");
            } else {
                if(player.getCodex().insertIntoCodex(side, row, col)){
                    // If everything was ok when placing the card, update the currPlayer index and update player action.
                    updateCurrPlayer();
                    player.setAction(PlayerAction.WAIT);
                } else {
                    throw new Exception("Error in placing a card.");
                }
            }
        }
    }


    public synchronized void updateCurrPlayer(){
        int curr = game.getCurrPlayer();
        if(curr==game.getPlayers().size()-1){
            game.setCurrPlayer(0);
        } else {
            game.setCurrPlayer(curr+1);
        }
    }

    public synchronized void placeStarterOnCodex(Player player, Side side){
        int currPlayerIndex = game.getPlayers().indexOf(player);
        if(game.getCurrPlayer() == currPlayerIndex && game.getStatus().equals(GameStatus.RUNNING) && player.getAction().equals(PlayerAction.PLACESTARTER)){
            player.getCodex().insertStarterIntoCodex(side);
            player.setAction(PlayerAction.WAIT);
            updateCurrPlayer();
        }
    }

    public Game getGame(){
        return game;
    }
    @Override
    public void run() {
        while (!Thread.interrupted()){
            if(game.getStatus().equals(GameStatus.RUNNING) || game.getStatus().equals(GameStatus.ENDING ) || game.getStatus().equals(GameStatus.ALTED)){
                List<Player> onlinePlayers = game.getOnlinePlayers();
                // If there are no players online, delete the game
                if(onlinePlayers.isEmpty()){
                    try {
                        MainController.getInstance().deleteGame(getGame().getIdGame());
                    } catch (NoSuchGameException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    if(onlinePlayers.size() == 1){
                        // If there is only one player and the status isn't WAITING
                        // then a timer start and if nobody reconnect before the timer's end
                        // the only player left is the winner
                        game.setStatus(GameStatus.ALTED);
                    }
                }
            }
        }
    }
}
