package it.polimi.ingsw.protocol;

import it.polimi.ingsw.server.model.Archipelago;
import it.polimi.ingsw.server.model.Cloud;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.Team;
import it.polimi.ingsw.server.model.characters.cards.Character;

import java.util.ArrayList;

/**
 * This message is sent from the server to each client to say that the game is starting.
 */
public class StartGameMessage extends Message{

    private final ArrayList<Player> players;
    private final Team[] teams;
    private final ArrayList<Cloud> clouds;
    private final ArrayList<Archipelago> archipelagos;
    private final Character[] characters;
    private final boolean expertMode;

    public StartGameMessage(ArrayList<Player> players, Team[] teams, ArrayList<Cloud> clouds, ArrayList<Archipelago> archipelagos, Character[] characters, boolean expertMode) {
        super(MessageLabel.START_GAME);
        this.players = players;
        this.teams = teams;
        this.clouds = clouds;
        this.archipelagos = archipelagos;
        this.characters = characters;
        this.expertMode = expertMode;
    }

    /**
     * @return the players of the game
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * @return the teams if the game is between 4 players, otherwise null
     */
    public Team[] getTeams() {
        return teams;
    }

    /**
     * @return the clouds of the game
     */
    public ArrayList<Cloud> getClouds() {
        return clouds;
    }

    /**
     * @return the archipelagos (all 12 islands because the game has to start and so archipelagos aren't already formed)
     */
    public ArrayList<Archipelago> getArchipelagos() {
        return archipelagos;
    }

    /**
     * @return the characters if the mode selected is the expert one, otherwise null
     */
    public Character[] getCharacters() {
        return characters;
    }

    /**
     * @return true if the mode selected is the expert one, false if it's the easy one
     */
    public boolean isExpertMode() {
        return expertMode;
    }
}
