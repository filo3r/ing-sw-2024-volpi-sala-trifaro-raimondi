package it.polimi.ingsw.gc03.view.inputHandler;

import it.polimi.ingsw.gc03.model.ChatMessage;
import it.polimi.ingsw.gc03.model.Player;
import it.polimi.ingsw.gc03.view.ui.Flow;

import java.time.LocalTime;

public class InputProcessor extends Thread{
    /**
     * The buffer from which I pop the data
     */
    private final InputQueue inputQueue;
    /**
     * The data to process
     */
    private final InputQueue dataToProcess;
    /**
     * The game flow
     */
    private final Flow flow;
    /**
     * The player
     */
    private Player p;
    /**
     * The game id
     */
    private Integer gameId;

    /**
     * Init class
     *
     * @param inputQueue
     * @param flow
     */
    public InputProcessor(InputQueue inputQueue, Flow flow) {
        this.inputQueue = inputQueue;
        dataToProcess = new InputQueue();
        this.flow = flow;
        this.p = null;
        this.gameId = null;
        this.start();
    }

    /**
     * Parses the data contained in the buffer
     */
    public void run() {
        String txt;
        while (!this.isInterrupted()) {

            //I keep popping data from the buffer sync
            //(so I wait myself if no data is available on the buffer)
            try {
                txt = inputQueue.popData();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (p!= null && txt.startsWith("/pchat")){
                LocalTime localTime = LocalTime.now();
                String receiver = txt.substring(0,txt.indexOf(" "));
                String message = txt.substring(receiver.length() + 1);
                flow.sendChatMessage(new ChatMessage(receiver,p,message,localTime));
            }

            if (p != null && txt.startsWith("/chat")) {
                //I send a message
                LocalTime localtime = LocalTime.now();
                String receiver = "everyone";
                txt = txt.charAt(2) == ' ' ? txt.substring(3) : txt.substring(2);
                flow.sendChatMessage(new ChatMessage(receiver,p, txt, localtime));

            } else {
                dataToProcess.addData(txt);
            }
        }
    }

    /**
     * Sets the game id to the param passed
     *
     * @param gameId game id to set
     */
    public void setIdGame(Integer gameId) {
        this.gameId = gameId;
    }

    /**
     * Sets the player
     *
     * @param p player to set
     */
    public void setPlayer(Player p) {
        this.p = p;
    }

    /**
     * @return data to process
     */
    public InputQueue getDataToProcess() {
        return dataToProcess;
    }

}
