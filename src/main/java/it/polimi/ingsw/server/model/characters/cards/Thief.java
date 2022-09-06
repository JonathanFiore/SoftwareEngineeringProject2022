package it.polimi.ingsw.server.model.characters.cards;

import it.polimi.ingsw.server.model.Bag;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.Student;
import it.polimi.ingsw.server.model.characters.parameters.Parameter;
import it.polimi.ingsw.server.model.characters.parameters.StudentParameter;

import java.util.ArrayList;

/**
 * This class implements the twelfth Character: the Thief
 */
public class Thief extends Character{

    private ArrayList<Player> players;
    private final Bag bag;
    private Student color;

    public Thief(ArrayList<Player> p, Bag bag){
        super(12, 3);
        players = new ArrayList<>();
        players.addAll(p);
        this.bag = bag;
    }

    /**
     * Sets the value to the attributes and calls the method that executes the character's effect
     * @param par contains just one element, whose type is StudentParameter
     */
    @Override
    public void task(ArrayList<Parameter> par){
        color = ((StudentParameter)par.get(0)).getStudent();
        super.setCoin();
        actualTask();
    }

    /**
     * For each player, up to three students (whose color corresponds to the chosen one) are taken from the
     * player's hall and put back in the bag. If the player has less than three students of the chose color,
     * all the students of that color are taken from their hall
     */
    private void actualTask(){
        for(Player p : players){
            int n = 3;
            if(p.getBoard().getNumberOfStudents(color)<n)
                n = p.getBoard().getNumberOfStudents(color);
            for(int i=0; i<n; i++)
                bag.addToBag(p.getBoard().removeFromHall(color));
        }
    }
}