package it.polimi.ingsw.protocol;

import java.util.ArrayList;

/**
 * This message is sent from the server to the client to make the player choose a cloud
 */
public class ChooseCloudMessage extends Message{

    private final ArrayList<Integer> availableClouds;

    public ChooseCloudMessage(ArrayList<Integer> availableClouds) {
        super(MessageLabel.CHOOSE_CLOUD);
        this.availableClouds = availableClouds;
    }

    /**
     * @return the available clouds that the player can choose
     */
    public ArrayList<Integer> getAvailableClouds() {
        return availableClouds;
    }
}
