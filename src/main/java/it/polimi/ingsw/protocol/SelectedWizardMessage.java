package it.polimi.ingsw.protocol;

import it.polimi.ingsw.server.model.Wizard;

/**
 * This message is sent from the client to the sever and it shows the selected wizard.
 */
public class SelectedWizardMessage extends Message{

    private final Wizard wizard;

    public SelectedWizardMessage(Wizard wizard) {
        super(MessageLabel.SELECTED_WIZARD);
        this.wizard = wizard;
    }

    /**
     * @return the selected wizard
     */
    public Wizard getWizard() {
        return wizard;
    }
}
