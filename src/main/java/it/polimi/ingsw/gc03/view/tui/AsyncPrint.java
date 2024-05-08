package it.polimi.ingsw.gc03.view.tui;

public class AsyncPrint {
    public static void asyncPrint(StringBuilder text) {
        new Thread(() -> {
            System.out.print(text);
        }).start();
    }

    public static void asyncPrint(StringBuilder text, int row, int col) {
        new Thread(() -> {
            System.out.print("\033[" + row + ";" + col + "H" + text);
        }).start();
    }
}