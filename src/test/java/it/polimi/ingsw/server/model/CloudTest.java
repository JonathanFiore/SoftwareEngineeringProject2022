package it.polimi.ingsw.server.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CloudTest {

    @Test
    void addStudent() {
        Cloud c = new Cloud();
        assertEquals(0, c.getStudents().size()); // check if the cloud is empty
        for (int i = 1; i <= 10; i++) {
            c.addStudent(Student.RED);
            assertEquals(c.getStudents().size(), i); // checks if the number of students on the cloud has increased by 1 after calling the addStudent method
        }
        assertEquals(10, c.getStudents().stream().filter(x -> x == Student.RED).count()); // checks if, with each call of the addStudent method, one RED Student is added to the cloud
        c.addStudent(Student.BLUE);
        assertSame(c.getStudents().get(c.getStudents().size() - 1), Student.BLUE); // checks if the last student of the cloud is equal to the last added
    }

    @Test
    void clearCloud() {
        Cloud c = new Cloud();
        c.addStudent(Student.RED);
        assertEquals(1, c.getStudents().size()); // checks if the number of students on the cloud has increased by 1 after calling the addStudent method
        c.clearCloud();
        assertEquals(0, c.getStudents().size()); // checks if the previously added student has been removed
        for (int i = 0; i < 10; i++) {
            Student t = Student.RED;
            c.addStudent(t);
        }
        assertEquals(10, c.getStudents().size()); // checks if each call of addStudent has worked fine
        c.clearCloud();
        assertEquals(0, c.getStudents().size()); // checks if all the students on the cloud are removed
    }
}