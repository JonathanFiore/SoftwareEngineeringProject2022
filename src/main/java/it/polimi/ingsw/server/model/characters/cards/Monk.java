package it.polimi.ingsw.server.model.characters.cards;

import it.polimi.ingsw.server.model.Archipelago;
import it.polimi.ingsw.server.model.Bag;
import it.polimi.ingsw.server.model.Student;
import it.polimi.ingsw.server.model.characters.parameters.ArchipelagoParameter;
import it.polimi.ingsw.server.model.characters.parameters.IntegerParameter;
import it.polimi.ingsw.server.model.characters.parameters.Parameter;

import java.util.ArrayList;

/**
 * This class implements the first Character: the Monk
 */
public class Monk extends Character{

    private final ArrayList<Student> students;
    private final Bag bag;

    private Integer index;
    private Archipelago archipelago;


    public Monk(Bag bag)
    {
        super(1,1);

        this.bag = bag;
        this.students = new ArrayList<>();

        for(int i=0; i<4;i++)
        {
            students.add(bag.drawStudent());
        }
    }

    /**
     * @return the students on this card
     */
    public ArrayList<Student> getStudents() {
        return students;
    }

    /**
     *We just set the parameters we need to perform the effect of this card and call the real method which
     * performs the effect
     * @param parameters contains all the parameters that a specific character needs
     */
    @Override
    public void task(ArrayList<Parameter> parameters) {

        archipelago = ((ArchipelagoParameter) parameters.get(0)).getArchipelago();
        index = ((IntegerParameter) parameters.get(1)).getValue();
        super.setCoin();
        actualTask();

    }

    /**
     *It's the real effect of the card: we take a student from this card and we put it on an archipelago
     */
    private void actualTask()
    {
        archipelago.getIslands().get(0).addStudent(students.get(index));
        students.remove((int) index);
        Student s = bag.drawStudent();
        if(s!=null)
            students.add(s);
    }
}
