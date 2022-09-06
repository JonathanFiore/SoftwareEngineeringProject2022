package it.polimi.ingsw.protocol;

/**
 * This message is used to verify connectivity by testing if a particular client is reachable across an IP network.
 * It is sent from the client to the server and vice versa.
 */
public class PingMessage extends Message{

    public PingMessage() {
        super(MessageLabel.PING);
    }
}
