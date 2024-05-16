package it.polimi.ingsw.gc03.view.flow.utilities;

public class InputReaderGUI implements InputReader{
    private final BufferData buffer;

    /**
     * Init
     */
    public InputReaderGUI(){
        buffer = new BufferData();
    }
    @Override
    public BufferData getBuffer() {
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
