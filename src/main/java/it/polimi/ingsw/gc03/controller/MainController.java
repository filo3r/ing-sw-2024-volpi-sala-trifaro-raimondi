package it.polimi.ingsw.gc03.controller;

import it.polimi.ingsw.gc03.model.Player;
import it.polimi.ingsw.gc03.model.enumerations.GameStatus;
import it.polimi.ingsw.gc03.model.exceptions.CannotJoinGameException;
import it.polimi.ingsw.gc03.model.exceptions.DeskIsFullException;
import it.polimi.ingsw.gc03.model.exceptions.PlayerAlreadyJoinedException;

import java.util.ArrayList;
import java.util.List;

public class MainController {
    /**
     * instance of the MainControlle, needed for the singleton design pattern
     */
    private static MainController instance = null;

    private List<GameController> gameControllers;


    /**
     * Singleton's instance
     */
    private MainController(){
        gameControllers = new ArrayList<>();
    }

    /**
     * Singleton's getInstance method
     * @return MainController instance.
     */
    public synchronized static MainController getInstance(){
        if(instance == null){
            instance = new MainController();
        }
        return instance;
    }

    public synchronized GameController createGame(String firstPlayerNickname){
        Player firstPlayer = new Player(firstPlayerNickname);
        GameController controller = new GameController(firstPlayer);
        gameControllers.add(controller);
        try {
            controller.addPlayerToGame(firstPlayer);
        } catch (CannotJoinGameException e) {
            throw new RuntimeException(e);
        }
    return controller;
    }

    // Join an available game i
    public synchronized void joinGame(String playerNickname){
        //First of all check if there is any game where is possible to join (GCs stands for gameControllers
        List<GameController> GCs =  gameControllers.stream().filter(x -> (x.getGame().getStatus().equals(GameStatus.WAITING))).toList();
        if(!GCs.isEmpty()){
            //If there are some (at least 1) available games to join, join the last.
            try {
                gameControllers.removeLast().getGame().addPlayer(new Player(playerNickname));
            } catch (PlayerAlreadyJoinedException e) {
                throw new RuntimeException(e);
            } catch (DeskIsFullException e) {
                throw new RuntimeException(e);
            }
        } else {
            // If there are no available games to join, create a new game.
            createGame(playerNickname);
        }
    }
}
