module io.github.berinamajdancic {
    requires javafx.controls;
    requires javafx.fxml;

    opens io.github.berinamajdancic to javafx.fxml;
    exports io.github.berinamajdancic;
}
