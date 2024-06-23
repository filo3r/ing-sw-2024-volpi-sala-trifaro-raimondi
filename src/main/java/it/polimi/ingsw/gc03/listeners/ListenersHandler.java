package it.polimi.ingsw.gc03.listeners;

import it.polimi.ingsw.gc03.model.ChatMessage;
import it.polimi.ingsw.gc03.model.Game;
import it.polimi.ingsw.gc03.model.Model;
import it.polimi.ingsw.gc03.model.Player;
import it.polimi.ingsw.gc03.model.card.Card;
import it.polimi.ingsw.gc03.model.card.cardObjective.CardObjective;
import it.polimi.ingsw.gc03.model.enumerations.Value;
import it.polimi.ingsw.gc03.view.tui.print.AsyncLogger;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;

/**
 * This class manages a list of GameListener objects and notifies the view when changes occur int the Game.
 * It provides the updated Game as a GameImmutable instance when notifying events.
 */
public class ListenersHandler {

    /**
     * List of GameListener objects.
     */
    private ArrayList<GameListener> gameListeners;

    /**
     * Class constructor.
     */
    public ListenersHandler() {
        this.gameListeners = new ArrayList<>();
    }

    /**
     * Adds a new GameListener to the gameListeners list.
     * @param gameListener The GameListener to add.
     */
    public synchronized void addListener(GameListener gameListener) {
        this.gameListeners.add(gameListener);
    }

    /**
     * Removes a GameListener from the gameListeners list.
     * @param gameListener The GameListener to remove.
     */
    public synchronized void removeListener(GameListener gameListener) {
        this.gameListeners.remove(gameListener);
    }

    /**
     * Get the gameListeners list.
     * @return The list of GameListener.
     */
    public synchronized ArrayList<GameListener> getGameListeners() {
        return this.gameListeners;
    }

    /**
     * Notifies that a player has joined the game.
     * @param game The current game.
     */
    public synchronized void notifyPlayerJoined(Game game) {
        ArrayList<GameListener> gameListenersToRemove = new ArrayList<>();
        for (GameListener gameListener : this.gameListeners) {
            try {
                Model model = new Model(game);
                gameListener.playerJoined(model);
            } catch (RemoteException e) {
                AsyncLogger.log(Level.WARNING, "[LISTENER] Disconnection has been detected.");
                gameListenersToRemove.add(gameListener);
            }
        }
        this.gameListeners.removeAll(gameListenersToRemove);
    }

    /**
     * Notifies that a player has left the game.
     * @param game The current game.
     * @param nickname The nickname of the player who left the game.
     */
    public synchronized void notifyPlayerLeft(Game game, String nickname) {
        ArrayList<GameListener> gameListenersToRemove = new ArrayList<>();
        for (GameListener gameListener : this.gameListeners) {
            try {
                Model model = new Model(game);
                gameListener.playerLeft(model, nickname);
            } catch (RemoteException e) {
                AsyncLogger.log(Level.WARNING, "[LISTENER] Disconnection has been detected.");
                gameListenersToRemove.add(gameListener);
            }
        }
        this.gameListeners.removeAll(gameListenersToRemove);
    }

    /**
     * Notifies that a player tried to join a full game.
     * @param game The current game.
     * @param player The player that tried to join.
     */
    public synchronized void notifyJoinUnableGameFull(Game game, Player player) {
        ArrayList<GameListener> gameListenersToRemove = new ArrayList<>();
        for (GameListener gameListener : this.gameListeners) {
            try {
                Model model = new Model(game);
                gameListener.joinUnableGameFull(model, player);
            } catch (RemoteException e) {
                AsyncLogger.log(Level.WARNING, "[LISTENER] Disconnection has been detected.");
                gameListenersToRemove.add(gameListener);
            }
        }
        this.gameListeners.removeAll(gameListenersToRemove);
    }

    /**
     * Notifies that a player has reconnected to the game.
     * @param game The current game.
     * @param nickname The nickname of the player that has reconnected.
     */
    public synchronized void notifyPlayerReconnected(Game game, String nickname) {
        ArrayList<GameListener> gameListenersToRemove = new ArrayList<>();
        for (GameListener gameListener : this.gameListeners) {
            try {
                Model model = new Model(game);
                gameListener.playerReconnected(model, nickname);
            } catch (RemoteException e) {
                AsyncLogger.log(Level.WARNING, "[LISTENER] Disconnection has been detected.");
                gameListenersToRemove.add(gameListener);
            }
        }
        this.gameListeners.removeAll(gameListenersToRemove);
    }

