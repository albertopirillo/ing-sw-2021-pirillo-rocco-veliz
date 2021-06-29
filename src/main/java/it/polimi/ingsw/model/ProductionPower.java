package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * The Production Power that each Development card has<br>
 * When the player activates the production of a Development card,<br>
 * He will receive resources as output if he has the input resources
 */
public class ProductionPower implements Serializable, Cloneable {

    /**
     * The resources that the player must have to activate the card's production
     */
    private Resource input;
    /**
     * The resources that the player receive
     */
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