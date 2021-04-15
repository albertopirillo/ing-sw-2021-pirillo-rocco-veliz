package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.DevSlotEmptyException;

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

    public FaithTrack getFaithTrack() {
        return this.faithTrack;
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
    public void upgradeDepot(Depot depot) {
        this.depot = depot;
    }
    public Strongbox getStrongbox() {
        return strongbox;
    }
    public void setStrongbox(Strongbox strongbox) {
        this.strongbox = strongbox;
    }

    public void addDevCard(DevelopmentCard devCard/*, int slotNumber*/){
        int slotNumber;
        boolean can = false;
        for (slotNumber=0; slotNumber<devSlots.length && !can; slotNumber++){
            try {
                can = getSlot(slotNumber).canBeAdded(devCard);
            } catch (DevSlotEmptyException e) {
                e.printStackTrace();
            }
        }
        this.devSlots[slotNumber-1].addCard(devCard);
    }

    public DevelopmentSlot getSlot(int slotNumber){
        return devSlots[slotNumber];
    }

    public void endgame() {
        // TODO implement here
    }

}