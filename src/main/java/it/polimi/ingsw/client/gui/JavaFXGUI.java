package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.gui.controllers.ImageEffects;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Main JavaFx class
 */
public class JavaFXGUI extends Application {

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(JavaFXGUI.class.getClassLoader().getResource("fxml/login.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            scene.getStylesheets().add(JavaFXGUI.class.getClassLoader().getResource("css/login_nickname_settings.css").toExternalForm());

            stage.setTitle("Eriantys");
            Image icon = new Image(getClass().getClassLoader().getResource("images/texts/icon.png").toString());
            stage.getIcons().add(icon);

            ImageEffects.setCursor(scene);
            stage.setFullScreen(false);

            stage.setScene(scene);
            stage.setResizable(true);
            stage.setMinWidth(1200);
            stage.setMinHeight(800);
            stage.show();
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop(){
        Platform.exit();
        System.exit(0);
    }
}
