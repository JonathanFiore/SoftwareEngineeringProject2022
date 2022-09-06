package it.polimi.ingsw.server.model.characters.parameters;

/**
 *This is a parameter which contains an Integer
 */
public class IntegerParameter extends Parameter{


    private final Integer value;

    public IntegerParameter(String name, Integer value) {
        super(name);
        this.value = value;
    }

    /**
     * @return the parameter "value"
     */
    public Integer getValue() {
        return value;
    }
}
