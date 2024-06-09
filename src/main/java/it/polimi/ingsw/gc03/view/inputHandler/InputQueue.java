package it.polimi.ingsw.gc03.view.inputHandler;

import java.util.ArrayDeque;
import java.util.Queue;


/**
 * A thread-safe queue to handle input data for processing.
 */
public class InputQueue {

    /**
     * The underlying queue that stores input data.
     */
    private Queue<String> data;


    /**
     * Initializes the InputQueue.
     */
    public InputQueue(){
        data = new ArrayDeque<>();
    }


    /**
     * Adds one element to the queue.
     * @param txt The element to add.
     */
    public void addData(String txt){
        synchronized (this) {
            data.add(txt);
            this.notifyAll();
        }
    }


    /**
     * Pops one element from the queue.
     * @return The popped element.
     * @throws InterruptedException If the thread is interrupted while waiting.
     */
    public String popData() throws InterruptedException {
        try{
            synchronized (this){
                while(data.isEmpty()){this.wait();}
                return data.poll();
            }
        } catch (InterruptedException e){
            System.err.println(e);
        }
        return "";
    }


    /**
     * Empties the queue.
     */
    public void popAllData(){
        synchronized (this) {
            while (!data.isEmpty()) {
                data.poll();
            }
        }
    }


}
