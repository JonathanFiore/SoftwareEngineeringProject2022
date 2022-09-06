package it.polimi.ingsw.server.model.characters.cards;

import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.server.model.characters.parameters.Parameter;
import it.polimi.ingsw.server.model.characters.parameters.StudentParameter;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MushroomHunterTest {

    @Test
    void actualTask() {
        MushroomHunter mh = new MushroomHunter();
        // checks if the MushroomHunter's id and cost are correct
        assertEquals(9, mh.getId());
        assertEquals(3, mh.getCost());

        Board b = new Board(8, Tower.BLACK);
        b.updatePresenceOfProfessor(Student.RED);
        b.updatePresenceOfProfessor(Student.BLUE);
        Player pl = new Player("p", false, b, Wizard.WIZARD1); // Player 'pl' has the RED and BLUE Professors

        Archipelago a = new Archipelago(true);
        ArrayList<Island> i = new ArrayList<>();
        i.add(new Island());
        a.addIslands(i);
        a.setTower(Tower.WHITE);
        for(int n=0; n<10; n++)
            a.getIslands().get(0).addStudent(Student.RED);
        for(int n=0; n<5; n++)
            a.getIslands().get(0).addStudent(Student.PINK);
        for(int n=0; n<2; n++)
            a.getIslands().get(0).addStudent(Student.GREEN);
        for(int n=0; n<4; n++)
            a.getIslands().get(1).addStudent(Student.BLUE);
        for(int n=0; n<8; n++)
            a.getIslands().get(1).addStudent(Student.YELLOW);
        assertEquals(14, a.totalPlayerInfluence(pl)); // checks if the influence is correctly calculated before the Character's effect is activated

        ArrayList<Parameter> pa = new ArrayList<>();
        Parameter s = new StudentParameter("s", Student.BLUE); // BLUE is the chose Student color
        pa.add(s);
        mh.task(pa);
        assertEquals(4, mh.getCost()); // checks if the cost of the character has increased after using its effect
        assertEquals(10, mh.actualTask(pl,a)); // checks if the influence is correctly calculated considering that the BLUE color adds no influence
        a.setTower(Tower.BLACK);
        assertEquals(12, mh.actualTask(pl,a)); // checks if the influence is correctly calculated considering that the BLUE color adds no influence and that there are two towers on the archipelago
    }
}