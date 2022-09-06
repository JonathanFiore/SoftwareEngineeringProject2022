package it.polimi.ingsw.server.model.characters.cards;

import it.polimi.ingsw.server.model.Archipelago;
import it.polimi.ingsw.server.model.Island;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.characters.parameters.Parameter;

import java.util.ArrayList;

/**
 * This class implements the sixth Character: the Centaur
 */
public class Centaur extends Character{

    public Centaur() {
        super(6, 3);
    }

    /**
     * @param p is actually useless
     */
    @Override
    public void task(ArrayList<Parameter> p){
        super.setCoin();
    }

    /**
     * calculate influence without counting towers
     * @param p player for whom I have to count the influence
     * @param a archipelago where I have to count the influence
     * @return the value of the influence (without towers)
     */
    public int actualTask(Player p, Archipelago a) {
        int tot = 0;
        for (Island i : a.getIslands())
            tot += i.playerInfluence(p);
        return tot;
    }

}

