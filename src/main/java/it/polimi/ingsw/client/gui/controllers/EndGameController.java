package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.JavaFXGUI;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This class extends Controller and it's the EndGame's phase Controller
 */
public class EndGameController extends Controller{

    @FXML
    private ImageView play;
    @FXML
    private ImageView quit;
    @FXML
    private Text winnerText;

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
     * This method sets a text on the scene with the name of the winner
     * @param s it's the string with the winning player
     */
    public void setWinnerText(String s){
        winnerText.setText(s);
    }
}
