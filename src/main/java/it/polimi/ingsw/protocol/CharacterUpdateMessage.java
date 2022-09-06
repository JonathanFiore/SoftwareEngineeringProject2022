package it.polimi.ingsw.protocol;

import it.polimi.ingsw.server.model.characters.cards.Character;

/**
 * This message is sent from the server to the client to indicate the occurred update of a character card made by the
 * current player.
 */
public class CharacterUpdateMessage extends Message{

    private final Character updatedCharacter;
    private final int index;

    public CharacterUpdateMessage(Character updatedCharacter, int index) {
        super(MessageLabel.CHARACTER_UPDATE);
        this.updatedCharacter = updatedCharacter;
        this.index = index;
    }

    /**
     * @return the updatedCharacter
     */
    public Character getUpdatedCharacter() {
        return updatedCharacter;
    }

    /**
     * @return the index of the character that has to be updated
     */
    public int getIndex() {
        return index;
    }
}
