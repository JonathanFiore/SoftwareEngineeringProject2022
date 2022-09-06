package it.polimi.ingsw.protocol;

import it.polimi.ingsw.server.model.characters.parameters.Parameter;

import java.util.ArrayList;

/**
 * This message is sent from the client to the server to indicate the will to play a character card
 */
public class UseCharacterMessage extends Message{

    private final int characterIndex;
    private final ArrayList<Parameter> parameters;

    public UseCharacterMessage(int characterIndex, ArrayList<Parameter> parameters) {
        super(MessageLabel.USE_CHARACTER);
        this.characterIndex = characterIndex;
        this.parameters = parameters;
    }

    /**
     * @return the index of the character card selected from the player
     */
    public int getCharacterIndex() {
        return characterIndex;
    }

    /**
     * @return the parameters that need to be passed to task method in Character
     */
    public ArrayList<Parameter> getParameters() {
        return parameters;
    }
}
