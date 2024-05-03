package it.polimi.ingsw.gc03.networking;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.*;


/**
 * An asynchronous logger that logs messages to the console using a thread pool and a queue.
 */
public class AsyncLogger {

    /**
     * The logger instance.
     */
    private static final Logger LOGGER = Logger.getLogger(AsyncLogger.class.getName());

    /**
     * The executor service for managing log tasks.
     */
    private static final ExecutorService executor = Executors.newFixedThreadPool(15);

    /**
     * The queue for storing log messages.
     */
    private static final LinkedBlockingQueue<LogMessage> logQueue = new LinkedBlockingQueue<>();


    /**
     * Represents a log message with a logging level and a message.
     */
    private static class LogMessage {
        /**
         * The logging level.
         */
        Level level;
        /**
         * The log message.
         */
        String message;

        /**
         * Constructs a log message.
         * @param level The logging level
         * @param message The log message
         */
        LogMessage(Level level, String message) {
            this.level = level;
            this.message = message;
        }
    }


    /**
     * Custom formatter to colorize log messages based on their logging level.
     */
    private static class CustomFormatter extends Formatter {
        /**
         * The ANSI reset code.
         */
        private static final String ANSI_RESET = "\u001B[0m";
        /**
         * The ANSI red color code.
         */
        private static final String ANSI_RED = "\u001B[31m";
        /**
         * The ANSI yellow color code.
         */
        private static final String ANSI_YELLOW = "\u001B[33m";
        /**
         * The ANSI green color code.
         */
        private static final String ANSI_GREEN = "\u001B[32m";

        /**
         * Formats a log record to include ANSI color coding based on the logging level.
         * @param record The log record to format
         * @return The formatted log record as a string
         */
        @Override
        public String format(LogRecord record) {
            String levelColor;
            switch (record.getLevel().toString()) {
                case "SEVERE":
                    levelColor = ANSI_RED;
                    break;
                case "WARNING":
                    levelColor = ANSI_YELLOW;
                    break;
                case "INFO":
                    levelColor = ANSI_GREEN;
                    break;
                default:
                    levelColor = ANSI_RESET;
                    break;
            }
            return levelColor + record.getLevel() + ": " + formatMessage(record) + ANSI_RESET + System.lineSeparator();
        }
    }


    static {
        LOGGER.setUseParentHandlers(false);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(new CustomFormatter());
        handler.setLevel(Level.ALL);
        LOGGER.addHandler(handler);
        LOGGER.setLevel(Level.ALL);
        executor.submit(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    LogMessage message = logQueue.take();
                    LOGGER.log(message.level, message.message);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                handler.close();
            }
        });
    }


    /**
     * Logs a message at the specified logging level.
     * @param level The logging level
     * @param message The log message
     */
    public static void log(Level level, String message) {
        logQueue.offer(new LogMessage(level, message));
    }


    /**
     * Shuts down the executor service gracefully.
     */
    public static void shutdownExecutor() {
        // Begin the process of shutting down the ExecutorService
        executor.shutdown();
        try {
            // Wait up to 60 seconds for all tasks to complete
            if (!executor.awaitTermination(30, TimeUnit.SECONDS)) {
                // If tasks do not finish within the allotted time, force termination of active tasks
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            // If the current thread is interrupted while waiting for the executor to finish, reset the interrupt state
            Thread.currentThread().interrupt();
            // After restoring the interrupt state, force the executor to terminate
            executor.shutdownNow();
        }
    }


}
