package it.polimi.ingsw.gc03.view.flow;

import it.polimi.ingsw.gc03.listeners.GameListener;
import it.polimi.ingsw.gc03.model.GameImmutable;
import it.polimi.ingsw.gc03.model.Player;
import it.polimi.ingsw.gc03.view.flow.utilities.SaveGameData;

public abstract class Flow implements GameListener {

    protected void resetGameId(SaveGameData saveGameData, GameImmutable model) {
        for (Player p : model.getPlayers()) {
            saveGameData.saveGameData(p.getNickname(),-1 );
        }
    }

    protected void saveGameId(SaveGameData saveGameData, String nick, int gameId) {
        saveGameData.saveGameData(nick, gameId);
    }

    public abstract void noConnectionError();

}
