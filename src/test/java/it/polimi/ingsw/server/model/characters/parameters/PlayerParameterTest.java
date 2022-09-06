package it.polimi.ingsw.server.model.characters.parameters;

import it.polimi.ingsw.server.model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerParameterTest {

    @Test
    void getPlayer() {
        Board b = new Board(8, Tower.BLACK);
        Player p = new Player("p", false, b, Wizard.WIZARD1);
        Parameter pl = new PlayerParameter("player", p);
        assertInstanceOf(Player.class, ((PlayerParameter)pl).getPlayer()); // checks if the method correctly returns a Player
        assertEquals(p, ((PlayerParameter)pl).getPlayer()); // checks if the returned Player is equal to the one passed to the PlayerParameter constructor
        assertEquals("player", pl.getName()); // checks if the returned String is equal to the one passed to the PlayerParameter constructor
    }
}