package it.polimi.ingsw.gc03.controller;

import it.polimi.ingsw.gc03.model.*;
import it.polimi.ingsw.gc03.model.Card.Card;
import it.polimi.ingsw.gc03.model.Exceptions.NoMoreCardException;

import java.util.ArrayList;
import java.util.List;

public class GameController {
    private Game game;
    public void handleGame(){
        ArrayList<Player> players = new ArrayList<>();
        Desk gameDesk = new Desk(players);
        startGame();


    }

    private void startGame(){
        // Check if enough players are ready to play
        if(game.getDesk().getPlayers().size() == game.getSize()){
            game.setStatus(1);
        }
        // Give every player a starting card, a personal objective, and 3 cards (2 CardResource and 1 CardResource)
        game.getDesk().getPlayers().forEach(player -> {
            try {
                player.setCardStarter(game.getDesk().drawCardStarter());
                player.setCardObjective(game.getDesk().drawCardObjective());
                List<Card> currHand = player.getHand();
                currHand.add(game.getDesk().drawCardResource());
                currHand.add(game.getDesk().drawCardResource());
                currHand.add(game.getDesk().drawCardGold());
                player.setHand(currHand);
            } catch (NoMoreCardException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
