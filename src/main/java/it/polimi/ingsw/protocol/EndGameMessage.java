package it.polimi.ingsw.protocol;

/**
 * This message is sent from the server to the client to indicate the end of the game and to show the winner.
 */
public class EndGameMessage extends Message{

    private final String winner;

    public EndGameMessage(String winner) {
        super(MessageLabel.END_GAME);
        this.winner = winner;
    }

    /**
     * @return the nickname of the winner
     */
    public String getWinner() {
        return winner;
    }
}
