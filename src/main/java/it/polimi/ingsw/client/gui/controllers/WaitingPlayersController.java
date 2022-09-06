package it.polimi.ingsw.client.gui.controllers;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

/**
 * This class extends Controller and it's the WaitingPlayers' phase Controller, when you are waiting for your turn
 */
public class WaitingPlayersController extends Controller{

    @FXML
    private ImageView waiting;

    @FXML
    private ImageView players;

    @FXML
    private ImageView loading;

    /**
     * This method is used to change the image shown, depending on the numbers of players that joined
     * it also calls the method startLoading() after it changes the image
     * @param n it's the number of players that joined
     */
    public void setNumPlayers(int n){
        switch (n){
            case 1:
                Image p1 = new Image("/images/texts/players_in_game_1.png");
                players.setImage(p1);
                players.setFitHeight(waiting.getFitHeight());
                players.setFitWidth(waiting.getFitWidth());
                break;
            case 2:
                Image p2 = new Image("/images/texts/players_in_game_2.png");
                players.setImage(p2);
                players.setFitHeight(waiting.getFitHeight());
                players.setFitWidth(waiting.getFitWidth());
                break;
            case 3:
                Image p3 = new Image("/images/texts/players_in_game_3.png");
                players.setImage(p3);
                players.setFitHeight(waiting.getFitHeight());
                players.setFitWidth(waiting.getFitWidth());
                break;
        }
        startLoading();
    }

    /**
     * This method is used to add a loading animate icon that rotates cyclically while the players are waiting
     */
    private void startLoading(){
        RotateTransition rotate = new RotateTransition();
        rotate.setNode(loading);
        rotate.setDuration(Duration.millis(4000));
        rotate.setCycleCount(TranslateTransition.INDEFINITE);
        rotate.setByAngle(360);
        rotate.setAxis(Rotate.Z_AXIS);
        rotate.setInterpolator(Interpolator.LINEAR);
        rotate.play();
    }
}