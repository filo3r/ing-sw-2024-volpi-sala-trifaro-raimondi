package it.polimi.ingsw.gc03.view.ui;

import it.polimi.ingsw.gc03.listeners.GameListener;
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
import it.polimi.ingsw.gc03.view.OptionSelection;
import it.polimi.ingsw.gc03.view.gui.Gui;
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

import static it.polimi.ingsw.gc03.view.ui.events.EventType.*;

public class Flow implements Runnable, ClientAction, GameListener {

    private String nickname;

    private final EventList events = new EventList();

    private ClientAction clientActions;
    private String lastPlayerReconnected;
    private int row;
    private int col;

    private boolean frontCard;
    private UI ui;

    protected InputProcessor inputProcessor;
    protected InputReader inputReader;

    protected List<String> importantEvents;
    private boolean ended = false;

    public Flow(OptionSelection uiSelection, OptionSelection connectionSelection, String serverIpAddress, int port) throws InterruptedException {
        switch(uiSelection){
            case OptionSelection.GUI ->{
                ui = new Gui();
                this.inputReader = new InputReaderGUI();
                this.inputProcessor = new InputProcessor(this.inputReader.getQueue(),this);
            }

            case OptionSelection.TUI ->{
                ui = new Tui(180, 15);
                this.inputReader = new InputReaderTUI();
                this.inputProcessor = new InputProcessor(this.inputReader.getQueue(),this);
            }
        }
        switch(connectionSelection){
            case OptionSelection.RMI->{
                clientActions = new RmiClient(serverIpAddress, port, this);
            }
            case OptionSelection.SOCKET ->{
                clientActions = new SocketClient(serverIpAddress, port, this);
            }
        }
        new Thread(this).start();
    }


