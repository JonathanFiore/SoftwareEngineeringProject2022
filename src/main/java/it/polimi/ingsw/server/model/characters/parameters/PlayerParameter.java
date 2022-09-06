package it.polimi.ingsw.server.model.characters.parameters;

import it.polimi.ingsw.server.model.Player;

/**
 *This is a parameter which contains a Player
 */
public class PlayerParameter extends Parameter{

    private final Player player;

    public PlayerParameter(String name, Player player) {
        super(name);
        this.player = player;
    }

    /**
     * @return the parameter "player"
     */
    public Player getPlayer() {
        return player;
    }
}
