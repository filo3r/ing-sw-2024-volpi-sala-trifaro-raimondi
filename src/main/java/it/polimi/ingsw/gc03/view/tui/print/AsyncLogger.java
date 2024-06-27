package it.polimi.ingsw.gc03.view.tui.print;

import java.util.logging.*;

/**
 * An asynchronous logger that logs messages to the console using a shared queue.
 */
public class AsyncLogger {

    /**
     * The logger instance.
     */
    private static final Logger LOGGER = Logger.getLogger(AsyncLogger.class.getName());

    /*
     * Initializes the logger with a custom formatter and a console handler.
     * The logger is configured to use a custom formatter that colorizes log messages based on their logging level.
     */
    static {
        LOGGER.setUseParentHandlers(false);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(new CustomFormatter());
        handler.setLevel(Level.ALL);
        LOGGER.addHandler(handler);
        LOGGER.setLevel(Level.ALL);
    }

    /**
     * Logs a message at the specified logging level.
     * @param level The logging level
     * @param message The log message
     */
    public static void log(Level level, String message) {
        AsyncHandler.executeAsync(() -> LOGGER.log(level, message));
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

}