package it.polimi.ingsw.protocol;

import static it.polimi.ingsw.protocol.MessageLabel.SELECTED_GAME_SETTINGS;

/**
 * This message is sent from the Client to the Server to show the selected settings
 */
public class SelectedGameSettingsMessage extends  Message{

    private final boolean expertMode;
    private final int numPlayers;

    public SelectedGameSettingsMessage(boolean expertMode, int numPlayers) {
        super(SELECTED_GAME_SETTINGS);
        this.expertMode = expertMode;
        this.numPlayers = numPlayers;
    }

    /**
     * @return true if the selected mode is the expert mode, false if it's the easy mode
     */
    public boolean isExpertMode() {
        return expertMode;
    }

    /**
     * @return the selected number of players
     */
    public int getNumPlayers() {
        return numPlayers;
    }
}

