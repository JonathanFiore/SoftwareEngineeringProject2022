package it.polimi.ingsw.server.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Each island contains students, which are placed by the players. If the expert mode is on, a No Entry card could
 * be placed on the island
 */
public class Island implements Serializable {

    private final ArrayList<Student> students;

    public Island() {
        students = new ArrayList<>();
    }

    /**
     * @param student represents the student to be added to the island
     */
    public void addStudent(Student student){
        students.add(student);
    }

    /**
     * This method calculates the influence of a player on the island
     * @param p is the player for whom calculate the influence
     * @return the int value of the influence
     */
    public int playerInfluence(Player p){
        int sum=0;
        for(Student s : students){
            if(p.getBoard().isProfessorPresent(s))
                sum++;
        }
        return sum;
    }

    /**
     * @return all the students on the island
     */
    public ArrayList<Student> getStudents() {
        return students;
    }
}