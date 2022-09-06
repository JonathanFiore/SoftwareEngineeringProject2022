package it.polimi.ingsw.server.model.characters.cards;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.characters.parameters.Parameter;
import it.polimi.ingsw.server.model.characters.parameters.PlayerParameter;

import java.util.ArrayList;

/**
 * This class implements the eighth Character: the Knight
 */
public class Knight extends Character{

    private final int increment;
    private Player currentPlayer;

    public Knight() {
        super(8, 2);
        increment = 2;
    }

    /**
     * assigns parameters' values to the class' attributes
     * @param p its only element is a player type parameter
     */
    @Override
    public void task(ArrayList<Parameter> p) {
        currentPlayer = ((PlayerParameter) p.get(0)).getPlayer();
        super.setCoin();
    }

    /**
     * it verifies that the current player is also the one who used the card: if so, we will add 2 extra points to his
     * influence (if it's not, we will add 0)
     * @param player it's the player of which we want to calculate the influence
     * @return it returns 0 or increment=2, which are the values that we will add to the influence of the players
     */
    public int actualTask(Player player){
        if(player.equals(currentPlayer)){
            return increment;
        }else{
            return 0;
        }
    }

}
