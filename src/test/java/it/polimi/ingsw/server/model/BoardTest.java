package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.serverexceptions.EndGameException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void getTowerColor() {
        Board b = new Board(8, Tower.BLACK);
        assertSame(b.getTowerColor(), Tower.BLACK); // check if returned tower has the same color of the one passed to the Board constructor
    }

    @Test
    void getNumberTowers() {
        Board b = new Board(8, Tower.BLACK);
        assertEquals(8, b.getNumberTowers()); // checks if the method returns the correct amount of towers (the one passed to the constructor of Board)
    }

    @Test
    void addTower() {
        Board b = new Board(8, Tower.WHITE);
        assertEquals(8, b.getNumberTowers());
        for(int i = 0; i<3;i++) // if the add works correctly, 'b' will have a total of 11 towers at the end of the for loop
            b.addTower();
        assertEquals(11, b.getNumberTowers()); // check if the number of towers has increased by 3 after calling the addTower method thrice
    }

    @Test
    void removeTower() {
        Board b = new Board(8, Tower.BLACK);
        for (int i = 0; i < 7; i++)
            assertDoesNotThrow(() -> b.removeTower()); // check if the first 7 removed towers do not cause an EndGame Exception
        assertThrowsExactly(EndGameException.class, () -> b.removeTower()); // check if the eighth removed tower causes an EndGame Exception
    }

    @Test
    void moveToEntrance() {
        Board b = new Board(8, Tower.BLACK);
        assertNull(b.getEntrance()[0]); // check if the entrance does not have a Student in its first position
        b.moveToEntrance(Student.RED);
        assertEquals(Student.RED, b.getEntrance()[0]); // checks if the RED Student has been moved to the entrance
    }

    @Test
    void moveStudentToHall() {
        Board b = new Board(8, Tower.BLACK);
        // checks if the Hall is empty when instantiating a new Board
        assertEquals(0, b.getHallStudents().get(Student.RED));
        assertEquals(0, b.getHallStudents().get(Student.BLUE));
        assertEquals(0, b.getHallStudents().get(Student.PINK));
        assertEquals(0, b.getHallStudents().get(Student.YELLOW));
        assertEquals(0, b.getHallStudents().get(Student.GREEN));

        // three Students are added to the entrance, then moved to the hall
        b.moveToEntrance(Student.RED);
        b.moveToEntrance(Student.RED);
        b.moveToEntrance(Student.GREEN);
        b.moveStudentToHall(0);
        b.moveStudentToHall(1);
        b.moveStudentToHall(2);
        // checks if the three chosen Students have been correctly moved from the entrance to the hall
        assertEquals(2, b.getHallStudents().get(Student.RED));
        assertEquals(0, b.getHallStudents().get(Student.BLUE));
        assertEquals(0, b.getHallStudents().get(Student.PINK));
        assertEquals(0, b.getHallStudents().get(Student.YELLOW));
        assertEquals(1, b.getHallStudents().get(Student.GREEN));
    }

    @Test
    void removeFromHall() {
        Board b = new Board(8, Tower.BLACK); // when instantiating a Board, its hall is empty
        // checks if it is not possible to remove students form the hall
        assertNull(b.removeFromHall(Student.RED));
        assertNull(b.removeFromHall(Student.YELLOW));
        assertNull(b.removeFromHall(Student.GREEN));
        assertNull(b.removeFromHall(Student.PINK));
        assertNull(b.removeFromHall(Student.BLUE));

        // three students are added to the entrance, then moved to the hall
        b.moveToEntrance(Student.RED);
        b.moveToEntrance(Student.RED);
        b.moveToEntrance(Student.GREEN);
        b.moveStudentToHall(0);
        b.moveStudentToHall(1);
        b.moveStudentToHall(2);

        // check if it is possible to remove from the hall the three students previously added
        assertEquals(Student.RED, b.removeFromHall(Student.RED));
        assertEquals(Student.RED, b.removeFromHall(Student.RED));
        assertNull(b.removeFromHall(Student.RED));
        assertNull(b.removeFromHall(Student.YELLOW));
        assertEquals(Student.GREEN, b.removeFromHall(Student.GREEN));
        assertNull(b.removeFromHall(Student.GREEN));
        assertNull(b.removeFromHall(Student.PINK));
        assertNull(b.removeFromHall(Student.BLUE));
    }

    @Test
    void moveStudentToIsland() {
        Board b = new Board(8, Tower.BLACK);
        Island i = new Island();
        assertEquals(0, i.getStudents().size()); // check if the island is empty

        // three students are added to the entrance
        b.moveToEntrance(Student.RED);
        b.moveToEntrance(Student.RED);
        b.moveToEntrance(Student.GREEN);
        b.moveStudentToIsland(i, 0); // the RED Student is moved from the entrance to the island
        assertEquals(i.getStudents().size(), 1); // checks if the number of students on the island has increased
        assertEquals(i.getStudents().get(0), Student.RED); // checks if the new Student on the island is equal to the Student that was in the first position of the entrance
        assertNull(b.getEntrance()[0]); // checks if the moved student has been removed from the entrance
        // the other two Students of the entrance are moved to the island
        b.moveStudentToIsland(i, 1);
        b.moveStudentToIsland(i, 2);
        assertEquals(i.getStudents().size(), 3); // checks if the number of students on the island has increased after moving another two students
        // checks if the students on the island are the same students that were previously on the entrance
        assertEquals(i.getStudents().get(0), Student.RED);
        assertEquals(i.getStudents().get(1), Student.RED);
        assertEquals(i.getStudents().get(2), Student.GREEN);
        // checks if the moved students have been removed from the entrance
        assertNull(b.getEntrance()[0]);
        assertNull(b.getEntrance()[1]);
        assertNull(b.getEntrance()[2]);
    }

    @Test
    void getNumberOfStudents() {
        Board b = new Board(8, Tower.BLACK);
        // checks if the hall is empty when the Board is instantiated
        assertEquals(0, b.getNumberOfStudents(Student.RED));
        assertEquals(0, b.getNumberOfStudents(Student.BLUE));
        assertEquals(0, b.getNumberOfStudents(Student.PINK));
        assertEquals(0, b.getNumberOfStudents(Student.YELLOW));
        assertEquals(0, b.getNumberOfStudents(Student.GREEN));

        // three students are moved to the entrance, then moved to the hall
        b.moveToEntrance(Student.RED);
        b.moveToEntrance(Student.RED);
        b.moveToEntrance(Student.GREEN);
        b.moveStudentToHall(0);
        b.moveStudentToHall(1);
        b.moveStudentToHall(2);

        assertEquals(2, b.getNumberOfStudents(Student.RED)); // checks if the returned value is equal to the number of RED Students moved to the hall
        assertEquals(0, b.getNumberOfStudents(Student.BLUE)); // checks if the returned value is equal to the number of BLUE Students moved to the hall
        assertEquals(0, b.getNumberOfStudents(Student.PINK)); // checks if the returned value is equal to the number of PINK Students moved to the hall
        assertEquals(0, b.getNumberOfStudents(Student.YELLOW)); // checks if the returned value is equal to the number of YELLOW Students moved to the hall
        assertEquals(1, b.getNumberOfStudents(Student.GREEN)); // checks if the returned value is equal to the number of GREEN Students moved to the hall
    }

    @Test
    void isProfessorPresent() {
        Board b = new Board(8, Tower.BLACK);
        // checks if the board has 0 Professors when instantiated
        assertFalse(b.isProfessorPresent(Student.YELLOW));
        assertFalse(b.isProfessorPresent(Student.GREEN));
        assertFalse(b.isProfessorPresent(Student.BLUE));
        assertFalse(b.isProfessorPresent(Student.PINK));
        assertFalse(b.isProfessorPresent(Student.RED));
    }

    @Test
    void updatePresenceOfProfessor() {
        Board b = new Board(8, Tower.BLACK);
        // the board has the YELLOW and BLUE Professors
        b.updatePresenceOfProfessor(Student.YELLOW);
        b.updatePresenceOfProfessor(Student.BLUE);
        //checks if the Professor table has been correctly updated
        assertTrue(b.isProfessorPresent(Student.YELLOW));
        assertFalse(b.isProfessorPresent(Student.GREEN));
        assertTrue(b.isProfessorPresent(Student.BLUE));
        assertFalse(b.isProfessorPresent(Student.PINK));
        assertFalse(b.isProfessorPresent(Student.RED));

        // the board has the BLUE Professor too
        b.updatePresenceOfProfessor(Student.BLUE);
        // checks if it is correctly recognized the presence of the three Professors
        assertTrue(b.isProfessorPresent(Student.YELLOW));
        assertFalse(b.isProfessorPresent(Student.GREEN));
        assertFalse(b.isProfessorPresent(Student.BLUE));
        assertFalse(b.isProfessorPresent(Student.PINK));
        assertFalse(b.isProfessorPresent(Student.RED));
    }

    @Test
    void updateTakenCoins() {
        Board b = new Board(8, Tower.BLACK);
        // checks if all the taken coins for each color are set to 0
        assertEquals(0, b.getTakenCoins().get(Student.YELLOW));
        assertEquals(0, b.getTakenCoins().get(Student.PINK));
        assertEquals(0, b.getTakenCoins().get(Student.BLUE));
        assertEquals(0, b.getTakenCoins().get(Student.GREEN));
        assertEquals(0, b.getTakenCoins().get(Student.RED));

        b.updateTakenCoins(Student.YELLOW, 1);
        b.updateTakenCoins(Student.PINK, 2);
        // checks if the updateTakenCoins method has added the correct amount of coins
        assertEquals(1, b.getTakenCoins().get(Student.YELLOW));
        assertEquals(2, b.getTakenCoins().get(Student.PINK));
        assertEquals(0, b.getTakenCoins().get(Student.BLUE));
        assertEquals(0, b.getTakenCoins().get(Student.GREEN));
        assertEquals(0, b.getTakenCoins().get(Student.RED));

        b.updateTakenCoins(Student.YELLOW, 2);
        // check if the method works fine even when it updates the same color twice
        assertEquals(3, b.getTakenCoins().get(Student.YELLOW));
        assertEquals(2, b.getTakenCoins().get(Student.PINK));
        assertEquals(0, b.getTakenCoins().get(Student.BLUE));
        assertEquals(0, b.getTakenCoins().get(Student.GREEN));
        assertEquals(0, b.getTakenCoins().get(Student.RED));
    }

    @Test
    void numberProfessorOwned(){
        Board b = new Board(8, Tower.BLACK);
        assertEquals(0, b.numberProfessorOwned()); // checks if the board has 0 Professor when instantiated

        b.updatePresenceOfProfessor(Student.YELLOW);
        b.updatePresenceOfProfessor(Student.BLUE);
        // Board 'b' has the BLUE and YELLOW Professor (total = 2)
        assertEquals(2, b.numberProfessorOwned()); // checks if the number of professors changed according to the updates made

        // Board 'b' has the YELLOW Professor (total = 1)
        b.updatePresenceOfProfessor(Student.BLUE);
        assertEquals(1, b.numberProfessorOwned()); // checks if the number of professors changed according to the updates made

        // Board 'b' has the YELLOW, RED and GREEN Professor (total = 3)
        b.updatePresenceOfProfessor(Student.RED);
        b.updatePresenceOfProfessor(Student.GREEN);
        assertEquals(3, b.numberProfessorOwned()); // checks if the number of professors changed according to the updates made

        // Board 'b' has the RED and PINK Professor (total = 2)
        b.updatePresenceOfProfessor(Student.GREEN);
        b.updatePresenceOfProfessor(Student.YELLOW);
        b.updatePresenceOfProfessor(Student.PINK);
        assertEquals(2, b.numberProfessorOwned()); // checks if the number of professors changed according to the updates made

        // Board 'b' has no Professor (total = 0)
        b.updatePresenceOfProfessor(Student.RED);
        b.updatePresenceOfProfessor(Student.PINK);
        assertEquals(0, b.numberProfessorOwned()); // checks if the number of professors changed according to the updates made
    }

    @Test
    void equals(){
        Board b = new Board(8, Tower.BLACK);
        assertTrue(b.equals(b)); // checks if the method works fine when comparing the same object

        Deck d = new Deck();
        assertFalse(b.equals(d)); // checks if the method works fine when comparing instances of different classes

        Board b2 = new Board(8, Tower.BLACK);
        assertTrue(b.equals(b2)); // checks if the method works fine when comparing two newly created boards

        Board b3 = new Board(8, Tower.WHITE);
        assertFalse(b.equals(b3)); // checks if the method works fine when comparing two boards with different tower colors

        Board b4 = new Board(6, Tower.BLACK);
        assertFalse(b.equals(b4)); // checks if the method works fine when comparing two boards with a different amount of towers

        b.moveToEntrance(Student.RED);
        b.moveToEntrance(Student.GREEN);
        b2.moveToEntrance(Student.RED);
        b2.moveToEntrance(Student.GREEN);
        assertTrue(b.equals(b2)); // checks if the method works fine when comparing two boards with the same Students (and in the same positions) in the entrance

        b.moveStudentToHall(0);
        assertFalse(b.equals(b2)); // checks if the method works fine when comparing two boards with different Students both in the entrance and in the hall

        b2.moveStudentToHall(0);
        assertTrue(b.equals(b2)); // checks if the method works fine when comparing two boards with the same Students (and in the same position) in the entrance and with the same amount of Students for each color in the hall

        b.updatePresenceOfProfessor(Student.BLUE);
        assertFalse(b.equals(b2)); // checks if the method works fine when comparing two boards with different Professor tables

        b2.updatePresenceOfProfessor(Student.YELLOW);
        assertFalse(b.equals(b2)); // checks if the method works fine when comparing two boards with the same amount of Professors but with different colors

        b.updatePresenceOfProfessor(Student.YELLOW);
        b2.updatePresenceOfProfessor(Student.BLUE);
        assertTrue(b.equals(b2)); // checks if the method works fine when comparing two boards with the same Professor

        b.updateTakenCoins(Student.RED, 2);
        assertFalse(b.equals(b2)); // checks if the method works fine when comparing two boards with different amount of 'taken coins' for each Student color

        b2.updateTakenCoins(Student.RED, 2);
        b.updateTakenCoins(Student.YELLOW, 1);
        b2.updateTakenCoins(Student.YELLOW, 1);
        assertTrue(b.equals(b2)); // checks if the method works fine when comparing two boards with the same amount of 'taken coins' for each Student color
    }
}