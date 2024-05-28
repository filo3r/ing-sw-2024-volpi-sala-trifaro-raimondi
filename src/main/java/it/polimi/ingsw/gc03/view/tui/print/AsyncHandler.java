package it.polimi.ingsw.gc03.view.tui.print;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;


/**
 * Provides a shared queue and executor service for asynchronous printing tasks.
 */
public class AsyncHandler {

    /**
     * The executor service for managing asynchronous tasks.
     */
    private static final ExecutorService executorService = Executors.newFixedThreadPool(20);


    /**
     * Submits a Runnable task for execution by the executor service.
     * This method is used to perform tasks asynchronously using the threads managed by the executor service.
     * @param task The Runnable task to be executed asynchronously.
     */
    public static void executeAsync(Runnable task) {
        executorService.execute(task);
    }


    /**
     * Shuts down the executor service gracefully, waiting up to 30 seconds for tasks to complete.
     */
    public static void shutdown() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(30, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }


}
