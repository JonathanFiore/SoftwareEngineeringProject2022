package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.NetworkHandler;
import it.polimi.ingsw.client.ViewGUI;
import it.polimi.ingsw.protocol.JoinGameMessage;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

/**
 * This class extends Controller and it represents the Controller of the scene that appears
 * when the nickname selected by the player is already taken by another player
 */
public class NicknameController extends Controller {

    @FXML
    private TextField nicknameTextF;

    @FXML
    private Text nicknameLongError;

    @FXML
    private Text nicknameTakenError;

    @FXML
    private ImageView confirm;


    private String nickname;

    boolean okNicknameSize = true;

    /**
     * This method sets an effect when you press the mouse button on the confirm image
     * and, if the size of the newly selected nickname is invalid, a text appears notifying the error
     */
    public void clickConfirmON() {

        nickname = nicknameTextF.getText();

        if (nickname.length() > 20 || nickname.length() < 1) {
            okNicknameSize = false;
        } else {
            okNicknameSize = true;
        }

        if (okNicknameSize) {
            nicknameLongError.setOpacity(0);
            nicknameTakenError.setOpacity(0);
        } else {
            nicknameLongError.setOpacity(1);
            nicknameTakenError.setOpacity(0);
        }

        if(okNicknameSize){
            confirm.setScaleX(0.8);
            confirm.setScaleY(0.8);
        }
    }

    /**
     * This method sets an effect when you release the mouse button on the confirm image
     * then it sends a message with the selected nickname to the Server via NetworkHandler
     */
    public void clickConfirmOFF() {

        if (okNicknameSize) {

            confirm.setScaleX(1.0);
            confirm.setScaleY(1.0);

            JoinGameMessage m = new JoinGameMessage(nickname);
            getNetworkHandler().update(m);
            ((ViewGUI) ((NetworkHandler) getNetworkHandler()).getView()).setNickname(nickname);

        }
    }
}
