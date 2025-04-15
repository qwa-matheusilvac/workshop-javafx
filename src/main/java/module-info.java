module github.matheusilvac.workshopjavafx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires jdk.compiler;

    opens github.matheusilvac.workshopjavafx.gui to javafx.fxml;
    exports github.matheusilvac.workshopjavafx;
}