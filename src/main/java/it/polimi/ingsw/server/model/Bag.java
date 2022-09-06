package it.polimi.ingsw.server.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The bag contains the students than can be drawn during the game
 */

public class Bag implements Serializable {

    private final ArrayList<Student> students;

    public Bag() {
        students = new ArrayList<>();
        for(int i=0; i<24; i++){
            students.add(Student.BLUE);
            students.add(Student.GREEN);
            students.add(Student.PINK);
            students.add(Student.RED);
            students.add(Student.YELLOW);
        }
    }

    /**
     * @return the drawn student
     */
    public Student drawStudent() {
        int index = (int)Math.floor(Math.random() * students.size());
        if(students.size()==0)
            return null;
        Student s = students.get(index);
        students.remove(index);
        return s;
    }

    /**
     * @return true if the bag is empty, otherwise false
     */
    public boolean isEmpty(){
        return students.size() == 0;
    }

    /**
     *
     * @param student represents the student to be added to the bag
     */
    public void addToBag(Student student){
        students.add(student);
    }
}
