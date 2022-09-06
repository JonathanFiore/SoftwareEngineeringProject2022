package it.polimi.ingsw.server.model.characters.cards;

import it.polimi.ingsw.server.model.Archipelago;
import it.polimi.ingsw.server.model.Island;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.Student;
import it.polimi.ingsw.server.model.characters.parameters.Parameter;
import it.polimi.ingsw.server.model.characters.parameters.StudentParameter;

import java.util.ArrayList;

/**
 * This class implements the ninth Character: the MushroomHunter
 */
public class MushroomHunter extends Character{

    private Student chosenColor;

    public MushroomHunter(){
        super(9, 3);
    }

    /**
     * This method assigns a Student to the attribute "chosenColor"
     * @param par contains just one element, whose type is StudentParameter
     */
    @Override
    public void task(ArrayList<Parameter> par){
        chosenColor = ((StudentParameter) par.get(0)).getStudent();
        super.setCoin();
    }

    /**
     * Calculates the total influence value on an archipelago when this card is active
     * @param p is the player for whom calculate the influence
     * @param a is the archipelago on which calculate the influence
     * @return the int value of the influence
     */
    public int actualTask(Player p, Archipelago a){
        int tot = 0;
        for(Island i : a.getIslands())
            tot += influence(p,i);
        if(a.getTower() == p.getBoard().getTowerColor())
            tot += a.getIslands().size();
        return tot;
    }

    /**
     * Calculates the influence value on an island when this card is active
     * @param p is the player for whom calculate the influence
     * @param i is the island on which calculate the influence
     * @return the int value of the influence
     */
    private int influence(Player p, Island i){
        int sum=0;
        for(Student s : i.getStudents()){
            if(!(s==chosenColor) && p.getBoard().isProfessorPresent(s))
                sum++;
        }
        return sum;
    }
}