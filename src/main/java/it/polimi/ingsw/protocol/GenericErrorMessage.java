package it.polimi.ingsw.protocol;

/**
 * This message is sent from the server to the client when a generic error occurs
 */
public class GenericErrorMessage extends Message{

    private final String error;

    public GenericErrorMessage(MessageLabel messageType, String error) {
        super(messageType);
        this.error = error;
    }

    /**
     * @return a string that contains a generic error
     */
    public String getError() {
        return error;
    }
}
