package it.polimi.ingsw.server.model;

import java.io.Serializable;

/**
 * An Assistant is a card, All the Players have a deck, each deck is a group of 10 cards Assistant
 * @author Fiore Jonathan
 */
public class Assistant implements Serializable {

    private final int cardValue;
    private final int motherMovements;


    public Assistant(int cardValue, int motherMovements) {
        this.cardValue = cardValue;
        this.motherMovements = motherMovements;
    }

    /**
     *
     * @return the card's value
     */
    public int getCardValue() {
        return cardValue;
    }

    /**
     *
     * @return motherNature movements
     */
    public int getMotherMovements() {
        return motherMovements;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Assistant assistant = (Assistant) o;
        return cardValue == assistant.cardValue && motherMovements == assistant.motherMovements;
    }
}
