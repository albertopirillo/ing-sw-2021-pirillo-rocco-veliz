package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidSlotException;

import java.util.*;

public class PersonalBoard {

    private SoloActionTokens[] soloActionTokens;

    private DevelopmentSlot[] devSlots;

    private Depot depot;

    private Strongbox strongbox;

    private FaithTrack faithTrack;

    public PersonalBoard() {
        depot = new ConcreteDepot();
        strongbox = new Strongbox();
    }

    public DevelopmentSlot[] getDevSlots() {
        return devSlots;
    }

    public void setDevSlots(DevelopmentSlot[] devSlots) {
        this.devSlots = devSlots;
    }

    public Depot getDepot() {
        return depot;
    }

    public void setDepot(Depot depot) {
        this.depot = depot;
    }

    public Strongbox getStrongbox() {
        return strongbox;
    }

    public void setStrongbox(Strongbox strongbox) {
        this.strongbox = strongbox;
    }

    public void addDevCard(DevelopmentCard devCard, int slotNumber) throws InvalidSlotException {
        if (slotNumber < 1 || slotNumber > 3) throw new InvalidSlotException();
        //TODO: this.devSlots[slotNumber].addCard();
    }

    public void basicProduction(ResourceType input, ResourceType output) {
        // TODO implement here
    }

    public void endgame() {
        // TODO implement here
    }

}