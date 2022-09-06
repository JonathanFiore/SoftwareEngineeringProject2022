package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.protocol.SelectedGameSettingsMessage;
import javafx.fxml.FXML;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

/**
 * This class extends Controller and it's the GameSetting's phase Controller, when the first player chooses the number of players and the mode
 */
public class GameSettingsController extends Controller{

    @FXML
    private ImageView twoplayers;

    @FXML
    private ImageView threeplayers;

    @FXML
    private ImageView fourplayers;

    @FXML
    private ImageView expmode;

    @FXML
    private ImageView easmode;
    @FXML
    private ImageView confirm;

    private boolean b2=false, b3=false, b4=false, bEasy=false, bExpert=false;


    /**
     * This method scales the image of the TwoPlayers when the player selects it
     * and calls the deselect method on ThreePlayers image and FourPlayers image
     * then calls the lightConfirm method
     */
    public void clickTwo (){
        if(b2==false) {
            twoplayers.setScaleX(1.2);
            twoplayers.setScaleY(1.2);
            ImageEffects.addWhiteGlow(twoplayers);
            b2 = true;
            b3=deselect(threeplayers);
            b4=deselect(fourplayers);
            lightConfirm();
        }else{
            b2=deselect(twoplayers);
            lightConfirm();
        }
    }

    /**
     * This method scales the image of the ThreePlayers when the player selects it
     * and calls the deselect method on TwoPlayers image and FourPlayers image
     * then calls the lightConfirm method
     */
    public void clickThree (){
        if(b3==false) {
            threeplayers.setScaleX(1.2);
            threeplayers.setScaleY(1.2);
            ImageEffects.addWhiteGlow(threeplayers);
            b3 = true;
            b2=deselect(twoplayers);
            b4=deselect(fourplayers);
            lightConfirm();
        }else{
            b3=deselect(threeplayers);
            lightConfirm();
        }
    }

    /**
     * This method scales the image of the FourPlayers when the player selects it
     * and calls the deselect method on TwoPlayers image and ThreePlayers image
     * then calls the lightConfirm method
     */
    public void clickFour (){
        if(b4==false) {
            fourplayers.setScaleX(1.2);
            fourplayers.setScaleY(1.2);
            ImageEffects.addWhiteGlow(fourplayers);
            b4 = true;
            b2=deselect(twoplayers);
            b3=deselect(threeplayers);
            lightConfirm();
        }else{
            b4=deselect(fourplayers);
            lightConfirm();
        }
    }

    /**
     * This method scales the image of the easy mode image when the player selects it
     * and calls the deselect method on the expert mode image
     * then calls the lightConfirm method
     */
    public void clickEasy (){
        if(bEasy==false) {
            easmode.setScaleX(1.2);
            easmode.setScaleY(1.2);
            ImageEffects.addWhiteGlow(easmode);
            bEasy=true;
            bExpert=deselect(expmode);
            lightConfirm();
        }else{
            bEasy=deselect(easmode);
            lightConfirm();
        }
    }

    /**
     * This method scales the image of the expert mode image when the player selects it
     * and calls the deselect method on the easy mode image
     * then calls the lightConfirm method
     */
    public void clickExpert (){
        if(bExpert==false) {
            expmode.setScaleX(1.2);
            expmode.setScaleY(1.2);
            ImageEffects.addWhiteGlow(expmode);
            bExpert=true;
            bEasy=deselect(easmode);
            lightConfirm();
        }else{
            bExpert=deselect(expmode);
            lightConfirm();
        }
    }

    /**
     * This method sets an effect when you release the mouse button on the confirm image
     * then it sends a message with the selected mode and number of players to the Server via NetworkHandler
     */
    public void clickConfirmOFF(){
        if((b2 || b3 || b4) && (bExpert || bEasy)){
            confirm.setScaleX(1.0);
            confirm.setScaleY(1.0);

            int numP;
            boolean mode;

            if(b2)
                numP=2;
            else if(b3)
                numP=3;
            else
                numP=4;

            if(bExpert)
                mode=true;
            else
                mode=false;

            SelectedGameSettingsMessage m = new SelectedGameSettingsMessage(mode, numP);
            getNetworkHandler().update(m);
        }
    }

    /**
     * This method sets an effect when you press the mouse button on the confirm image
     */
    public void clickConfirmON(){
        if((b2 || b3 || b4) && (bExpert || bEasy)){
            confirm.setScaleX(0.8);
            confirm.setScaleY(0.8);
        }
    }

    /**
     * This method is used to remove the previous effect from an ImageView
     * @param i it's the ImageView from which the effect will be remove
     */
    private boolean deselect(ImageView i){
        i.setScaleX(1.0);
        i.setScaleY(1.0);

        DropShadow borderGlow = new DropShadow();
        borderGlow.setOffsetX(0f);
        borderGlow.setOffsetY(0f);
        borderGlow.setColor(new Color(0.21559999883174896, 0.9672600030899048, 0.9800000190734863, 0.6499999761581421));
        borderGlow.setWidth(10);
        borderGlow.setHeight(10);

        i.setEffect(borderGlow);

        return false;
    }

    /**
     * This method is used to apply a dark effect on the confirm button if the player hasn't selected one mode and one number of players yet
     * or, if the player selects a mode and a number, it applies another effect on the confirm image
     */
    public void lightConfirm(){
        if((b2 || b3 || b4) && (bExpert || bEasy))
            deselect(confirm);
        else{
            ImageEffects.turnOff(confirm);
        }
    }
}