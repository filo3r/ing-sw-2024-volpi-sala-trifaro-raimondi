package it.polimi.ingsw.gc03.model.exceptions;

public class CardNotFoundException extends Exception{
    public CardNotFoundException(){
        super("Error: Card not found");
    }
    public CardNotFoundException(String message){
        super(message);
    }
}
