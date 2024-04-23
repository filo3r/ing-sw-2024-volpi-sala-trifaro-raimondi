package it.polimi.ingsw.gc03.rmi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
//Manca parte di Scanner input e gestione observer
public class RmiClient extends UnicastRemoteObject implements VirtualView{

    final VirtualServer server;

    public RmiClient(VirtualServer server) throws RemoteException{
        this.server = server;
    }

    private void run() throws RemoteException{
        this.server.connect(this);
        this.runCli();
    }

    private void runCli() throws RemoteException{
        Scanner scan = new Scanner(System.in);
        while(true){
            if(scan.nextLine().equals("joinGame")){
                System.out.println("Choose your Nickname\n");
                String nickname = scan.nextLine();
                System.out.println("Joining a Server...");
                server.joinGame(nickname);
            }
        }
    }
    public static void main(String[] args) throws RemoteException, NotBoundException {
        final String serverName = "Server";
        Registry registry = LocateRegistry.getRegistry("localhost",1234);
        VirtualServer server = (VirtualServer) registry.lookup(serverName);

        new RmiClient(server).run();
    }

    @Override
    public void showUpdate() throws RemoteException {

    }

    @Override
    public void reportError(String details) throws RemoteException {

    }
}
