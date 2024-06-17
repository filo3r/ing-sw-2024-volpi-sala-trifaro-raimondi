package it.polimi.ingsw.gc03.view.ui;

import it.polimi.ingsw.gc03.listeners.GameListener;
import it.polimi.ingsw.gc03.model.ChatMessage;
import it.polimi.ingsw.gc03.model.GameImmutable;
import it.polimi.ingsw.gc03.model.Player;
import it.polimi.ingsw.gc03.model.card.Card;
import it.polimi.ingsw.gc03.model.card.cardObjective.CardObjective;
import it.polimi.ingsw.gc03.model.enumerations.DeckType;
import it.polimi.ingsw.gc03.model.enumerations.Value;
import it.polimi.ingsw.gc03.model.side.Side;
import it.polimi.ingsw.gc03.networking.rmi.RmiClient;
import it.polimi.ingsw.gc03.networking.socket.client.ClientAction;
import it.polimi.ingsw.gc03.networking.socket.client.SocketClient;
import it.polimi.ingsw.gc03.view.OptionSelection;
import it.polimi.ingsw.gc03.view.gui.Gui;
import it.polimi.ingsw.gc03.view.gui.ApplicationGui;
import it.polimi.ingsw.gc03.view.tui.print.AsyncPrint;
import it.polimi.ingsw.gc03.view.ui.events.Event;
import it.polimi.ingsw.gc03.view.ui.events.EventList;
import it.polimi.ingsw.gc03.view.ui.events.EventType;
import it.polimi.ingsw.gc03.view.inputHandler.*;
import it.polimi.ingsw.gc03.view.tui.Tui;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static it.polimi.ingsw.gc03.view.ui.events.EventType.*;

/**
 * This class manages the flow of the game, handling the user interface (UI) and the events.
 */
public class Flow implements Runnable, ClientAction, GameListener {

    /**
     * The player's nickname.
     */
    private String nickname;

    /**
     * The list of events.
     */
    private final EventList events = new EventList();

    /**
     * The client actions interface.
     */
    private ClientAction clientActions;

    /**
     * The last player who reconnected.
     */
    private String lastPlayerReconnected;

    /**
     * The row coordinate for placing a card.
     */
    private int row;

    /**
     * The column coordinate for placing a card.
     */
    private int col;

    /**
     * A flag indicating if the front side of a card is chosen.
     */
    private boolean frontCard;

    /**
     * The user interface (UI).
     */
    private UI ui;

    /**
     * The input processor.
     */
    protected InputProcessor inputProcessor;

    /**
     * The input reader.
     */
    protected InputReader inputReader;

    /**
     * The list of important events.
     */
    protected List<String> importantEvents;

    /**
     * A flag indicating if the game has ended.
     */
    private boolean ended = false;

    /**
     * The last event processed.
     */
    Event lastEvent = null;

    /**
     * The immutable game model.
     */
    private GameImmutable gameImmutable = null;

    /**
     * Constructs a new Flow object with the given UI and connection options, server IP address, and port.
     *
     * @param uiSelection          the UI selection option
     * @param connectionSelection  the connection selection option
     * @param serverIpAddress      the server IP address
     * @param port                 the server port
     * @throws InterruptedException if the thread is interrupted
     */
    public Flow(OptionSelection uiSelection, OptionSelection connectionSelection, String serverIpAddress, int port) throws InterruptedException {
        switch (uiSelection) {
            case TUI -> {
                ui = new Tui(150, 35);
                this.inputReader = new InputReaderTUI();
                this.inputProcessor = new InputProcessor(this.inputReader.getQueue(), this);
            }
        }
        switch (connectionSelection) {
            case RMI -> clientActions = new RmiClient(serverIpAddress, port, this);
            case SOCKET -> clientActions = new SocketClient(serverIpAddress, port, this);
        }
        new Thread(this).start();
    }

    public Flow(ApplicationGui applicationGui, OptionSelection connectionSelection, String serverIpAddress, int port) {
        switch (connectionSelection) {
            case RMI -> clientActions = new RmiClient(serverIpAddress, port, this);
            case SOCKET -> clientActions = new SocketClient(serverIpAddress, port, this);
        }
        this.inputReader = new InputReaderGUI();
        ui = new Gui(applicationGui, (InputReaderGUI) inputReader);
        this.inputProcessor = new InputProcessor(this.inputReader.getQueue(), this);
        new Thread(this).start();
    }

