package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.server.model.characters.cards.Character;

import java.util.ArrayList;

/**
 * This interface is used to specify the actions required to the player
 */
public interface View
{
    /**
     *It asks the player to choose a card
     */
    void chooseCard(ArrayList<Assistant> availableCards);

    /**
     *It tells the player that a wrong card has been chosen
     * @param error details of the error
     * @param availableCards show the available cards
     */
    void errorCard(String error, ArrayList<Assistant> availableCards);

    /**
     *It tells the player is its turn
     */
    void isYourTurn();

    /**
     *It tells the player to wait its turn
     */
    void waitTurn(String currentPlayer);

    /**
     *It tells the player that a problem occurred with the students decided to move
     */
    void errorStudents(String error);

    /**
     *It tells the player to move mother nature
     */
    void moveMotherNature(int maxSteps);


    /**
     * It tells the players that a wrong number of steps has been chosen
     * @param error details
     * @param maxSteps specifies the limit a steps available
     */
    void errorMotherNature(String error, int maxSteps);

    /**
     * It tells the player to choose a cloud
     * @param availableClouds specifies the clouds available
     */
    void chooseCloud(ArrayList<Integer> availableClouds);

    /**
     * It tells the players that a problem occurred with the character chosen
     * @param error details
     */
    void errorCharacter(String error);

    /**
     * It tells the player a wrong cloud has been chosen
     * @param error details
     * @param availableClouds specifies the clouds available
     */
    void errorCloud(String error, ArrayList<Integer> availableClouds);

    /**
     * It tells players that the game is finished
     * @param winner specifies the winner
     */
    void endGame(String winner);


    /**
     * It tells the player that a generic error occurred
     * @param s specifies the details of the error
     */
    void joinGameError(String s);

    /**
     * It tells the player to choose a towerColor during login session
     * @param availableColors shows the colors available
     */
    void chooseTowerColor(ArrayList<Tower> availableColors);

    /**
     *  It tells the player that a wrong color has been chosen
     * @param error details
     * @param availableColors shows the colors available
     */
    void errorTowerColor(String error, ArrayList<Tower> availableColors);

    /**
     * It tells the player to choose a Wizard during login session
     * @param availableWizards shows the wizards available
     */
    void chooseWizard(ArrayList<Wizard> availableWizards);

    /**
     * It tells the player that a wrong Wizard has been chosen
     * @param error details
     * @param availableWizards shows the wizards available
     */
    void errorWizard(String error, ArrayList<Wizard> availableWizards);

    /**
     * It tells the player to wait, others players need to join before the game can start
     * @param numCurrentPlayers specifies how many players there are now
     */
    void waitingPlayers(int numCurrentPlayers);

    /**
     * It tells the player the game is started
     * @param players specifies all the players of this game
     * @param teams specifies the teams (just for 4-player mode)
     * @param clouds specifies the clouds with the students on them
     * @param archipelagos specifies the archipelagos with the students and motherNature on them
     * @param characters specifies the characters chosen by the server for this game (just for expertMode)
     */
    void startGame(ArrayList<Player> players, Team[] teams, ArrayList<Cloud> clouds,
                   ArrayList<Archipelago> archipelagos, Character[] characters, boolean expertMode);


    /**
     * It tells the player to update players' status
     * @param updatedPlayers: list of players
     */
    void playersUpdate(ArrayList<Player> updatedPlayers);

    /**
     * It tells the player to update islands' status
     * @param updatedArchipelagos: list of archipelagos changed
     */
    void islandsUpdate(ArrayList<Archipelago> updatedArchipelagos);


    /**
     *It tells the player to update cloud's status
     * @param clouds ArrayList<Cloud>
     */
    void cloudUpdate(ArrayList<Cloud> clouds);

    /**
     * It tells the player to update character's status
     * @param updatedCharacter specifies the character changed
     * @param index of character changed
     */
    void characterUpdate(Character updatedCharacter, int index);

    /**
     * It tells the player to choose the settings of the game
     */
    void chooseGameSettings();

    /**
     * Add an observer
     * @param observer is the observer for the object
     */
    void addObserver(Observer observer);


}
