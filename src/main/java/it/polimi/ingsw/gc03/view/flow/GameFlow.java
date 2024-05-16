package it.polimi.ingsw.gc03.view.flow;

import it.polimi.ingsw.gc03.controller.GameController;
import it.polimi.ingsw.gc03.controller.MainController;
import it.polimi.ingsw.gc03.model.GameImmutable;
import it.polimi.ingsw.gc03.model.Player;
import it.polimi.ingsw.gc03.model.card.Card;
import it.polimi.ingsw.gc03.model.enumerations.GameStatus;
import it.polimi.ingsw.gc03.networking.rmi.RmiClient;
import it.polimi.ingsw.gc03.networking.rmi.RmiServer;
import it.polimi.ingsw.gc03.networking.socket.client.ClientAction;
import it.polimi.ingsw.gc03.networking.socket.client.SocketClient;
import it.polimi.ingsw.gc03.view.flow.utilities.*;
import it.polimi.ingsw.gc03.view.flow.utilities.events.EventElement;
import it.polimi.ingsw.gc03.view.flow.utilities.events.EventList;
import it.polimi.ingsw.gc03.view.flow.utilities.events.EventType;
import it.polimi.ingsw.gc03.view.tui.Tui;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.gc03.view.flow.utilities.events.EventType.*;

public class GameFlow extends Flow implements Runnable, ClientAction {

    private String nickname;

    private final EventList events = new EventList();

    private ClientAction clientActions;

    private final FileDisconnection fileDisconnection;

    private String lastPlayerReconnected;
    private int columnChosen = -1;
    private final UI ui;




    protected CommandProcessor commandProcessor;
    protected InputReader inputReader;

    protected List<String> importantEvents;
    private boolean ended = false;

    public GameFlow(ConnectionSelection connectionSelection) throws RemoteException {
        //Invoked for starting with TUI
        switch (connectionSelection) {
            case SOCKET -> clientActions = new SocketClient();
            case RMI -> clientActions = new RmiClient();
        }
        ui = new Tui();

        importantEvents = new ArrayList<>();
        nickname = "";
        fileDisconnection = new FileDisconnection();
        this.inputReader = new InputReaderTUI();
        this.commandProcessor = new CommandProcessor(this.inputReader.getBuffer(), this);

        new Thread(this).start();
    }

    public GameFlow(GUIApplication guiApplication, ConnectionSelection connectionSelection) {
        //Invoked for starting with GUI
        switch (connectionSelection) {
            case SOCKET -> clientActions = new SocketClient();
            case RMI -> clientActions = new RmiClient();
        }
        this.inputReader = new InputReaderGUI();

        ui = new GUI(guiApplication, (InputReaderGUI) inputReader);
        importantEvents = new ArrayList<>();
        nickname = "";
        fileDisconnection = new FileDisconnection();

        this.commandProcessor = new CommandProcessor(this.inputReader.getBuffer(), this);
        new Thread(this).start();
    }

