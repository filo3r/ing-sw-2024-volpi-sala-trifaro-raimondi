package it.polimi.ingsw.gc03;

public class CardNotFoundException extends Exception{
    public CardNotFoundException(){
        super("Error: Card not found");
    }
    public CardNotFoundException(String message){
        super(message);
    }
}
