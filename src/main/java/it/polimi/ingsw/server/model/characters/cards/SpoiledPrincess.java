package it.polimi.ingsw.server.model.characters.cards;

import it.polimi.ingsw.server.model.Bag;
import it.polimi.ingsw.server.model.Board;
import it.polimi.ingsw.server.model.Student;
import it.polimi.ingsw.server.model.characters.parameters.IntegerParameter;
import it.polimi.ingsw.server.model.characters.parameters.Parameter;
import it.polimi.ingsw.server.model.characters.parameters.PlayerParameter;

import java.util.ArrayList;

/**
 * This class implements the eleventh Character: the SpoiledPrincess
 */
public class SpoiledPrincess extends Character{

    private ArrayList<Student> students;
    private final Bag bag;
    private Board board;
    private int chosenStudent;

    public SpoiledPrincess(Bag bag){
        super(11, 2);
        this.bag = bag;
        students = new ArrayList<>();
        for(int i=0; i<4; i++)
            students.add(this.bag.drawStudent());
    }

    /**
     * Sets the value to the attributes and calls the method that executes the character's effect
     * @param par contains in order a PlayerParameter and an IntegerParameter
     */
    @Override
    public void task(ArrayList<Parameter> par){
        board = ((PlayerParameter)par.get(0)).getPlayer().getBoard();
        chosenStudent = ((IntegerParameter)par.get(1)).getValue();
        super.setCoin();
        actualTask();
    }

    /**
     * The student chosen by the player is removed from the card and placed in the hall; then a new student
     * is drawn and placed on this card
     */
    private void actualTask(){
        Student s = students.get(chosenStudent);
        board.getHallStudents().put(s,board.getHallStudents().get(s)+1);
        students.remove(chosenStudent);
        Student studentBag = bag.drawStudent();
        if(studentBag!=null)
            students.add(bag.drawStudent());
    }

    /**
     * @return the students on the card
     */
    public ArrayList<Student> getStudents() {
        return students;
    }
}