    @SuppressWarnings("BusyWait")
    @Override
    public void run() {
        EventElement event;
        events.add(null, APP_MENU);

        while (!Thread.interrupted()) {
            if (events.isJoined()) {
                //Get one event
                event = events.pop();
                if (event != null) {
                    //if something happened
                    switch (event.getModel().getStatus()) {
                        case WAITING,HALTED -> {
                            try {
                                statusWait(event);
                            } catch (IOException | InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        case RUNNING, LASTROUND -> {
                            try {
                                statusRunning(event);
                            } catch (IOException | InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        case ENDED -> statusEnded(event);
                    }
                }
            } else {
                event = events.pop();
                if (event != null) {
                    statusNotInAGame(event);
                }
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void statusNotInAGame(EventElement event) {
        switch (event.getType()) {
            case APP_MENU -> {
                boolean selectionok;
                do {
                    selectionok = askSelectGame();
                } while (!selectionok);
            }

            case JOIN_UNABLE_NICKNAME_ALREADY_IN -> {
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
                    this.commandProcessor.getDataToProcess().popData();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                events.add(null, APP_MENU);
            }
        }
    }
    private void statusWait(EventElement event) throws IOException, InterruptedException {
        String nickLastPlayer = event.getModel().getPlayers().getLast().getNickname();
        //If the event is that I joined then I wait until the user inputs 'y'
        switch (event.getType()) {
            case PLAYER_JOINED -> {
                if (nickLastPlayer.equals(nickname)) {
                    ui.show_playerJoined(event.getModel(), nickname);
                    saveGameId(fileDisconnection, nickname, event.getModel().getIdGame());
                    askReadyToStart();
                }
            }
        }

    }

    private void statusRunning(EventElement event) throws IOException, InterruptedException {
        switch (event.getType()) {
            case GAMESTARTED -> {
                ui.show_gameStarted(event.getModel());
                this.commandProcessor.setPlayer(event.getModel().getPlayers().stream().filter(x->x.getNickname().equals(nickname)).toList().getFirst();
                this.commandProcessor.setIdGame(event.getModel().getIdGame());
            }
            case PLACE_STARTER_ON_CODEX -> {
                ui.showPlaceStarterCardOnCodex(event.getModel());

            }
            case SENT_MESSAGE -> {
                ui.show_sentMessage(event.getModel(), nickname);
            }

            case NEXT_TURN, PLAYER_RECONNECTED -> {
                ui.show_nextTurnOrPlayerReconnected(event.getModel(), nickname);

                columnChosen = -1;

                if (event.getType().equals(PLAYER_RECONNECTED) && lastPlayerReconnected.equals(nickname)) {
                    this.commandProcessor.setPlayer(event.getModel().getPlayers().stream().filter(x->x.getNickname().equals(nickname)).toList().getFirst();
                    this.commandProcessor.setIdGame(event.getModel().getIdGame());
                }

                if (event.getModel().getPlayers().get(event.getModel().getCurrPlayer()).getNickname().equals(nickname)) {

                    if (event.getType().equals(PLAYER_RECONNECTED)) {

                        if (nickname.equals(lastPlayerReconnected)) {
                            placeCardOnCodex(event.getModel());
                            if (ended) return;
                        }
                        //else the player who has just reconnected is not me, and so I do nothing
                    } else {
                        placeCardOnCodex(event.getModel());
                        if (ended) return;
                    }
                }
            }

            case DRAW_CARD -> {

                if (event.getModel().getPlayers().get(event.getModel().getCurrPlayer()).getNickname().equals(nickname)) {
                    //It's my turn, so I'm the current playing
                boolean choosenDeck = false;
                if (!choosenDeck) {
                    askDeckToDrawFrom(event.getModel());
                    if (ended) return;
                }
            }
            case PLACE_CARD_ON_CODEX -> {
                ui.addImportantEvent("Player " + event.getModel().getPlayers().get(event.getModel().getCurrPlayer()).getNickname() + " has positioned a Card on his Codex!");
                if (!event.getModel().getPlayers().get(event.getModel().getCurrPlayer()).getHand().isEmpty() && event.getModel().getPlayers().get(event.getModel().getCurrPlayer()).getNickname().equals(nickname)) {
                    //Ask to place other tiles
                    events.add(event.getModel(), ASK_TO_SELECT_CARD_TO_PLACE);
                }
                ui.showPlacedCard(event.getModel(), nickname);

            }
            case GRABBED_TILE_NOT_CORRECT -> {
                ui.show_grabbedTileNotCorrect(event.getModel(), nickname);

                if (event.getModel().getNicknameCurrentPlaying().equals(nickname)) {
                    columnChosen = -1;
                    askPickTiles(event.getModel());
                }

            }

        }

    }
    private void statusEnded(EventElement event) {
        switch (event.getType()) {
            case GAMEENDED -> {
                ui.show_returnToMenuMsg();
                //new Scanner(System.in).nextLine();
                this.commandProcessor.getDataToProcess().popAllData();
                try {
                    this.commandProcessor.getDataToProcess().popData();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                this.leave(nickname, event.getModel().getGameId());
                this.youLeft();
            }
        }
    }

    public void youLeft() {
        ended = true;
        ui.resetImportantEvents();
        events.add(null, APP_MENU);

        this.commandProcessor.setPlayer(null);
        this.commandProcessor.setIdGame(null);
    }
    public boolean isEnded() {
        return ended;
    }
    public void setEnded(boolean ended) {
        this.ended = ended;
    }

    private void askNickname() {
        ui.show_insertNicknameMsg();
        //nickname = scanner.nextLine();
        try {
            nickname = this.commandProcessor.getDataToProcess().popData();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        ui.show_chosenNickname(nickname);
    }

    /**
     * Ask the player to select a game to join
     *
     * @return ture if the player has selected a game, false otherwise
     */
    private boolean askSelectGame() {
        String optionChoose;
        ended = false;
        ui.show_menuOptions();

        try {
            optionChoose = this.commandProcessor.getDataToProcess().popData();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (optionChoose.equals("."))
            System.exit(1);
        askNickname();

        switch (optionChoose) {
            case "c" -> createGame(nickname);
            case "j" -> joinFirstAvailable(nickname);
            case "js" -> {
                Integer gameId = askGameId();
                if (gameId == -1)
                    return false;
                else
                    joinGame(nickname, gameId);
            }
            case "x" -> reconnect(nickname, fileDisconnection.getLastGameId(nickname));
            default -> {
                return false;
            }
        }

        return true;
    }

    /**
     * Ask the player the game id to join
     *
     * @return the game id
     */
    private Integer askGameId() {
        String temp;
        Integer gameId = null;
        do {
            ui.show_inputGameIdMsg();
            try {
                try {
                    temp = this.commandProcessor.getDataToProcess().popData();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if (temp.equals(".")) {
                    return -1;
                }
                gameId = Integer.parseInt(temp);
            } catch (NumberFormatException e) {
                ui.show_NaNMsg();
            }

        } while (gameId == null);
        return gameId;
    }

    /**
     * Ask the player if it's ready to start the game
     */
    public void askReadyToStart() {
        String ris;
        do {
            try {
                ris = this.commandProcessor.getDataToProcess().popData();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } while (!ris.equals("y"));
        setAsReady();
    }


    /**
     * Asks the player to choose number of tiles to pick up
     *
     * @param msg       message to be shown
     * @param gameModel model where the message needs to be shown
     * @return number of tiles to pick up
     */
    private Integer askNum(String msg, GameModelImmutable gameModel) {
        String temp;
        int numT = -1;
        do {
            try {
                ui.show_askNum(msg, gameModel, nickname);
                //System.out.flush();

                try {
                    temp = this.commandProcessor.getDataToProcess().popData();
                    if (ended) return null;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                numT = Integer.parseInt(temp);
            } catch (InputMismatchException | NumberFormatException e) {
                ui.show_NaNMsg();
            }
        } while (numT < 0);
        return numT;
    }

    /**
     * Asks about the tiles to pick up
     *
     * @param gameModel game model {@link GameModelImmutable}
     */
    public void askPickTiles(GameModelImmutable gameModel) {
        ui.show_askPickTilesMainMsg();
        int numTiles;
        do {
            numTiles = Objects.requireNonNullElse(askNum("> How many tiles do you want to get? ", gameModel), DefaultValue.minNumOfGrabbableTiles - 1);
            if (ended) return;
        } while (!(numTiles >= DefaultValue.minNumOfGrabbableTiles && numTiles <= DefaultValue.maxNumOfGrabbableTiles));

        int row;
        do {
            row = Objects.requireNonNullElse(askNum("> Which tiles do you want to get?\n\t> Choose row: ", gameModel), DefaultValue.PlaygroundSize + 11);
            if (ended) return;
        } while (row > DefaultValue.PlaygroundSize);

        int column;
        do {
            column = Objects.requireNonNullElse(askNum("> Which tiles do you want to get?\n\t> Choose column: ", gameModel), DefaultValue.PlaygroundSize + 1);
            if (ended) return;
        } while (column > DefaultValue.PlaygroundSize);

        //Ask the direction only if the player wants to grab more than 1 tile
        Direction d = Direction.RIGHT;
        if (numTiles > 1) {
            String direction;
            do {
                ui.show_direction();

                try {
                    direction = this.commandProcessor.getDataToProcess().popData();
                    if (ended) return;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                d = Direction.getDirection(direction);
            } while (d == null);
        }

        grabTileFromPlayground(row, column, d, numTiles);
    }

    /**
     * Asks the player which column to place the tiles
     *
     * @param model game model {@link GameModelImmutable}
     */
    private void askColumn(GameModelImmutable model) {
        Integer column;
        ui.show_askColumnMainMsg();
        boolean isColumnBigEnough;
        do {
            isColumnBigEnough = true;
            column = askNum("> Choose column to place all the tiles:", model);
            ui.show_playerHand(model);
            if (ended) return;

            //Check by client side (// to server)
            if (!(model.getPlayerEntity(this.nickname).getNumOfFreeSpacesInCol(column) >= model.getPlayerEntity(this.nickname).getInHandTile_IC().size())) {
                ui.columnShelfTooSmall(model);
                isColumnBigEnough = false;
            }
        } while (column == null || column >= DefaultValue.NumOfColumnsShelf || column < 0 || !isColumnBigEnough);
        columnChosen = column;
    }

    /**
     * Asks the player which tile to place
     *
     * @param model game model {@link GameModelImmutable}
     */
    public void askWhichTileToPlace(GameModelImmutable model) {

        ui.show_whichTileToPlaceMsg();
        Integer indexHand;
        do {
            indexHand = Objects.requireNonNullElse(askNum("\t> Choose Tile in hand (0,1,2):", model), -1);
            ui.show_playerHand(model);
            if (ended) return;
            if (indexHand < 0 || indexHand >= model.getPlayerEntity(nickname).getInHandTile_IC().size()) {
                ui.show_wrongSelectionHandMsg();
                indexHand = null;
            }
        } while (indexHand == null);

        positionTileOnShelf(columnChosen, model.getPlayerEntity(nickname).getInHandTile_IC().get(indexHand).getType());

    }





    /*============ Methods that the client can request to the server ============*/

    /**
     * Throw a nonConnection error
     */
    public void noConnectionError() {
        ui.show_noConnectionError();
    }

    /**
     * The client asks the server to create a new game
     *
     * @param nick nickname of the player
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
     * The client asks the server to join the first available game
     *
     * @param nick nickname of the player
     */
    @Override
    public void joinFirstAvailable(String nick) {
        ui.show_joiningFirstAvailableMsg(nick);
        try {
            clientActions.joinFirstAvailable(nick);
        } catch (IOException | InterruptedException | NotBoundException e) {
            noConnectionError();
        }
    }

    /**
     * The client asks the server to join a specific game
     *
     * @param nick   nickname of the player
     * @param idGame id of the game to join
     */
    @Override
    public void joinGame(String nick, int idGame) {
        ui.show_joiningToGameIdMsg(idGame, nick);
        try {
            clientActions.joinGame(nick, idGame);
        } catch (IOException | InterruptedException | NotBoundException e) {
            noConnectionError();
        }
    }

    /**
     * The client asks the server to reconnect to a specific game
     *
     * @param nick   nickname of the player
     * @param idGame id of the game to reconnect
     */
    @Override
    public void reconnect(String nick, int idGame) {
        //System.out.println("> You have selected to join to Game with id: '" + idGame + "', trying to reconnect");
        if (idGame != -1) {
            ui.show_joiningToGameIdMsg(idGame, nick);
            try {
                clientActions.reconnect(nickname, idGame);
            } catch (IOException | InterruptedException | NotBoundException e) {
                noConnectionError();
            }
        } else {
            ui.show_noAvailableGamesToJoin("No disconnection previously detected");
            try {
                this.commandProcessor.getDataToProcess().popData();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            events.add(null, APP_MENU);
        }
    }

    /**
     * The client asks the server to leave the game
     *
     * @param nick   nickname of the player
     * @param idGame id of the game to leave
     */
    @Override
    public void leave(String nick, int idGame) {
        try {
            clientActions.leave(nick, idGame);
        } catch (IOException | NotBoundException e) {
            noConnectionError();
        }
    }

    /**
     * The client set himself as ready
     */
    @Override
    public void setAsReady() {
        try {
            clientActions.setAsReady();
        } catch (IOException e) {
            noConnectionError();
        }
    }

    @Override
    public boolean isMyTurn() {
        return false;
    }

    /**
     * The client asks the server to grab a tile from the playground
     *
     * @param x         x coordinate of the tile
     * @param y         y coordinate of the tile
     * @param direction direction of the tile {@link Direction}
     * @param num       number of tiles to grab
     */
    @Override
    public void grabTileFromPlayground(int x, int y, Direction direction, int num) {
        try {
            clientActions.grabTileFromPlayground(x, y, direction, num);
        } catch (IOException e) {
            noConnectionError();
        }
    }

    /**
     * The client asks the server to position a tile on the shelf
     *
     * @param column column of the shelf
     * @param type   type of the tile {@link TileType}
     */
    @Override
    public void positionTileOnShelf(int column, TileType type) {
        try {
            clientActions.positionTileOnShelf(column, type);
        } catch (IOException | GameEndedException e) {
            noConnectionError();
        }
    }

    @Override
    public void heartbeat() {

    }

    /**
     * The client asks the server to send a message
     *
     * @param msg message to send {@link Message}
     */
    @Override
    public void sendMessage(Message msg) {
        try {
            clientActions.sendMessage(msg);
        } catch (RemoteException e) {
            noConnectionError();
        }
    }




    /*============ Server event received ============*/

    /**
     * A player has joined the game
     * @param gameModel game model {@link GameModelImmutable}
     */
    @Override
    public void playerJoined(GameModelImmutable gameModel) {
        //shared.setLastModelReceived(gameModel);
        events.add(gameModel, PLAYER_JOINED);

        //Print also here because: If a player is in askReadyToStart is blocked and cannot showPlayerJoined by watching the events
        ui.show_playerJoined(gameModel, nickname);

    }

    /**
     * A player has left the game
     * @param gamemodel game model {@link GameModelImmutable}
     * @param nick nickname of the player
     * @throws RemoteException if the reference could not be accessed
     */
    @Override
    public void playerLeft(GameModelImmutable gamemodel, String nick) throws RemoteException {
        if (gamemodel.getStatus().equals(GameStatus.WAIT)) {
            ui.show_playerJoined(gamemodel, nickname);
        } else {
            ui.addImportantEvent("[EVENT]: Player " + nick + " decided to leave the game!");
        }

    }

    /**
     * A player wanted to join a game but the game is full
     * @param wantedToJoin player that wanted to join
     * @param gameModel game model {@link GameModelImmutable}
     * @throws RemoteException if the reference could not be accessed
     */
    @Override
    public void joinUnableGameFull(Player wantedToJoin, GameModelImmutable gameModel) throws RemoteException {
        events.add(null, JOIN_UNABLE_GAME_FULL);
    }

    /**
     * A player reconnected to the game
     * @param gameModel game model {@link GameModelImmutable}
     * @param nickPlayerReconnected nickname of the player
     */
    @Override
    public void playerReconnected(GameModelImmutable gameModel, String nickPlayerReconnected) {
        lastPlayerReconnected = nickPlayerReconnected;
        events.add(gameModel, PLAYER_RECONNECTED);
        ui.addImportantEvent("[EVENT]: Player reconnected!");
        //events.add(gameModel, EventType.PLAYER_JOINED);
    }

    /**
     * A player has sent a message
     * @param gameModel game model {@link GameModelImmutable}
     * @param msg message sent {@link Message}
     */
    @Override
    public void sentMessage(GameModelImmutable gameModel, Message msg) {
        //Show the message only if is for everyone or is for me (or I sent it)
        if (msg.whoIsReceiver().equals("*") || msg.whoIsReceiver().equalsIgnoreCase(nickname) || msg.getSender().getNickname().equalsIgnoreCase(nickname)) {
            ui.addMessage(msg, gameModel);
            events.add(gameModel, SENT_MESSAGE);
            //msg.setText("[PRIVATE]: " + msg.getText());
        }
    }

    /**
     * A player wanted to join a game but the nickname is already in
     * @param wantedToJoin player that wanted to join {@link Player}
     * @throws RemoteException if the reference could not be accessed
     */
    @Override
    public void joinUnableNicknameAlreadyIn(Player wantedToJoin) throws RemoteException {
        //System.out.println("[EVENT]: "+ wantedToJoin.getNickname() + " has already in");
        events.add(null, JOIN_UNABLE_NICKNAME_ALREADY_IN);
    }

    /**
     * A player wanted to join a game but the gameID is not valid
     * @param gameid game id
     * @throws RemoteException if the reference could not be accessed
     */
    @Override
    public void gameIdNotExists(int gameid) throws RemoteException {
        ui.show_noAvailableGamesToJoin("No currently game available with the following GameID: " + gameid);
        events.add(null, GENERIC_ERROR_WHEN_ENTRYING_GAME);
    }

    /**
     * Generic error when entering a game
     * @param why why the error occurred
     * @throws RemoteException if the reference could not be accessed
     */
    @Override
    public void genericErrorWhenEnteringGame(String why) throws RemoteException {
        ui.show_noAvailableGamesToJoin(why);
        events.add(null, GENERIC_ERROR_WHEN_ENTRYING_GAME);
    }

    /**
     * A player is ready to start
     * @param gameModel game model {@link GameImmutable}
     * @param nick nickname of the player
     * @throws IOException if the reference could not be accessed
     */
    @Override
    public void playerIsReadyToStart(GameImmutable gameModel, String nick) throws IOException {
        ui.show_playerJoined(gameModel, nickname);

        if (nick.equals(nickname)) {
            ui.show_youReadyToStart(gameModel, nickname);
        }
        // if(nick.equals(nickname))
        //    toldIAmReady=true;
        events.add(gameModel, PLAYER_IS_READY_TO_START);
    }

    /**
     * Common cards are extracted
     * @param gameModel game model {@link GameImmutable}
     * @throws RemoteException if the reference could not be accessed
     */
    @Override
    public void commonCardsExtracted(GameImmutable gameModel) throws RemoteException {
        events.add(gameModel, EventType.COMMON_CARD_EXTRACTED);
    }

    /**
     * The game started
     * @param gameModel game model {@link GameImmutable}
     */
    @Override
    public void gameStarted(GameImmutable gameModel) {
        events.add(gameModel, GAMESTARTED);
    }

    /**
     * The game ended
     * @param gameModel game model {@link GameImmutable}
     */
    @Override
    public void gameEnded(GameImmutable gameModel) {
        ended = true;
        events.add(gameModel, EventType.GAMEENDED);
        ui.show_gameEnded(gameModel);
        resetGameId(fileDisconnection, gameModel);

    }

    /**
     * A tile has been grabbed
     * @param gameModel game model {@link GameModelImmutable}
     */
    @Override
    public void grabbedTile(GameModelImmutable gameModel) {
        events.add(gameModel, EventType.GRABBED_TILE);
    }

    /**
     * A tile has not been grabbed correctly
     * @param gameModel game model {@link GameModelImmutable}
     */
    @Override
    public void grabbedTileNotCorrect(GameModelImmutable gameModel) {
        events.add(gameModel, EventType.GRABBED_TILE_NOT_CORRECT);
        ui.addImportantEvent("[EVENT]: A set of not grabbable tiles has been requested by Player: " + gameModel.getNicknameCurrentPlaying());
    }

    /**
     * A tile has been positioned
     * @param gameModel game model {@link GameModelImmutable}
     * @param type type of the tile {@link TileType}
     * @param column column where the tile has been positioned
     */
    @Override
    public void positionedTile(GameModelImmutable gameModel, TileType type, int column) {
        events.add(gameModel, EventType.POSITIONED_TILE);
    }

    /**
     * It adds the NextTurn event to the event list
     * @param gameModel game model {@link GameModelImmutable}
     */
    @Override
    public void nextTurn(GameModelImmutable gameModel) {
        events.add(gameModel, EventType.NEXT_TURN);

        //I remove all the input that the user sends when It is not his turn
        this.commandProcessor.getDataToProcess().popAllData();
    }

    /**
     * Points have been added
     * @param p player {@link Player}
     * @param point point {@link Point}
     * @param gamemodel game model {@link GameImmutable}
     */
    @Override
    public void addedPoint(Player p, int point, GameImmutable gamemodel) {
        ui.addImportantEvent("Player " + p.getNickname() + " obtained " + point.getPoint() + " points by achieving " + point.getReferredTo());
        ui.show_addedPoint(p, point, gamemodel);
    }

    /**
     * A player has been disconnected
     * @param gameModel game model {@link GameImmutable}
     * @param nick nickname of the player
     */
    @Override
    public void playerDisconnected(GameImmutable gameModel, String nick) {
        ui.addImportantEvent("Player " + nick + " has just disconnected");

        //Print also here because: If a player is in askReadyToStart is blocked and cannot showPlayerJoined by watching the events
        if (gameModel.getStatus().equals(GameStatus.WAITING)) {
            ui.show_playerJoined(gameModel, nickname);
        }
    }

    /**
     * A column shelf is too small to place all the tiles
     * @param gameModel game model {@link GameImmutable}
     * @param column  column where the tiles should be placed
     * @throws RemoteException if the reference could not be accessed
     */
    @Override
    public void columnShelfTooSmall(GameImmutable gameModel, int column) throws RemoteException {
        ui.addImportantEvent("Cannot place Tiles in " + column + " column because there are no spaces available to place all");
    }

    /**
     * Only one player is connected
     * @param gameModel game model {@link GameImmutable}
     * @param secondsToWaitUntilGameEnded seconds to wait until the game ends
     * @throws RemoteException if the reference could not be accessed
     */
    @Override
    public void onlyOnePlayerConnected(GameImmutable gameModel, int secondsToWaitUntilGameEnded) throws RemoteException {
        ui.addImportantEvent("Only one player is connected, waiting " + secondsToWaitUntilGameEnded + " seconds before calling Game Ended!");
    }

    /**
     * Last circle begins
     * @param gameModel game model {@link GameImmutable}
     * @throws RemoteException if the reference could not be accessed
     */
    @Override
    public void lastCircle(GameImmutable gameModel) throws RemoteException {
        ui.addImportantEvent("Last cycle begins!");
    }


    /*==Testing purpose==*/
    @Deprecated
    public BufferData getBuffer_ForTesting() {
        return this.inputReader.getBuffer();
    }

    @Deprecated
    public boolean isEnded_ForTesting() {
        return this.ended;
    }
}
