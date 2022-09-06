package it.polimi.ingsw.server.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * This class contains information about the player
 */
public class Player implements Serializable {

    private final String nickname;
    private int coins;
    private final Board board;
    private final Deck deck;
    private final Wizard wizard;

    public Player(String nickname, boolean expertMode, Board board, Wizard wizard) {

        this.nickname = nickname;
        this.board = board;
        deck = new Deck();
        this.wizard = wizard;

        if(expertMode)
            coins = 1;
        else
            coins = 0;
    }

    /**
     * @return player's id
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @return the player's amount of coins
     */
    public int getCoins() {
        return coins;
    }

    /**
     * increases the amount of coins by 1
     */
    public void addCoin(){
        coins++;
    }

    /**
     * reduces the amount of coins by 1
     */
    public void removeCoin(){
        coins--;
    }

    /**
     * @return the player's board
     */
    public Board getBoard(){
        return board;
    }

    /**
     * @return the player's deck
     */
    public Deck getDeck() {
        return deck;
    }

    /**
     * @return the player's wizard
     */
    public Wizard getWizard() {
        return wizard;
    }

    /**
     * @param coins specifies number of coins
     */
    public void setCoins(int coins) {
        this.coins = coins;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return coins == player.coins && nickname.equals(player.nickname) && Objects.equals(board, player.board) && Objects.equals(deck, player.deck) && wizard == player.wizard;
    }

}
