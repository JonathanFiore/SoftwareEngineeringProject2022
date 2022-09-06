package it.polimi.ingsw.server.model.characters.parameters;

import it.polimi.ingsw.server.model.Student;


/**
 *This is a parameter which contains a student
 */
public class StudentParameter extends Parameter{

    private final Student student;

    public StudentParameter(String name, Student student) {
        super(name);
        this.student = student;
    }

    /**
     * @return the parameter "student"
     */
    public Student getStudent() {
        return student;
    }

}
