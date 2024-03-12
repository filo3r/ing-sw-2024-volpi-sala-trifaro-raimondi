module it.polimi.ingsw.gc03 {
    requires javafx.controls;
    requires javafx.fxml;


    opens it.polimi.ingsw.gc03 to javafx.fxml;
    exports it.polimi.ingsw.gc03;
}