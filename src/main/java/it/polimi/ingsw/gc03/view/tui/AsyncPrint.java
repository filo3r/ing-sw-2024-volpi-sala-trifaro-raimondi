package it.polimi.ingsw.gc03.view.tui;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncPrint {
    private static final ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static void asyncPrint(StringBuilder text) {
        executorService.submit(() -> {
            System.out.print(text);
        });
    }

    public static void asyncPrint(StringBuilder text, int row, int col) {
        executorService.submit(() -> {
            System.out.print("\033[" + row + ";" + col + "H" + text);
        });
    }
}
