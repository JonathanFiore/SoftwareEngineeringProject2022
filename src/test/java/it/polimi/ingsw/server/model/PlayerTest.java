package it.polimi.ingsw.server.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void getNickname() {
        Board b = new Board(8, Tower.BLACK);
        Player p = new Player("player nickname", false, b, Wizard.WIZARD1);
        assertEquals("player nickname", p.getNickname()); // checks if the String returned by getNickname() is the same as the one assigned in the constructor
    }

    @Test
    void getCoins() {
        Board b1 = new Board(8, Tower.BLACK);
        Player p1 = new Player("p1", false, b1, Wizard.WIZARD1);
        assertEquals(0, p1.getCoins()); // checks if the number of coin of the player is 0, according to the choice of the expert mode
        Board b2 = new Board(8, Tower.BLACK);
        Player p2 = new Player("p2", true, b2, Wizard.WIZARD1);
        assertEquals(1, p2.getCoins()); // checks if the number of coin of the player is 1, according to the choice of the expert mode
    }

    @Test
    void addCoin() {
        Board b1 = new Board(8, Tower.BLACK);
        Player p1 = new Player("p1", false, b1, Wizard.WIZARD1);
        for(int i=1; i<10; i++) {
            p1.addCoin();
            assertEquals(i, p1.getCoins()); // checks if the amount of coins increases after each call of the addCoin method
        }
    }

    @Test
    void removeCoin() {
        Board b1 = new Board(8, Tower.BLACK);
        Player p1 = new Player("p1", false, b1, Wizard.WIZARD1);
        for(int i=0; i<10; i++) // 10 coins are given to the player
            p1.addCoin();
        for(int i=1; i<10; i++){
            p1.removeCoin();
            assertEquals(10-i, p1.getCoins()); // checks if the amount of coins decreases after each call of the removeCoin method
        }
    }

    @Test
    void getBoard() {
        Board b1 = new Board(8, Tower.BLACK);
        Player p1 = new Player("p1", false, b1, Wizard.WIZARD1);
        assertEquals(b1, p1.getBoard()); // checks if the player's board is the same of the one assigned in the constructor
    }

    @Test
    void getDeck() {
        ArrayList<Assistant> a = new ArrayList<>(); // 10 Assistants are added to the ArrayList 'a' the same way as they are added in the Deck's constructor
        a.add(new Assistant(1,1));
        a.add(new Assistant(2,1));
        a.add(new Assistant(3,2));
        a.add(new Assistant(4,2));
        a.add(new Assistant(5,3));
        a.add(new Assistant(6,3));
        a.add(new Assistant(7,4));
        a.add(new Assistant(8,4));
        a.add(new Assistant(9,5));
        a.add(new Assistant(10,5));
        Board b1 = new Board(8, Tower.BLACK);
        Player p1 = new Player("p1", false, b1, Wizard.WIZARD1);
        for(int i=0; i<a.size(); i++) {
            assertEquals(a.get(i).getCardValue(), p1.getDeck().getCards().get(i).getCardValue()); // compares the card's id of the Assistants in 'a' with the ones in the Player's Deck to check if the Deck assigned to the Player has the Assistant cards in the expected order
            assertEquals(a.get(i).getMotherMovements(), p1.getDeck().getCards().get(i).getMotherMovements()); // compares the card's Mother Nature movements of the Assistants in 'a' with the ones in the Player's Deck to check if the Deck assigned to the Player has the Assistant cards in the expected order
        }
    }

    @Test
    void getWizard() {
        Board b1 = new Board(8, Tower.BLACK);
        Player p1 = new Player("p1", false, b1, Wizard.WIZARD3);
        assertEquals(Wizard.WIZARD3, p1.getWizard()); // checks if the player's Wizard is the same of the one assigned in the constructor
    }

    @Test
    void setCoins() {
        Board b1 = new Board(8, Tower.BLACK);
        Player p1 = new Player("p1", false, b1, Wizard.WIZARD1);
        assertEquals(0, p1.getCoins()); // checks if the number of coin of the player is 0, according to the choice of the expert mode
        p1.setCoins(1);
        assertEquals(1, p1.getCoins()); // check if the number of coin of the player is 1, according to the setCoin's parameter
        p1.setCoins(p1.getCoins() + 2);
        assertEquals(3, p1.getCoins()); // check if the number of coin of the player is 3, according to the setCoin's parameter
    }
}