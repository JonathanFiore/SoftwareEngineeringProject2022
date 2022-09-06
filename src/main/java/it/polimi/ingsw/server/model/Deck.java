package it.polimi.ingsw.server.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Each Player has a deck
 */
public class Deck implements Serializable {

    private final ArrayList<Assistant> cards;
    private Assistant cardPlayed;

    public Deck() {
        cards = new ArrayList<>();
        setUpDeck();
    }

    /**
     * @return all the deck's cards
     */
    public ArrayList<Assistant> getCards() {
        return cards;
    }


    /**
     * @return the card played by the player
     */
    public Assistant getCardPlayed() {
        return cardPlayed;
    }


    /**
     * @return the number of cards left
     */
    public int remainingCards() {
        return cards.size();
    }


    /**
     * @param Card index of card chosen
     */
    public void playACard(int Card) {
        cardPlayed = cards.get(Card);
        cards.remove(Card);
    }


    /**
     * It's used to fill the deck
     */
    private void setUpDeck() {
        cards.add(new Assistant(1, 1));
        cards.add(new Assistant(2, 1));
        cards.add(new Assistant(3, 2));
        cards.add(new Assistant(4, 2));
        cards.add(new Assistant(5, 3));
        cards.add(new Assistant(6, 3));
        cards.add(new Assistant(7, 4));
        cards.add(new Assistant(8, 4));
        cards.add(new Assistant(9, 5));
        cards.add(new Assistant(10, 5));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Deck deck = (Deck) o;
        return Objects.equals(cards, deck.cards) && Objects.equals(cardPlayed, deck.cardPlayed);
    }
}
