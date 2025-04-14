module github.matheusilvac.workshopjavafx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens github.matheusilvac.workshopjavafx to javafx.fxml;
    exports github.matheusilvac.workshopjavafx;
}