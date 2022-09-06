package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.protocol.SelectedWizardMessage;
import it.polimi.ingsw.server.model.Wizard;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import java.util.ArrayList;

/**
 * This class extends Controller and it's the ChooseWizard's phase Controller
 */
public class ChooseWizardController extends Controller{

    private boolean w1up = false;
    private boolean w2up = false;
    private boolean w3up = false;
    private boolean w4up = false;

    private boolean w1available = false;
    private boolean w2available = false;
    private boolean w3available = false;
    private boolean w4available = false;

    private int chosen = 0;

    @FXML
    private ImageView w1;
    @FXML
    private ImageView w2;
    @FXML
    private ImageView w3;
    @FXML
    private ImageView w4;
    @FXML
    private ImageView confirm;

    /**
     * This method is used to select the 1st wizard (W1) and also adds effects when you select it
     */
    public void selectW1() {
        if (w1available) {
            if (!w1up) {
                resize();
                w1.setFitHeight(w1.getFitHeight() + 40);
                ImageEffects.addWhiteGlow(w1);
                w1up = true;
                chosen = 1;
                ImageEffects.addLightBluShadow(confirm);
            } else {
                resize();
                chosen = 0;
                ImageEffects.turnOff(confirm);
            }
        }
    }

    /**
     * This method is used to select the 2nd wizard (W2) and also adds effects when you select it
     */
    public void selectW2() {
        if (w2available) {
            if (!w2up) {
                resize();
                w2.setFitHeight(w2.getFitHeight() + 40);
                ImageEffects.addWhiteGlow(w2);
                w2up = true;
                chosen = 2;
                ImageEffects.addLightBluShadow(confirm);
            } else {
                resize();
                chosen = 0;
                ImageEffects.turnOff(confirm);
            }
        }
    }

    /**
     * This method is used to select the 3rd wizard (W3) and also adds effects when you select it
     */
    public void selectW3() {
        if (w3available) {
            if (!w3up) {
                resize();
                w3.setFitHeight(w3.getFitHeight() + 40);
                ImageEffects.addWhiteGlow(w3);
                w3up = true;
                chosen = 3;
                ImageEffects.addLightBluShadow(confirm);
            } else {
                resize();
                chosen = 0;
                ImageEffects.turnOff(confirm);
            }
        }
    }

    /**
     * This method is used to select the 4th wizard (W4) and also adds effects when you select it
     */
    public void selectW4() {
        if (w4available) {
            if (!w4up) {
                resize();
                w4.setFitHeight(w4.getFitHeight() + 40);
                ImageEffects.addWhiteGlow(w4);
                w4up = true;
                chosen = 4;
                ImageEffects.addLightBluShadow(confirm);
            } else {
                resize();
                chosen = 0;
                ImageEffects.turnOff(confirm);
            }
        }
    }

    /**
     * This method is used to add effect to the "confirm" image when the player is pressing the mouse button on an image
     */
    public void mouseOnConfirm() {
        if (chosen != 0) {
            confirm.setScaleX(0.8);
            confirm.setScaleY(0.8);
        }
    }

    /**
     * This method is used to add effect to the "confirm" image when the player is releasing the mouse button on an image
     */
    public void mouseOffConfirm() {
        if (chosen != 0) {
            confirm.setScaleX(1.0);
            confirm.setScaleY(1.0);
            selectConfirm();
        }
    }

    /**
     * This method is used to create a SelectedWizardMessage with the selected wizard and sends it to the Server via NetworkHandler
     */
    private void selectConfirm() {
        Wizard wizard;

        if(chosen==1)
            wizard=Wizard.WIZARD1;
        else if(chosen==2)
            wizard=Wizard.WIZARD2;
        else if(chosen==3)
            wizard=Wizard.WIZARD3;
        else
            wizard=Wizard.WIZARD4;

        SelectedWizardMessage m = new SelectedWizardMessage(wizard);
        getNetworkHandler().update(m);
    }

    /**
     * This method is used to resize and remove the effect applied previously to the wizard when it's deselected
     */
    private void resize() {
        if (w1up) {
            w1.setFitHeight(w1.getFitHeight() - 40);
            ImageEffects.addShadow(w1);
            w1up = false;
        } else if (w2up) {
            w2.setFitHeight(w2.getFitHeight() - 40);
            ImageEffects.addShadow(w2);
            w2up = false;
        } else if (w3up) {
            w3.setFitHeight(w3.getFitHeight() - 40);
            ImageEffects.addShadow(w3);
            w3up = false;
        } else if (w4up) {
            w4.setFitHeight(w4.getFitHeight() - 40);
            ImageEffects.addShadow(w4);
            w4up = false;
        }
    }

    /**
     * This method sets the available wizards that the player can select
     * @param wizards contains the available wizards
     */
    public void setAvailableWizards(ArrayList<Wizard> wizards) {
        for (Wizard w : wizards) {
            switch (w) {
                case WIZARD1:
                    w1available = true;
                    break;
                case WIZARD2:
                    w2available = true;
                    break;
                case WIZARD3:
                    w3available = true;
                    break;
                case WIZARD4:
                    w4available = true;
                    break;
            }
        }

        if (!w1available)
            ImageEffects.turnOff(w1);
        if (!w2available)
            ImageEffects.turnOff(w2);
        if (!w3available)
            ImageEffects.turnOff(w3);
        if (!w4available)
            ImageEffects.turnOff(w4);
    }
}
