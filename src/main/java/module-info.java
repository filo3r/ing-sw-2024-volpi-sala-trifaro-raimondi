module it.polimi.ingsw.gc03 {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.logging;
    requires java.rmi;


    opens it.polimi.ingsw.gc03 to javafx.fxml;
    exports it.polimi.ingsw.gc03;
    exports it.polimi.ingsw.gc03.model.exceptions;
    opens it.polimi.ingsw.gc03.model.exceptions to javafx.fxml;
    exports it.polimi.ingsw.gc03.model.card;
    opens it.polimi.ingsw.gc03.model.card to javafx.fxml, com.google.gson;
    exports it.polimi.ingsw.gc03.model.side;
    opens it.polimi.ingsw.gc03.model.side to javafx.fxml, com.google.gson;
    exports it.polimi.ingsw.gc03.model.side.front;
    opens it.polimi.ingsw.gc03.model.side.front to javafx.fxml, com.google.gson;
    exports it.polimi.ingsw.gc03.model.side.back;
    opens it.polimi.ingsw.gc03.model.side.back to javafx.fxml, com.google.gson;
    exports it.polimi.ingsw.gc03.model.enumerations;
    opens it.polimi.ingsw.gc03.model.enumerations to javafx.fxml, com.google.gson;
    exports it.polimi.ingsw.gc03.model.card.card.objective;
    opens it.polimi.ingsw.gc03.model.card.card.objective to javafx.fxml, com.google.gson;
    exports it.polimi.ingsw.gc03.rmi;
    exports it.polimi.ingsw.gc03.model;
    opens it.polimi.ingsw.gc03.model to com.google.gson, javafx.fxml;
}