    /**
     * Notifies that a player has disconnected.
     * @param game The current game.
     * @param nickname The nickname of the player that has disconnected.
     */
    public synchronized void notifyPlayerDisconnected(Game game, String nickname) {
        ArrayList<GameListener> gameListenersToRemove = new ArrayList<>();
        for (GameListener gameListener : this.gameListeners) {
            try {
                Model model = new Model(game);
                gameListener.playerDisconnected(model, nickname);
            } catch (RemoteException e) {
                AsyncLogger.log(Level.WARNING, "[LISTENER] Disconnection has been detected.");
                gameListenersToRemove.add(gameListener);
            }
        }
        this.gameListeners.removeAll(gameListenersToRemove);
    }

    /**
     * Notifies that only one player is connected.
     * @param game The current game.
     * @param timer The number of seconds to wait until the game ends.
     */
    public synchronized void notifyOnlyOnePlayerConnected(Game game, int timer) {
        ArrayList<GameListener> gameListenersToRemove = new ArrayList<>();
        for (GameListener gameListener : this.gameListeners) {
            try {
                Model model = new Model(game);
                gameListener.onlyOnePlayerConnected(model, timer);
            } catch (RemoteException e) {
                AsyncLogger.log(Level.WARNING, "[LISTENER] Disconnection has been detected.");
                gameListenersToRemove.add(gameListener);
            }
        }
        this.gameListeners.removeAll(gameListenersToRemove);
    }

    /**
     * Notifies that a player tried to use a nickname that is already in use.
     * @param player The player that tried to use the nickname.
     */
    public synchronized void notifyJoinUnableNicknameAlreadyInUse(Player player) {
        ArrayList<GameListener> gameListenersToRemove = new ArrayList<>();
        for (GameListener gameListener : this.gameListeners) {
            try {
                gameListener.joinUnableNicknameAlreadyInUse(player);
            } catch (RemoteException e) {
                AsyncLogger.log(Level.WARNING, "[LISTENER] Disconnection has been detected.");
                gameListenersToRemove.add(gameListener);
            }
        }
        this.gameListeners.removeAll(gameListenersToRemove);
    }

    /**
     * Notifies that the game id doesn't exist.
     * @param gameId The game id.
     */
    public synchronized void notifyGameIdNotExists(int gameId) {
        ArrayList<GameListener> gameListenersToRemove = new ArrayList<>();
        for (GameListener gameListener : this.gameListeners) {
            try {
                gameListener.gameIdNotExists(gameId);
            } catch (RemoteException e) {
                AsyncLogger.log(Level.WARNING, "[LISTENER] Disconnection has been detected.");
                gameListenersToRemove.add(gameListener);
            }
        }
        this.gameListeners.removeAll(gameListenersToRemove);
    }

    /**
     * Notifies that the game has started.
     * @param game The current game.
     */
    public synchronized void notifyGameStarted(Game game) {
        ArrayList<GameListener> gameListenersToRemove = new ArrayList<>();
        for (GameListener gameListener : this.gameListeners) {
            try {
                Model model = new Model(game);
                gameListener.gameStarted(model);
            } catch (RemoteException e) {
                AsyncLogger.log(Level.WARNING, "[LISTENER] Disconnection has been detected.");
                gameListenersToRemove.add(gameListener);
            }
        }
        this.gameListeners.removeAll(gameListenersToRemove);
    }

    /**
     * Notifies that the game has ended.
     * @param game The current game.
     */
    public synchronized void notifyGameEnded(Game game) {
        ArrayList<GameListener> gameListenersToRemove = new ArrayList<>();
        for (GameListener gameListener : this.gameListeners) {
            try {
                Model model = new Model(game);
                gameListener.gameEnded(model);
            } catch (RemoteException e) {
                AsyncLogger.log(Level.WARNING, "[LISTENER] Disconnection has been detected.");
                gameListenersToRemove.add(gameListener);
            }
        }
        this.gameListeners.removeAll(gameListenersToRemove);
    }

