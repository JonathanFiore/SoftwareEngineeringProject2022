package it.polimi.ingsw.protocol;

/**
 * All the labels which represent the types of messages exchanged between client and server
 */
public enum MessageLabel
{
    /**
     * NetworkConnection
     */
    PING,

    /**
     * StartGame
     */
    JOIN_GAME,
    ERROR_JOIN_GAME,
    CHOOSE_GAME_SETTINGS,
    SELECTED_GAME_SETTINGS,
    CHOOSE_TOWER_COLOR,
    SELECTED_TOWER_COLOR,
    ERROR_TOWER_COLOR,
    CHOOSE_WIZARD,
    SELECTED_WIZARD,
    ERROR_WIZARD,
    WAITING_OTHER_PLAYERS,
    START_GAME,

    /**
     * turns
     */

    WAIT_YOUR_TURN,
    IS_YOUR_TURN,

    /**
     * PLANNING
     */

    CHOOSE_CARD,
    SELECTED_CARD,
    ERROR_CARD,

    /**
     * Action
     */

    MOVE_STUDENTS,
    ERROR_STUDENTS,
    MOVE_MOTHER_NATURE,
    STEPS_MOTHER_NATURE,
    ERROR_MOTHER_NATURE,
    CHOOSE_CLOUD,
    SELECTED_CLOUD,
    ERROR_CLOUD,
    USE_CHARACTER,
    ERROR_CHARACTER,
    MOVE_ON,

    /**
     * Updates
     */

    PLAYERS_UPDATE,
    ISLANDS_UPDATE,
    CLOUD_UPDATE,
    CHARACTER_UPDATE,

    /**
     * EndGame
     */

    END_GAME,
}
