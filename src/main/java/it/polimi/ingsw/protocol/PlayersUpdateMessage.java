package it.polimi.ingsw.protocol;

import it.polimi.ingsw.server.model.Player;

import java.util.ArrayList;

/**
 * This message is sent from the server to the client to indicate the occurred update of a player.
 */
public class PlayersUpdateMessage extends Message{

    private final ArrayList<Player> updatedPlayers;

    public PlayersUpdateMessage(ArrayList<Player> updatedPlayers) {
        super(MessageLabel.PLAYERS_UPDATE);
        this.updatedPlayers = updatedPlayers;
    }

    /**
     * @return the updates of all the players of the game
     */
    public ArrayList<Player> getUpdatedPlayers() {
        return updatedPlayers;
    }
}
