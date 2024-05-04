package it.polimi.ingsw.gc03.model;

import it.polimi.ingsw.gc03.networking.rmi.VirtualView;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

abstract class Observable {
    private List<VirtualView> obs;

    /**
     * The constructor creates a new ArrayList of Observers
     */
    public Observable() {
        obs = new ArrayList<>();
    }

    /**
     * The addListener method adds a new Observer to the List of Observable
     * @param obj is the Observer to add
     */
    public synchronized void addObserver(VirtualView obj) {
        obs.add(obj);
    }

    public synchronized void removeObserver(VirtualView obj){
        obs.remove(obj);
    }
    /**
     * The getListeners method returns the List of Observers <br>
     * @return the List of Observers
     */
    public synchronized List<VirtualView> getListeners() {
        return obs;
    }

    public void notifyObservers(Game game) throws RemoteException {
        for(VirtualView obs: obs){
            try {
                obs.updateGame(game);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void notifyObservers(Desk desk) throws RemoteException {
        for(VirtualView obs: obs){
            try {
                obs.updateDesk(desk);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void notifyObservers(Codex codex) throws RemoteException {
        for(VirtualView obs: obs){
            try {
                obs.updateCodex(codex);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
