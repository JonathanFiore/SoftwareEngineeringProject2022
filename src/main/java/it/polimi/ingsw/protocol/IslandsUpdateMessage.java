package it.polimi.ingsw.protocol;

import it.polimi.ingsw.server.model.Archipelago;

import java.util.ArrayList;

/**
 * This message is sent from the server to the client to indicate the occurred update of an island (NB it also
 * comprehends mother nature’s movement).
 */
public class IslandsUpdateMessage extends Message{

    private final ArrayList<Archipelago> updatedArchipelagos;

    public IslandsUpdateMessage(ArrayList<Archipelago> updatedArchipelagos) {
        super(MessageLabel.ISLANDS_UPDATE);
        this.updatedArchipelagos = updatedArchipelagos;
    }

    /**
     * @return the updated Archipelagos
     */
    public ArrayList<Archipelago> getUpdatedArchipelagos() {
        return updatedArchipelagos;
    }
}
