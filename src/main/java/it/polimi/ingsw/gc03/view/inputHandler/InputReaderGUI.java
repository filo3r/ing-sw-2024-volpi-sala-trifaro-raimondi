package it.polimi.ingsw.gc03.view.inputHandler;

public class InputReaderGUI implements InputReader{
    private final InputQueue inputQueue;

    /**
     * Init
     */
    public InputReaderGUI(){
        inputQueue = new InputQueue();
    }
    @Override
    public InputQueue getQueue() {
        return null;
    }
    /**
     *
     * @param txt text to add to the buffer
     */
    public synchronized void addTxt(String txt){
        inputQueue.addData(txt);
    }

}
