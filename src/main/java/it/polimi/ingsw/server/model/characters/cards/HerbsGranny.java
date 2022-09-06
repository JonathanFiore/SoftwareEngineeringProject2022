package it.polimi.ingsw.server.model.characters.cards;

import it.polimi.ingsw.server.model.Archipelago;
import it.polimi.ingsw.server.model.characters.parameters.ArchipelagoParameter;
import it.polimi.ingsw.server.model.characters.parameters.Parameter;

import java.util.ArrayList;

/**
 * This class implements the fifth Character: the HerbsGranny
 */
public class HerbsGranny extends Character {

    private int noEntry;
    private Archipelago archipelago;

    public HerbsGranny() {
        super(5, 2);
        noEntry = 4;
    }

    /**
     * assigns parameters' values to the class' attributes
     *
     * @param p its only element is an archipelago type parameter
     */
    @Override
    public void task(ArrayList<Parameter> p) {
        archipelago = ((ArchipelagoParameter) p.get(0)).getArchipelago();
        super.setCoin();
        actualTask();
    }

    /**
     * updates the no entry on the selected archipelago (or island) and decreases the number of no entries on the card
     */
    private void actualTask() {
        if (noEntry > 0) {
            archipelago.addNoEntry(1);
            noEntry--;
        }
    }

    /**
     * increases the number of no entries on the card after the effect took place on the archipelago (so that no entry
     * can be reused in other turns)
     */
    public void addNoEntry() {
        noEntry++;
    }

    /**
     * @return the number of noEntry cards
     */
    public int getNoEntry() {
        return noEntry;
    }

}
