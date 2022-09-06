package it.polimi.ingsw.protocol;

/**
 * This message is sent from the client to the server when a player wants to join the game.
 */
public class JoinGameMessage extends Message{

    private final String nickname;

    public JoinGameMessage(String nickname) {
        super(MessageLabel.JOIN_GAME);
        this.nickname = nickname;
    }

    /**
     * @return a string that contains the nickname of the player that wants to join
     */
    public String getNickname() {
        return nickname;
    }
}
