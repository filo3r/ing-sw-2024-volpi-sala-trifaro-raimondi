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
     * The queue for storing tasks.
     */
    private static final LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();


    /*
     * Initializes the executor service to process tasks from the queue.
     * The executor service runs in a separate thread that continuously takes tasks from the queue and executes them.
     */
    static {
        executorService.submit(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    Runnable task = queue.take();
                    task.run();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }


    /**
     * Returns the shared queue for submitting tasks.
     * @return the queue for submitting tasks.
     */
    public static LinkedBlockingQueue<Runnable> getQueue() {
        return queue;
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
