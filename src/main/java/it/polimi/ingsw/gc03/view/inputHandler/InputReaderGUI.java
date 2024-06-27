package it.polimi.ingsw.gc03.view.inputHandler;

/**
 * Input reader implementation for a GUI.
 */
public class InputReaderGUI implements InputReader{

    /**
     * The input queue that stores the text input data.
     */
    private final InputQueue inputQueue;

    /**
     * Initializes the InputReaderGUI.
     */
    public InputReaderGUI(){
        inputQueue = new InputQueue();
    }

    /**
     * Retrieves the input queue.
     * @return The input queue.
     */
    @Override
    public InputQueue getQueue() {
        return inputQueue;
    }

    /**
     * Adds text to the input queue.
     * @param txt Text to add to the buffer.
     */
    public synchronized void addTxt(String txt){
        System.out.println(txt);
        inputQueue.addData(txt);
    }

}