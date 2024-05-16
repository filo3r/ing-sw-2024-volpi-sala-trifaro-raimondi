package it.polimi.ingsw.gc03.view.flow;

import it.polimi.ingsw.gc03.listeners.GameListener;
import it.polimi.ingsw.gc03.model.GameImmutable;
import it.polimi.ingsw.gc03.model.Player;
import it.polimi.ingsw.gc03.view.flow.utilities.FileDisconnection;

public abstract class Flow implements GameListener {

    protected void resetGameId(FileDisconnection fileDisconnection, GameImmutable model) {
        for (Player p : model.getPlayers()) {
            fileDisconnection.setLastGameId(p.getNickname(), -1);
        }
    }

    protected void saveGameId(FileDisconnection fileDisconnection, String nick, int gameId) {
        fileDisconnection.setLastGameId(nick, gameId);
    }

    public abstract void noConnectionError();

}
