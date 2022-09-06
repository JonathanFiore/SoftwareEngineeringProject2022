package it.polimi.ingsw.protocol;

import it.polimi.ingsw.server.model.Assistant;

import java.util.ArrayList;

/**
 * This message is sent from the server to the client of the player who is currently on duty in planning phases.
 */
public class ChooseCardMessage extends Message{

    private final ArrayList<Assistant> availableCards;

    public ChooseCardMessage(ArrayList<Assistant> availableCards) {
        super(MessageLabel.CHOOSE_CARD);
        this.availableCards = availableCards;
    }

    /**
     * @return the available cards that the player can choose
     */
    public ArrayList<Assistant> getAvailableCards() {
        return availableCards;
    }
}
