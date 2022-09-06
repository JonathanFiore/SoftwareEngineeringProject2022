package it.polimi.ingsw.server.model.characters.cards;

import it.polimi.ingsw.server.model.Archipelago;
import it.polimi.ingsw.server.model.Bag;
import it.polimi.ingsw.server.model.Student;
import it.polimi.ingsw.server.model.characters.parameters.ArchipelagoParameter;
import it.polimi.ingsw.server.model.characters.parameters.IntegerParameter;
import it.polimi.ingsw.server.model.characters.parameters.Parameter;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MonkTest {

    @Test
    void getStudents() {
        Bag b = new Bag();
        Monk m = new Monk(b);
        // checks if the Monk's id and cost are correct
        assertEquals(1, m.getId());
        assertEquals(1, m.getCost());

        assertEquals(4, m.getStudents().size()); // checks if the Character card has 4 pawns on it
        for(int i=0; i<4; i++)
            assertInstanceOf(Student.class, m.getStudents().get(i)); // checks if each element of the 'students' attribute is a Student
    }

    @Test
    void task() {
        Bag b = new Bag();
        Monk m = new Monk(b);

        Student s = m.getStudents().get(0); // 's' is the first Student of the Character card

        ArrayList<Student> t = new ArrayList<>();
        for(int i=1; i<4; i++)
            t.add(m.getStudents().get(i)); // 't' contains the second, third and fourth Student of the Character card

        ArrayList<Parameter> p = new ArrayList<>();
        Archipelago a = new Archipelago(false);
        Parameter ap = new ArchipelagoParameter("ap", a); // the destination Archipelago
        Parameter ip = new IntegerParameter("ip", 0); // the index of the Student that has to be moved
        p.add(ap);
        p.add(ip);

        assertEquals(0, a.getIslands().get(0).getStudents().size()); // check if the archipelago is empty
        assertFalse(m.isCoin()); // check if the Character card has 0 coins on it
        m.task(p);
        assertTrue(m.isCoin()); // checks if a coin is added to the Character card when its effect is activated
        assertEquals(2, m.getCost()); // checks if the cost of the character has increased after using its effect
        assertEquals(1, a.getIslands().get(0).getStudents().size()); // check if a Student is added to the archipelago
        assertEquals(s, a.getIslands().get(0).getStudents().get(0)); // checks if the added Student is equal to the Student that was the first on the Character card (its index was '0')

        assertEquals(4, m.getStudents().size()); // checks if the Character card has the same number of Students as before
        for(int i=0; i<3; i++)
            assertEquals(t.get(i), m.getStudents().get(i)); // checks if the Students on the card are the same as before (except for the one that has been moved)
        assertInstanceOf(Student.class, m.getStudents().get(3)); // checks if a new Student has been added to the card

    }
}