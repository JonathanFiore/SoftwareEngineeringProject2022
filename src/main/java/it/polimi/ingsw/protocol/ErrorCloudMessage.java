package it.polimi.ingsw.protocol;

import java.util.ArrayList;

/**
 * This message is sent from the server to the client to indicates an invalid choice of the cloud.
 */
public class ErrorCloudMessage extends Message{

    private final String error;
    private final ArrayList<Integer> availableClouds;

    public ErrorCloudMessage(String error, ArrayList<Integer> availableClouds) {
        super(MessageLabel.ERROR_CLOUD);
        this.error = error;
        this.availableClouds = availableClouds;
    }

    /**
     * @return a string that contains an error due to the chosen cloud
     */
    public String getError() {
        return error;
    }

    /**
     * @return the available clouds for the player to select
     */
    public ArrayList<Integer> getAvailableClouds() {
        return availableClouds;
    }
}
