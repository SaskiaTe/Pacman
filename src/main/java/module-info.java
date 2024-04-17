module ch.css.leistungen.pacman {
    requires javafx.controls;
    requires javafx.fxml;


    opens ch.css.leistungen.pacman to javafx.fxml;
    exports ch.css.leistungen.pacman;
}