package it.polimi.ingsw.server.serverexceptions;

/**
 * This exception is launched when the players need to be notified that the game has ended
 */
public class EndGameException extends Exception{

    public EndGameException(String message) {
        super(message);
    }
}
