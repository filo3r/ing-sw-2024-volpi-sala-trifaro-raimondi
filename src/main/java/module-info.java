module it.polimi.ingsw.gc03 {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    opens it.polimi.ingsw.gc03 to javafx.fxml;
    exports it.polimi.ingsw.gc03;
    exports it.polimi.ingsw.gc03.model.Exceptions;
    opens it.polimi.ingsw.gc03.Exceptions to javafx.fxml;
    exports it.polimi.ingsw.gc03.model.Card;
    opens it.polimi.ingsw.gc03.Card to javafx.fxml, com.google.gson;
    exports it.polimi.ingsw.gc03.model.Side;
    opens it.polimi.ingsw.gc03.Side to javafx.fxml, com.google.gson;
    exports it.polimi.ingsw.gc03.model.Side.Front;
    opens it.polimi.ingsw.gc03.Side.Front to javafx.fxml, com.google.gson;
    exports it.polimi.ingsw.gc03.model.Side.Back;
    opens it.polimi.ingsw.gc03.Side.Back to javafx.fxml, com.google.gson;
    exports it.polimi.ingsw.gc03.model.Enumerations;
    opens it.polimi.ingsw.gc03.Enumerations to javafx.fxml, com.google.gson;
    exports it.polimi.ingsw.gc03.model.Card.CardObjective;
    opens it.polimi.ingsw.gc03.Card.CardObjective to javafx.fxml, com.google.gson;
}