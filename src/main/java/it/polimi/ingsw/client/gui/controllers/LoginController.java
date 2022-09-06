package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.NetworkHandler;
import it.polimi.ingsw.client.ViewGUI;
import it.polimi.ingsw.protocol.JoinGameMessage;
import it.polimi.ingsw.server.controller.View;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This class extends Controller and it's the Login's phase Controller
 */
public class LoginController extends Controller{

    @FXML
    private AnchorPane anchorp;
    @FXML
    private TextField ipTextF;

    @FXML
    private TextField portTextF;

    @FXML
    private TextField nicknameTextF;

    @FXML
    private ImageView confirm;

    @FXML
    private Text ipError;

    @FXML
    private Text portError;

    @FXML
    private Text nicknameError;

    private String ip;
    private String port;
    private String nickname;
    private boolean okIp=false, okPort=false, okNickname=false;


    /**
     * This method sets an effect when you press the mouse button on the submit image
     * it also checks if the nickname, the Ip address and the port number are valid (by calling checkIp and checkPort methods)
     * to set the three values at true if they're valid, false if they're not
     */
    public void submitON() {
        ip = ipTextF.getText();
        port = portTextF.getText();
        nickname = nicknameTextF.getText();

        okIp = InputChecker.checkIp(ip);

        if(okIp){
            ipError.setOpacity(0);
        }else{
            ipError.setOpacity(1);
        }


        okPort = InputChecker.checkPort(port);

        if(okPort){
            portError.setOpacity(0);
        }else{
            portError.setOpacity(1);
        }

        okNickname = true;

        if(nickname.length()>20 || nickname.length()<1){
            okNickname=false;
        }

        if(okNickname)
            nicknameError.setOpacity(0);
        else
            nicknameError.setOpacity(1);

        if(okNickname && okIp && okPort){
            confirm.setScaleX(0.8);
            confirm.setScaleY(0.8);

        }


    }

    /**
     * This method sets an effect when you release the mouse button on the submit image
     * then it sends a message with the selected nickname to the Server via NetworkHandler
     * if these three values are all true
     */
    public void submitOFF(){
        if(okNickname && okIp && okPort){
            confirm.setScaleX(0.8);
            confirm.setScaleY(0.8);

            try{
                JoinGameMessage message = new JoinGameMessage(nickname);
                Stage s = (Stage) anchorp.getScene().getWindow();
                View v = new ViewGUI(s);
                ((ViewGUI)v).setCurrentController(this);
                ((ViewGUI)v).setNickname(nickname);
                NetworkHandler networkHandler = new NetworkHandler(v, ip, Integer.parseInt(port), message);
                v.addObserver(networkHandler);
            }catch (IOException e){
                ipError.setOpacity(1);
                portError.setOpacity(1);
            }
        }
    }
}



