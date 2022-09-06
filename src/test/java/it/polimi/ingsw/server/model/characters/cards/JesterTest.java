package it.polimi.ingsw.server.model.characters.cards;

import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.server.model.characters.parameters.IntegerParameter;
import it.polimi.ingsw.server.model.characters.parameters.Parameter;
import it.polimi.ingsw.server.model.characters.parameters.PlayerParameter;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class JesterTest {

    @Test
    void getStudents() {
        Jester j = new Jester(new Bag());
        assertEquals(6, j.getStudents().size()); // checks if the Jester card has 6 pawns on it when it is instantiated
        for(int i=0; i<6; i++)
            assertInstanceOf(Student.class, j.getStudents().get(i)); // checks if each element on the card is a Student pawn
    }

    @Test
    void task() {
        Jester j = new Jester(new Bag());
        // checks if the Jester's id and cost are correct
        assertEquals(7, j.getId());
        assertEquals(1, j.getCost());

        Board b = new Board(8, Tower.WHITE);
        Player pl = new Player("pl", false, b, Wizard.WIZARD1);
        Parameter pp = new PlayerParameter("pp", pl);
        Parameter ip1 = new IntegerParameter("ip1", 3); // number of Students to swap
        Parameter ip2 = new IntegerParameter("ip2", 0); // first selected Student index on the card
        Parameter ip3 = new IntegerParameter("ip3", 1); // second selected Student index on the card
        Parameter ip4 = new IntegerParameter("ip4", 2); // third selected Student index on the card
        Parameter ip5 = new IntegerParameter("ip5", 0); // first selected Student index on the Entrance
        Parameter ip6 = new IntegerParameter("ip6", 1); // second selected Student index on the Entrance
        Parameter ip7 = new IntegerParameter("ip7", 2); // third selected Student index on the Entrance
        ArrayList<Parameter> p = new ArrayList<>();
        p.add(pp);
        p.add(ip1);
        p.add(ip2);
        p.add(ip3);
        p.add(ip4);
        p.add(ip5);
        p.add(ip6);
        p.add(ip7);

        // the player's entrance is updated
        b.moveToEntrance(Student.RED);
        b.moveToEntrance(Student.PINK);
        b.moveToEntrance(Student.GREEN);

        // 'cs' contains Students that are equal to the first three Students on the Character card
        ArrayList<Student> cs = new ArrayList<>();
        for(int i=0; i<3; i++)
            cs.add(j.getStudents().get(i));

        j.task(p);
        assertEquals(2, j.getCost()); // checks if the cost of the character has increased after using its effect

        for(int i=0; i<3; i++)
            assertEquals(cs.get(i), b.getEntrance()[i]); // checks if the selected Students on the card have been moved to the player's entrance

        assertEquals(6, j.getStudents().size()); // checks if the number of Students on the card is still the same

        // checks if the selected Students on the entrance have been moved on the Character card
        assertEquals(Student.RED, j.getStudents().get(3));
        assertEquals(Student.PINK, j.getStudents().get(4));
        assertEquals(Student.GREEN, j.getStudents().get(5));
    }
}