package it.polimi.ingsw.server.model.characters.cards;

import it.polimi.ingsw.server.model.Bag;
import it.polimi.ingsw.server.model.Board;
import it.polimi.ingsw.server.model.Student;
import it.polimi.ingsw.server.model.characters.parameters.IntegerParameter;
import it.polimi.ingsw.server.model.characters.parameters.Parameter;
import it.polimi.ingsw.server.model.characters.parameters.PlayerParameter;


import java.util.ArrayList;

/**
 * This class implements the seventh Character: the Jester
 */
public class Jester extends Character {
    private ArrayList<Student> students;
    private Board board;
    private ArrayList<Integer> chosenCardStudent;
    private ArrayList<Integer> chosenBoardStudents;

    public Jester(Bag bag) {
        super(7, 1);
        students = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            students.add(bag.drawStudent());
        }
        chosenBoardStudents = new ArrayList<>();
        chosenCardStudent = new ArrayList<>();
    }

    /**
     * assigns parameters' values to the class' attributes
     *
     * @param p, the first element of the ArrayList is a player (who activates the card),
     *           the second is an integer (number of students that I want to swap),
     *           then the others are the students that the player chooses to swap from the card to the entrance and vice-versa
     */
    @Override
    public void task(ArrayList<Parameter> p) {
        board = ((PlayerParameter) p.get(0)).getPlayer().getBoard();
        int num = ((IntegerParameter) p.get(1)).getValue();
        super.setCoin();
        for (int i = 0; i < num; i++) {
            chosenCardStudent.add(((IntegerParameter) p.get(2 + i)).getValue());
        }
        for (int i = 0; i < num; i++) {
            chosenBoardStudents.add(((IntegerParameter) p.get(2 + num + i)).getValue());
        }
        actualTask(num);
    }


    /**
     * removes the selected students from the entrance and swap them with the selected students on the card (max 3)
     *
     * @param num number of students that I want to change
     */
    private void actualTask(int num) {
        for (int i = 0; i < num; i++) {
            students.add(board.getEntrance()[chosenBoardStudents.get(i)]);
            board.getEntrance()[chosenBoardStudents.get(i)] = null;
        }

        for (int i = 0; i < num; i++) {
            board.moveToEntrance(students.get(chosenCardStudent.get(i)));
        }

        for (int i = 0; i < num; i++) {
            students.set(chosenCardStudent.get(i), null);
        }

        for (int i = 0; i < students.size(); i++) {
            if(students.get(i) == null) {
                students.remove(i);
                i--;
            }
        }

        chosenCardStudent.clear();
        chosenBoardStudents.clear();
    }

    /**
     * @return the students on the card
     */
    public ArrayList<Student> getStudents() {
        return students;
    }
}