package it.polimi.ingsw.gc03.controller;

import it.polimi.ingsw.gc03.model.*;
import it.polimi.ingsw.gc03.model.card.Card;
import it.polimi.ingsw.gc03.model.card.CardGold;
import it.polimi.ingsw.gc03.model.card.CardResource;
import it.polimi.ingsw.gc03.model.enumerations.GameStatus;
import it.polimi.ingsw.gc03.model.enumerations.PlayerAction;
import it.polimi.ingsw.gc03.model.exceptions.*;
import it.polimi.ingsw.gc03.model.side.Side;

import java.util.*;


public class GameController implements Runnable {
    private Game game;

    private Timer timer;

    private final Random random = new Random();
    private TimerTask timerTask;


    public GameController() {
        game = new Game(random.nextInt(1000000000));
        new Thread(this).start();
    }

    public void addPlayerToGame(String playerNickname) throws CannotJoinGameException, DeskIsFullException, PlayerAlreadyJoinedException {
        // It's possible to add new players only if the game's status is WAITING
        // When the game is in WAITING status, the players.size < game.size, so
        // new players can join.
        if(game.getStatus().equals(GameStatus.WAITING)){
            try {
                if(!game.addPlayer(playerNickname)){
                    throw new CannotJoinGameException();
                }
            } catch (DeskIsFullException e) {
                throw new DeskIsFullException();
            } catch (PlayerAlreadyJoinedException e) {
                throw new PlayerAlreadyJoinedException();
            }
            ;
            // If enough players joined the game, initialize the game
            if(game.getPlayers().size() == game.getSize() && game.getNumPlayer()!=1){
                game.setStatus(GameStatus.STARTING);
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

    public synchronized void reconnectPlayer(String playerNickname){
        // Check if there is any game with a player with "playerNickname" as nickname.
        List<Player> result = game.getPlayers().stream().filter(x -> (x.getNickname().equals(playerNickname))).toList();
        if(!result.isEmpty()){
            result.getFirst().setOnline(true);
            if(game.getStatus().equals(GameStatus.ALTED)){
                stopTimer();
                game.setStatus(GameStatus.RUNNING);
            }
        }
    }

    /**
     *
     * @param player  is the player that will place the card
     * @param index is the position of the card that will be placed in the hand.
     * @param col is the column in the codex where it will be placed.
     * @param row is the row in the codex where it will be placed.
     */
    public synchronized void placeCardOnCodex(Player player, int index, boolean frontOrBack, int row, int col) throws Exception {
        // Check:
        // - if this game's status is RUNNING
        // - if the player's action is PLACE
        if(game.getStatus().equals(GameStatus.RUNNING) && player.getAction().equals(PlayerAction.PLACE)){
            Side side = getSide(player, index, frontOrBack);
            if(player.getCodex().insertIntoCodex(side, row, col)){
                updateCurrPlayer();
                player.removeCardFromHand(index);
            } else {
                throw new Exception("Error in placing a card.");
            }
        } else {
            throw new Exception("The player is not the current player or the game is not running or he's current action is not place");
        }
    }

    private static Side getSide(Player player, int index, boolean frontOrBack) {
        Card card = player.getHand().get(index);
        Side side = null;
        if (card instanceof CardResource) {
            if(frontOrBack){
                side = ((CardResource) card).getFrontResource();
            } else {
                side = ((CardResource) card).getBackResource();
            }
        } else if (card instanceof CardGold) {
            if(frontOrBack){
                side = ((CardGold) card).getFrontGold();
            } else {
                side = ((CardGold) card).getBackGold();
            }
        }
        return side;
    }

    public synchronized void updateCurrPlayer(){
        int curr = game.getCurrPlayer();
        game.getPlayers().get(curr).setAction(PlayerAction.DRAW);
        if(curr==game.getPlayers().size()-1){
            game.setCurrPlayer(0);
        } else {
            game.setCurrPlayer(curr+1);
        }
    }

    public synchronized void placeStarterOnCodex(Player player, Side side) throws Exception {
        if (!game.getStatus().equals(GameStatus.STARTING)) {
            throw new Exception("The current game is not in the starting phase.");
        }

        if (!player.getAction().equals(PlayerAction.FIRSTMOVES)) {
            throw new Exception("The player has already placed his starter card");
        }

        player.getCodex().insertStarterIntoCodex(side);
        if (player.getCardObjective().size() == 1) {
            player.setAction(PlayerAction.WAIT);
            List<Player> firstMovers = game.getPlayers().stream()
                    .filter(x -> x.getAction().equals(PlayerAction.FIRSTMOVES))
                    .toList();

            if (firstMovers.isEmpty()) {
                game.setStatus(GameStatus.RUNNING);
                game.getPlayers().get(game.getCurrPlayer()).setAction(PlayerAction.PLACE);
            }
        }
    }

    public synchronized void drawCardFromDeck(Player player,ArrayList<? extends Card> deck) throws Exception {
        if(player.getAction().equals(PlayerAction.DRAW)){
            player.addCardToHand(game.getDesk().drawCardDeck(deck));
            player.setAction(PlayerAction.WAIT);
            game.getPlayers().stream().toList().get(game.getCurrPlayer()).setAction(PlayerAction.PLACE);
        } else {
            throw new Exception("Player's action is not draw.");
        }
    }

    public synchronized void drawCardDisplayed(Player player,ArrayList<? extends Card> deck, int index) throws Exception {
        if(player.getAction().equals(PlayerAction.DRAW)){
            player.addCardToHand(game.getDesk().drawCardDisplayed(deck, index));
            player.setAction(PlayerAction.WAIT);
            game.getPlayers().stream().toList().get(game.getCurrPlayer()).setAction(PlayerAction.PLACE);
        } else {
            throw new Exception("Player's action is not draw.");
        }
    }

    public synchronized void selectCardObjective(Player player, int cardObjective) throws Exception {
        if(!game.getStatus().equals(GameStatus.STARTING)){
            throw new Exception("The current game is not in the starting phase.");
        }
        if(!player.getAction().equals(PlayerAction.FIRSTMOVES)){
            throw new Exception("The player has already chosen his personal objective");
        }

        player.selectObjectiveCard(cardObjective);
        if(player.getCodex().getCardStarterInserted()){
            player.setAction((PlayerAction.WAIT));
            List<Player> firstMovers = game.getPlayers().stream().
                    filter(x->(x.getAction().equals(PlayerAction.FIRSTMOVES)))
                    .toList();

            if(firstMovers.isEmpty()){
                game.setStatus(GameStatus.RUNNING);
                game.getPlayers().get(game.getCurrPlayer()).setAction(PlayerAction.PLACE);
            }
        }
    }

    public Game getGame(){
        return game;
    }
    @Override
    public void run() {
        while (!Thread.interrupted()){
            if(game.getStatus().equals(GameStatus.STARTING) || game.getStatus().equals(GameStatus.RUNNING) || game.getStatus().equals(GameStatus.ENDING ) || game.getStatus().equals(GameStatus.ALTED)){
                List<Player> onlinePlayers = game.getOnlinePlayers();
                // If there are no players online, delete the game
                if(onlinePlayers.isEmpty()){
                    try {
                        MainController.getInstance().deleteGame(getGame().getIdGame());
                    } catch (NoSuchGameException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    if(onlinePlayers.size() == 1 && onlinePlayers.size()!= getGame().getNumPlayer()){
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
