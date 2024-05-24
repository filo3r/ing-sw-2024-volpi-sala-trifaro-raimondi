package it.polimi.ingsw.gc03.main;

import it.polimi.ingsw.gc03.networking.AsyncLogger;
import it.polimi.ingsw.gc03.networking.rmi.RmiServer;
import it.polimi.ingsw.gc03.networking.socket.server.SocketServer;
import it.polimi.ingsw.gc03.view.tui.AsyncPrint;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;


/**
 * Main class for starting the server.
 * This class initializes both RMI and Socket servers and sets the server IP address.
 */
public class MainServer {

    /**
     * Port for the Socket server.
     */
    private final static int SOCKET_PORT = 49160;

    /**
     * Port for the RMI server.
     */
    private final static int RMI_PORT = 1099;

    /**
     * The server's IP address.
     */
    private static String serverIpAddress;


    /**
     * Main method to start the server.
     * @param args Command line arguments.
     * @throws IOException If an I/O error occurs.
     */
    public static void main(String[] args) throws IOException {
        // Clear console
        try {
            clearConsole();
        } catch (IOException | InterruptedException e) {

        }
        // Set the server IP address
        AsyncLogger.log(Level.INFO, "[SERVER] Trying to initialize the servers...");
        List<String> ipAddresses = getLocalIpAddress();
        if (!ipAddresses.isEmpty()) {
            serverIpAddress = ipAddresses.getFirst();
            AsyncLogger.log(Level.INFO, "[SERVER] Server IP address automatically found: " + serverIpAddress);
        } else {
            AsyncLogger.log(Level.WARNING, "[SERVER] Unable to automatically determine the server IP address.");
            serverIpAddress = getUserInputIpAddress();
        }
        // Initialize RMI Server
        try {
            RmiServer.startRmiServer(RMI_PORT);
        } catch (RemoteException e) {
            System.exit(1);
        }
        // Initialize Socket Server
        SocketServer socketServer = new SocketServer();
        try {
            socketServer.startSocketServer(SOCKET_PORT);
        } catch (IOException e) {
            System.exit(1);
        }
        // Print result
        try {
            clearConsole();
        } catch (IOException | InterruptedException e) {

        }
        AsyncLogger.log(Level.INFO, "[SERVER] Server IP address: " + serverIpAddress);
        AsyncLogger.log(Level.INFO, "[SERVER] Server RMI initialized and listening for connections.");
        AsyncLogger.log(Level.INFO, "[SERVER] Server socket initialized and listening for connections.");
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
     * Retrieves the local IP addresses of the machine.
     * @return A list of local IP addresses.
     */
    private static List<String> getLocalIpAddress() {
        List<String> ipAddresses = new ArrayList<>();
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = inetAddresses.nextElement();
                    if (inetAddress.isSiteLocalAddress() && !inetAddress.isLoopbackAddress()) {
                        ipAddresses.add(inetAddress.getHostAddress());
                    }
                }
            }
        } catch (SocketException e) {

        }
        return ipAddresses;
    }


    /**
     * Prompts the user to enter the server's IP address.
     * @return The server's IP address entered by the user.
     */
    private static String getUserInputIpAddress() {
        String input;
        Scanner scanner = new Scanner(System.in);
        try {
            do {
                AsyncPrint.asyncPrint("[SERVER] Enter the server's private IP address: ");
                input = scanner.nextLine();
                if (!isValidIPv4(input))
                    AsyncLogger.log(Level.WARNING, "[SERVER] Invalid IP address.");
            } while (!isValidIPv4(input));
        } finally {
            scanner.close();
        }
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


}
