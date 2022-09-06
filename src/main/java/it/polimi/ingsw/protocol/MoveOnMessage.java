package it.polimi.ingsw.protocol;

/**
 * This message is sent from the client to the server to warn about the fact that the player does not want to use a
 * character anymore.
 */
public class MoveOnMessage extends Message{

    public MoveOnMessage() {
        super(MessageLabel.MOVE_ON);
    }
}
