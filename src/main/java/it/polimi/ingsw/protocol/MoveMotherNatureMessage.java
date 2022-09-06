package it.polimi.ingsw.protocol;

/**
 * This message is sent from the server to the client to say that mother nature needs to be moved.
 */
public class MoveMotherNatureMessage extends Message{

    private final int maxSteps;

    public MoveMotherNatureMessage(int maxSteps) {
        super(MessageLabel.MOVE_MOTHER_NATURE);
        this.maxSteps = maxSteps;
    }

    /**
     * @return the maximum number of steps that mother nature is allowed to do
     */
    public int getMaxSteps() {
        return maxSteps;
    }
}
