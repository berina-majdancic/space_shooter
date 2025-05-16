module io.github.berinamajdancic {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.sql;

    opens io.github.berinamajdancic to javafx.fxml;
    opens io.github.berinamajdancic.controllers to javafx.fxml;
    opens io.github.berinamajdancic.db to javafx.fxml;

    exports io.github.berinamajdancic;
    exports io.github.berinamajdancic.controllers to javafx.fxml;
    exports io.github.berinamajdancic.db;

}
