package it.polimi.ingsw.server.model.characters.cards;

import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.server.model.characters.parameters.IntegerParameter;
import it.polimi.ingsw.server.model.characters.parameters.Parameter;
import it.polimi.ingsw.server.model.characters.parameters.PlayerParameter;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SpoiledPrincessTest {

    @Test
    void getStudents(){
        Bag b = new Bag();
        SpoiledPrincess sp = new SpoiledPrincess(b);
        // checks if the SpoiledPrincess's id and cost are correct
        assertEquals(11, sp.getId());
        assertEquals(2, sp.getCost());

        assertEquals(4, sp.getStudents().size()); // checks if the Character card has 4 pawns on it
        for(int i=0; i<4; i++)
            assertInstanceOf(Student.class, sp.getStudents().get(i)); // checks if each element of the 'students' attribute is a Student
    }

    @Test
    void task() {
        Bag bag = new Bag();
        SpoiledPrincess sp = new SpoiledPrincess(bag);

        ArrayList<Parameter> p = new ArrayList<>();
        Board b = new Board(8, Tower.BLACK);
        Player pl = new Player("pl", false, b, Wizard.WIZARD1);
        Parameter pp = new PlayerParameter("pp", pl);
        Parameter ip = new IntegerParameter("ip", 0); // index of the Student that has to be removed from the card
        p.add(pp);
        p.add(ip);

        Student s = sp.getStudents().get(0); // 's' is the first Student on the Character card
        assertEquals(0, b.getNumberOfStudents(s)); // checks if the Dining Room of the player 'pl' has 0 Student whose color is equal to the color of 's'

        ArrayList<Student> t = new ArrayList<>();
        for(int i=1; i<4; i++)
            t.add(sp.getStudents().get(i)); // 't' contains all the Student of the Character card, excluded the first one

        sp.task(p);
        assertEquals(3, sp.getCost()); // checks if the cost of the character has increased after using its effect
        assertEquals(1, b.getNumberOfStudents(s)); // checks if the Student has been moved to the player's Hall
        assertEquals(4, sp.getStudents().size()); // checks if the Character card has the same number of Students as before
        for(int i=0; i<3; i++)
            assertEquals(t.get(i), sp.getStudents().get(i));  // checks if the Students on the card are the same as before (except for the one that has been moved)
        assertInstanceOf(Student.class, sp.getStudents().get(3));  // checks if a new Student has been added to the card
    }
}