    /**
     * Notifies that a message has been sent.
     * @param game The current game.
     * @param chatMessage The message that has been sent.
     */
    public synchronized void notifySentChatMessage(Game game, ChatMessage chatMessage) {
        ArrayList<GameListener> gameListenersToRemove = new ArrayList<>();
        for (GameListener gameListener : this.gameListeners) {
            try {
                Model model = new Model(game);
                gameListener.sentChatMessage(model, chatMessage);
            } catch (RemoteException e) {
                AsyncLogger.log(Level.WARNING, "[LISTENER] Disconnection has been detected.");
                gameListenersToRemove.add(gameListener);
            }
        }
        this.gameListeners.removeAll(gameListenersToRemove);
    }

    /**
     * Notifies that the next turn triggered.
     * @param game The current game.
     */
    public synchronized void notifyNextTurn(Game game) {
        ArrayList<GameListener> gameListenersToRemove = new ArrayList<>();
        for (GameListener gameListener : this.gameListeners) {
            try {
                Model model = new Model(game);
                gameListener.nextTurn(model);
            } catch (RemoteException e) {
                AsyncLogger.log(Level.WARNING, "[LISTENER] Disconnection has been detected.");
                gameListenersToRemove.add(gameListener);
            }
        }
        this.gameListeners.removeAll(gameListenersToRemove);
    }

    /**
     * Notifies that the last circle has started.
     * @param game The current game.
     */
    public synchronized void notifyLastCircle(Game game) {
        ArrayList<GameListener> gameListenersToRemove = new ArrayList<>();
        for (GameListener gameListener : this.gameListeners) {
            try {
                Model model = new Model(game);
                gameListener.lastCircle(model);
            } catch (RemoteException e) {
                AsyncLogger.log(Level.WARNING, "[LISTENER] Disconnection has been detected.");
                gameListenersToRemove.add(gameListener);
            }
        }
        this.gameListeners.removeAll(gameListenersToRemove);
    }

    /**
     * Notifies that the card was placed into the Codex.
     * @param game The current game.
     * @param row The row where the card was placed.
     * @param column The column where the card was placed.
     */
    public synchronized void notifyPositionedCardIntoCodex(Game game, int row, int column) {
        ArrayList<GameListener> gameListenersToRemove = new ArrayList<>();
        for (GameListener gameListener : this.gameListeners) {
            try {
                Model model = new Model(game);
                gameListener.positionedCardIntoCodex(model, row, column);
            } catch (RemoteException e) {
                AsyncLogger.log(Level.WARNING, "[LISTENER] Disconnection has been detected.");
                gameListenersToRemove.add(gameListener);
            }
        }
        this.gameListeners.removeAll(gameListenersToRemove);
    }

    /**
     * Notifies that the Starter card was placed into the Codex.
     * @param game The current game.
     */
    public synchronized void notifyPositionedStarterCardIntoCodex(Game game, String nickname) {
        ArrayList<GameListener> gameListenersToRemove = new ArrayList<>();
        for (GameListener gameListener : this.gameListeners) {
            try {
                Model model = new Model(game);
                gameListener.positionedStarterCardIntoCodex(model, nickname);
            } catch (RemoteException e) {
                AsyncLogger.log(Level.WARNING, "[LISTENER] Disconnection has been detected.");
                gameListenersToRemove.add(gameListener);
            }
        }
        this.gameListeners.removeAll(gameListenersToRemove);
    }

    /**
     * Notifies that the coordinates where he wants to insert the card are not valid.
     * @param game The current game.
     * @param row The row where the card wanted to be placed.
     * @param column The column where the card wanted to be placed.
     */
    public synchronized void notifyInvalidCoordinates(Game game, int row, int column) {
        ArrayList<GameListener> gameListenersToRemove = new ArrayList<>();
        for (GameListener gameListener : this.gameListeners) {
            try {
                Model model = new Model(game);
                gameListener.invalidCoordinates(model, row, column);
            } catch (RemoteException e) {
                AsyncLogger.log(Level.WARNING, "[LISTENER] Disconnection has been detected.");
                gameListenersToRemove.add(gameListener);
            }
        }
        this.gameListeners.removeAll(gameListenersToRemove);
    }

