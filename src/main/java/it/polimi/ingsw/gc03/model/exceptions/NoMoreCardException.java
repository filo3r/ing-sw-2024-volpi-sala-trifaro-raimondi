package it.polimi.ingsw.gc03.model.exceptions;

public class NoMoreCardException extends Exception{
    public NoMoreCardException(){
        super("The card in the deck are finished");
    }
    public NoMoreCardException(String message){
        super(message);
    }
}
