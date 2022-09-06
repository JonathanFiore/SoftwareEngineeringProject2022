package it.polimi.ingsw.server.model.characters.parameters;


import java.io.Serializable;

/**
 *This class represents a generic parameter given to each character, we have different types of parameters
 * according to what each character needs to perform its task
 * This class is required because each character needs different types of parameters to perform its task
 * This is an extension of the strategy Pattern
 */
public abstract class Parameter implements Serializable {

    private final String name;

    public Parameter(String name) {
        this.name = name;
    }

    /**
     * @return the parameter's name
     */
    public String getName() {
        return name;
    }
}
