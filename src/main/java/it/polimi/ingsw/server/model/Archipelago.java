package it.polimi.ingsw.server.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * An archipelago is a group of islands. During the game, when two or more islands are joined together, they become
 * part of the same archipelago
 */
public class Archipelago implements Serializable {

    private final ArrayList<Island> islands;
    private Tower tower;
    private boolean motherNature;
    private int noEntry;

    public Archipelago(boolean motherNature) {
        Island island = new Island();
        islands = new ArrayList<>();
        islands.add(island);
        this.tower = null;
        this.motherNature = motherNature;
        noEntry = 0;
    }

    /**
     * @return the tower present on the island
     */
    public Tower getTower() {
        return tower;
    }

    /**
     * @param tower overwrites the value of the archipelago's tower attribute
     */
    public void setTower(Tower tower) {
        this.tower = tower;
    }

    /**
     * @return true if Mother Nature is on this island, otherwise false
     */
    public boolean checkMotherNature(){
        return motherNature;
    }

    /**
     * switches the value of motherNature from true to false and vice versa
     */
    public void updateMotherNature(){
        motherNature = !motherNature;
    }

    /**
     * @param i is added to the list of islands
     */
    public void addIslands(ArrayList<Island> i){
        islands.addAll(i);
    }

    /**
     * @return the islands that are joined in the archipelago
     */
    public ArrayList<Island> getIslands(){
        return islands;
    }

    /**
     * This method calculates the influence of a player on the archipelago
     * @param p is the player for whom calculate the influence
     * @return the int value of the influence
     */
    public int totalPlayerInfluence(Player p){
        int tot = 0;
        for (Island i : islands)
            tot += i.playerInfluence(p);
        if(tower == p.getBoard().getTowerColor())
            tot += islands.size();
        return tot;
    }

    /**
     * @return the number of noEntry cards on this archipelago
     */
    public int checkNoEntry(){
        return noEntry;
    }


    /**
     * add a noEntry card in this archipelago
     * @param value specifies how many noEntry cards to add
     */
    public void addNoEntry(int value){
        noEntry+=value;
    }

    /**
     * reduce by one the number of NoEntry cards
     */
    public void removeNoEntry()
    {
        noEntry--;
    }
}
