module org.openjfx {
    requires javafx.controls;
    requires javafx.media;
    requires javafx.fxml;

    opens org.openjfx to javafx.fxml;
    exports org.openjfx;
}