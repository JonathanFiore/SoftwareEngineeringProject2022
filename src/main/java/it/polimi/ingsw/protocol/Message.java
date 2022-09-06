package it.polimi.ingsw.protocol;

import java.io.Serializable;

/**
 * This class is extended by each "message" class and represents the objects that are exchanged between server and
 * clients during the game
 */
public abstract class Message implements Serializable
{

    private final MessageLabel messageType;

    public Message(MessageLabel messageType) {
        this.messageType = messageType;
    }

    /**
     * @return the type (label) of the message that is being exchanged
     */
    public MessageLabel getMessageType() {
        return messageType;
    }
}