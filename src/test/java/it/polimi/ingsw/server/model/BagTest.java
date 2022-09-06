package it.polimi.ingsw.server.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BagTest {

    @Test
    void drawStudent() {
        Bag b = new Bag(); // the bag should contain 120 students when instantiated
        for (int i = 0; i < 120; i++) // checks if, after drawing 120 students, the 121st drawn is not actually a student, but it is equal to null
            assertNotNull(b.drawStudent());
        assertNull(b.drawStudent());

        Bag bag = new Bag();
        ArrayList<Student> s1 = new ArrayList<>();
        ArrayList<Student> s2 = new ArrayList<>();
        for (int i = 0; i < 24; i++) { // s1 has the same number of students for each color, for a total of 120 students
            s1.add(Student.BLUE);
            s1.add(Student.GREEN);
            s1.add(Student.PINK);
            s1.add(Student.RED);
            s1.add(Student.YELLOW);
        }
        for (int i = 0; i < 120; i++)
            s2.add(bag.drawStudent()); // s2 has all the students previously contained in the bag
        assertEquals(s1.size(), s2.size()); // checks if s1 has the same number of students of the bag
        assertEquals(s1.stream().filter(x -> x == Student.RED).count(), s2.stream().filter(x -> x == Student.RED).count()); // checks if the total number of RED students previously contained in the bag is exactly 24
        assertEquals(s1.stream().filter(x -> x == Student.GREEN).count(), s2.stream().filter(x -> x == Student.GREEN).count()); // checks if the total number of GREEN students previously contained in the bag is exactly 24
        assertEquals(s1.stream().filter(x -> x == Student.PINK).count(), s2.stream().filter(x -> x == Student.PINK).count()); // checks if the total number of PINK students previously contained in the bag is exactly 24
        assertEquals(s1.stream().filter(x -> x == Student.BLUE).count(), s2.stream().filter(x -> x == Student.BLUE).count()); // checks if the total number of BLUE students previously contained in the bag is exactly 24
        assertEquals(s1.stream().filter(x -> x == Student.YELLOW).count(), s2.stream().filter(x -> x == Student.YELLOW).count()); // checks if the total number of YELLOW students previously contained in the bag is exactly 24
    }

    @Test
    void isEmpty() {
        Bag b = new Bag();
        for (int i = 0; i < 120; i++) { // checks if, after drawing the first 119 students, the bag is still not empty
            assertFalse(b.isEmpty());
            b.drawStudent();
        }
        assertTrue(b.isEmpty()); // checks if the bag is empty when the 120th student is drawn
    }

    @Test
    void addToBag() {
        Bag b = new Bag();
        ArrayList<Student> s = new ArrayList<>();
        ArrayList<Student> s2 = new ArrayList<>();
        for (int i = 0; i < 24; i++) { // s contains the same students of a new Bag
            s.add(Student.BLUE);
            s.add(Student.GREEN);
            s.add(Student.PINK);
            s.add(Student.RED);
            s.add(Student.YELLOW);
        }
        for (int i = 0; i < 120; i++) // after exiting the for loop, the bag is empty
            b.drawStudent();
        int i = 0;
        while (i < 120) { // each student of 's' is added to the empty bag 'b'
            b.addToBag(s.get(i));
            i++;
        }
        for (i = 0; i < 120; i++) // s2 is filled with every student contained in 'b'
            s2.add(b.drawStudent());

        // checks if the addToBag method worked correctly: all the 120 students are added to the empty bag, the total number of students for each color is the same and it's equal to 24
        assertEquals(s.size(), s2.size());
        assertTrue(s.containsAll(s2));
        assertTrue(s2.containsAll(s));
    }
}