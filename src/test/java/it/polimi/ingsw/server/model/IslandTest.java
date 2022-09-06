package it.polimi.ingsw.server.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IslandTest {

    @Test
    void addStudent() {
        Island i = new Island();
        assertEquals(0,i.getStudents().stream().filter(x->x==Student.RED).count()); // check if the number of RED students is 0 when an Island is instantiated
        assertEquals(0,i.getStudents().stream().filter(x->x==Student.BLUE).count()); // check if the number of BLUE students is 0 when an Island is instantiated
        assertEquals(0,i.getStudents().stream().filter(x->x==Student.GREEN).count()); // check if the number of GREEN students is 0 when an Island is instantiated
        assertEquals(0,i.getStudents().stream().filter(x->x==Student.PINK).count()); // check if the number of PINK students is 0 when an Island is instantiated
        assertEquals(0,i.getStudents().stream().filter(x->x==Student.YELLOW).count()); // check if the number of YELLOW students is 0 when an Island is instantiated
        i.addStudent(Student.RED);
        i.addStudent(Student.BLUE);
        i.addStudent(Student.RED);
        assertEquals(2,i.getStudents().stream().filter(x->x==Student.RED).count()); // check if the number of RED students has increased after calling the addStudent method twice
        assertEquals(1,i.getStudents().stream().filter(x->x==Student.BLUE).count()); // check if the number of BLUE students has increased after calling the addStudent method once
        assertEquals(0,i.getStudents().stream().filter(x->x==Student.GREEN).count()); // check if the number of GREEN students has not changed after calling the addStudent method
        assertEquals(0,i.getStudents().stream().filter(x->x==Student.PINK).count()); // check if the number of PINK students has not changed after calling the addStudent method
        assertEquals(0,i.getStudents().stream().filter(x->x==Student.YELLOW).count()); // check if the number of YELLOW students has not changed after calling the addStudent method
        assertSame(i.getStudents().get(0), Student.RED); // check if the first student on the Island is the same as the parameter of the addStudent method called as first
        assertSame(i.getStudents().get(1), Student.BLUE); // check if the second student on the Island is the same as the parameter of the addStudent method called as second
        assertSame(i.getStudents().get(2), Student.RED); // check if the third student on the Island is the same as the parameter of the addStudent method called as third
        assertEquals(3, i.getStudents().size()); // check if the amount of students on the island is equal to the times the addStudent method is called
    }

    @Test
    void playerInfluence() {
        Board b = new Board(8, Tower.BLACK);
        b.updatePresenceOfProfessor(Student.RED);
        b.updatePresenceOfProfessor(Student.BLUE);
        Player p = new Player("p", false, b, Wizard.WIZARD1); // the player 'p' has both the RED and the BLUE Professor
        Island i = new Island();
        for(int n=0; n<10; n++) // 10 RED Students are added to the island
            i.addStudent(Student.RED);
        for(int n=0; n<5; n++) // 5 PINK Students are added to the island
            i.addStudent(Student.PINK);
        for(int n=0; n<2; n++) // 2 GREEN Students are added to the island
            i.addStudent(Student.GREEN);
        for(int n=0; n<4; n++) // 4 BLUE Students are added to the island
            i.addStudent(Student.BLUE);
        for(int n=0; n<8; n++) // 8 YELLOW Students are added to the island
            i.addStudent(Student.YELLOW);
        assertEquals(14, i.playerInfluence(p)); // checks if the influence of the player is equal to the sum of RED and BLUE students (the same colors of the Professors owned by the player)
        p.getBoard().updatePresenceOfProfessor(Student.YELLOW); // the YELLOW Professor is given to the player
        p.getBoard().updatePresenceOfProfessor(Student.RED); // the player does not have the RED Professor anymore
        assertEquals(12, i.playerInfluence(p)); // checks if the influence of the player is equal to the sum of YELLOW and BLUE students (the same colors of the Professors owned by the player)
        p.getBoard().updatePresenceOfProfessor(Student.BLUE); // the player does not have the BLUE Professor anymore
        p.getBoard().updatePresenceOfProfessor(Student.YELLOW); // the player does not have the YELLOW Professor anymore
        assertEquals(0, i.playerInfluence(p)); // checks if the influence of the player is equal to 0, because the player hasn't got any Professor
        p.getBoard().updatePresenceOfProfessor(Student.RED); // the RED Professor is given to the player
        p.getBoard().updatePresenceOfProfessor(Student.PINK); // the PINK Professor is given to the player
        p.getBoard().updatePresenceOfProfessor(Student.GREEN); // the GREEN Professor is given to the player
        p.getBoard().updatePresenceOfProfessor(Student.BLUE); // the BLUE Professor is given to the player
        p.getBoard().updatePresenceOfProfessor(Student.YELLOW); // the YELLOW Professor is given to the player
        assertEquals(29, i.playerInfluence(p)); // checks if the influence of the player is equal to the sum of students of each color (the player has got every Professor)
    }
}