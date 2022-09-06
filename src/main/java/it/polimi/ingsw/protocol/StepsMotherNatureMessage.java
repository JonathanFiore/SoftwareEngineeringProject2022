package it.polimi.ingsw.protocol;

/**
 * This message is sent from the client to the server to show mother nature’s number of steps, which is chosen
 * by the player.
 */
public class StepsMotherNatureMessage extends Message{

    private final int steps;

    public StepsMotherNatureMessage(int steps) {
        super(MessageLabel.STEPS_MOTHER_NATURE);
        this.steps = steps;
    }

    /**
     * @return the chosen number of mother nature's steps
     */
    public int getSteps() {
        return steps;
    }
}
