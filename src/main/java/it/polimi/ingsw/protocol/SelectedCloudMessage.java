package it.polimi.ingsw.protocol;

/**
 * This message is sent from the client to the server to show the chosen cloud.
 */
public class SelectedCloudMessage extends Message{

    private final int cloudIndex;

    public SelectedCloudMessage(int cloudIndex) {
        super(MessageLabel.SELECTED_CLOUD);
        this.cloudIndex = cloudIndex;
    }

    /**
     * @return the index of the cloud chosen by the player
     */
    public int getCloudIndex() {
        return cloudIndex;
    }
}