    @SuppressWarnings("BusyWait")
    @Override
    public void run() {
        Event event;
        events.add(null, APP_MENU);
        ui.show_GameTitle();
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
                        case STARTING, RUNNING, LASTROUND -> {
                            try {
                                statusRunning(event);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                        case ENDED -> {
                            try {
                                statusEnded(event);
                            } catch (NotBoundException e) {
                                throw new RuntimeException(e);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
            } else {
                event = events.pop();
                if (event != null) {
                    try {
                        statusNotInAGame(event);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void statusNotInAGame(Event event) throws Exception {
        switch (event.getType()) {
            case APP_MENU -> {
                boolean selectionok;
                do {
                    selectionok = askSelectGame();
                } while (!selectionok);
            }

            case JOIN_UNABLE_NICKNAME_ALREADY_IN_USE -> {
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
    private void statusWait(Event event) throws IOException, InterruptedException {
        String nickLastPlayer = event.getModel().getPlayers().getLast().getNickname();
        switch (event.getType()) {
            case PLAYER_JOINED -> {
                if (nickLastPlayer.equals(nickname)) {
                    //ui.show_playerJoined(event.getModel(), nickname);
                }
            }
            case SENT_MESSAGE -> {
                ui.show_sentMessage(event.getModel(), nickname);
            }
        }

    }

    private void statusRunning(Event event) throws Exception {
        switch (event.getType()) {
            case GAMESTARTED -> {
                ui.show_gameStarted(event.getModel());
                this.inputProcessor.setPlayer(event.getModel().getPlayers().stream().filter(x->x.getNickname().equals(nickname)).toList().getFirst());
                this.inputProcessor.setIdGame(event.getModel().getIdGame());
            }
            case PLACE_STARTER_ON_CODEX -> {
                askToPlaceStarterOnCodex(event.getModel());
                ui.showPlaceStarterCardOnCodex(event.getModel());
            }
            case COMMON_CARDS_EXTRACTED -> {
                ui.showCommonCards(event.getModel());
            }
            case CHOOSE_OBJECTIVE_CARD->{
                askToChooseACardObjective(event.getModel(), nickname);
            }
            case SENT_MESSAGE -> {
                ui.show_sentMessage(event.getModel(), nickname);
            }

            case NEXT_TURN, PLAYER_RECONNECTED -> {
                ui.show_nextTurnOrPlayerReconnected(event.getModel(), nickname);
                if (event.getType().equals(PLAYER_RECONNECTED) && lastPlayerReconnected.equals(nickname)) {
                    this.inputProcessor.setPlayer(event.getModel().getPlayers().stream().filter(x->x.getNickname().equals(nickname)).toList().getFirst());
                    this.inputProcessor.setIdGame(event.getModel().getIdGame());
                }

                if (event.getModel().getPlayers().get(event.getModel().getCurrPlayer()).getNickname().equals(nickname)) {

                    if (event.getType().equals(PLAYER_RECONNECTED)) {

                        if (nickname.equals(lastPlayerReconnected)) {
                            askToPlaceCardOnCodex(event.getModel());
                            if (ended) return;
                        }
                        //else the player who has just reconnected is not me, and so I do nothing
                    } else {
                        if (ended) return;
                    }
                }
            }

            case DRAW_CARD ->{
                if (event.getModel().getPlayers().get(event.getModel().getCurrPlayer()).getNickname().equals(nickname)) {
                   askToChooseADeck(event.getModel());
                   ui.showDrawnCard(event.getModel());
                }
            }
            case PLACE_CARD_ON_CODEX -> {
                if (!event.getModel().getPlayers().get(event.getModel().getCurrPlayer()).getHand().isEmpty() && event.getModel().getPlayers().get(event.getModel().getCurrPlayer()).getNickname().equals(nickname)) {
                    askToPlaceCardOnCodex(event.getModel());
                    ui.showCodex(event.getModel());
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
    private void statusEnded(Event event) throws NotBoundException, IOException, InterruptedException {
        switch (event.getType()) {
            case GAMEENDED -> {
                ui.showWinner(event.getModel());
                ui.show_returnToMenuMsg();
                //new Scanner(System.in).nextLine();
                this.inputProcessor.getDataToProcess().popAllData();
                try {
                    this.inputProcessor.getDataToProcess().popData();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                this.youLeft();
            }
        }
    }

    public void youLeft() {
        ended = true;
        ui.resetImportantEvents();
        events.add(null, APP_MENU);

        this.inputProcessor.setPlayer(null);
        this.inputProcessor.setIdGame(null);
    }
    public boolean isEnded() {
        return ended;
    }
    public void setEnded(boolean ended) {
        this.ended = ended;
    }

    /**
     * Ask to choose a Nickname
     */
    private void askNickname() {
        ui.show_insertNicknameMsg();
        try {
            nickname = this.inputProcessor.getDataToProcess().popData();
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
            case "c" -> {
                createGame(nickname);
                askGameSize(null);
            }
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
     * Asks to set the GameSize
     * @param model
     * @throws InterruptedException
     */
    private void askGameSize(GameImmutable model) throws Exception {
        ui.showAskSize(model);
        boolean sizeValid = false;
        do{
            int size;
            size = Integer.parseInt(this.inputProcessor.getDataToProcess().popData());
            if(size>1 && size<=4){
                sizeValid = true;
                gameSizeUpdated(size);
            }
        }while(!sizeValid);


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
                    temp = this.inputProcessor.getDataToProcess().popData();
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
     * Asks to choose a deck to draw from and draws a card
     *
     * @param gameModel game model
     */
    public void askToChooseADeck(GameImmutable gameModel) throws Exception {
        ui.showAskToChooseADeck();
        String choice;
        choice = this.inputProcessor.getDataToProcess().popData();
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
                    index = Integer.parseInt(this.inputProcessor.getDataToProcess().popData());
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
                    index = Integer.parseInt(this.inputProcessor.getDataToProcess().popData());
                        if(index==1 || index==0) {
                            drawCardDisplayed(gameModel.getPlayers().get(gameModel.getCurrPlayer()), gameModel.getDesk().getDisplayedResource(), index);
                            wrongIndex = false;
                        }
                }while(wrongIndex);
            }
            default->{
                ui.showInvalidInput();
                askToChooseADeck(gameModel);
            }
        }
    }

    /**
     * Asks to choose a CardObjective
     * @param model
     * @throws Exception
     */
    public void askToChooseACardObjective(GameImmutable model, String nickname) throws Exception {
        ui.showObjectiveNotChosen(model, nickname);
        boolean wrongIndex = true;
        do{
            int index;
            ui.showAskIndex(model);
            index = Integer.parseInt(this.inputProcessor.getDataToProcess().popData());
            if(index==1 || index==0) {
                selectCardObjective(model.getPlayers().get(model.getCurrPlayer()), index);
                wrongIndex = false;
            }
        }while(wrongIndex);
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
            indexHand = Integer.parseInt(this.inputProcessor.getDataToProcess().popData());
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
        ui.showCodex(model);
    }

    /**
     * Asks which Side of the StarterCard to use
     * @param model
     * @return
     * @throws InterruptedException
     */
    public Side askSideStarter(GameImmutable model) throws InterruptedException {
        ui.show_askSide(model);
        String choice;
        choice= this.inputProcessor.getDataToProcess().popData();
        Side side = null;
        switch(choice){
            case "f"->{
                side = model.getPlayers().get(model.getCurrPlayer()).getCardStarter().getFrontStarter();
            }
            case "b"->{
                side = model.getPlayers().get(model.getCurrPlayer()).getCardStarter().getBackStarter();
            }
            default->{
                ui.showInvalidInput();
            }
        }
        return side;
    }

    /**
     * Asks to place a StarterCard
     * @param model
     * @throws Exception
     */
    public void askToPlaceStarterOnCodex(GameImmutable model) throws Exception {
        Side side;
        do {
            side = askSideStarter(model);
        }while(side==null);
        placeStarterOnCodex(model.getPlayers().get(model.getCurrPlayer()),side);
        ui.showCodex(model);

    }

    /**
     * Asks the coordinates to place a card
     * @param model
     * @throws InterruptedException
     */

    public void askCoordinates(GameImmutable model) throws InterruptedException {
        ui.showAskCoordinatesRow(model);
        row = Integer.parseInt(this.inputProcessor.getDataToProcess().popData());
        ui.showAskCoordinatesCol(model);
        col = Integer.parseInt(this.inputProcessor.getDataToProcess().popData());
    }

    /**
     * Asks which side you want to place
     * @param model model
     * @throws InterruptedException exception
     */
    public void askSide(GameImmutable model) throws InterruptedException {
        ui.show_askSide(model);
        String side;
        side = this.inputProcessor.getDataToProcess().popData();
        switch(side){
            case "f"->{
                frontCard = true;
            }
            case "b"->{
                frontCard = false;
            }
            default->{
                ui.showInvalidInput();
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

    /**
     * Client asks to set the size
     * @param size
     */
    public void gameSizeUpdated(int size) throws Exception {
        ui.show_sizeSetted(size);
        try{
            clientActions.gameSizeUpdated(size);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * The client asks the server to join a specific game
     *
     * @param nick   nickname of the player
     * @param idGame id of the game to join
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
     * The client asks the server to join the first available game
     *
     * @param nick nickname of the player
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
     * The client asks the server to reconnect to a specific game
     *
     * @param nick   nickname of the player
     */
    @Override
    public void reconnectToGame(String nick) {
        //System.out.println("> You have selected to join to Game with id: '" + idGame + "', trying to reconnect");
            ui.show_joiningToGameIdMsg(0, nick);
            try {
                clientActions.reconnectToGame(nickname);
            } catch (Exception e) {
                noConnectionError();
            }
            events.add(null, APP_MENU);
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
    public void placeStarterOnCodex(Player player, Side side) throws IOException, InterruptedException, Exception {
        try {
            clientActions.placeStarterOnCodex(player,side);
        } catch (IOException e) {
            noConnectionError();
        }
    }

    @Override
    public void selectCardObjective(Player player, int cardObjective) throws IOException, InterruptedException, Exception {
        try {
            clientActions.selectCardObjective(player,cardObjective);
        } catch (IOException e) {
            noConnectionError();
        }
    }

    @Override
    public void drawCardFromDeck(Player player, ArrayList<? extends Card> deck) throws IOException, InterruptedException, Exception {
        try {
            clientActions.drawCardFromDeck(player,deck);
        } catch (IOException e) {
            noConnectionError();
        }
    }

    @Override
    public void drawCardDisplayed(Player player, ArrayList<? extends Card> deck, int index) throws IOException, InterruptedException, Exception {
        try {
            clientActions.drawCardDisplayed(player,deck,index);
        } catch (IOException e) {
            noConnectionError();
        }
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
    public void sendPing(String player) throws RemoteException {
        try {
            clientActions.sendPing(player);
        }catch (RemoteException e){
            noConnectionError();
        }
    }




    /*============ Server event received ============*/

    /**
     * A player has joined the game
     * @param gameModel game model
     */
    @Override
    public void playerJoined(GameImmutable gameModel) {
        events.add(gameModel, PLAYER_JOINED);
        ui.show_playerJoined(gameModel, gameModel.getPlayers().getLast().getNickname());

    }

    /**
     * A player has left the game
     * @param model game model
     * @param nick nickname of the player
     * @throws RemoteException if the reference could not be accessed
     */
    @Override
    public void playerLeft(GameImmutable model, String nick) throws RemoteException {
        if (model.getStatus().equals(GameStatus.WAITING)) {
            ui.show_playerJoined(model, nickname);
        } else {
            ui.addImportantEvent("[EVENT]: Player " + nick + " left the game!");
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
    public void sentChatMessage(GameImmutable gameModel, ChatMessage msg) {
        //Show the message only if is for everyone or is for me (or I sent it)

        ui.addMessage(msg, gameModel);
        events.add(gameModel, SENT_MESSAGE);
        //msg.setText("[PRIVATE]: " + msg.getText());
    }

    /**
     * A player wanted to join a game but the gameID is not valid
     * @param gameid game id
     * @throws RemoteException if the reference could not be accessed
     */
    @Override
    public void gameIdNotExists(int gameid) throws RemoteException {
        ui.show_noAvailableGamesToJoin("NO CURRENT GAME AVAILABLE WITH " + gameid + " ID");
        events.add(null, ERROR_WHEN_ENTERING_GAME);
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
     * @param gameModel game model
     */
    @Override
    public void gameEnded(GameImmutable gameModel) {
        ended = true;
        events.add(gameModel, EventType.GAMEENDED);
        ui.show_gameEnded(gameModel);
    }

//
//    /**
//     * A card has been drawn
//     * @param gameModel game model
//     */
//    @Override
//    public void drawCard(GameImmutable gameModel) {
//        events.add(gameModel,DRAW_CARD);
//    }

    /**
     * It adds the NextTurn event to the event list
     * @param gameModel game model
     */
    @Override
    public void nextTurn(GameImmutable gameModel) {
        events.add(gameModel, EventType.NEXT_TURN);

        //I remove all the input that the user sends when It is not his turn
        this.inputProcessor.getDataToProcess().popAllData();
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
        events.add(null, JOIN_UNABLE_NICKNAME_ALREADY_IN_USE);
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
    public void positionedCardIntoCodex(GameImmutable model, int row, int column) throws RemoteException {
        ui.addImportantEvent(model.getPlayers().get(model.getCurrPlayer()).getNickname() + " positioned a card on his Codex");
    }

    @Override
    public void positionedStarterCardIntoCodex(GameImmutable model) throws RemoteException {
        ui.addImportantEvent(model.getPlayers().get(model.getCurrPlayer()).getNickname() + "positioned the Starter Card on his Codex");
    }

//    @Override
//    public void invalidCoordinates(GameImmutable gameImmutable, int row, int column) throws RemoteException {
//        ui.showInvalidInput();
//        ui.addImportantEvent();
//        //Valutare se serve perchè esiste già show InvalidInput nella ui e non serve un notify per tutti nel caso siano errati gli input
//    }

//    @Override
//    public void requirementsPlacementNotRespected(GameImmutable gameImmutable, ArrayList<Value> requirementsPlacement) throws RemoteException {
//        ui.showReqNotRespected(gameImmutable,requirementsPlacement);
//        ui.addImportantEvent();
//        //Valutare se serve perchè esiste già show InvalidInput nella ui e non serve un notify per tutti nel caso siano errati gli input
//    }

    @Override
    public void addedPoint(GameImmutable model, Player player, int point) throws RemoteException {
        ui.addImportantEvent(player.getNickname() + " gained " + point + "placing his Card");
    }

    @Override
    public void objectiveCardChosen(GameImmutable model, CardObjective cardObjective) throws RemoteException {
        ui.showObjectiveChosen(model,cardObjective);
    }

    @Override
    public void objectiveCardNotChosen(GameImmutable model) throws RemoteException {
        events.add(model,CHOOSE_OBJECTIVE_CARD);
    }
//
//    @Override
//    public void indexNotValid(GameImmutable gameImmutable, int index) throws RemoteException {
//        ui.showInvalidInput();
//        ui.addImportantEvent();
//        //valutare se serve perchè esiste già show InvalidInput nella ui e non serve un notify per tutti nel caso siano errati gli input
//    }

    @Override
    public void deckHasNoCards(GameImmutable model, ArrayList<? extends Card> deck) throws RemoteException {
        boolean both = false;
        if(model.getDesk().getDeckGold().isEmpty() && model.getDesk().getDeckResource().isEmpty()){
            ui.addImportantEvent("Resource Deck and GoldDeck are now Empty");
            both = true;
        }
        if(model.getDesk().getDeckResource().isEmpty() && !both){
            ui.addImportantEvent("ResourceDeck is now Empty");
        }
        if(model.getDesk().getDeckGold().isEmpty() && !both){
            ui.addImportantEvent("GoldDeck is now Empty");
        }
    }

    @Override
    public void cardAddedToHand(GameImmutable model, Card card) throws RemoteException {
        ui.showCardAddedToHand(model,card);
    }

//    @Override
//    public void cardNotAddedToHand(GameImmutable model) throws RemoteException {
//        ui.showCardNotAddedHand(model);
//        ui.addImportantEvent();
//        //Valutare se serve
//    }

    @Override
    public void endGameConditionsReached(GameImmutable gameImmutable) throws RemoteException {
        ui.addImportantEvent("EndGame Conditions have been reached");
    }

    @Override
    public void addedPointObjective(GameImmutable model, int objectivePoint) throws RemoteException {
        ui.addImportantEvent("ObjectivePoints have been added");
    }

    @Override
    public void winnerDeclared(GameImmutable model, ArrayList<String> nickname) throws RemoteException {
        ui.addImportantEvent("And the Winner is..." + nickname);
   }

    @Override
    public void invalidCoordinates(GameImmutable gameImmutable, int row, int column) throws RemoteException {

    }

    @Override
    public void requirementsPlacementNotRespected(GameImmutable gameImmutable, ArrayList<Value> requirementsPlacement) throws RemoteException {

    }

    @Override
    public void indexNotValid(GameImmutable gameImmutable, int index) throws RemoteException {

    }

    @Override
    public void cardNotAddedToHand(GameImmutable gameImmutable) throws RemoteException {

    }

    @Override
    public void gameSizeUpdated(GameImmutable gameImmutable, int size) throws RemoteException {

    }
}
