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

    public static void asyncClean() {
        executorService.submit(() -> {
            try {
                String os = System.getProperty("os.name").toLowerCase();
                ProcessBuilder processBuilder;
                if (os.contains("win")) {
                    processBuilder = new ProcessBuilder("cmd", "/c", "cls");
                } else {
                    processBuilder = new ProcessBuilder("clear");
                }
                Process process = processBuilder.inheritIO().start();
                process.waitFor();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}
