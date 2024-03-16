package it.polimi.ingsw.gc03;

public class DeskIsFullException extends Exception {
    public DeskIsFullException(){
        super("Error:The Desk is already full");
    }
    public DeskIsFullException(String message){
        super(message);
    }
}
