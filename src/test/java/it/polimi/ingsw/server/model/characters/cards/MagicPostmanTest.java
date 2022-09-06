package it.polimi.ingsw.server.model.characters.cards;

import it.polimi.ingsw.server.model.characters.parameters.IntegerParameter;
import it.polimi.ingsw.server.model.characters.parameters.Parameter;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MagicPostmanTest {

    @Test
    void getAdditionalSteps() {
        MagicPostman mp = new MagicPostman();
        // checks if the Centaur's id and cost are correct
        assertEquals(4, mp.getId());
        assertEquals(1, mp.getCost());

        ArrayList<Parameter> p = new ArrayList<>();
        Parameter ip = new IntegerParameter("ip", 1);
        p.add(ip);
        mp.task(p);
        assertEquals(2, mp.getCost()); // checks if the cost of the character has increased after using its effect
        assertEquals(1, mp.getAdditionalSteps()); // checks if the amount of the additional Mother Nature steps given by the Character card is equal to the integer passed to the IntegerParameter constructor
    }
}