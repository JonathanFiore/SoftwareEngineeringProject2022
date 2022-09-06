package it.polimi.ingsw.server.model.characters.cards;

import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.server.model.characters.parameters.Parameter;
import it.polimi.ingsw.server.model.characters.parameters.PlayerParameter;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class KnightTest {

    @Test
    void actualTask() {
        Knight k = new Knight();
        // checks if the Knight's id and cost are correct
        assertEquals(8, k.getId());
        assertEquals(2, k.getCost());

        // two different players are created
        Board b1 = new Board(8, Tower.BLACK);
        Player pl1 = new Player("pl1", false, b1, Wizard.WIZARD1);
        Board b2 = new Board(8, Tower.WHITE);
        Player pl2 = new Player("pl2", false, b2, Wizard.WIZARD2);

        Parameter pp = new PlayerParameter("pp", pl1);
        ArrayList<Parameter> p = new ArrayList<>();
        p.add(pp); // the Character's effect will be activated for the first player (pl1)
        k.task(p);
        assertEquals(3, k.getCost()); // checks if the cost of the character has increased after using its effect

        assertEquals(2, k.actualTask(pl1)); // checks if 2 additional influence points are given to the first player
        assertEquals(0, k.actualTask(pl2));  // checks if no additional influence points are given to the second player
    }
}