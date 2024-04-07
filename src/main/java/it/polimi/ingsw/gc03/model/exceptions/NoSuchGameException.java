package it.polimi.ingsw.gc03.model.exceptions;

public class NoSuchGameException extends Exception{
    public NoSuchGameException(){
        super("This game doesn't exist");
    }
    public NoSuchGameException(String message){
        super(message);
    }
}
