package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.serverexceptions.EndGameException;

import java.io.Serializable;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Objects;

/**
 * Each Player has a board
 */
public class Board implements Serializable {

    private int numberTowers;
    private final Tower towerColor;
    private final Student[] entrance;
    private EnumMap<Student,Integer> hallStudents;
    private EnumMap<Student,Boolean> professorTable;

    private EnumMap<Student,Integer> takenCoins;

    public Board(int numberTowers, Tower towerColor)
    {
        this.towerColor = towerColor;
        this.numberTowers = numberTowers;

        entrance = new Student[9];
        setUp();
    }

    /**
     *
     * @return the towers' color of this board
     */
    public Tower getTowerColor()
    {
        return towerColor;
    }


    /**
     * Add a tower on the board
     */
    public void addTower()
    {
        numberTowers++;
    }


    /**
     * Decrease number of towers
     * @throws EndGameException to say that this player wins the game when their last tower is placed on an island
     */
    public void removeTower() throws EndGameException
    {
        numberTowers--;

        if(numberTowers==0)
            throw new EndGameException("tower");
    }

    /**
     *
     * @param student is the student to remove
     * @return the enum student you have to add to bag, "null" if there are no more students of that color
     * in player's board
     */
    public Student removeFromHall(Student student)
    {
        if(hallStudents.get(student)>0)
        {
            Student s = Student.valueOf(student.name());
            hallStudents.put(student,hallStudents.get(student)-1);
            return s;
        }

        return null;
    }

    /**
     *
     * @param position specifies which student the player wants to move in the board's hall
     */
    public void moveStudentToHall(int position)
    {
        Student s = entrance[position];
        entrance[position]=null;
        hallStudents.put(s,hallStudents.get(s)+1);
    }


    /**
     I have to call this method in this way: moveStudentToIsland(island, Student.COLOR)
     * @param island: where I want to add a student
     * @param position specifies which student in the entrance has to be moved
     */
    public void moveStudentToIsland(Island island, int position)
    {
        island.addStudent(entrance[position]);
        entrance[position]=null;
    }


    /**
     *Add a student to the board's entrance
     * @param student is the student I want to add to the board's entrance
     */
    public void moveToEntrance(Student student)
    {
        boolean foundNull=false;
        for(int i=0; i<entrance.length && !foundNull; i++)
        {
            if(entrance[i]==null)
            {
                entrance[i]=student;
                foundNull = true;
            }
        }
    }

    /**
     *
     * @param student is used to specify the color of the students whose number I want to know
     * @return the number of students of a specific color
     */
    public int getNumberOfStudents(Student student)
    {
        return hallStudents.get(student);
    }

    /**
     *
     * @param professorColor is used to specify the color of the teacher I want to check
     * @return true if that specific professor is present
     */
    public boolean isProfessorPresent(Student professorColor)
    {
        return professorTable.get(professorColor);
    }

    /**
     *It changes the status of the Professor:
     * if it's present ==> we remove it
     * if it's not present ==> we put it
     * @param professorColor is used to specify which professor I have to update
     */
    public void updatePresenceOfProfessor(Student professorColor)
    {
        professorTable.put(professorColor,!professorTable.get(professorColor));
    }


    /**
     *setUp "hallStudents" and "professorTable"
     */
    private void setUp()
    {
        hallStudents = new EnumMap<>(Student.class);
        hallStudents.put(Student.RED,0);
        hallStudents.put(Student.GREEN,0);
        hallStudents.put(Student.BLUE,0);
        hallStudents.put(Student.PINK,0);
        hallStudents.put(Student.YELLOW,0);

        professorTable = new EnumMap<>(Student.class);
        professorTable.put(Student.RED,false);
        professorTable.put(Student.GREEN,false);
        professorTable.put(Student.BLUE,false);
        professorTable.put(Student.PINK,false);
        professorTable.put(Student.YELLOW,false);

        takenCoins = new EnumMap<>(Student.class);
        takenCoins.put(Student.RED,0);
        takenCoins.put(Student.GREEN,0);
        takenCoins.put(Student.BLUE,0);
        takenCoins.put(Student.PINK,0);
        takenCoins.put(Student.YELLOW,0);
    }

    /**
     * @return the whole hall of the board
     */
    public EnumMap<Student, Integer> getHallStudents() {
        return hallStudents;
    }

    /**
     * @return the whole entrance
     */
    public Student[] getEntrance() {
        return entrance;
    }

    /**
     * @return the number of coins already taken from the hall for a certain color
     */
    public EnumMap<Student, Integer> getTakenCoins() {
        return takenCoins;
    }

    /**
     * This method is used to update the coins taken from the hall
     * @param student is used to get the color of the student's table in the dining room where we need to update the coins
     * @param num represents the number of coins that has to be updated
     */
    public void updateTakenCoins(Student student, int num){
        takenCoins.put(student, takenCoins.get(student) + num);
    }

    /**
     * @return the number of towers still on the player's board
     */
    public int getNumberTowers() {
        return numberTowers;
    }

    /**
     * This method is used to get the number of professors which the player has
     * @return the number of professor in this board
     */
    public int numberProfessorOwned()
    {
        int number=0;
        if(professorTable.get(Student.RED))
            number++;
        if(professorTable.get(Student.GREEN))
            number++;
        if(professorTable.get(Student.BLUE))
            number++;
        if(professorTable.get(Student.YELLOW))
            number++;
        if(professorTable.get(Student.PINK))
            number++;

        return number;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board = (Board) o;
        return numberTowers == board.numberTowers && towerColor == board.towerColor && Arrays.equals(entrance, board.entrance) && Objects.equals(hallStudents, board.hallStudents) && Objects.equals(professorTable, board.professorTable) && Objects.equals(takenCoins, board.takenCoins);
    }
}
