package it.polimi.ingsw.server.model;

import java.io.Serializable;

/**
 * This class is available only in "4-player game" mode and groups two players in a team
 */
public class Team implements Serializable {

    private final Player playerLeader;
    private final Player player2;
    private final Tower tower;

    public Team(Player playerLeader, Player player2, Tower tower) {
        this.playerLeader = playerLeader;
        this.player2 = player2;
        this.tower = tower;
    }

    /**
     * @return team's tower
     */
    public Tower getTower() {
        return tower;
    }

    /**
     * @return team's players
     */
    public Player[] getPlayers(){
        Player[] p = new Player[2];
        p[0] = playerLeader;
        p[1] = player2;
        return p;
    }


    /**
     * @return the playerLeader
     */
    public Player getPlayerLeader()
    {
        return playerLeader;
    }
}
