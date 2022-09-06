package it.polimi.ingsw.protocol;

import it.polimi.ingsw.server.model.Wizard;

import java.util.ArrayList;

/**
 * This message is sent from the server to the client to show an incorrect wizard’s choice.
 */
public class ErrorWizardMessage extends Message{

    private final String error;
    private final ArrayList<Wizard> availableWizards;

    public ErrorWizardMessage(String error, ArrayList<Wizard> availableWizards) {
        super(MessageLabel.ERROR_WIZARD);
        this.error = error;
        this.availableWizards = availableWizards;
    }

    /**
     * @return a string that contains an error due to the invalid wizard's choice
     */
    public String getError() {
        return error;
    }

    /**
     * @return the wizards available for the player to choose
     */
    public ArrayList<Wizard> getAvailableWizards() {
        return availableWizards;
    }
}
