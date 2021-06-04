package it.polimi.ingsw.model;

import java.io.Serializable;

public class ProductionPower implements Serializable, Cloneable {

    private Resource input;
    private Resource output;

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

    @Override
    public ProductionPower clone() {
        ProductionPower clone = null;
        try {
            clone = (ProductionPower) super.clone();
            clone.input = this.input.clone();
            clone.output = this.output.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return clone;
    }
}