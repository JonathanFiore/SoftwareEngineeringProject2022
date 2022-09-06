package it.polimi.ingsw.server.model.characters.cards;

import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.server.model.characters.parameters.Parameter;
import it.polimi.ingsw.server.model.characters.parameters.PlayerParameter;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FarmerTest {

    @Test
    void actualTask() {
        // creates an ArrayList of Students, whose Boards are already updated
        Board b1 = new Board(8, Tower.BLACK);
        b1.getHallStudents().put(Student.RED,6);
        b1.getHallStudents().put(Student.GREEN,0);
        b1.getHallStudents().put(Student.PINK,1);
        b1.getHallStudents().put(Student.BLUE,2);
        b1.getHallStudents().put(Student.YELLOW,3);
        b1.updatePresenceOfProfessor(Student.RED);
        Player pl1 = new Player("pl1", false, b1, Wizard.WIZARD1);
        Board b2 = new Board(8, Tower.WHITE);
        b2.getHallStudents().put(Student.RED,1);
        b2.getHallStudents().put(Student.GREEN,0);
        b2.getHallStudents().put(Student.PINK,1);
        b2.getHallStudents().put(Student.BLUE,4);
        b2.getHallStudents().put(Student.YELLOW,5);
        b2.updatePresenceOfProfessor(Student.PINK);
        b2.updatePresenceOfProfessor(Student.BLUE);
        b2.updatePresenceOfProfessor(Student.YELLOW);
        Player pl2 = new Player("pl2", false, b2, Wizard.WIZARD2);
        ArrayList<Player> pl = new ArrayList<>();
        pl.add(pl1);
        pl.add(pl2);

        Farmer f = new Farmer(pl);
        // checks if the Farmer's id and cost are correct
        assertEquals(2, f.getId());
        assertEquals(2, f.getCost());

        Archipelago a = new Archipelago(true);
        a.setTower(Tower.BLACK);
        ArrayList<Island> i = new ArrayList<>();
        i.add(new Island());
        a.addIslands(i);
        for(int n=0; n<10; n++)
            a.getIslands().get(0).addStudent(Student.RED);
        for(int n=0; n<2; n++)
            a.getIslands().get(0).addStudent(Student.GREEN);
        for(int n=0; n<5; n++)
            a.getIslands().get(0).addStudent(Student.PINK);
        for(int n=0; n<4; n++)
            a.getIslands().get(1).addStudent(Student.BLUE);
        for(int n=0; n<8; n++)
            a.getIslands().get(1).addStudent(Student.YELLOW);
        assertEquals(12, a.totalPlayerInfluence(pl1)); // checks if the first player's influence is correctly calculated before the activation of the character's effect
        assertEquals(17, a.totalPlayerInfluence(pl2)); // checks if the second player's influence is correctly calculated before the activation of the character's effect

        ArrayList<Parameter> p = new ArrayList<>();
        Parameter pp = new PlayerParameter("pp", pl1);
        p.add(pp);
        f.task(p);
        assertEquals(3,f.getCost()); // checks if the cost of the character has increased after using its effect

        assertEquals(17, f.actualTask(pl1, a)); // checks if the first player's influence is correctly calculated after the activation of the character's effect (the Player pl1 also controls the Pink Professor)
        assertEquals(12, f.actualTask(pl2, a)); // checks if the second player's influence is correctly calculated after the activation of the character's effect (the Player pl2 does not control the Pink Professor anymore)
    }
}