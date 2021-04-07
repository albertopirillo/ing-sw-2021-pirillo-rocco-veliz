package it.polimi.ingsw.model;

public class ProductionPower {

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
}