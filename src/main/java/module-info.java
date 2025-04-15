module github.matheusilvac.workshopjavafx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires jdk.compiler;
    requires static lombok;
    requires jakarta.persistence;
    requires java.desktop;

    opens github.matheusilvac.workshopjavafx.gui to javafx.fxml;
    opens github.matheusilvac.workshopjavafx.model.persistence.entity to javafx.base;
    opens github.matheusilvac.workshopjavafx.model.services to javafx.base;
    exports github.matheusilvac.workshopjavafx;
}