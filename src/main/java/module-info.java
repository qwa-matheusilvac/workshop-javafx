module github.matheusilvac.workshopjavafx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires jdk.compiler;
    requires static lombok;

    opens github.matheusilvac.workshopjavafx.gui to javafx.fxml;
    exports github.matheusilvac.workshopjavafx;
}