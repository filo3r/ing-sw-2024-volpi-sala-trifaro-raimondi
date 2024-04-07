package it.polimi.ingsw.gc03.controller;

import it.polimi.ingsw.gc03.model.enumerations.GameStatus;
import it.polimi.ingsw.gc03.model.exceptions.CannotJoinGameException;
import it.polimi.ingsw.gc03.model.exceptions.NoSuchGameException;

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

    public synchronized void createGame(String firstPlayerNickname){
        GameController controller = new GameController();
        gameControllers.add(controller);
        try {
            controller.addPlayerToGame(firstPlayerNickname);
        } catch (CannotJoinGameException e) {
            throw new RuntimeException(e);
        }
    }

    // Join an available game
    public synchronized void joinGame(String playerNickname){
        //First of all check if there is any game where is possible to join (GCs stands for gameControllers
        List<GameController> GCs =  gameControllers.stream().filter(x -> (x.getGame().getStatus().equals(GameStatus.WAITING))).toList();
        if(!GCs.isEmpty()){
            //If there are some (at least 1) available games to join, join the last.
            try {
                gameControllers.getLast().addPlayerToGame(playerNickname);
            } catch (CannotJoinGameException e) {
                throw new RuntimeException(e);
            }
        } else {
            // If there are no available games to join, create a new game.
            createGame(playerNickname);
        }
    }

    public synchronized void deleteGame(int idGame) throws NoSuchGameException {
        List<GameController> gameToRemove = gameControllers.stream().filter(x -> (x.getGame().getIdGame() == idGame)).toList();
        if(gameToRemove.size()==1){
            gameControllers.remove(gameToRemove.getFirst());
        } else {
            throw new NoSuchGameException();
        }
    }
}
