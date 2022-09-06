package it.polimi.ingsw.protocol;

/**
 * This message is sent from the server to the client to indicate an invalid choice of the mother nature’s steps
 */
public class ErrorMotherNatureMessage extends Message{

    private final String error;
    private final int maxSteps;

    public ErrorMotherNatureMessage(String error, int maxSteps) {
        super(MessageLabel.ERROR_MOTHER_NATURE);
        this.error = error;
        this.maxSteps = maxSteps;
    }

    /**
     * @return a string that contains an error due to the invalid number of mother nature's steps
     */
    public String getError() {
        return error;
    }

    /**
     * @return the maximum number of steps that mother nature can do
     */
    public int getMaxSteps() {
        return maxSteps;
    }
}
