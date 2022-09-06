package it.polimi.ingsw.server.model.characters.cards;

import it.polimi.ingsw.server.model.Archipelago;
import it.polimi.ingsw.server.model.Island;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.Student;
import it.polimi.ingsw.server.model.characters.parameters.Parameter;
import it.polimi.ingsw.server.model.characters.parameters.PlayerParameter;

import java.util.ArrayList;

/**
 * This class implements the second Character: the Farmer
 */
public class Farmer extends Character {


    private Player currentPlayer;
    private final ArrayList<Player> players;

    public Farmer(ArrayList<Player> players) {
        super(2, 2);
        this.players = players;
    }

    /**
     * @param parameters contains all the parameters that a specific character needs
     */
    @Override
    public void task(ArrayList<Parameter> parameters) {
        currentPlayer = ((PlayerParameter) parameters.get(0)).getPlayer();
        super.setCoin();
    }


    /**
     * When this card is activated this method is used to calculate the influence of the players, instead of
     * using the method in gameModel
     *
     * @param player:      the player whose we want to calculate the influence
     * @param archipelago: where we want to calculate the majority
     * @return the influence of the player on the archipelago specified
     */
    public int actualTask(Player player, Archipelago archipelago) {
        int tot = 0;
        ArrayList<Island> islands = archipelago.getIslands();
        ArrayList<Student> students = new ArrayList<>();

        for (Island i : islands) {
            students.addAll(i.getStudents());
            tot += realPlayerInfluence(player, students);
            students.clear();
        }
        if (archipelago.getTower() == player.getBoard().getTowerColor())
            tot += islands.size();
        return tot;
    }

    /**
     * It calculates the influence of a player according to this card's task
     *
     * @param player:  the player whose I want to calculate the influence
     * @param students is a list of student in a specific island
     * @return the influence value of the player on a specific island
     */
    private int realPlayerInfluence(Player player, ArrayList<Student> students) {
        int sum = 0;
        Player p;
        for (Student s : students) {
            p = checkTeacher(s);
            // If the current player is equal to the player who owns the teacher and is equal to the player I am analyzing
            if (player.equals(currentPlayer) && currentPlayer.equals(p)) {
                sum++;
            } else {
                // If the current player is not equal to the player I am analyzing, but it is equal to the player who owns the teacher
                if (!player.equals(currentPlayer) && player.equals(p)) {
                    if (currentPlayer.getBoard().getNumberOfStudents(s) < player.getBoard().getNumberOfStudents(s))
                        sum++;
                } else {
                    // If the current player is equal to the player I am analyzing, and it is not equal to the player who owns the teacher
                    if (player.equals(currentPlayer) && !player.equals(p)) {
                        if (p != null && currentPlayer.getBoard().getNumberOfStudents(s) == p.getBoard().getNumberOfStudents(s))
                            sum++;
                    }
                }
            }
        }

        return sum;
    }


    /**
     * Check which player has the teacher for a specific color
     *
     * @param s is used to specify the color
     * @return which player has the teacher of that color
     */
    private Player checkTeacher(Student s) {
        Player maxPlayer = null;
        boolean found = false;
        for (int i = 0; i < players.size() && !found; i++) {
            if (players.get(i).getBoard().isProfessorPresent(Student.valueOf(s.name()))) {
                maxPlayer = players.get(i);
                found = true;
            }
        }
        return maxPlayer;
    }
}