    /**
     * Notifies that the positioning requirements are not respected.
     * @param game The current game.
     * @param requirementsPlacement The requirements for card placement.
     */
    public synchronized void notifyRequirementsPlacementNotRespected(Game game, ArrayList<Value> requirementsPlacement) {
        ArrayList<GameListener> gameListenersToRemove = new ArrayList<>();
        for (GameListener gameListener : this.gameListeners) {
            try {
                Model model = new Model(game);
                gameListener.requirementsPlacementNotRespected(model, requirementsPlacement);
            } catch (RemoteException e) {
                AsyncLogger.log(Level.WARNING, "[LISTENER] Disconnection has been detected.");
                gameListenersToRemove.add(gameListener);
            }
        }
        this.gameListeners.removeAll(gameListenersToRemove);
    }

    /**
     * Notifies that the points have been added.
     * @param game The current game.
     * @param player The player that has added the points.
     * @param point The number of points that have been added.
     */
    public synchronized void notifyAddedPoint(Game game, Player player, int point) {
        ArrayList<GameListener> gameListenersToRemove = new ArrayList<>();
        for (GameListener gameListener : this.gameListeners) {
            try {
                Model model = new Model(game);
                gameListener.addedPoint(model, player, point);
            } catch (RemoteException e) {
                AsyncLogger.log(Level.WARNING, "[LISTENER] Disconnection has been detected.");
                gameListenersToRemove.add(gameListener);
            }
        }
        this.gameListeners.removeAll(gameListenersToRemove);
    }

    /**
     * Notifies that the Objective card was chosen correctly.
     * @param game The current game.
     *
     * @param cardObjective The chosen Objective card.
     */
    public synchronized void notifyObjectiveCardChosen(Game game, CardObjective cardObjective, String nickname) {
        ArrayList<GameListener> gameListenersToRemove = new ArrayList<>();
        for (GameListener gameListener : this.gameListeners) {
            try {
                Model model = new Model(game);
                gameListener.objectiveCardChosen(model, cardObjective, nickname);
            } catch (RemoteException e) {
                AsyncLogger.log(Level.WARNING, "[LISTENER] Disconnection has been detected.");
                gameListenersToRemove.add(gameListener);
            }
        }
        this.gameListeners.removeAll(gameListenersToRemove);
    }

    /**
     * Notifies that the Objective card was not chosen correctly.
     * @param game The current game.
     */
    public synchronized void notifyObjectiveCardNotChosen(Game game) {
        ArrayList<GameListener> gameListenersToRemove = new ArrayList<>();
        for (GameListener gameListener : this.gameListeners) {
            try {
                Model model = new Model(game);
                gameListener.objectiveCardNotChosen(model);
            } catch (RemoteException e) {
                AsyncLogger.log(Level.WARNING, "[LISTENER] Disconnection has been detected.");
                gameListenersToRemove.add(gameListener);
            }
        }
        this.gameListeners.removeAll(gameListenersToRemove);
    }


    /**
     * Notifies that the index is invalid.
     * @param game The current game.
     * @param index The index.
     */
    public synchronized void notifyIndexNotValid(Game game, int index) {
        ArrayList<GameListener> gameListenersToRemove = new ArrayList<>();
        for (GameListener gameListener : this.gameListeners) {
            try {
                Model model = new Model(game);
                gameListener.indexNotValid(model, index);
            } catch (RemoteException e) {
                AsyncLogger.log(Level.WARNING, "[LISTENER] Disconnection has been detected.");
                gameListenersToRemove.add(gameListener);
            }
        }
        this.gameListeners.removeAll(gameListenersToRemove);
    }

    /**
     * Notifies that a deck has no cards.
     * @param game The current game.
     * @param deck The deck without cards.
     */
    public synchronized void notifyIndexNotValid(Game game, ArrayList<? extends Card> deck) {
        ArrayList<GameListener> gameListenersToRemove = new ArrayList<>();
        for (GameListener gameListener : this.gameListeners) {
            try {
                Model model = new Model(game);
                gameListener.deckHasNoCards(model, deck);
            } catch (RemoteException e) {
                AsyncLogger.log(Level.WARNING, "[LISTENER] Disconnection has been detected.");
                gameListenersToRemove.add(gameListener);
            }
        }
        this.gameListeners.removeAll(gameListenersToRemove);
    }

    /**
     * Notifies that a card has been successfully added to his hand.
     * @param game The current game.
     * @param card The card that was added.
     */
    public synchronized void notifyCardAddedToHand(Game game, Card card) {
        ArrayList<GameListener> gameListenersToRemove = new ArrayList<>();
        for (GameListener gameListener : this.gameListeners) {
            try {
                Model model = new Model(game);
                gameListener.cardAddedToHand(model, card);
            } catch (RemoteException e) {
                AsyncLogger.log(Level.WARNING, "[LISTENER] Disconnection has been detected.");
                gameListenersToRemove.add(gameListener);
            }
        }
        this.gameListeners.removeAll(gameListenersToRemove);
    }

