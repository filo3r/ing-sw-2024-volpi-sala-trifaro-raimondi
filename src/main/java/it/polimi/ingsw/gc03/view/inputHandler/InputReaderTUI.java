package it.polimi.ingsw.gc03.view.inputHandler;

import java.util.Scanner;

public class InputReaderTUI extends Thread implements InputReader {
    private final InputQueue inputQueue = new InputQueue();

    /**
     * Init
     */
    public InputReaderTUI(){
        this.start();
    }

    /**
     * Reads player's inputs
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
     * @return the buffer
     */
    public InputQueue getQueue(){
        return inputQueue;
    }
}

