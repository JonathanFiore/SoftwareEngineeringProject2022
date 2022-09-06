package it.polimi.ingsw.server.model.characters.cards;

import it.polimi.ingsw.server.model.characters.parameters.IntegerParameter;
import it.polimi.ingsw.server.model.characters.parameters.Parameter;

import java.util.ArrayList;

/**
 *This class implements the fourth Character: the MagicPostman
 */
public class MagicPostman extends Character{

    private Integer additionalSteps;

    public MagicPostman() {
        super(4, 1);
    }

    /**
     *We just set the parameter we need to perform the effect of this card
     * @param parameters contains all the parameters that a specific character needs
     */
    @Override
    public void task(ArrayList<Parameter> parameters) {
        additionalSteps = ((IntegerParameter) parameters.get(0)).getValue();
        super.setCoin();
    }

    /**
     *
     * @return additionalSteps decided by the player
     */
    public Integer getAdditionalSteps() {
        return additionalSteps;
    }
}
