package it.polimi.ingsw.gc03.view.inputHandler;

import it.polimi.ingsw.gc03.model.ChatMessage;
import it.polimi.ingsw.gc03.view.ui.Flow;

import java.time.LocalTime;

/**
 * The InputProcessor class processes input commands and messages, managing the game flow and interactions.
 */
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
    private String nickname;
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
        this.nickname = null;
        this.gameId = null;
        this.start();
    }

    /**
     * Parses the data contained in the buffer
     */
    public void run() {
        String txt = null;
        while (!this.isInterrupted()) {

            // Popping data from the buffer sync
            try {
                txt = inputQueue.popData();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (nickname != null && txt.startsWith("pm ")) {
                LocalTime localTime = LocalTime.now();
                String[] parts = txt.split(" ", 3);
                if (parts.length >= 3) {
                    String receiver = parts[1];
                    String message = parts[2];
                    flow.sendChatMessage(new ChatMessage(receiver, nickname, message, localTime));
                }
            } else if (nickname != null && txt.startsWith("m ")) {
                LocalTime localTime = LocalTime.now();
                String[] parts = txt.split(" ", 2);
                if (parts.length >= 2) {
                    String receiver = "everyone";
                    String message = parts[1];
                    flow.sendChatMessage(new ChatMessage(receiver, nickname, message, localTime));
                }
            } else if (nickname != null && txt.equals("leave")){
                flow.leaveGame(nickname);
            } else if (txt.startsWith("resize ")) {
                String dimensions = txt.substring(txt.indexOf(" ") + 1);
                int x = Integer.parseInt(dimensions.substring(0, dimensions.indexOf(" ")));
                int y = Integer.parseInt(dimensions.substring(dimensions.indexOf(" ") + 1));
                flow.resizeScreen(x, y);
            } else if (txt.startsWith("move ")){
                String dimensions = txt.substring(txt.indexOf(" ") + 1);
                int x = Integer.parseInt(dimensions.substring(0, dimensions.indexOf(" ")));
                int y = Integer.parseInt(dimensions.substring(dimensions.indexOf(" ") + 1));
                flow.moveScreen(x, y);
            } else if(nickname != null && txt.equals("chat")){
                flow.showChat();
            }
            else {
                dataToProcess.addData(txt);
            }
            flow.terminalPrint("✓");
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
     * @param nickname The player's nickname.
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * @return data to process
     */
    public InputQueue getDataToProcess() {
        return dataToProcess;
    }

}
