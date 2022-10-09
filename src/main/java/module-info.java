module pl.com.bohdziewicz.pongfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens pl.com.bohdziewicz.pongfx to javafx.fxml;
    exports pl.com.bohdziewicz.pongfx;
}