package it.polimi.ingsw.gc03.view.flow.utilities;

import it.polimi.ingsw.gc03.model.Player;
import it.polimi.ingsw.gc03.view.flow.GameFlow;

public class CommandProcessor extends Thread{
    /**
     * The buffer from which I pop the data
     */
    private final BufferData bufferInput;
    /**
     * The data to process
     */
    private final BufferData dataToProcess;
    /**
     * The game flow
     */
    private final GameFlow gameFlow;
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
     * @param bufferInput
     * @param gameFlow
     */
    public CommandProcessor(BufferData bufferInput, GameFlow gameFlow) {
        this.bufferInput = bufferInput;
        dataToProcess = new BufferData();
        this.gameFlow = gameFlow;
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
                txt = bufferInput.popData();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            //I popped an input from the buffer
            if (p != null && txt.startsWith("/cs")) {
                txt = txt.charAt(3) == ' ' ? txt.substring(4) : txt.substring(3);
                if(txt.contains(" ")){
                    String receiver = txt.substring(0, txt.indexOf(" "));
                    String msg = txt.substring(receiver.length() + 1);
                    gameFlow.sendMessage(new MessagePrivate(msg, p, receiver));
                }
            } else if (p != null && txt.startsWith("/c")) {
                //I send a message
                txt = txt.charAt(2) == ' ' ? txt.substring(3) : txt.substring(2);
                gameFlow.sendMessage(new Message(txt, p));

            } else if (txt.startsWith("/quit") || (txt.startsWith("/leave"))) {
                assert p != null;
                System.exit(1);
                //gameFlow.leave(p.getNickname(), gameId);
                // gameFlow.youLeft();

            } else {
                //I didn't pop a message

                //I add the data to the buffer processed via GameFlow
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
    public BufferData getDataToProcess() {
        return dataToProcess;
    }

}
