package github.matheusilvac.workshopjavafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static Scene mainScene;

    @Override
    public void start(Stage stage) throws IOException {
        ScrollPane scrollPane = FXMLLoader.load(getClass().getResource("/github/matheusilvac/workshopjavafx/gui/MainView.fxml"));
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);


        mainScene =  new Scene(scrollPane);
        stage.setTitle("Hello!");
        stage.setScene(mainScene);
        stage.show();
    }

    public static Scene getMainScene() {
        return mainScene;
    }

    public static void main(String[] args) {
        launch();
    }
}