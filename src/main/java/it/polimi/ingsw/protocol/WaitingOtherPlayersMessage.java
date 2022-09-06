package it.polimi.ingsw.protocol;

/**
 * This message is sent from the server to the client. It indicates that the player needs to wait for other players
 * to join.
 */
public class WaitingOtherPlayersMessage extends Message{

    private final int numCurrentPlayers;

    public WaitingOtherPlayersMessage(int numCurrentPlayers) {
        super(MessageLabel.WAITING_OTHER_PLAYERS);
        this.numCurrentPlayers = numCurrentPlayers;
    }

    /**
     * @return the number of the players currently in the game that have to wait for other players to join
     */
    public int getNumCurrentPlayers() {
        return numCurrentPlayers;
    }
}
