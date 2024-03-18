package it.polimi.ingsw.gc03.Exceptions;

public class PlayerAlreadyJoinedException extends Exception{
    public PlayerAlreadyJoinedException(){
        super("This Player is already in the game");
    }
    public PlayerAlreadyJoinedException(String message){
        super(message);
    }
}
