package it.polimi.ingsw.protocol;

import it.polimi.ingsw.server.model.Cloud;

import java.util.ArrayList;

/**
 * This message is sent from the server to the client to indicate the occurred update of a cloud (NB the update is
 * only related to the cloud emptied by the current player).
 */
public class CloudUpdateMessage extends Message{

    private final ArrayList<Cloud> clouds;

    public CloudUpdateMessage(ArrayList<Cloud> clouds) {
        super(MessageLabel.CLOUD_UPDATE);
        this.clouds = clouds;
    }

    /**
     * @return the updated clouds
     */
    public ArrayList<Cloud> getClouds() {
        return clouds;
    }
}
