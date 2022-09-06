package it.polimi.ingsw.server.model.characters.cards;

import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.server.model.characters.parameters.Parameter;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CentaurTest {

    @Test
    void actualTask() {
        Centaur c = new Centaur();
        // checks if the Centaur's id and cost are correct
        assertEquals(6, c.getId());
        assertEquals(3, c.getCost());

        Archipelago a = new Archipelago(true);
        Board b = new Board(8, Tower.BLACK);
        b.updatePresenceOfProfessor(Student.RED);
        b.updatePresenceOfProfessor(Student.BLUE);
        Player p = new Player("p", false, b, Wizard.WIZARD1);
        ArrayList<Island> i = new ArrayList<>();
        i.add(new Island());
        for(int n=0; n<10; n++)
            a.getIslands().get(0).addStudent(Student.RED);
        for(int n=0; n<5; n++)
            a.getIslands().get(0).addStudent(Student.PINK);
        for(int n=0; n<2; n++)
            a.getIslands().get(0).addStudent(Student.GREEN);
        for(int n=0; n<4; n++)
            i.get(0).addStudent(Student.BLUE);
        for(int n=0; n<8; n++)
            i.get(0).addStudent(Student.YELLOW);
        a.addIslands(i);

        // checks if the totalPlayerInfluence method works fine before activating the character effect
        assertEquals(14, a.totalPlayerInfluence(p));
        a.setTower(Tower.BLACK);
        assertEquals(16, a.totalPlayerInfluence(p));

        ArrayList<Parameter> par = new ArrayList<>();
        c.task(par);
        assertEquals(4,c.getCost()); // checks if the cost of the character has increased after using its effect
        assertEquals(14, c.actualTask(p,a)); // checks if, after the activation of the character's effect, the towers on the archipelago are not counted when the influence is calculated
    }
}