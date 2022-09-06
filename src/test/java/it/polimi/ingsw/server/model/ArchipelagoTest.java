package it.polimi.ingsw.server.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ArchipelagoTest {

    @Test
    void getTower() {
        Archipelago a = new Archipelago(false);
        assertNull(a.getTower()); // checks if, when an archipelago is instantiated, it hasn't a tower associated to it
    }

    @Test
    void setTower() {
        Archipelago a = new Archipelago(false);
        assertNull(a.getTower()); // when an archipelago is instantiated, it hasn't a tower associated to it
        a.setTower(Tower.BLACK);
        assertEquals(Tower.BLACK, a.getTower()); // checks if the archipelago's tower is updated according to the setTower parameter's value
    }

    @Test
    void checkMotherNature() {
        Archipelago a1 = new Archipelago(false);
        Archipelago a2 = new Archipelago(true);
        assertFalse(a1.checkMotherNature()); // checks if Mother Nature is not on the archipelago, according the parameter of its constructor
        assertTrue(a2.checkMotherNature()); // checks if Mother Nature is on the archipelago, according the parameter of its constructor
    }

    @Test
    void updateMotherNature() {
        Archipelago a = new Archipelago(false);
        assertFalse(a.checkMotherNature()); // checks if Mother Nature is not on the archipelago, according the parameter of its constructor
        a.updateMotherNature();
        assertTrue(a.checkMotherNature()); // check if the value of the boolean 'motherNature, has changed when calling the updateMotherNature method
    }

    @Test
    void addIslands() {
        Archipelago a = new Archipelago(false);
        ArrayList<Island> i = new ArrayList<>();
        assertEquals(1, a.getIslands().size()); // checks if an archipelago has 1 island when instantiated
        for(int n=0; n<5; n++)
            i.add(new Island());
        a.addIslands(i); // 'i' contains 5 islands
        assertEquals(6, a.getIslands().size()); // checks if all the 5 island are added to the archipelago
        for(int n=0; n<5; n++)
            assertEquals(i.get(n), a.getIslands().get(n+1)); // checks if the 5 islands are added in the correct order
    }

    @Test
    void totalPlayerInfluence() {
        Archipelago a = new Archipelago(true);
        Board b = new Board(8, Tower.BLACK);
        b.updatePresenceOfProfessor(Student.RED);
        b.updatePresenceOfProfessor(Student.BLUE);
        Player p = new Player("p", false, b, Wizard.WIZARD1); // 'p' has the RED and BLUE Professor
        ArrayList<Island> i = new ArrayList<>();
        i.add(new Island());
        for(int n=0; n<10; n++)
            a.getIslands().get(0).addStudent(Student.RED); // 10 RED Students are added to the archipelago 'a'
        for(int n=0; n<5; n++)
            a.getIslands().get(0).addStudent(Student.PINK); // 5 PINK Students are added to the archipelago 'a'
        for(int n=0; n<2; n++)
            a.getIslands().get(0).addStudent(Student.GREEN); // 2 GREEN Students are added to the archipelago 'a'
        for(int n=0; n<4; n++)
            i.get(0).addStudent(Student.BLUE); // 4 BLUE Students are added to the island 'i'
        for(int n=0; n<8; n++)
            i.get(0).addStudent(Student.YELLOW); // 8 YELLOW Students are added to the island 'i'
        assertEquals(10, a.totalPlayerInfluence(p)); // checks if the value of the influence is equal to the number of RED Students on the archipelago (the player has the RED Professor)
        a.setTower(Tower.BLACK);
        assertEquals(11, a.totalPlayerInfluence(p));  // checks if the value of the influence increases by the number of islands of the archipelago when on the archipelago there is a tower with the same color of the player's tower
        a.setTower(Tower.GREY); // archipelago's tower is updated
        a.addIslands(i);
        assertEquals(14, a.totalPlayerInfluence(p)); // checks if the value of the influence is equal to the sum of RED and BLUE Students on the archipelago (the player has the RED and BLUE Professor)
        a.setTower(Tower.BLACK);
        assertEquals(16, a.totalPlayerInfluence(p)); // checks if the value of the influence increases by the number of islands of the archipelago when on the archipelago there is a tower with the same color of the player's tower
        b.updatePresenceOfProfessor(Student.YELLOW);
        assertEquals(24, a.totalPlayerInfluence(p)); // checks if the value of the influence is equal to the sum of RED, BLUE and YELLOW Students on the archipelago (the player has the RED, BLUE and YELLOW Professor), plus the additional points given by the towers
        a.setTower(Tower.WHITE);
        assertEquals(22, a.totalPlayerInfluence(p)); // checks if, after updating the archipelago's towers, the towers do not give additional points anymore
    }

    @Test
    void checkNoEntry() {
        Archipelago a = new Archipelago(false);
        assertEquals(a.checkNoEntry(),0); // checks if an archipelago does not have any No Entry cards when instantiated
        a.addNoEntry(1);
        assertEquals(a.checkNoEntry(),1); // checks if the number of No Entry card on the archipelago increases by the number specified as parameter in the noEntry method
        a.addNoEntry(2);
        assertEquals(a.checkNoEntry(),3); // checks if the number of No Entry card on the archipelago increases by the number specified as parameter in the noEntry method
        for(int i = 0; i < 3; i++) {
            a.removeNoEntry();
            assertEquals(a.checkNoEntry(), 2 - i); // checks if, after each call of removeNoEntry(), the number of No Entry cards on the archipelago decreases by 1
        }
    }
}