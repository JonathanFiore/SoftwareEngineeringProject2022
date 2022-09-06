package it.polimi.ingsw.protocol;

import it.polimi.ingsw.server.model.Assistant;

import java.util.ArrayList;

/**
 * This message is sent from the server to the client to show that the chosen card is invalid.
 */
public class ErrorCardMessage extends Message{

    private final String error;
    private final ArrayList<Assistant> availableCards;

    public ErrorCardMessage(String error, ArrayList<Assistant> availableCards) {
        super(MessageLabel.ERROR_CARD);
        this.error = error;
        this.availableCards = availableCards;
    }

    /**
     * @return a string that contains an error due to the chosen card
     */
    public String getError() {
        return error;
    }

    /**
     * @return the available Assistants' cards
     */
    public ArrayList<Assistant> getAvailableCards() {
        return availableCards;
    }
}
