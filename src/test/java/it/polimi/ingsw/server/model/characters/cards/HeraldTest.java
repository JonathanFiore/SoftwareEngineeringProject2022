package it.polimi.ingsw.server.model.characters.cards;

import it.polimi.ingsw.server.model.characters.parameters.IntegerParameter;
import it.polimi.ingsw.server.model.characters.parameters.Parameter;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class HeraldTest {

    @Test
    void getArchipelago() {
        Herald h = new Herald();
        // checks if the Herald's id and cost are correct
        assertEquals(3, h.getId());
        assertEquals(3, h.getCost());

        ArrayList<Parameter> p = new ArrayList<>();
        Parameter ip = new IntegerParameter("ip", 4);
        p.add(ip);
        h.task(p);
        assertEquals(4,h.getCost()); // checks if the cost of the character has increased after using its effect
        assertEquals(4, h.getArchipelago().intValue()); // checks if the value of the attribute 'indexArchipelago' is equal to the int value passed to the IntegerParameter constructor
    }
}