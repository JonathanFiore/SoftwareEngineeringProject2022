package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.protocol.SelectedTowerColorMessage;
import it.polimi.ingsw.server.model.Tower;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import java.util.ArrayList;

/**
 * This class extends Controller and it's the ChooseTower's phase Controller
 */
public class ChooseTowerController extends Controller{
    private boolean t1up = false;
    private boolean t2up = false;
    private boolean t3up = false;

    private boolean t1available = false;
    private boolean t2available = false;
    private boolean t3available = false;

    private int chosen = 0;

    @FXML
    private ImageView t1;
    @FXML
    private ImageView t2;
    @FXML
    private ImageView t3;
    @FXML
    private ImageView confirm;

    /**
     * This method is used to select the white tower (T1) and also adds effects when you select it
     */
    public void selectT1() {
        if(t1available) {
            if (!t1up) {
                resize();
                t1.setFitHeight(t1.getFitHeight() + 40);
                ImageEffects.addWhiteGlow(t1);
                t1up = true;
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
     * This method is used to select the grey tower (T2) and also adds effects when you select it
     */
    public void selectT2() {
        if(t2available) {
            if (!t2up) {
                resize();
                t2.setFitHeight(t2.getFitHeight() + 40);
                ImageEffects.addWhiteGlow(t2);
                t2up = true;
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
     * This method is used to select the black tower (T3) and also adds effects when you select it
     */
    public void selectT3() {
        if(t3available) {
            if (!t3up) {
                resize();
                t3.setFitHeight(t3.getFitHeight() + 40);
                ImageEffects.addWhiteGlow(t3);
                t3up = true;
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
     * This method is used to create a SelectedTowerColorMessage with the selected tower and sends it to the Server via NetworkHandler
     */
    private void selectConfirm() {
        Tower tower;
        if(chosen==1)
            tower=Tower.WHITE;
        else if(chosen==2)
            tower=Tower.GREY;
        else
            tower=Tower.BLACK;

        SelectedTowerColorMessage m = new SelectedTowerColorMessage(tower);
        getNetworkHandler().update(m);
    }

    /**
     * This method is used to resize and remove the effect applied previously to the wizard when it's deselected
     */
    private void resize() {
        if (t1up) {
            t1.setFitHeight(t1.getFitHeight() - 40);
            ImageEffects.addShadow(t1);
            t1up = false;
        } else if (t2up) {
            t2.setFitHeight(t2.getFitHeight() - 40);
            ImageEffects.addShadow(t2);
            t2up = false;
        } else if (t3up) {
            t3.setFitHeight(t3.getFitHeight() - 40);
            ImageEffects.addShadow(t3);
            t3up = false;
        }
    }


    /**
     * This method sets the available towers that the player can select
     * @param towers contains the available towers
     */
    public void setAvailableTowers(ArrayList<Tower> towers) {
        for (Tower t : towers) {
            switch (t) {
                case WHITE:
                    t1available = true;
                    break;
                case GREY:
                    t2available = true;
                    break;
                case BLACK:
                    t3available = true;
                    break;
            }
        }

        if (!t1available)
            ImageEffects.turnOff(t1);
        if (!t2available)
            ImageEffects.turnOff(t2);
        if (!t3available)
            ImageEffects.turnOff(t3);
    }
}

