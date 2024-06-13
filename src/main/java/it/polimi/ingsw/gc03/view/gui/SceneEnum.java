package it.polimi.ingsw.gc03.view.gui;


/**
 * The SceneEnum class holds the file paths for the FXML files of the various scenes.
 */
public enum SceneEnum {


    /**
     * NICKNAME is the scene where the user is asked to enter their nickname.
     */
    NICKNAME("/nickname.fxml"),

    /**
     * MENU is the scene that displays the menu with possible actions for the player to choose from.
     */
    MENU("/menu.fxml"),

    /**
     * GAME_ID is the scene where the user is asked to enter the game ID.
     */
    GAME_ID("/gameId.fxml"),

    /**
     * LOBBY2 is the scene that represents the lobby for a two-player game.
     */
    LOBBY2("/lobby2.fxml"),

    /**
     * LOBBY3 is the scene that represents the lobby for a three-player game.
     */
    LOBBY3("/lobby3.fxml"),

    /**
     * LOBBY4 is the scene that represents the lobby for a four-player game.
     */
    LOBBY4("/lobby4.fxml"),

    /**
     * LOBBY_PLAYER1 is the scene that represents the first player in the lobby.
     */
    LOBBY_PLAYER1("/lobbyPlayer1.fxml"),

    /**
     * LOBBY_PLAYER2 is the scene that represents the second player in the lobby.
     */
    LOBBY_PLAYER2("/lobbyPlayer2.fxml"),

    /**
     * LOBBY_PLAYER3 is the scene that represents the third player in the lobby.
     */
    LOBBY_PLAYER3("/lobbyPlayer3.fxml"),

    /**
     * LOBBY_PLAYER4 is the scene that represents the fourth player in the lobby.
     */
    LOBBY_PLAYER4("/lobbyPlayer4.fxml"),

    /**
     * CARD_STARTER is the scene where the player is asked to choose the side of their starter card.
     */
    CARD_STARTER("/cardStarter.fxml"),

    /**
     * CARD_OBJECTIVE is the scene where the player is asked to choose their personal objective card.
     */
    CARD_OBJECTIVE("/cardObjective.fxml"),

    /**
     * GAME_RUNNING is the scene displayed during the actual execution of the game.
     */
    GAME_RUNNING("/gameRunning.fxml"),

    /**
     * WINNERS is the final scene where the players' scores and the winners of the game are displayed.
     */
    WINNERS("/winners.fxml"),

    /**
     * ERROR is the scene displayed in case an error occurs.
     */
    ERROR("/error.fxml");

    private final String value;

    SceneEnum(final String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }

}