package it.polimi.ingsw.gc03.view.gui;


/**
 * The SceneEnum class holds the file paths for the FXML files of the various scenes.
 */
public enum SceneEnum {

    /**
     * NICKNAME is the scene where the user is asked to enter their nickname.
     */
    NICKNAME(),

    /**
     * MENU is the scene that displays the menu with possible actions for the player to choose from.
     */
    MENU(),

    /**
     * GAME_ID is the scene where the user is asked to enter the game ID.
     */
    GAME_ID(),

    /**
     * LOBBY2 is the scene that represents the lobby for a two-player game.
     */
    LOBBY2(),

    /**
     * LOBBY3 is the scene that represents the lobby for a three-player game.
     */
    LOBBY3(),

    /**
     * LOBBY4 is the scene that represents the lobby for a four-player game.
     */
    LOBBY4(),

    /**
     * LOBBY_PLAYER1 is the scene that represents the first player in the lobby.
     */
    LOBBY_PLAYER1(),

    /**
     * LOBBY_PLAYER2 is the scene that represents the second player in the lobby.
     */
    LOBBY_PLAYER2(),

    /**
     * LOBBY_PLAYER3 is the scene that represents the third player in the lobby.
     */
    LOBBY_PLAYER3(),

    /**
     * LOBBY_PLAYER4 is the scene that represents the fourth player in the lobby.
     */
    LOBBY_PLAYER4(),

    /**
     * CARD_STARTER is the scene where the player is asked to choose the side of their starter card.
     */
    CARD_STARTER(),

    /**
     * CARD_OBJECTIVE is the scene where the player is asked to choose their personal objective card.
     */
    CARD_OBJECTIVE(),

    /**
     * GAME_RUNNING is the scene displayed during the actual execution of the game.
     */
    GAME_RUNNING(),

    /**
     * WINNERS is the final scene where the players' scores and the winners of the game are displayed.
     */
    WINNERS(),

    /**
     * ERROR is the scene displayed in case an error occurs.
     */
    ERROR()

}