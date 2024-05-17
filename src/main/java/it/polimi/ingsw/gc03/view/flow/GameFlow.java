package it.polimi.ingsw.gc03.view.flow;

import it.polimi.ingsw.gc03.model.ChatMessage;
import it.polimi.ingsw.gc03.model.GameImmutable;
import it.polimi.ingsw.gc03.model.Player;
import it.polimi.ingsw.gc03.model.card.Card;
import it.polimi.ingsw.gc03.model.card.cardObjective.CardObjective;
import it.polimi.ingsw.gc03.model.enumerations.GameStatus;
import it.polimi.ingsw.gc03.model.enumerations.Value;
import it.polimi.ingsw.gc03.model.side.Side;
import it.polimi.ingsw.gc03.networking.rmi.RmiClient;
import it.polimi.ingsw.gc03.networking.socket.client.ClientAction;
import it.polimi.ingsw.gc03.networking.socket.client.SocketClient;
import it.polimi.ingsw.gc03.view.flow.utilities.*;
import it.polimi.ingsw.gc03.view.flow.utilities.events.EventElement;
import it.polimi.ingsw.gc03.view.flow.utilities.events.EventList;
import it.polimi.ingsw.gc03.view.flow.utilities.events.EventType;
import it.polimi.ingsw.gc03.view.tui.Tui;

