package it.polimi.ingsw.gc03.controller;

import it.polimi.ingsw.gc03.model.*;
import it.polimi.ingsw.gc03.model.card.CardStarter;
import it.polimi.ingsw.gc03.model.card.card.objective.CardObjective;
import it.polimi.ingsw.gc03.model.enumerations.GameStatus;
import it.polimi.ingsw.gc03.model.exceptions.CannotJoinGameException;
import it.polimi.ingsw.gc03.model.exceptions.DeskIsFullException;
import it.polimi.ingsw.gc03.model.exceptions.PlayerAlreadyJoinedException;

import java.util.List;


public class GameController implements Runnable {
    private Game game;

    public GameController(Player player) {
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

    public Game getGame(){
        return game;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()){
            if(game.getStatus().equals(GameStatus.RUNNING) || game.getStatus().equals(GameStatus.ENDING)){
                List<Player> onlinePlayers = game.getOnlinePlayers();
                if(onlinePlayers.isEmpty()){
                    // DELETE THE GAME WITH THE SINGLETON
                } else {
                    if(onlinePlayers.size() == 1){
                        game.setStatus(GameStatus.ALTED);
                    }
                }
            }
        }
    }
}
