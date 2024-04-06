package it.polimi.ingsw.gc03.model.exceptions;

public class CannotJoinGameException extends Exception{
    public CannotJoinGameException(){
        super("Error: It's not possible to join this game, because it's status is not WAITING");
    }
    public CannotJoinGameException(String message){
        super(message);
    }
}