package it.polimi.ingsw.server.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AssistantTest {

    @Test
    void getCardValue() {
        Assistant a1 = new Assistant(1,4);
        Assistant a2 = new Assistant(2,5);
        Assistant a3 = new Assistant(3,6);
        assertEquals(1, a1.getCardValue()); // checks if the card value is equal to the one specified in the constructor
        assertEquals(2, a2.getCardValue()); // checks if the card value is equal to the one specified in the constructor
        assertEquals(3, a3.getCardValue()); // checks if the card value is equal to the one specified in the constructor
    }

    @Test
    void getMotherMovements() {
        Assistant a1 = new Assistant(1,4);
        Assistant a2 = new Assistant(2,5);
        Assistant a3 = new Assistant(3,6);
        assertEquals(4, a1.getMotherMovements()); // checks if the Mother Nature movements value is equal to the one specified in the constructor
        assertEquals(5, a2.getMotherMovements()); // checks if the Mother Nature movements value is equal to the one specified in the constructor
        assertEquals(6, a3.getMotherMovements()); // checks if the Mother Nature movements value is equal to the one specified in the constructor
    }
}