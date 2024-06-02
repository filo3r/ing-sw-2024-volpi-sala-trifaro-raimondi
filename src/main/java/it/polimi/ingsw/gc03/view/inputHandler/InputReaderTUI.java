package it.polimi.ingsw.gc03.view.inputHandler;

import java.util.Scanner;


/**
 * Input reader implementation for a Text User Interface (TUI).
 * This class reads input from the console and adds it to the input queue.
 */
public class InputReaderTUI extends Thread implements InputReader {

    /**
     * The input queue that stores the text input data.
     */
    private final InputQueue inputQueue = new InputQueue();


    /**
     * Initializes the InputReaderTUI and starts the thread.
     */
    public InputReaderTUI(){
        this.start();
    }


    /**
     * Reads player's inputs in a separate thread.
     * Continuously reads input from the console and adds it to the input queue.
     */
    @Override
    public void run(){
        Scanner sc = new Scanner(System.in);
        while(!this.isInterrupted()){
            //Reads the input and add what It reads to the inputQueue
            String temp = sc.nextLine();
            inputQueue.addData(temp);
        }
    }


    /**
     * Retrieves the input queue.
     * @return The input queue.
     */
    public InputQueue getQueue(){
        return inputQueue;
    }

}

