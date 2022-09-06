package it.polimi.ingsw.protocol;

import it.polimi.ingsw.server.model.Wizard;

import java.util.ArrayList;

/**
 * This message is sent from the server to the client and it contains the available wizards that can be chosen
 * by the player.
 */
public class ChooseWizardMessage extends Message{

    private final ArrayList<Wizard> availableWizards;

    public ChooseWizardMessage(ArrayList<Wizard> availableWizards) {
        super(MessageLabel.CHOOSE_WIZARD);
        this.availableWizards = availableWizards;
    }

    /**
     * @return the wizards available for the player's choice
     */
    public ArrayList<Wizard> getAvailableWizards() {
        return availableWizards;
    }
}
