package it.polimi.ingsw.server.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TeamTest {

    @Test
    void getTower() {
        Board b1 = new Board(8, Tower.BLACK);
        Board b2 = new Board(8, Tower.BLACK);
        Player p1 = new Player("p1", false, b1, Wizard.WIZARD1);
        Player p2 = new Player("p2", false, b2, Wizard.WIZARD2);
        Team t = new Team(p1, p2, Tower.BLACK);
        assertSame(t.getTower(), Tower.BLACK); // checks if the tower color is the same assigned in the constructor
    }

    @Test
    void getPlayers() {
        Board b1 = new Board(8, Tower.BLACK);
        Board b2 = new Board(8, Tower.BLACK);
        Player p1 = new Player("p1", false, b1, Wizard.WIZARD1);
        Player p2 = new Player("p2", false, b2, Wizard.WIZARD2);
        Team t = new Team(p1, p2, Tower.BLACK);
        Player[] p;
        p = t.getPlayers();
        assertEquals(p[0], p1); // checks if the first player of the team is the same passed as first in the constructor
        assertEquals(p[1], p2); // checks if the second player of the team is the same passed as second in the constructor
    }

    @Test
    void getPlayerLeader() {
        Board b1 = new Board(8, Tower.BLACK);
        Board b2 = new Board(8, Tower.BLACK);
        Player p1 = new Player("p1", false, b1, Wizard.WIZARD1);
        Player p2 = new Player("p2", false, b2, Wizard.WIZARD2);
        Team t = new Team(p1, p2, Tower.BLACK);
        assertEquals(p1, t.getPlayerLeader()); // checks if the team leader is the same player passed as first in the constructor
    }
}