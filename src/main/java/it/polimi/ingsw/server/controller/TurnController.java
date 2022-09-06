package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.model.Player;

/**
 * This class is used to associate the Player object with the VirtualView of that specific player
 */
public class TurnController {
    private final VirtualView playerView;
    private final Player player;

    public TurnController(VirtualView playerView, Player player) {
        this.playerView = playerView;
        this.player = player;
    }

    /**
     * @return the playerView
     */
    public VirtualView getPlayerView() {
        return playerView;
    }

    /**
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }
}
