package it.polimi.ingsw.gc03.view.flow.utilities;

import java.util.Scanner;

public class InputReaderTUI extends Thread implements InputReader {
    private final InputQueue buffer = new InputQueue();

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
            //Reads the input and add what It reads to the buffer synch
            String temp = sc.nextLine();
            buffer.addData(temp);
        }
    }

    /**
     * @return the buffer
     */
    public InputQueue getBuffer(){
        return buffer;
    }
}

