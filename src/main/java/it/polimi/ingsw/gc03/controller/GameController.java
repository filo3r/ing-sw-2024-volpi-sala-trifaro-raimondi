package it.polimi.ingsw.gc03.controller;

import it.polimi.ingsw.gc03.model.*;
import it.polimi.ingsw.gc03.model.enumerations.GameStatus;
import it.polimi.ingsw.gc03.model.exceptions.DeskIsFullException;
import it.polimi.ingsw.gc03.model.exceptions.NoMoreCardException;
import it.polimi.ingsw.gc03.model.exceptions.PlayerAlreadyJoinedException;

public class GameController {
    private Game game;

    public GameController(Game game, Player player) {
        game = new Game();
    }

    private void initGame(){
        // Check if enough players are ready to play
        if(game.getDesk().getPlayers().size() == game.getSize() && game.getStatus().equals(GameStatus.STARTING)){
            game.setStatus(GameStatus.RUNNING);
        }
        // Give every player a starting card, a personal objective, and 3 cards (2 CardResource and 1 CardGold)
        game.getDesk().getPlayers().forEach(player -> {
            try {
                player.setCardStarter(game.getDesk().drawCardStarter());
                player.setCardObjective(game.getDesk().drawCardObjective());
                player.addCardToHand(game.getDesk().drawCardResource());
                player.addCardToHand(game.getDesk().drawCardResource());
                player.addCardToHand(game.getDesk().drawCardGold());
            } catch (NoMoreCardException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void addPlayerToGame(Player player){
        try {
            game.getDesk().addPlayer(player);
        } catch (PlayerAlreadyJoinedException e) {
            throw new RuntimeException(e);
        } catch (DeskIsFullException e) {
            throw new RuntimeException(e);
        }
    }
}
