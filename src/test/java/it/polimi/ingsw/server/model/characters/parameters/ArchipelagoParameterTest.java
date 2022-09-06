package it.polimi.ingsw.server.model.characters.parameters;

import it.polimi.ingsw.server.model.Archipelago;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArchipelagoParameterTest {

    @Test
    void getArchipelago() {
        Archipelago a = new Archipelago(false);
        Parameter ap = new ArchipelagoParameter("archipelago", a);
        assertInstanceOf(Archipelago.class, ((ArchipelagoParameter)ap).getArchipelago()); // checks if the method correctly returns an Archipelago
        assertEquals(a, ((ArchipelagoParameter)ap).getArchipelago()); // checks if the method returns the same Archipelago as the one passed to the constructor
        assertEquals("archipelago", ap.getName());  // checks if the method returns the same String as the one passed to the constructor
    }
}