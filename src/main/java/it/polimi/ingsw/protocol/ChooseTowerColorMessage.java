package it.polimi.ingsw.protocol;

import it.polimi.ingsw.server.model.Tower;

import java.util.ArrayList;

/**
 * This message is sent to the client when the player has already been added to the game and they have to choose the
 * color of their tower.
 */
public class ChooseTowerColorMessage extends  Message{

    private final ArrayList<Tower> availableColors;

    public ChooseTowerColorMessage(ArrayList<Tower> availableColors) {
        super(MessageLabel.CHOOSE_TOWER_COLOR);
        this.availableColors = availableColors;
    }

    /**
     * @return the available colors of the towers that can be selected from the player
     */
    public ArrayList<Tower> getAvailableColors() {
        return availableColors;
    }
}
