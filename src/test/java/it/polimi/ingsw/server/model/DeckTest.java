package it.polimi.ingsw.server.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    @Test
    void setUpDeck() {
        Deck d = new Deck();
        ArrayList<Assistant> c = new ArrayList<>(); // to the ArrayList 'c' are added the same Assistant cards in the same order as they are added in the setUpDeck private method
        c.add(new Assistant(1,1));
        c.add(new Assistant(2,1));
        c.add(new Assistant(3,2));
        c.add(new Assistant(4,2));
        c.add(new Assistant(5,3));
        c.add(new Assistant(6,3));
        c.add(new Assistant(7,4));
        c.add(new Assistant(8,4));
        c.add(new Assistant(9,5));
        c.add(new Assistant(10,5));
        for(int i=0; i<c.size(); i++) { // checks if the Assistant cards (and their order) are correctly assigned to the Deck
            assertEquals(c.get(i).getCardValue(), d.getCards().get(i).getCardValue());
            assertEquals(c.get(i).getMotherMovements(), d.getCards().get(i).getMotherMovements());
        }
    }

    @Test
    void getCards() {
        Deck d = new Deck();
        d.playACard(0);
        Assistant a = new Assistant(1,1); // creates a new Assistant that is equal to the first Assistant of the Deck
        // checks if the played card is equal to the Assistant that is expected to be the first of the Deck
        assertEquals(a.getCardValue(), d.getCardPlayed().getCardValue());
        assertEquals(a.getMotherMovements(), d.getCardPlayed().getMotherMovements());

        d.playACard(6);
        Assistant b = new Assistant(8,4); // creates a new Assistant that is equal to the eighth Assistant of a newly instantiated Deck
        // checks if the played card is equal to the Assistant that is expected to be the seventh of the Deck (previously it was the eighth but after calling playACard() it's become the seventh)
        assertEquals(b.getCardValue(), d.getCardPlayed().getCardValue());
        assertEquals(b.getMotherMovements(), d.getCardPlayed().getMotherMovements());
    }

    @Test
    void playACard() {
        Deck d = new Deck();
        for(int i=0; i<10; i++) { // plays each card of the Deck
            Assistant a = d.getCards().get(0);
            d.playACard(0);
            assertEquals(a, d.getCardPlayed()); // checks if the playACard method stores in the 'cardPlayed' attribute the Assistant whose index is equal to the parameter of the method
            assertEquals(0, d.getCards().stream().filter(x -> x.equals(a)).count()); // checks if the played card is removed from the ArrayList 'cards'
        }
    }

    @Test
    void remainingCards() {
        Deck d = new Deck();
        assertEquals(10, d.remainingCards()); // checks if the deck is full
        for(int i=0; i<3; i++) // the first three Assistant cards of the deck are played
            d.playACard(0);
        assertEquals(7, d.remainingCards()); // checks if the amount of cards in the deck has decreased by times the method playACard is called
        for(int i=0; i<7; i++) // the remaining seven Assistant cards of the deck are played
            d.playACard(0);
        assertEquals(0, d.remainingCards()); // checks if the deck is empty after playing all the remaining cards
    }

    @Test
    void equals() {
        Deck d1 = new Deck();
        assertEquals(d1, d1); // checks if the method works fine when comparing the same object

        Board b = new Board(6, Tower.WHITE);
        assertFalse(d1.equals(b)); // checks if the method works fine when comparing objects of different classes

        ArrayList<Assistant> c = new ArrayList<>(); // 'c' is built the same way as the attribute 'cards' in Deck
        c.add(new Assistant(1,1));
        c.add(new Assistant(2,1));
        c.add(new Assistant(3,2));
        c.add(new Assistant(4,2));
        c.add(new Assistant(5,3));
        c.add(new Assistant(6,3));
        c.add(new Assistant(7,4));
        c.add(new Assistant(8,4));
        c.add(new Assistant(9,5));
        c.add(new Assistant(10,5));
        assertTrue(d1.getCards().equals(c)); // checks if the method works fine when comparing different ArrayLists containing the same Assistant cards

        Deck d2 = new Deck();
        assertTrue(d1.equals(d2)); // checks if the method works fine when comparing two instances of Deck

        d1.playACard(3);
        d2.playACard(3);
        assertTrue(d1.equals(d2)); // checks if the method works fine when comparing two instances of Deck from which has been removed the same card

        d1.playACard(5);
        d2.playACard(7);
        assertFalse(d1.equals(d2)); // checks if the method works fine when comparing two instances of Deck from which have been removed different cards

        ArrayList<Assistant> c2 = new ArrayList<>(); // 'c' is built the same way as the attribute 'cards' in Deck but only for it's first eight cards
        c.add(new Assistant(1,1));
        c.add(new Assistant(2,1));
        c.add(new Assistant(3,2));
        c.add(new Assistant(4,2));
        c.add(new Assistant(5,3));
        c.add(new Assistant(6,3));
        c.add(new Assistant(7,4));
        c.add(new Assistant(8,4));
        assertFalse(d1.getCards().equals(c2)); // checks if the method works fine when comparing different ArrayLists containing different Assistant cards

        ArrayList<Assistant> c3 = new ArrayList<>();
        c.add(new Assistant(7,4));
        c.add(new Assistant(8,4));
        c.add(new Assistant(9,5));
        c.add(new Assistant(10,5));
        c.add(new Assistant(1,1));
        c.add(new Assistant(2,1));
        c.add(new Assistant(3,2));
        c.add(new Assistant(4,2));
        c.add(new Assistant(5,3));
        c.add(new Assistant(6,3));
        assertFalse(d1.getCards().equals(c3)); // checks if the method works fine when comparing different ArrayLists containing the same Assistant cards but in a different order
    }
}