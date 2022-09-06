package it.polimi.ingsw.server.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Each cloud contains students that can be moved by the players
 */

public class Cloud implements Serializable {

    private final ArrayList<Student> students;

    public Cloud() {
        students = new ArrayList<>();
    }

    /**
     * @return all the students on the cloud
     */
    public ArrayList<Student> getStudents() {
        return students;
    }

    /**
     * @param student represents the student to be added to the cloud
     */
    public void addStudent(Student student){
        students.add(student);
    }

    /**
     * All the students on the cloud are removed
     */
    public void clearCloud(){
        students.clear();
    }
}