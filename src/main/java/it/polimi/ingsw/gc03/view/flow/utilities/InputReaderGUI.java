package it.polimi.ingsw.gc03.view.flow.utilities;

public class InputReaderGUI implements InputReader{
    private final InputQueue buffer;

    /**
     * Init
     */
    public InputReaderGUI(){
        buffer = new InputQueue();
    }
    @Override
    public InputQueue getBuffer() {
        return null;
    }
    /**
     *
     * @param txt text to add to the buffer
     */
    public synchronized void addTxt(String txt){
        buffer.addData(txt);
    }

}
