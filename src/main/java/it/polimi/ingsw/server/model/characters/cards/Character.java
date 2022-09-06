package it.polimi.ingsw.server.model.characters.cards;

import it.polimi.ingsw.server.model.characters.parameters.Parameter;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class represents a generic character
 * Each character has an 'id' and a 'cost'
 */
public abstract class Character implements Serializable {

    private final int id;
    private final int cost;

    //It's used to represent the presence of the coin on this card
    private boolean coin;

    public Character(int id, int cost) {
        this.id = id;
        this.cost = cost;
        this.coin = false;
    }


    /**
     * @return cost: if a coin is present the real cost is: cost + 1
     */
    public int getCost() {
        if(coin)
            return cost+1;
        else
            return cost;
    }

    /**
     * set presence of coin
     */
    public void setCoin() {
        this.coin = true;
    }

    /**
     * @return true if there is a coin on the character card
     */
    public boolean isCoin() {
        return coin;
    }

    /**
     * @param parameters contains all the parameters that a specific character needs
     */
    public abstract void task(ArrayList<Parameter> parameters);


    /**
     *
     * @return the ID of the card
     */
    public int getId() {
        return id;
    }
}
