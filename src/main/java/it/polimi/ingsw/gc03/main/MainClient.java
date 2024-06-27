package it.polimi.ingsw.gc03.main;

import it.polimi.ingsw.gc03.view.gui.ApplicationGui;
import it.polimi.ingsw.gc03.view.tui.print.AsyncLogger;
import it.polimi.ingsw.gc03.view.OptionSelection;
import it.polimi.ingsw.gc03.view.tui.print.AsyncPrint;
import it.polimi.ingsw.gc03.view.ui.Flow;
import javafx.application.Application;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Main class for the client application.
 */
public class MainClient {

    /**
     * The server IP address.
     */
    private static String serverIpAddress;

    /**
     * Port for the Socket server.
     */
    private final static int SOCKET_PORT = 49160;

    /**
     * Port for the RMI server.
     */
    private final static int RMI_PORT = 1099;

    /**
     * The main method for the client application.
     * @param args The command-line arguments.
     */
    public static void main(String[] args)  {
        // Clear console
        try {
            clearConsole();
        } catch (IOException | InterruptedException e) {

        }
        // Disable JavaFX loggers
        disableJavaFXLogging();
        // Set the server IP address
        AsyncLogger.log(Level.INFO, "[CLIENT] Trying to connect to the server...");
        serverIpAddress = getUserInputIpAddress();
        // Get the user selection for connection type and interface type
        int userChoice = getUserChoice();
        // Clear console
        try {
            clearConsole();
        } catch (IOException | InterruptedException e) {

        }
        // Connect to the server using the specified connection type and interface type
        connectToServer(userChoice);
        // Clear console
        try {
            clearConsole();
        } catch (IOException | InterruptedException e) {

        }
    }

    /**
     * Clears the console.
     * @throws IOException If an I/O error occurs.
     * @throws InterruptedException If the current thread is interrupted.
     */
    private static void clearConsole() throws IOException, InterruptedException {
        try {
            // Try running the command for Windows systems
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (IOException e1) {
            try {
                // Try running the command for Unix systems
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            } catch (IOException e2) {

            }
        }
    }

    /**
     * Prompts the user to enter an IP address.
     * @return The valid IP address entered by the user.
     */
    private static String getUserInputIpAddress() {
        String input;
        Scanner scanner = new Scanner(System.in);
        do {
            AsyncPrint.asyncPrint("[CLIENT] Enter the server's private IP address: ");
            input = scanner.nextLine();
            if (!isValidIPv4(input))
                AsyncLogger.log(Level.WARNING, "[CLIENT] Invalid IP address.");
        } while (!isValidIPv4(input));
        return input;
    }

    /**
     * Validates an IPv4 address.
     * @param ip The IP address to validate.
     * @return True if the IP address is valid, false otherwise.
     */
    private static boolean isValidIPv4(String ip) {
        // Pattern for an IPv4 address
        String IPV4_PATTERN = "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}" +
                "([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$";
        if (ip == null)
            return false;
        else
            return ip.matches(IPV4_PATTERN);
    }

    /**
     * Prompts the user to choose the connection type and interface type.
     * @return The user's choice as an integer.
     */
    private static int getUserChoice() {
        int choice;
        Scanner scanner = new Scanner(System.in);
        do {
            AsyncPrint.asyncPrint("Select option:\n(1) TUI + RMI\n(2) TUI + Socket\n(3) GUI + RMI\n(4) GUI + Socket\nYour choice: ");
            while (!scanner.hasNextInt()) {
                AsyncLogger.log(Level.WARNING, "[CLIENT] Invalid choice. Enter a valid option number.");
                AsyncPrint.asyncPrint("Select option:\n(1) TUI + RMI\n(2) TUI + Socket\n(3) GUI + RMI\n(4) GUI + Socket\nYour choice: ");
                scanner.next();
            }
            choice = scanner.nextInt();
        } while (choice < 1 || choice > 4);
        return choice;
    }

    /**
     * Connects to the server using the specified connection type and interface type.
     * @param userChoice The user choice for connection type and interface type.
     */
    private static void connectToServer(int userChoice)  {
        switch (userChoice) {
            case 1:
                // TUI + RMI
                try {
                    new Flow(OptionSelection.TUI, OptionSelection.RMI, serverIpAddress, RMI_PORT);
                } catch (Exception e) {
                    AsyncLogger.log(Level.SEVERE, "[CLIENT RMI] Failed to connect to server: " + e.getMessage());
                    System.exit(1);
                }
                break;
            case 2:
                // TUI + Socket
                try {
                    new Flow(OptionSelection.TUI, OptionSelection.SOCKET, serverIpAddress, SOCKET_PORT);
                } catch (InterruptedException e) {
                    AsyncLogger.log(Level.SEVERE, "[CLIENT SOCKET] Failed to connect to server: " + e.getMessage());
                    System.exit(1);
                }
                break;
            case 3:
                // GUI + RMI
                try {
                    Application.launch(ApplicationGui.class, OptionSelection.RMI.toString(), serverIpAddress.toString(), String.valueOf(RMI_PORT));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 4:
                // GUI + Socket
                try{
                    Application.launch(ApplicationGui.class, OptionSelection.SOCKET.toString(), serverIpAddress.toString(), String.valueOf(SOCKET_PORT));
                 } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                AsyncLogger.log(Level.SEVERE, "[CLIENT] Invalid selection.");
                System.exit(1);
                break;
        }
    }

    /**
     * Disables logging for JavaFX by setting the logging level of the JavaFX logger and its child loggers to OFF.
     */
    private static void disableJavaFXLogging() {
        // Get the global LogManager
        LogManager logManager = LogManager.getLogManager();
        // Get the JavaFX logger
        Logger logger = Logger.getLogger("javafx");
        // Disable the JavaFX logger by setting the level to OFF
        if (logger != null)
            logger.setLevel(Level.OFF);
        // Disable all JavaFX child loggers
        disableChildJavaFxLoggers(logManager, "javafx");
    }

    /**
     * Disables logging for all child loggers of a specified parent logger by setting their logging levels to OFF.
     * @param logManager The global LogManager that manages the loggers.
     * @param parentLoggerName The name of the parent logger whose child loggers will be disabled.
     */
    private static void disableChildJavaFxLoggers(LogManager logManager, String parentLoggerName) {
        // Gets an enumeration of all logger names registered in the LogManager
        Enumeration<String> loggerNames = logManager.getLoggerNames();
        // Iterate through all logger names
        while (loggerNames.hasMoreElements()) {
            // Get the next logger name from the enumeration
            String loggerName = loggerNames.nextElement();
            // Check if the logger name starts with the main logger name followed by a period
            // This means that the current logger is a child of the main logger
            if (loggerName.startsWith(parentLoggerName + ".")) {
                // Gets the child logger corresponding to the name
                Logger childLogger = Logger.getLogger(loggerName);
                // If the child logger is not null, set its logging level to OFF
                if (childLogger != null) {
                    childLogger.setLevel(Level.OFF);
                }
            }
        }
    }

}