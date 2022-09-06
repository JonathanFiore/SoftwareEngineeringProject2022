package it.polimi.ingsw.server.model.characters.cards;

import it.polimi.ingsw.server.model.Archipelago;
import it.polimi.ingsw.server.model.characters.parameters.ArchipelagoParameter;
import it.polimi.ingsw.server.model.characters.parameters.Parameter;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class HerbsGrannyTest {

    @Test
    void task() {
        HerbsGranny hb = new HerbsGranny();
        // checks if the HerbsGranny's id and cost are correct
        assertEquals(5, hb.getId());
        assertEquals(2, hb.getCost());

        Archipelago a = new Archipelago(false);
        Parameter ap = new ArchipelagoParameter("ap", a);
        ArrayList<Parameter> p = new ArrayList<>();
        p.add(ap);

        assertEquals(a.checkNoEntry(),0); // checks if the archipelago has 0 No Entry cards on it before the activation of the character's effect

        for(int i=1; i<=4; i++) {
            hb.task(p);
            assertEquals(a.checkNoEntry(),i); // checks if the amount of No Entry cards on the archipelago increases after each usage of the character card
        }
        hb.task(p);
        assertEquals(3,hb.getCost()); // checks if the cost of the character has increased by only 1 after using its effect multiple times
        assertNotEquals(a.checkNoEntry(), 4 + 1);  // checks if the amount of No Entry cards on the archipelago has not increased after using the character card for the fifth time
    }

    @Test
    void addNoEntry() {
        HerbsGranny hb = new HerbsGranny();
        Archipelago a = new Archipelago(false);
        Parameter ap = new ArchipelagoParameter("ap", a);
        ArrayList<Parameter> p = new ArrayList<>();
        p.add(ap);

        for(int i=0; i<4; i++) { // every No Entry card is removed from the Character card
            hb.task(p);
        }
        assertEquals(hb.getNoEntry(), 0); // checks if the Character card hasn't got any No Entry card on it

        hb.addNoEntry();
        assertEquals(hb.getNoEntry(), 1); // check if the No Entry card is correctly added to the card
    }
}