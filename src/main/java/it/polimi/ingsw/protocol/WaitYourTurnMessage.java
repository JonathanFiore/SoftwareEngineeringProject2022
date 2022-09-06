package it.polimi.ingsw.protocol;

/**
 * This message is sent from the server to the clients. It’s used to say to the players that they need to wait
 * for their turn.
 */
public class WaitYourTurnMessage extends Message{

    private final String currentPlayer;

    public WaitYourTurnMessage(String currentPlayer) {
        super(MessageLabel.WAIT_YOUR_TURN);
        this.currentPlayer = currentPlayer;
    }

    /**
     * @return the current player that is playing its turn
     */
    public String getCurrentPlayer() {
        return currentPlayer;
    }
}
