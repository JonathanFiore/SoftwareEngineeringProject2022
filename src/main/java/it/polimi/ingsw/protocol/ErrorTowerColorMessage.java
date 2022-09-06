package it.polimi.ingsw.protocol;

import it.polimi.ingsw.server.model.Tower;

import java.util.ArrayList;

/**
 * This message is sent from the server to the client if the towers’ color is invalid.
 */
public class ErrorTowerColorMessage extends Message{

    private final String error;
    private final ArrayList<Tower> availableColors;

    public ErrorTowerColorMessage(String error, ArrayList<Tower> availableColors) {
        super(MessageLabel.ERROR_TOWER_COLOR);
        this.error = error;
        this.availableColors = availableColors;
    }

    /**
     * @return a string that contains an error due to the invalid choice of the towers' color
     */
    public String getError() {
        return error;
    }

    /**
     * @return the available towers' colors for the player to choose
     */
    public ArrayList<Tower> getAvailableColors() {
        return availableColors;
    }
}
