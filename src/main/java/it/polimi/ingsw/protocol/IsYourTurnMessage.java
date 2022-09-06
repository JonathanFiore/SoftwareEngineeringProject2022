package it.polimi.ingsw.protocol;

/**
 * This message is sent from the server to the client to warn the player that it is their turn.
 */
public class IsYourTurnMessage extends Message{

    public IsYourTurnMessage() {
        super(MessageLabel.IS_YOUR_TURN);
    }
}
