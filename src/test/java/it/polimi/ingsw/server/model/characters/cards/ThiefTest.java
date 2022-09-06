package it.polimi.ingsw.server.model.characters.cards;

import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.server.model.characters.parameters.Parameter;
import it.polimi.ingsw.server.model.characters.parameters.StudentParameter;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ThiefTest {

    @Test
    void task() {
        Board b1 = new Board(8, Tower.BLACK);
        b1.getHallStudents().put(Student.RED,4);
        Player pl1 = new Player("pl1", false, b1, Wizard.WIZARD1); // player 'pl1' has 4 RED Students in their hall

        Board b2 = new Board(8, Tower.WHITE);
        b2.getHallStudents().put(Student.RED,2);
        Player pl2 = new Player("pl2", false, b2, Wizard.WIZARD2); // player 'pl2' has 2 RED Students in their hall

        ArrayList<Player> pl = new ArrayList<>();
        pl.add(pl1);
        pl.add(pl2);

        Bag b = new Bag();
        Thief t = new Thief(pl,b);
        // checks if the Thief's id and cost are correct
        assertEquals(12, t.getId());
        assertEquals(3, t.getCost());

        ArrayList<Parameter> p = new ArrayList<>();
        Parameter sp = new StudentParameter("sp", Student.RED); // RED is the chosen Student color for the Character's effect
        p.add(sp);
        t.task(p);
        assertEquals(4, t.getCost()); // checks if the cost of the character has increased after using its effect
        assertEquals(1,b1.getNumberOfStudents(Student.RED)); // checks if 3 RED students are removed from the first player's hall
        assertEquals(0,b2.getNumberOfStudents(Student.RED)); // checks if all the RED Students are removed from the second player's hall (because the player had less than 3 RED Students)
    }
}