import java.io.IOException;
import java.rmi.NotBoundException;
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
    private int row;
    private int col;

    private boolean frontCard;
    private final UI ui;

    private boolean chosenDeck;




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
                            } catch (Exception e) {
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
            case SENT_MESSAGE -> {
                ui.show_sentMessage(event.getModel(), nickname);
            }
        }

    }

    private void statusRunning(EventElement event) throws Exception {
        switch (event.getType()) {
            case GAMESTARTED -> {
                ui.show_gameStarted(event.getModel());
                this.commandProcessor.setPlayer(event.getModel().getPlayers().stream().filter(x->x.getNickname().equals(nickname)).toList().getFirst());
                this.commandProcessor.setIdGame(event.getModel().getIdGame());
            }
            case PLACE_STARTER_ON_CODEX -> {
                ui.showPlaceStarterCardOnCodex(event.getModel());

            }
            case SENT_MESSAGE -> {
                ui.show_sentMessage(event.getModel(), nickname);
            }

            /*case NEXT_TURN, PLAYER_RECONNECTED -> {
                ui.show_nextTurnOrPlayerReconnected(event.getModel(), nickname);

                columnChosen = -1;

                if (event.getType().equals(PLAYER_RECONNECTED) && lastPlayerReconnected.equals(nickname)) {
                    this.commandProcessor.setPlayer(event.getModel().getPlayers().stream().filter(x->x.getNickname().equals(nickname)).toList().getFirst();
                    this.commandProcessor.setIdGame(event.getModel().getIdGame());
                }

                if (event.getModel().getPlayers().get(event.getModel().getCurrPlayer()).getNickname().equals(nickname)) {

                    if (event.getType().equals(PLAYER_RECONNECTED)) {

                        if (nickname.equals(lastPlayerReconnected)) {
                            askToPlaceCardOnCodex(event.getModel());
                            if (ended) return;
                        }
                        //else the player who has just reconnected is not me, and so I do nothing
                    } else {
                        askToPlaceCardOnCodex(event.getModel());
                        if (ended) return;
                    }
                }
            }

             */

            case DRAW_FROM_CHOSEN_DECK ->{
                if (event.getModel().getPlayers().get(event.getModel().getCurrPlayer()).getNickname().equals(nickname)) {
                   askToChooseADeck(event.getModel());
                   ui.showDrawnCard(event.getModel());
                }
            }
            case PLACE_CARD_ON_CODEX -> {
                ui.addImportantEvent("Player " + event.getModel().getPlayers().get(event.getModel().getCurrPlayer()).getNickname() + " has positioned a Card on his Codex!");
                if (!event.getModel().getPlayers().get(event.getModel().getCurrPlayer()).getHand().isEmpty() && event.getModel().getPlayers().get(event.getModel().getCurrPlayer()).getNickname().equals(nickname)) {
                    askToPlaceCardOnCodex(event.getModel());
                    ui.showPlacedCard(event.getModel(), nickname);
                }
            }
            case CARD_CANNOT_BE_PLACED -> {
                ui.showCardCannotBePlaced(event.getModel(), nickname);

                if (event.getModel().getPlayers().get(event.getModel().getCurrPlayer()).getNickname().equals(nickname)) {
                    events.add(event.getModel(),PLACE_CARD_ON_CODEX);
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

                this.leave(nickname, event.getModel().getIdGame());
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
     * Asks about the tiles to pick up
     *
     * @param gameModel game model {@link GameImmutable}
     */
    public void askToChooseADeck(GameImmutable gameModel) throws Exception {
        ui.showAskToChooseADeck();
        String choice;
        choice = this.commandProcessor.getDataToProcess().popData();
        switch(choice){
            case "gD" ->{
                drawCardFromDeck(gameModel.getPlayers().get(gameModel.getCurrPlayer()),gameModel.getDesk().getDeckGold());
            }
            case "g" ->{
                ui.showDisplayedGold(gameModel);
                boolean wrongIndex = true;
                do {
                    int index;
                    ui.showAskIndex(gameModel);
                    index = Integer.parseInt(this.commandProcessor.getDataToProcess().popData());
                    if(index==1 || index==0) {
                        drawCardDisplayed(gameModel.getPlayers().get(gameModel.getCurrPlayer()), gameModel.getDesk().getDisplayedGold(), index);
                        wrongIndex = false;
                    }
                }while(wrongIndex);
            }
            case "rD" ->{
                drawCardFromDeck(gameModel.getPlayers().get(gameModel.getCurrPlayer()),gameModel.getDesk().getDeckResource());
            }
            case "r" ->{
                ui.showDisplayedResource(gameModel);
                boolean wrongIndex = true;
                do {
                    int index;
                    ui.showAskIndex(gameModel);
                    index = Integer.parseInt(this.commandProcessor.getDataToProcess().popData());
                        if(index==1 || index==0) {
                            drawCardDisplayed(gameModel.getPlayers().get(gameModel.getCurrPlayer()), gameModel.getDesk().getDisplayedResource(), index);
                            wrongIndex = false;
                        }
                }while(wrongIndex);
            }
            default->{
                ui.invalidChoice();
                askToChooseADeck(gameModel);
            }
        }
    }

    /**
     * Asks the player which card to place and where
     *
     * @param model game model {@link GameImmutable}
     */
    public void askToPlaceCardOnCodex(GameImmutable model) throws Exception {

        ui.show_playerHand(model);
        Integer indexHand;
        do {
            ui.showAskIndex(model);
            indexHand = Integer.parseInt(this.commandProcessor.getDataToProcess().popData());
            ui.show_playerHand(model);
            if (ended) return;
            if (indexHand < 0 || indexHand >= model.getPlayers().stream().filter(x->x.getNickname().equals(nickname)).toList().getFirst().getHand().size()) {
                ui.show_wrongSelectionHandMsg();
                indexHand = null;
            }
        } while (indexHand == null);
        askCoordinates(model);
        askSide(model);
        placeCardOnCodex(model.getPlayers().stream().filter(x->x.getNickname().equals(nickname)).toList().getFirst(),indexHand,frontCard,row,col);
    }

    public void askCoordinates(GameImmutable model) throws InterruptedException {
        ui.showAskCoordinatesRow(model);
        row = Integer.parseInt(this.commandProcessor.getDataToProcess().popData());
        ui.showAskCoordinatesCol(model);
        col = Integer.parseInt(this.commandProcessor.getDataToProcess().popData());
    }

    public void askSide(GameImmutable model) throws InterruptedException {
        ui.show_askSide(model);
        String side;
        side = this.commandProcessor.getDataToProcess().popData();
        switch(side){
            case "f"->{
                frontCard = true;
            }
            case "b"->{
                frontCard = false;
            }
            default->{
                ui.invalidChoice();
                askSide(model);
            }
        }

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

    @Override
    public void joinFirstAvailableGame(String nickname) throws IOException, InterruptedException, NotBoundException {

    }

    @Override
    public void joinSpecificGame(String nickname, int idGame) throws IOException, InterruptedException, NotBoundException {

    }

    @Override
    public void leaveGame(String nickname, int idGame) throws IOException, InterruptedException, NotBoundException {

    }

    @Override
    public void reconnectToGame(String nickname, int idGame) throws IOException, InterruptedException, NotBoundException {

    }

    @Override
    public void placeStarterOnCodex(Player player, Side side) throws IOException, InterruptedException, Exception {

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
        } catch (Exception e) {
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
        } catch (Exception e) {
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
            } catch (Exception e) {
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
        } catch (Exception e) {
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



    @Override
    public void placeCardOnCodex(Player player,int index, boolean frontCard, int row,int col) throws Exception {
        try {
            clientActions.placeCardOnCodex(player,index,frontCard,row,col);
        } catch (IOException e) {
            noConnectionError();
        }
    }

    @Override
    public void selectCardObjective(Player player, int cardObjective) throws IOException, InterruptedException, Exception {

    }

    @Override
    public void drawCardFromDeck(Player player, ArrayList<? extends Card> deck) throws IOException, InterruptedException, Exception {

    }

    @Override
    public void drawCardDisplayed(Player player, ArrayList<? extends Card> deck, int index) throws IOException, InterruptedException, Exception {

    }

    /**
     * The client asks the server to send a message
     *
     * @param msg message to send
     */
    @Override
    public void sendChatMessage(ChatMessage msg) {
        try {
            clientActions.sendChatMessage(msg);
        } catch (RemoteException e) {
            noConnectionError();
        }
    }

    @Override
    public void ping() throws RemoteException {

    }




    /*============ Server event received ============*/

    /**
     * A player has joined the game
     * @param gameModel game model
     */
    @Override
    public void playerJoined(GameImmutable gameModel) {
        //shared.setLastModelReceived(gameModel);
        events.add(gameModel, PLAYER_JOINED);

        //Print also here because: If a player is in askReadyToStart is blocked and cannot showPlayerJoined by watching the events
        ui.show_playerJoined(gameModel, nickname);

    }

    /**
     * A player has left the game
     * @param gamemodel game model
     * @param nick nickname of the player
     * @throws RemoteException if the reference could not be accessed
     */
    @Override
    public void playerLeft(GameImmutable gamemodel, String nick) throws RemoteException {
        if (gamemodel.getStatus().equals(GameStatus.WAITING)) {
            ui.show_playerJoined(gamemodel, nickname);
        } else {
            ui.addImportantEvent("[EVENT]: Player " + nick + " decided to leave the game!");
        }

    }

    @Override
    public void joinUnableGameFull(GameImmutable gameImmutable, Player player) throws RemoteException {
        events.add(null, JOIN_UNABLE_GAME_FULL);
    }
    /**
     * A player reconnected to the game
     * @param gameModel game model
     * @param nickPlayerReconnected nickname of the player
     */
    @Override
    public void playerReconnected(GameImmutable gameModel, String nickPlayerReconnected) {
        lastPlayerReconnected = nickPlayerReconnected;
        events.add(gameModel, PLAYER_RECONNECTED);
        ui.addImportantEvent("[EVENT]: Player reconnected!");
        //events.add(gameModel, EventType.PLAYER_JOINED);
    }

    /**
     * A player has sent a message
     * @param gameModel game model
     * @param msg message sent
     */
    @Override
    public void sentMessage(GameImmutable gameModel, ChatMessage msg) {
        //Show the message only if is for everyone or is for me (or I sent it)

        ui.addMessage(msg, gameModel);
        events.add(gameModel, SENT_MESSAGE);
        //msg.setText("[PRIVATE]: " + msg.getText());
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
        events.add(null, ERROR_WHEN_ENTERING_GAME);
    }

    /**
     * Generic error when entering a game
     * @param why why the error occurred
     * @throws RemoteException if the reference could not be accessed
     */
    @Override
    public void genericErrorWhenEnteringGame(String why) throws RemoteException {
        ui.show_noAvailableGamesToJoin(why);
        events.add(null, ERROR_WHEN_ENTERING_GAME);
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

    @Override
    public void sentChatMessage(GameImmutable gameImmutable, ChatMessage chatMessage) throws RemoteException {

    }

    /**
     * A card has been drawn
     * @param gameModel game model
     */
    @Override
    public void drawCard(GameImmutable gameModel) {
        events.add(gameModel, DRAW_FROM_CHOSEN_DECK);
    }

    /**
     * It adds the NextTurn event to the event list
     * @param gameModel game model
     */
    @Override
    public void nextTurn(GameImmutable gameModel) {
        events.add(gameModel, EventType.NEXT_TURN);

        //I remove all the input that the user sends when It is not his turn
        this.commandProcessor.getDataToProcess().popAllData();
    }

    /**
     * Points have been added
     * @param p player
     * @param point point
     * @param gamemodel game model
     */
    @Override
    public void addedPoint(Player p, int point, GameImmutable gamemodel) {
        ui.addImportantEvent("Player " + p.getNickname() + " obtained " + point + " points");
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
     * Only one player is connected
     * @param gameModel game model {@link GameImmutable}
     * @param secondsToWaitUntilGameEnded seconds to wait until the game ends
     * @throws RemoteException if the reference could not be accessed
     */
    @Override
    public void onlyOnePlayerConnected(GameImmutable gameModel, int secondsToWaitUntilGameEnded) throws RemoteException {
        ui.addImportantEvent("Only one player is connected, waiting " + secondsToWaitUntilGameEnded + " seconds before calling Game Ended!");
    }

    @Override
    public void joinUnableNicknameAlreadyInUse(Player player) throws RemoteException {

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

    @Override
    public void positionedCardIntoCodex(GameImmutable gameImmutable, int row, int column) throws RemoteException {

    }

    @Override
    public void positionedStarterCardIntoCodex(GameImmutable gameImmutable) throws RemoteException {

    }

    @Override
    public void invalidCoordinates(GameImmutable gameImmutable, int row, int column) throws RemoteException {

    }

    @Override
    public void requirementsPlacementNotRespected(GameImmutable gameImmutable, ArrayList<Value> requirementsPlacement) throws RemoteException {

    }

    @Override
    public void addedPoint(GameImmutable gameImmutable, Player player, int point) throws RemoteException {

    }

    @Override
    public void objectiveCardChosen(GameImmutable gameImmutable, CardObjective cardObjective) throws RemoteException {

    }

    @Override
    public void objectiveCardNotChosen(GameImmutable gameImmutable) throws RemoteException {

    }

    @Override
    public void indexNotValid(GameImmutable gameImmutable, int index) throws RemoteException {

    }

    @Override
    public void deckHasNoCards(GameImmutable gameImmutable, ArrayList<? extends Card> deck) throws RemoteException {

    }

    @Override
    public void cardAddedToHand(GameImmutable gameImmutable, Card card) throws RemoteException {

    }

    @Override
    public void cardNotAddedToHand(GameImmutable gameImmutable) throws RemoteException {

    }

    @Override
    public void endGameConditionsReached(GameImmutable gameImmutable) throws RemoteException {

    }

    @Override
    public void addedPointObjective(GameImmutable gameImmutable, int objectivePoint) throws RemoteException {

    }

    @Override
    public void winnerDeclared(GameImmutable gameImmutable, ArrayList<String> nickname) throws RemoteException {

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