    /**
     * The main run loop for the Flow thread.
     */
    @Override
    public void run() {
        events.add(null, APP_MENU);
        ui.show_GameTitle();
        while (!Thread.interrupted()) {
            try {
                Event event = events.pop();
                if (event != null) {
                    lastEvent = event;
                    processEvent(event);
                    if (event.getModel() != null) {
                        this.gameImmutable = event.getModel();
                    }
                }
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Shows the chat messages.
     */
    public void showChat() {
        if (lastEvent != null && lastEvent.getModel() != null) {
            ui.showChat(lastEvent.getModel());
        } else {
            ui.show_NaNMsg();
        }
    }

    /**
     * Processes an event.
     *
     * @param event the event to process
     * @throws Exception if an error occurs during processing
     */
    private void processEvent(Event event) throws Exception {
        if (event.getType().equals(PLAYER_RECONNECTED) && nickname.equals(lastPlayerReconnected)) {
            handlePlayerReconnection(event);
        } else if (event.getModel() != null) {
            updateGameStateBasedOnModel(event);
        } else {
            statusNotInAGame(event);
        }
    }

    /**
     * Updates the game state based on the event model.
     *
     * @param event the event containing the game model
     * @throws Exception if an error occurs during updating
     */
    private void updateGameStateBasedOnModel(Event event) throws Exception {
        switch (event.getType()) {
            case GAMECREATED -> askGameSize(null);
            default -> {
                switch (event.getModel().getStatus()) {
                    case WAITING, HALTED -> statusWait(event);
                    case STARTING, RUNNING, LASTROUND -> statusRunning(event);
                    case ENDED -> statusEnded(event);
                    default -> throw new IllegalStateException("Unexpected value: " + event.getModel().getStatus());
                }
            }
        }
    }

    /**
     * Handles player reconnection.
     *
     * @param event the event containing the game model
     * @throws Exception if an error occurs during reconnection handling
     */
    private void handlePlayerReconnection(Event event) throws Exception {
        GameImmutable gameImmutable = event.getModel();
        Player player = gameImmutable.getPlayers().stream().filter(p -> p.getNickname().equals(nickname)).collect(Collectors.toList()).get(0);
        ui.setNickname(player.getNickname());
        inputProcessor.setNickname(nickname);
        switch (event.getModel().getStatus()) {
            case STARTING -> {
                switch (player.getAction()) {
                    case FIRSTMOVES -> {
                        if (player.getCodex().getCardStarterInserted()) {
                            askToChooseACardObjective(gameImmutable);
                            if (ended)
                                return;
                        } else {
                            askToPlaceStarterOnCodex(gameImmutable);
                            if (ended)
                                return;
                        }
                    }
                    case WAIT -> ui.showCodex(gameImmutable);
                }
            }
            case RUNNING, LASTROUND, ENDING -> {
                switch (player.getAction()) {
                    case PLACE -> {
                        askToPlaceCardOnCodex(gameImmutable);
                        if (ended)
                            return;
                    }
                    case WAIT -> ui.showCodex(gameImmutable);
                    case DRAW -> {
                        askToChooseADeck(gameImmutable);
                        if (ended)
                            return;
                    }
                }
            }
        }
    }

    /**
     * Handles events when the player is not in a game.
     *
     * @param event the event to process
     * @throws Exception if an error occurs during processing
     */
    private void statusNotInAGame(Event event) throws Exception {
        switch (event.getType()) {
            case APP_MENU -> {
                boolean selectionOk;
                ended = false;
                do {
                    selectionOk = askSelectGame();
                } while (!selectionOk);
            }
            case JOIN_UNABLE_NICKNAME_ALREADY_IN_USE -> {
                ui.showInvalidNickname(nickname);
                nickname = null;
                events.add(null, APP_MENU);
                ui.addImportantEvent("WARNING> Nickname already used!");
            }
            case JOIN_UNABLE_GAME_FULL -> {
                nickname = null;
                events.add(null, APP_MENU);
                ui.addImportantEvent("WARNING> Game is Full!");
            }
            case ERROR_WHEN_ENTERING_GAME -> {
                nickname = null;
                ui.show_returnToMenuMsg();
                try {
                    this.inputProcessor.getDataToProcess().popData();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                events.add(null, APP_MENU);
            }
        }
    }

    /**
     * Handles events when the game state is waiting.
     *
     * @param event the event to process
     * @throws Exception if an error occurs during processing
     */
    private void statusWait(Event event) throws Exception {
        String nickLastPlayer = event.getModel().getPlayers().get(event.getModel().getPlayers().size() - 1).getNickname();
        switch (event.getType()) {
            case PLAYER_JOINED -> {
                if (nickLastPlayer.equals(nickname)) {
                    //ui.show_playerJoined(event.getModel(), nickname);
                }
            }
            case SENT_MESSAGE -> ui.show_sentMessage(event.getModel(), nickname);
        }
    }

    /**
     * Handles events when the game state is running.
     *
     * @param event the event to process
     * @throws Exception if an error occurs during processing
     */
    private void statusRunning(Event event) throws Exception {
        switch (event.getType()) {
            case GAMESTARTED -> {
                ui.show_gameStarted(event.getModel());
                ui.setNickname(nickname);
                this.inputProcessor.setNickname(event.getModel().getPlayers().stream().filter(x -> x.getNickname().equals(nickname)).collect(Collectors.toList()).get(0).getNickname());
                this.inputProcessor.setIdGame(event.getModel().getIdGame());
                askToPlaceStarterOnCodex(event.getModel());
            }
            case PLACE_STARTER_ON_CODEX -> {
                askToPlaceStarterOnCodex(event.getModel());
                ui.showCodex(event.getModel());
            }
            case CHOOSE_OBJECTIVE_CARD -> askToChooseACardObjective(event.getModel());
            case SENT_MESSAGE -> ui.show_sentMessage(event.getModel(), nickname);
            case NEXT_TURN -> {
                String nextTurnPlayer = event.getModel().getPlayers().get(event.getModel().getCurrPlayer()).getNickname();
                if (event.getModel() != null && nextTurnPlayer.equals(nickname)) {
                    ui.showNextTurn(event.getModel(), nextTurnPlayer);
                    events.add(event.getModel(), PLACE_CARD_ON_CODEX);
                } else {
                    ui.showCodex(event.getModel());
                    ui.showNextTurn(event.getModel(), nextTurnPlayer);
                }
            }
            case DRAW_CARD -> {
                if (event.getModel().getPlayers().get(event.getModel().getCurrPlayer()).getNickname().equals(nickname)) {
                    askToChooseADeck(event.getModel());
                    ui.showCodex(event.getModel());
                    //ui.showDrawnCard(event.getModel());
                }
            }
            case PLACE_CARD_ON_CODEX -> {
                if (!event.getModel().getPlayers().get(event.getModel().getCurrPlayer()).getHand().isEmpty() && event.getModel().getPlayers().get(event.getModel().getCurrPlayer()).getNickname().equals(nickname)) {
                    askToPlaceCardOnCodex(event.getModel());
                    ui.showCodex(event.getModel());
                    //ui.showPoints(event.getModel());
                }
            }
            case CARD_CANNOT_BE_PLACED -> {
                if (event.getModel().getPlayers().get(event.getModel().getCurrPlayer()).getNickname().equals(nickname)) {
                    ui.showCardCannotBePlaced(event.getModel(), nickname);
                    events.add(event.getModel(), PLACE_CARD_ON_CODEX);
                }
            }
        }
    }

    /**
     * Handles events when the game state has ended.
     *
     * @param event the event to process
     * @throws NotBoundException, IOException, InterruptedException if an error occurs during processing
     */
    private void statusEnded(Event event) throws NotBoundException, IOException, InterruptedException {
        switch (event.getType()) {
            case GAMEENDED -> {
                ui.showWinner(event.getModel());
                this.inputProcessor.getDataToProcess().popAllData();
                try {
                    this.inputProcessor.getDataToProcess().popData();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                this.leaveGame(nickname);
            }
        }
    }

    /**
     * Method to handle when a player leaves the game.
     */
    public void youLeft() {
        ended = true;
        ui.resetImportantEvents();
        events.add(null, APP_MENU);
        this.inputProcessor.setNickname(null);
        this.inputProcessor.setIdGame(null);
    }

    /**
     * Checks if the game has ended.
     *
     * @return true if the game has ended, false otherwise.
     */
    public boolean isEnded() {
        return ended;
    }

    /**
     * Sets the game to ended or not.
     *
     * @param ended true if the game has ended, false otherwise.
     */
    public void setEnded(boolean ended) {
        this.ended = ended;
    }

    /**
     * Adds an event to the event list.
     *
     * @param event the event to add.
     */
    public void addEvent(Event event) {
        events.add(event.getModel(), event.getType());
    }

    /**
     * Resizes the screen.
     *
     * @param x the new width.
     * @param y the new height.
     */
    public void resizeScreen(int x, int y) {
        if ((x > 0 && y > 0) && (x<501 && y<201)) {
            ui.resizeScreenView(x, y);
        }
    }

    /**
     * Moves the screen view.
     *
     * @param x the number of units to move horizontally.
     * @param y the number of units to move vertically.
     */
    public void moveScreen(int x, int y) {
        ui.moveScreenView(x, y);
    }

    /**
     * Asks the user for their nickname.
     */
    private void askNickname() {
        ui.show_insertNicknameMsg();
        try {
            do {
                nickname = this.inputProcessor.getDataToProcess().popData();
            } while (!nickname.matches("^[a-zA-Z0-9]+$"));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        ui.show_chosenNickname(nickname);
    }

    /**
     * Asks the user to select a game option.
     *
     * @return true if a valid option is selected, false otherwise.
     * @throws Exception if an error occurs during selection.
     */
    private boolean askSelectGame() throws Exception {
        askNickname();
        String optionChoose;
        ended = false;
        ui.show_menuOptions();

        try {
            optionChoose = this.inputProcessor.getDataToProcess().popData();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (optionChoose.equals("."))
            System.exit(1);

        switch (optionChoose) {
            case "c" -> createGame(nickname);
            case "j" -> joinFirstAvailableGame(nickname);
            case "js" -> {
                Integer gameId = askGameId();
                if (gameId == -1)
                    return false;
                else
                    joinSpecificGame(nickname, gameId);
            }
            case "r" -> reconnectToGame(nickname);
            default -> {
                return false;
            }
        }
        return true;
    }

    /**
     * Asks the user for the game size.
     *
     * @param model the game model.
     * @throws Exception if an error occurs during input.
     */
    private void askGameSize(GameImmutable model) throws Exception {
        ui.showAskSize(model);
        boolean sizeValid = false;
        do {
            int size;
            try {
                String input = this.inputProcessor.getDataToProcess().popData();
                size = Integer.parseInt(input);
                if (size > 1 && size <= 4) {
                    sizeValid = true;
                    gameSizeUpdated(size);
                } else {
                    ui.showInvalidInput();
                }
            } catch (NumberFormatException e) {
                ui.showInvalidInput();
            }
        } while (!sizeValid);
    }

    /**
     * Asks the user for the game ID.
     *
     * @return the game ID.
     */
    private Integer askGameId() {
        String temp;
        Integer gameId = null;
        do {
            ui.show_inputGameIdMsg();
            try {
                temp = this.inputProcessor.getDataToProcess().popData();
                if (temp.equals(".")) {
                    return -1;
                }
                gameId = Integer.parseInt(temp);
            } catch (NumberFormatException | InterruptedException e) {
                ui.showInvalidInput();
            }
        } while (gameId == null);
        return gameId;
    }

    /**
     * Asks the user to choose a deck.
     *
     * @param gameModel the game model.
     * @throws Exception if an error occurs during input.
     */
    public void askToChooseADeck(GameImmutable gameModel) throws Exception {
        ui.showDesk(gameModel, nickname);
        ui.showAskToChooseADeck();
        String choice;
        try {
            choice = this.inputProcessor.getDataToProcess().popData();
            switch (choice) {
                case "gD" -> drawCardFromDeck(gameModel.getPlayers().get(gameModel.getCurrPlayer()), DeckType.DECK_GOLD);
                case "g1" -> drawCardDisplayed(gameModel.getPlayers().get(gameModel.getCurrPlayer()), DeckType.DISPLAYED_GOLD, 0);
                case "g2" -> drawCardDisplayed(gameModel.getPlayers().get(gameModel.getCurrPlayer()), DeckType.DISPLAYED_GOLD, 1);
                case "r1" -> drawCardDisplayed(gameModel.getPlayers().get(gameModel.getCurrPlayer()), DeckType.DISPLAYED_RESOURCE, 0);
                case "r2" -> drawCardDisplayed(gameModel.getPlayers().get(gameModel.getCurrPlayer()), DeckType.DISPLAYED_RESOURCE, 1);
                case "rD" -> drawCardFromDeck(gameModel.getPlayers().get(gameModel.getCurrPlayer()), DeckType.DECK_RESOURCE);
                default -> {
                    ui.showInvalidInput();
                    askToChooseADeck(gameModel);
                }
            }
        } catch (NumberFormatException e) {
            ui.showInvalidInput();
            askToChooseADeck(gameModel);
        }
    }

    /**
     * Asks the user to choose a card objective.
     *
     * @param model the game model.
     * @throws Exception if an error occurs during input.
     */
    public void askToChooseACardObjective(GameImmutable model) throws Exception {
        ui.showObjectiveNotChosen(model);
        boolean wrongIndex = true;
        do {
            int index;
            try {
                do {
                    index = Integer.parseInt(this.inputProcessor.getDataToProcess().popData());
                    if (ended) return;
                } while (index != 1 && index != 0 && !ended);
                Player player = model.getPlayers().stream().filter(p -> p.getNickname().equals(this.nickname)).collect(Collectors.toList()).get(0);
                if (index == 1 || index == 0) {
                    selectCardObjective(player, index);
                    wrongIndex = false;
                }
            } catch (NumberFormatException e) {
                ui.showInvalidInput();
            }
        } while (wrongIndex && !ended);
    }

    /**
     * Asks the user to place a card on the codex.
     *
     * @param model the game model.
     * @throws Exception if an error occurs during input.
     */
    public void askToPlaceCardOnCodex(GameImmutable model) throws Exception {
        Integer indexHand;
        do {
            ui.showAskIndex(model);
            try {
                indexHand = Integer.parseInt(this.inputProcessor.getDataToProcess().popData());
                if (ended) return;
                if (indexHand < 0 || indexHand >= model.getPlayers().stream().filter(x -> x.getNickname().equals(nickname)).collect(Collectors.toList()).get(0).getHand().size()) {
                    ui.show_wrongSelectionHandMsg();
                    indexHand = null;
                }
            } catch (NumberFormatException e) {
                ui.showInvalidInput();
                indexHand = null;
            }
        } while (indexHand == null && !ended);
        if (!ended) {
            askSide(model, model.getPlayers().stream().filter(p -> p.getNickname().equals(nickname)).collect(Collectors.toList()).get(0).getHand().get(indexHand));
            askCoordinates(model);
            placeCardOnCodex(model.getPlayers().stream().filter(x -> x.getNickname().equals(nickname)).collect(Collectors.toList()).get(0), indexHand, frontCard, row, col);
            ui.showCodex(model);
        }
    }

    /**
     * Asks the user to choose the side of the starter card.
     *
     * @param model the game model.
     * @return the chosen side.
     * @throws InterruptedException if the thread is interrupted.
     */
    public Side askSideStarter(GameImmutable model) throws InterruptedException {
        ui.show_askSideStarter(model, nickname);
        String choice;
        Side side = null;
        while (side == null && !ended) {
            choice = this.inputProcessor.getDataToProcess().popData();
            switch (choice) {
                case "f" -> side = model.getPlayers().stream().filter(p -> p.getNickname().equals(nickname)).toList().getFirst().getCardStarter().getFrontStarter();
                case "b" -> side = model.getPlayers().stream().filter(p -> p.getNickname().equals(nickname)).toList().getFirst().getCardStarter().getBackStarter();
                default -> ui.showInvalidInput();
            }
        }
        return side;
    }

    /**
     * Asks the user to place the starter card on the codex.
     *
     * @param model the game model.
     * @throws Exception if an error occurs during input.
     */
    public void askToPlaceStarterOnCodex(GameImmutable model) throws Exception {
        Side side;
        do {
            try {
                side = askSideStarter(model);
                if (ended) return;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } while (side == null && !ended);
        if (!ended) {
            Player player = model.getPlayers().stream().filter(x -> x.getNickname().equals(nickname)).findFirst().get();
            placeStarterOnCodex(player, side);
        }
    }

    /**
     * Asks the user for the coordinates to place a card.
     *
     * @param model the game model.
     * @throws InterruptedException if the thread is interrupted.
     */
    public void askCoordinates(GameImmutable model) throws InterruptedException {
        boolean validInput = false;
        while (!validInput && !ended) {
            ui.showAskCoordinates(model);
            String input = this.inputProcessor.getDataToProcess().popData();
            try {
                String[] coordinates = input.split(" ");
                if (coordinates.length == 2) {
                    row = Integer.parseInt(coordinates[0]);
                    col = Integer.parseInt(coordinates[1]);
                    validInput = true;
                } else {
                    ui.showInvalidInput();
                }
            } catch (NumberFormatException e) {
                ui.showInvalidInput();
            }
        }
    }

    /**
     * Asks the user to choose the side of a card.
     *
     * @param model the game model.
     * @param card  the card to place.
     * @throws InterruptedException if the thread is interrupted.
     */
    public void askSide(GameImmutable model, Card card) throws InterruptedException {
        ui.showAskSide(model, card);
        String sideChosen;
        while (true && !ended) {
            sideChosen = this.inputProcessor.getDataToProcess().popData();
            switch (sideChosen) {
                case "f" -> {
                    frontCard = true;
                    return;
                }
                case "b" -> {
                    frontCard = false;
                    return;
                }
                default -> ui.showInvalidInput();
            }
        }
    }

    /**
     * Print a text in the terminal.
     *
     * @param text The text to print.
     */
    public void terminalPrint(String text){
        AsyncPrint.asyncPrint(text+"\n");
    }


    /**
     * Displays a notification for no connection error.
     */
    public void noConnectionError() {
        ui.show_noConnectionError();
        events.clearEventQueue();
    }
    /**
     * Creates a new game with the given nickname.
     *
     * @param nick the nickname of the player creating the game
     */
    @Override
    public void createGame(String nick) {
        ui.show_creatingNewGameMsg(nick);
        try {
            clientActions.createGame(nick);
        } catch (IOException | InterruptedException | NotBoundException e) {
            noConnectionError();
        }
    }

    /**
     * Updates the game size.
     *
     * @param size the new size of the game
     * @throws Exception if an error occurs during updating
     */
    @Override
    public void gameSizeUpdated(int size) throws Exception {
        try {
            clientActions.gameSizeUpdated(size);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Joins a specific game with the given nickname and game ID.
     *
     * @param nick the nickname of the player
     * @param idGame the ID of the game to join
     */
    @Override
    public void joinSpecificGame(String nick, int idGame) {
        ui.show_joiningToGameIdMsg(idGame, nick);
        try {
            clientActions.joinSpecificGame(nick, idGame);
        } catch (Exception e) {
            noConnectionError();
        }
    }

    /**
     * Joins the first available game with the given nickname.
     *
     * @param nick the nickname of the player
     */
    @Override
    public void joinFirstAvailableGame(String nick) {
        ui.show_joiningFirstAvailableMsg(nick);
        try {
            clientActions.joinFirstAvailableGame(nick);
        } catch (Exception e) {
            noConnectionError();
        }
    }

    /**
     * Reconnects to a game with the given nickname.
     *
     * @param nick the nickname of the player
     */
    @Override
    public void reconnectToGame(String nick) {
        ui.show_joiningToGameIdMsg(0, nick);
        try {
            clientActions.reconnectToGame(nickname);
        } catch (Exception e) {
            noConnectionError();
        }
    }

    /**
     * Places a card on the codex.
     *
     * @param player the player placing the card
     * @param index the index of the card in the player's hand
     * @param frontCard true if the front side of the card is chosen, false otherwise
     * @param row the row coordinate for placing the card
     * @param col the column coordinate for placing the card
     * @throws Exception if an error occurs during placing
     */
    @Override
    public void placeCardOnCodex(Player player, int index, boolean frontCard, int row, int col) throws Exception {
        try {
            clientActions.placeCardOnCodex(player, index, frontCard, col, row);
        } catch (IOException e) {
            noConnectionError();
        }
    }

    /**
     * Places the starter card on the codex.
     *
     * @param player the player placing the starter card
     * @param side the side of the starter card to place
     * @throws IOException if an I/O error occurs
     * @throws InterruptedException if the thread is interrupted
     * @throws Exception if an error occurs during placing
     */
    @Override
    public void placeStarterOnCodex(Player player, Side side) throws IOException, InterruptedException, Exception {
        try {
            clientActions.placeStarterOnCodex(player, side);
        } catch (IOException e) {
            noConnectionError();
        }
    }

    /**
     * Selects a card objective.
     *
     * @param player the player selecting the card objective
     * @param cardObjective the index of the card objective to select
     * @throws IOException if an I/O error occurs
     * @throws InterruptedException if the thread is interrupted
     * @throws Exception if an error occurs during selection
     */
    @Override
    public void selectCardObjective(Player player, int cardObjective) throws IOException, InterruptedException, Exception {
        try {
            clientActions.selectCardObjective(player, cardObjective);
        } catch (IOException e) {
            noConnectionError();
        }
    }

    /**
     * Draws a card from a deck.
     *
     * @param player the player drawing the card
     * @param deck the deck to draw the card from
     * @throws IOException if an I/O error occurs
     * @throws InterruptedException if the thread is interrupted
     * @throws Exception if an error occurs during drawing
     */
    @Override
    public void drawCardFromDeck(Player player, DeckType deck) throws IOException, InterruptedException, Exception {
        try {
            clientActions.drawCardFromDeck(player, deck);
        } catch (IOException e) {
            noConnectionError();
        }
    }

    /**
     * Draws a card from the displayed cards.
     *
     * @param player the player drawing the card
     * @param deck the deck of displayed cards
     * @param index the index of the card in the displayed deck
     * @throws IOException if an I/O error occurs
     * @throws InterruptedException if the thread is interrupted
     * @throws Exception if an error occurs during drawing
     */
    @Override
    public void drawCardDisplayed(Player player, DeckType deck, int index) throws IOException, InterruptedException, Exception {
        try {
            clientActions.drawCardDisplayed(player, deck, index);
        } catch (IOException e) {
            noConnectionError();
        }
    }

    /**
     * Sends a chat message.
     *
     * @param msg the chat message to send
     */
    @Override
    public void sendChatMessage(ChatMessage msg) {
        try {
            clientActions.sendChatMessage(msg);
        } catch (RemoteException e) {
            noConnectionError();
        }
    }

    /**
     * Sends a ping to the player.
     *
     * @param player the nickname of the player to ping
     * @throws RemoteException if a remote error occurs
     */
    @Override
    public void sendPing(String player) throws RemoteException {
        try {
            clientActions.sendPing(player);
        } catch (RemoteException e) {
            noConnectionError();
        }
    }

    /**
     * Leaves the game.
     *
     * @param nickname the nickname of the player leaving the game
     */
    @Override
    public void leaveGame(String nickname) {
        try {
            clientActions.leaveGame(nickname);
        } catch (Exception e) {
            noConnectionError();
        }
    }

    /**
     * Handles when a player joins the game.
     *
     * @param gameModel the game model
     */
    @Override
    public void playerJoined(GameImmutable gameModel) {
        events.add(gameModel, PLAYER_JOINED);
        ui.show_playerJoined(gameModel, gameModel.getPlayers().get(gameModel.getPlayers().size() - 1).getNickname());
    }

    /**
     * Handles when a player leaves the game.
     *
     * @param model the game model
     * @param nick the nickname of the player leaving
     * @throws RemoteException if a remote error occurs
     */
    @Override
    public void playerLeft(GameImmutable model, String nick) throws RemoteException {
        if (nick.equals(nickname)) {
            ui.showYouLeft();
            this.youLeft();
        } else {
            ui.addImportantEvent(nick + " has left the game!");
        }
    }

    /**
     * Handles when a player is unable to join a full game.
     *
     * @param gameImmutable the game model
     * @param player the player trying to join
     * @throws RemoteException if a remote error occurs
     */
    @Override
    public void joinUnableGameFull(GameImmutable gameImmutable, Player player) throws RemoteException {
        events.add(null, JOIN_UNABLE_GAME_FULL);
    }

    /**
     * Handles when a player reconnects to the game.
     *
     * @param gameModel the game model
     * @param nickPlayerReconnected the nickname of the player who reconnected
     */
    @Override
    public void playerReconnected(GameImmutable gameModel, String nickPlayerReconnected) {
        lastPlayerReconnected = nickPlayerReconnected;
        events.add(gameModel, PLAYER_RECONNECTED);
        ui.addImportantEvent(lastPlayerReconnected + " has reconnected!");
    }

    /**
     * Handles when a chat message is sent.
     *
     * @param gameModel the game model
     * @param msg the chat message
     */
    @Override
    public void sentChatMessage(GameImmutable gameModel, ChatMessage msg) {
        ui.addMessage(msg, gameModel);
    }

    /**
     * Handles when a game ID does not exist.
     *
     * @param gameid the game ID
     * @throws RemoteException if a remote error occurs
     */
    @Override
    public void gameIdNotExists(int gameid) throws RemoteException {
        ui.show_noAvailableGamesToJoin("NO CURRENT GAME AVAILABLE WITH " + gameid + " ID");
        events.add(null, ERROR_WHEN_ENTERING_GAME);
    }

    /**
     * Handles when the game starts.
     *
     * @param gameModel the game model
     */
    @Override
    public void gameStarted(GameImmutable gameModel) {
        events.add(gameModel, GAMESTARTED);
    }

    /**
     * Handles when the game ends.
     *
     * @param gameModel the game model
     */
    @Override
    public void gameEnded(GameImmutable gameModel) {
        ended = true;
        events.add(gameModel, EventType.GAMEENDED);
        ui.show_gameEnded(gameModel);
    }

    /**
     * Handles the next turn in the game.
     *
     * @param gameModel the game model
     */
    @Override
    public void nextTurn(GameImmutable gameModel) {
        events.add(gameModel, EventType.NEXT_TURN);
        this.inputProcessor.getDataToProcess().popAllData();
    }

    /**
     * Handles when a player disconnects from the game.
     *
     * @param gameModel the game model
     * @param nick the nickname of the player who disconnected
     */
    @Override
    public void playerDisconnected(GameImmutable gameModel, String nick) {
        ui.addImportantEvent(nick + " has just disconnected");
    }

    /**
     * Handles when only one player is connected to the game.
     *
     * @param gameModel the game model
     * @param secondsToWaitUntilGameEnded the number of seconds to wait until the game ends
     * @throws RemoteException if a remote error occurs
     */
    @Override
    public void onlyOnePlayerConnected(GameImmutable gameModel, int secondsToWaitUntilGameEnded) throws RemoteException {
        ui.addImportantEvent("Only one player is connected, waiting " + secondsToWaitUntilGameEnded + " seconds before calling Game Ended!");
    }

    /**
     * Handles when a nickname is already in use.
     *
     * @param player the player trying to join
     * @throws RemoteException if a remote error occurs
     */
    @Override
    public void joinUnableNicknameAlreadyInUse(Player player) throws RemoteException {
        events.add(null, JOIN_UNABLE_NICKNAME_ALREADY_IN_USE);
    }

    /**
     * Handles when the last cycle of the game begins.
     *
     * @param gameModel the game model
     * @throws RemoteException if a remote error occurs
     */
    @Override
    public void lastCircle(GameImmutable gameModel) throws RemoteException {
        ui.addImportantEvent("Last cycle begins!");
    }

    /**
     * Handles when a card is positioned into the codex.
     *
     * @param model the game model
     * @param row the row coordinate of the card
     * @param column the column coordinate of the card
     * @throws RemoteException if a remote error occurs
     */
    @Override
    public void positionedCardIntoCodex(GameImmutable model, int row, int column) throws RemoteException {
        ui.addImportantEvent(model.getPlayers().get(model.getCurrPlayer()).getNickname() + " has placed a card on his Codex");
    }

    /**
     * Handles when a starter card is positioned into the codex.
     *
     * @param model the game model
     * @param nickname the nickname of the player placing the starter card
     * @throws RemoteException if a remote error occurs
     */
    @Override
    public void positionedStarterCardIntoCodex(GameImmutable model, String nickname) throws RemoteException {
        ui.addImportantEvent(nickname + " placed his starter card on the codex.");
    }

    /**
     * Handles when a player gains a point.
     *
     * @param model the game model
     * @param player the player gaining the point
     * @param point the number of points gained
     * @throws RemoteException if a remote error occurs
     */
    @Override
    public void addedPoint(GameImmutable model, Player player, int point) throws RemoteException {
        ui.addImportantEvent(player.getNickname() + " has gained " + point + " point(s) by placing his Card");
    }

    /**
     * Handles when a card objective is chosen.
     *
     * @param model the game model
     * @param cardObjective the chosen card objective
     * @param nickname the nickname of the player choosing the card objective
     * @throws RemoteException if a remote error occurs
     */
    @Override
    public void objectiveCardChosen(GameImmutable model, CardObjective cardObjective, String nickname) throws RemoteException {
        ui.showObjectiveChosen(model, cardObjective, nickname);
    }

    /**
     * Handles when a card objective is not chosen.
     *
     * @param model the game model
     * @throws RemoteException if a remote error occurs
     */
    @Override
    public void objectiveCardNotChosen(GameImmutable model) throws RemoteException {
        events.add(model, CHOOSE_OBJECTIVE_CARD);
    }

    /**
     * Handles when a deck has no more cards.
     *
     * @param model the game model
     * @param deck the deck that is empty
     * @throws RemoteException if a remote error occurs
     */
    @Override
    public void deckHasNoCards(GameImmutable model, ArrayList<? extends Card> deck) throws RemoteException {
        boolean both = false;
        if (model.getDesk().getDeckGold().isEmpty() && model.getDesk().getDeckResource().isEmpty()) {
            ui.addImportantEvent("Resource Deck and GoldDeck are now Empty");
            both = true;
        }
        if (model.getDesk().getDeckResource().isEmpty() && !both) {
            ui.addImportantEvent("ResourceDeck is now Empty");
        }
        if (model.getDesk().getDeckGold().isEmpty() && !both) {
            ui.addImportantEvent("GoldDeck is now Empty");
        }
    }

    /**
     * Handles when a card is added to the player's hand.
     *
     * @param model the game model
     * @param card the card added to the hand
     * @throws RemoteException if a remote error occurs
     */
    @Override
    public void cardAddedToHand(GameImmutable model, Card card) throws RemoteException {
        ui.showCardAddedToHand(model, card);
    }

    /**
     * Handles when end game conditions are reached.
     *
     * @param gameImmutable the game model
     * @throws RemoteException if a remote error occurs
     */
    @Override
    public void endGameConditionsReached(GameImmutable gameImmutable) throws RemoteException {
        ui.addImportantEvent("EndGame Conditions have been reached");
    }

    /**
     * Handles when objective points are added.
     *
     * @param model the game model
     * @param objectivePoint the number of objective points added
     * @throws RemoteException if a remote error occurs
     */
    @Override
    public void addedPointObjective(GameImmutable model, int objectivePoint) throws RemoteException {
        ui.addImportantEvent("ObjectivePoints have been added");
    }

    /**
     * Handles when a winner is declared.
     *
     * @param model the game model
     * @param nickname the nickname of the winner
     * @throws RemoteException if a remote error occurs
     */
    @Override
    public void winnerDeclared(GameImmutable model, ArrayList<String> nickname) throws RemoteException {
        String text = "";
        for(int i = 0; i<nickname.size(); i++){
            text += nickname.get(i)+" ";
        }
        ui.addImportantEvent(text + " won the game.");
    }

    /**
     * Handles when invalid coordinates are provided.
     *
     * @param gameImmutable the game model
     * @param row the row coordinate
     * @param column the column coordinate
     * @throws RemoteException if a remote error occurs
     */
    @Override
    public void invalidCoordinates(GameImmutable gameImmutable, int row, int column) throws RemoteException {
        if (gameImmutable.getPlayers().get(gameImmutable.getCurrPlayer()).getNickname().equals(nickname)) {
            ui.showCardCannotBePlaced(gameImmutable, nickname);
            events.add(gameImmutable, PLACE_CARD_ON_CODEX);
        }
    }

    /**
     * Handles when placement requirements are not respected.
     *
     * @param gameImmutable the game model
     * @param requirementsPlacement the list of requirements not respected
     * @throws RemoteException if a remote error occurs
     */
    @Override
    public void requirementsPlacementNotRespected(GameImmutable gameImmutable, ArrayList<Value> requirementsPlacement) throws RemoteException {
        if (gameImmutable.getPlayers().get(gameImmutable.getCurrPlayer()).getNickname().equals(nickname)) {
            ui.showReqNotRespected(gameImmutable, requirementsPlacement);
            events.add(gameImmutable, PLACE_CARD_ON_CODEX);
        }
    }

    /**
     * Handles when an index is not valid.
     *
     * @param gameImmutable the game model
     * @param index the invalid index
     * @throws RemoteException if a remote error occurs
     */
    @Override
    public void indexNotValid(GameImmutable gameImmutable, int index) throws RemoteException {
        if (gameImmutable.getPlayers().get(gameImmutable.getCurrPlayer()).getNickname().equals(nickname)) {
            ui.addImportantEvent("Index is not valid");
        }
    }

    /**
     * Handles when a card is not added to the player's hand.
     *
     * @param gameImmutable the game model
     * @throws RemoteException if a remote error occurs
     */
    @Override
    public void cardNotAddedToHand(GameImmutable gameImmutable) throws RemoteException {
        if (gameImmutable.getPlayers().get(gameImmutable.getCurrPlayer()).getNickname().equals(nickname)) {
            ui.addImportantEvent("Card not added to hand");
        }
    }

    /**
     * Handles when the game size is updated.
     *
     * @param gameImmutable the game model
     * @param size the new size of the game
     * @throws RemoteException if a remote error occurs
     */
    @Override
    public void gameSizeUpdated(GameImmutable gameImmutable, int size) throws RemoteException {
        ui.show_sizeSetted(size, gameImmutable);
    }

    /**
     * Handles when a card is drawn.
     *
     * @param gameImmutable the game model
     * @param nickname the nickname of the player drawing the card
     * @throws RemoteException if a remote error occurs
     */
    @Override
    public void drawCard(GameImmutable gameImmutable, String nickname) throws RemoteException {
        events.add(gameImmutable, DRAW_CARD);
    }

    /**
     * Handles when there is no game to reconnect to.
     *
     * @param gameImmutable the game model
     * @param nickname the nickname of the player trying to reconnect
     * @throws RemoteException if a remote error occurs
     */
    @Override
    public void noGameToReconnect(GameImmutable gameImmutable, String nickname) throws RemoteException {
        events.add(null, APP_MENU);
        ui.addImportantEvent("There are no available games you can reconnect to");
    }

    /**
     * Handles when a game is created.
     *
     * @param gameImmutable the game model
     * @throws RemoteException if a remote error occurs
     */
    @Override
    public void gameCreated(GameImmutable gameImmutable) throws RemoteException {
        events.add(gameImmutable, EventType.GAMECREATED);
        ui.addImportantEvent("New game created");
    }
}
