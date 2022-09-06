package it.polimi.ingsw.server.model.characters.cards;

import it.polimi.ingsw.server.model.characters.parameters.IntegerParameter;
import it.polimi.ingsw.server.model.characters.parameters.Parameter;

import java.util.ArrayList;

/**
 * This class implements the third Character: the Herald
 */
public class Herald extends Character{

    private Integer indexArchipelago;

    public Herald() {
        super(3, 3);
    }

    /**
     *
     * @param parameters contains all the parameters that a specific character needs
     */
    @Override
    public void task(ArrayList<Parameter> parameters) {
        indexArchipelago = ((IntegerParameter) parameters.get(0)).getValue();
        super.setCoin();
    }

    /**
     * @return indexArchipelago chosen by the player where he wants to calculate influence
     */
    public Integer getArchipelago() {
        return indexArchipelago;
    }
}