    /**
     * Notifies that a card was not added to his hand.
     * @param game The current game.
     */
    public synchronized void notifyCardNotAddedToHand(Game game) {
        ArrayList<GameListener> gameListenersToRemove = new ArrayList<>();
        for (GameListener gameListener : this.gameListeners) {
            try {
                Model model = new Model(game);
                gameListener.cardNotAddedToHand(model);
            } catch (RemoteException e) {
                AsyncLogger.log(Level.WARNING, "[LISTENER] Disconnection has been detected.");
                gameListenersToRemove.add(gameListener);
            }
        }
        this.gameListeners.removeAll(gameListenersToRemove);
    }

    /**
     * Notifies that the conditions to end the game have been reached.
     * @param game The current game.
     */
    public synchronized void notifyEndConditionReached(Game game) {
        ArrayList<GameListener> gameListenersToRemove = new ArrayList<>();
        for (GameListener gameListener : this.gameListeners) {
            try {
                Model model = new Model(game);
                gameListener.endGameConditionsReached(model);
            } catch (RemoteException e) {
                AsyncLogger.log(Level.WARNING, "[LISTENER] Disconnection has been detected.");
                gameListenersToRemove.add(gameListener);
            }
        }
        this.gameListeners.removeAll(gameListenersToRemove);
    }


    /**
     * Notifies that the points obtained with the Objective cards have been calculated.
     * @param game The current game.
     * @param objectivePoint The points obtained with Objective cards.
     */
    public synchronized void notifyAddedPointObjective(Game game, int objectivePoint) {
        ArrayList<GameListener> gameListenersToRemove = new ArrayList<>();
        for (GameListener gameListener : this.gameListeners) {
            try {
                Model model = new Model(game);
                gameListener.addedPointObjective(model, objectivePoint);
            } catch (RemoteException e) {
                AsyncLogger.log(Level.WARNING, "[LISTENER] Disconnection has been detected.");
                gameListenersToRemove.add(gameListener);
            }
        }
        this.gameListeners.removeAll(gameListenersToRemove);
    }


    /**
     * Notifies which players won the game.
     * @param game The current game.
     * @param nickname The nicknames of the players who won.
     */
    public synchronized void notifyWinnerDeclared(Game game, ArrayList<String> nickname) {
        ArrayList<GameListener> gameListenersToRemove = new ArrayList<>();
        for (GameListener gameListener : this.gameListeners) {
            try {
                Model model = new Model(game);
                gameListener.winnerDeclared(model, nickname);
            } catch (RemoteException e) {
                AsyncLogger.log(Level.WARNING, "[LISTENER] Disconnection has been detected.");
                gameListenersToRemove.add(gameListener);
            }
        }
        this.gameListeners.removeAll(gameListenersToRemove);
    }

    /**
     * Notifies the new game's size.
     * @param game The current game.
     * @param size The new game's size.
     */
    public synchronized void notifyGameSizeUpdated(Game game, int size) {
        ArrayList<GameListener> gameListenersToRemove = new ArrayList<>();
        for (GameListener gameListener : this.gameListeners) {
            try {
                Model model = new Model(game);
                gameListener.gameSizeUpdated(model, size);
            } catch (RemoteException e) {
                AsyncLogger.log(Level.WARNING, "[LISTENER] Disconnection has been detected.");
                gameListenersToRemove.add(gameListener);
            }
        }
        this.gameListeners.removeAll(gameListenersToRemove);
    }

    /**
     * Notifies that a card has been drawn.
     * @param game The current game.
     * @param nickname The player who has drawn a card.
     */
    public synchronized void notifyDrawCard(Game game, String nickname) {
        ArrayList<GameListener> gameListenersToRemove = new ArrayList<>();
        for (GameListener gameListener : this.gameListeners) {
            try {
                Model model = new Model(game);
                gameListener.drawCard(model, nickname);
            } catch (RemoteException e) {
                AsyncLogger.log(Level.WARNING, "[LISTENER] Disconnection has been detected.");
                gameListenersToRemove.add(gameListener);
            }
        }
        this.gameListeners.removeAll(gameListenersToRemove);
    }
}
