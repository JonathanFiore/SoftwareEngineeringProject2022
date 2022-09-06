package it.polimi.ingsw.protocol;

import java.util.ArrayList;

/**
 * This message is sent from the client to the server, it indicates which students the player wants to move and where
 * the player wants to place them, if on the islands or in the hall.
 * the default hall's index is 13: if the player wants to move some students to hall the index which has to be
 * specified is 13
 */
public class MoveStudentsMessage extends Message{

    private final ArrayList<Integer> entranceIndexes;
    private final ArrayList<Integer> destinationIndexes;

    public MoveStudentsMessage(ArrayList<Integer> entranceIndexes, ArrayList<Integer> destinationIndexes) {
        super(MessageLabel.MOVE_STUDENTS);
        this.entranceIndexes = entranceIndexes;
        this.destinationIndexes = destinationIndexes;
    }

    /**
     * @return the indexes of the students from the entrance that the player wants to move
     */
    public ArrayList<Integer> getEntranceIndexes() {
        return entranceIndexes;
    }

    /**
     * @return the indexes of the destinations from 1 to 13: 1-12 are the islands, the 13th is the dining room
     */
    public ArrayList<Integer> getDestinationIndexes() {
        return destinationIndexes;
    }
}
