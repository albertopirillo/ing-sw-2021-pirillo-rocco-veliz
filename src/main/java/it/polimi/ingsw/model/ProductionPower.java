package it.polimi.ingsw.model;

import java.io.Serializable;

public class ProductionPower implements Serializable {

    private final Resource input;
    private final Resource output;

    public ProductionPower(Resource input, Resource output) {
        this.input = input;
        this.output = output;
    }

    public Resource getInput(){
        return input;
    }

    public Resource getOutput(){
        return output;
    }

    @Override
    public String toString() {
        return "\n\t\tInput:  " + input +
                "\n\t\tOutput: " + output;
    }
}