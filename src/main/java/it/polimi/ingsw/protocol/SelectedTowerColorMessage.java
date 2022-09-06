package it.polimi.ingsw.protocol;

import it.polimi.ingsw.server.model.Tower;

/**
 * This message is sent from the client to the server and it shows the chosen color of the towers.
 */
public class SelectedTowerColorMessage extends Message{

    private final Tower color;

    public SelectedTowerColorMessage(Tower color) {
        super(MessageLabel.SELECTED_TOWER_COLOR);
        this.color = color;
    }

    /**
     * @return the selected towers' color
     */
    public Tower getColor() {
        return color;
    }
}
