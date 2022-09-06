package it.polimi.ingsw.server.model.characters.parameters;

import it.polimi.ingsw.server.model.Student;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentParameterTest {

    @Test
    void getStudent() {
        Parameter s = new StudentParameter("student", Student.RED);
        assertInstanceOf(Student.class, ((StudentParameter)s).getStudent()); // checks if the method correctly returns a Student
        assertEquals(Student.RED, ((StudentParameter)s).getStudent()); // checks if the method returns the same Student as the one passed to the constructor
        assertEquals("student", s.getName()); // checks if the method returns the same String as the one passed to the constructor
    }
}