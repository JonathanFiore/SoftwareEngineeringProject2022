package it.polimi.ingsw.server.model.characters.cards;

import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.server.model.characters.parameters.IntegerParameter;
import it.polimi.ingsw.server.model.characters.parameters.Parameter;
import it.polimi.ingsw.server.model.characters.parameters.PlayerParameter;
import it.polimi.ingsw.server.model.characters.parameters.StudentParameter;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MinstrelTest {

    @Test
    void task() {
        Minstrel m = new Minstrel();
        // checks if the Centaur's id and cost are correct
        assertEquals(10, m.getId());
        assertEquals(1, m.getCost());

        ArrayList<Parameter> pa = new ArrayList<>();

        // new Player is created, and it's Board is updated
        Board b = new Board(8, Tower.BLACK);
        b.moveToEntrance(Student.YELLOW);
        b.moveToEntrance(Student.PINK);
        b.getHallStudents().put(Student.RED,1);
        b.getHallStudents().put(Student.BLUE,1);
        Player pl = new Player("p", false, b, Wizard.WIZARD1);

        Parameter pp = new PlayerParameter("p", pl);
        Parameter n1 = new IntegerParameter("n1", 2); // number of Students to swap
        Parameter n2 = new IntegerParameter("n2", 0); // first chosen entrance Student index
        Parameter n3 = new IntegerParameter("n3", 1); // second chosen entrance Student index
        Parameter s1 = new StudentParameter("s1", Student.RED); // first chosen hall Student color
        Parameter s2 = new StudentParameter("s1", Student.BLUE); // second chosen hall Student color
        pa.add(pp);
        pa.add(n1);
        pa.add(n2);
        pa.add(n3);
        pa.add(s1);
        pa.add(s2);

        // checks if the Player's Hall has been correctly updated
        assertEquals(1,b.getNumberOfStudents(Student.RED));
        assertEquals(1,b.getNumberOfStudents(Student.BLUE));
        assertEquals(0,b.getNumberOfStudents(Student.PINK));
        assertEquals(0,b.getNumberOfStudents(Student.YELLOW));
        assertEquals(0,b.getNumberOfStudents(Student.GREEN));

        m.task(pa);
        assertEquals(2, m.getCost()); // checks if the cost of the character has increased after using its effect

        // checks if the entrance Students has been correctly moved to the hall
        assertEquals(0,b.getNumberOfStudents(Student.RED));
        assertEquals(0,b.getNumberOfStudents(Student.BLUE));
        assertEquals(1,b.getNumberOfStudents(Student.PINK));
        assertEquals(1,b.getNumberOfStudents(Student.YELLOW));
        assertEquals(0,b.getNumberOfStudents(Student.GREEN));

        // checks if the hall Students has been correctly moved to the entrance
        assertEquals(b.getEntrance()[0],Student.RED);
        assertEquals(b.getEntrance()[1],Student.BLUE);
    }
}