package it.polimi.ingsw.server.model.characters.parameters;

import it.polimi.ingsw.server.model.Archipelago;

/**
 *This is a parameter which contains an archipelago
 */
public class ArchipelagoParameter extends Parameter{


    private final Archipelago archipelago;


    public ArchipelagoParameter(String name, Archipelago archipelago) {
        super(name);
        this.archipelago = archipelago;
    }

    /**
     * @return the parameter "archipelago"
     */
    public Archipelago getArchipelago() {
        return archipelago;
    }
}
