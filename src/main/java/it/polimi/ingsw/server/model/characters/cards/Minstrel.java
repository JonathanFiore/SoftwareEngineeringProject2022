package it.polimi.ingsw.server.model.characters.cards;

import it.polimi.ingsw.server.model.Board;
import it.polimi.ingsw.server.model.Student;
import it.polimi.ingsw.server.model.characters.parameters.IntegerParameter;
import it.polimi.ingsw.server.model.characters.parameters.Parameter;
import it.polimi.ingsw.server.model.characters.parameters.PlayerParameter;
import it.polimi.ingsw.server.model.characters.parameters.StudentParameter;

import java.util.ArrayList;

/**
 * This class implements the tenth Character: the Minstrel
 */
public class Minstrel extends Character{

    private Board board;
    private ArrayList<Integer> entranceStudents;
    private ArrayList<Student> hallStudents;

    public Minstrel(){
        super(10, 1);
        entranceStudents = new ArrayList<>();
        hallStudents = new ArrayList<>();
    }

    /**
     * Sets the value to the attributes and calls the method that executes the character's effect
     * @param par contains in order a PlayerParameter, an IntegerParameter and one or two
     * Integer/Student Parameter
     */
    @Override
    public void task(ArrayList<Parameter> par){
        board = ((PlayerParameter)par.get(0)).getPlayer().getBoard();
        int num = ((IntegerParameter)par.get(1)).getValue();
        super.setCoin();
        for (int i=0; i<num;i++)
            entranceStudents.add(((IntegerParameter)par.get(2+i)).getValue());
        for(int i=0; i<num;i++)
            hallStudents.add(((StudentParameter)par.get(2+num+i)).getStudent());
        actualTask(num);
    }

    /**
     * Swaps one or two students in the entrance for the ones in the hall and vice versa
     * @param num equals to the number of students to swap
     */
    private void actualTask(int num){
        for(int i=0; i<num; i++) {
            board.moveStudentToHall(entranceStudents.get(i));
            board.moveToEntrance(board.removeFromHall(hallStudents.get(i)));
        }
        entranceStudents.clear();
        hallStudents.clear();
    }
}