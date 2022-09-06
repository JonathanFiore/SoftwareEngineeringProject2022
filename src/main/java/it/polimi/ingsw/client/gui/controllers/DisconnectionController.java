package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.JavaFXGUI;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This class extends Controller and it's the Disconnection's phase Controller
 */
public class DisconnectionController extends Controller{

    @FXML
    private ImageView disconnectionTitle;
    @FXML
    private ImageView play;
    @FXML
    private ImageView quit;

    /**
     * This method sets an effect when you press the mouse button on the playAgain image
     */
    public void playAgainOn(){
        play.setScaleX(0.8);
        play.setScaleY(0.8);
    }

    /**
     * This method sets an effect when you release the mouse button on the playAgain image
     */
    public void playAgainOff(){
        play.setScaleX(1.0);
        play.setScaleY(1.0);

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(JavaFXGUI.class.getClassLoader().getResource("fxml/login.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            scene.getStylesheets().add(JavaFXGUI.class.getClassLoader().getResource("css/login_nickname_settings.css").toExternalForm());

            Stage s = (Stage) play.getScene().getWindow();
            s.setScene(scene);
            s.setResizable(true);
            s.show();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method sets an effect when you press the mouse button on the quit image
     */
    public void quitOn(){
        quit.setScaleX(0.8);
        quit.setScaleY(0.8);
    }

    /**
     * This method sets an effect when you release the mouse button on the quit image
     */
    public void quitOff(){
        quit.setScaleX(1.0);
        quit.setScaleY(1.0);

        Platform.exit();
        System.exit(0);
    }

    /**
     * This method sets a title on the scene with the reason why the player was disconnected from the game
     * @param s it's the string that says if the player was disconnected normally or because the game was full
     */
    public void setTitle(String s){
        switch (s){
            case "F":
                Image f = new Image(getClass().getClassLoader().getResource("images/texts/game_full.png").toString());
                disconnectionTitle.setImage(f);
                disconnectionTitle.setFitWidth(1200);
                disconnectionTitle.setFitHeight(650);
                break;
            case "D":
                Image d = new Image(getClass().getClassLoader().getResource("images/texts/disconnected.png").toString());
                disconnectionTitle.setImage(d);
                disconnectionTitle.setFitWidth(990);
                disconnectionTitle.setFitHeight(690);
                break;
        }
    }
}