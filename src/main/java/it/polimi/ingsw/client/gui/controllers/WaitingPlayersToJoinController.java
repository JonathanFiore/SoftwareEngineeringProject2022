package it.polimi.ingsw.client.gui.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class extends Controller and it's the WaitingPlayersToJoin' phase Controller, when the logged players wait for others
 */
public class WaitingPlayersToJoinController extends Controller implements Initializable {

    @FXML
    private ImageView waitingText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // in this method an animation is added to the scene, and it is started
        waitingText.setFitWidth(980);
        waitingText.setFitHeight(500);

        Timeline t = new Timeline();
        t.setCycleCount(Timeline.INDEFINITE);

        t.getKeyFrames().add(new KeyFrame(
                        Duration.millis(600),
                        (ActionEvent event) -> {
                            Image image = new Image(getClass().getClassLoader().getResource("images/other_players_joining/other_joining_0.png").toString());
                            waitingText.setImage(image);
                        }
                )
        );

        t.getKeyFrames().add(new KeyFrame(
                        Duration.millis(1200),
                        (ActionEvent event) -> {
                            Image image = new Image(getClass().getClassLoader().getResource("images/other_players_joining/other_joining_1.png").toString());
                            waitingText.setImage(image);
                        }
                )
        );

        t.getKeyFrames().add(new KeyFrame(
                        Duration.millis(1800),
                        (ActionEvent event) -> {
                            Image image = new Image(getClass().getClassLoader().getResource("images/other_players_joining/other_joining_2.png").toString());
                            waitingText.setImage(image);
                        }
                )
        );

        t.getKeyFrames().add(new KeyFrame(
                        Duration.millis(2400),
                        (ActionEvent event) -> {
                            Image image = new Image(getClass().getClassLoader().getResource("images/other_players_joining/other_joining_3.png").toString());
                            waitingText.setImage(image);
                        }
                )
        );

        t.play();
    }
}
