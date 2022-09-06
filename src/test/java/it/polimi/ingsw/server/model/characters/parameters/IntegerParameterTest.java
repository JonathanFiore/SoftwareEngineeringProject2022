package it.polimi.ingsw.server.model.characters.parameters;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntegerParameterTest {

    @Test
    void getValue() {
        Parameter i = new IntegerParameter("integer", 11);
        assertInstanceOf(Integer.class, ((IntegerParameter)i).getValue()); // checks if the method correctly returns an Integer
        assertEquals(11, ((IntegerParameter)i).getValue().intValue());  // checks if the method returns the same int as the one passed to the constructor
        assertEquals("integer", i.getName()); // checks if the method returns the same String as the one passed to the constructor
    }
}