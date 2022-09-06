package it.polimi.ingsw.protocol;

import static it.polimi.ingsw.protocol.MessageLabel.CHOOSE_GAME_SETTINGS;

/**
 * This message is sent from the server to the client to make the player choose the settings of the game
 */
public class ChooseGameSettingsMessage extends Message{

    public ChooseGameSettingsMessage() {
        super(CHOOSE_GAME_SETTINGS);
    }
}
