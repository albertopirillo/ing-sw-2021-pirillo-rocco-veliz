package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.DevSlotEmptyException;

import java.util.List;

public class PersonalBoard {

    private SoloActionTokens[] soloActionTokens;
    private DevelopmentSlot[] devSlots;
    private Depot depot;
    private Strongbox strongbox;
    private FaithTrack faithTrack;

    public PersonalBoard(Player player) {
        depot = new ConcreteDepot();
        strongbox = new Strongbox(player);
        faithTrack = new FaithTrack();
    }

    public FaithTrack getFaithTrack() {
        return this.faithTrack;
    }

    public DevelopmentSlot getSlot(int slotNumber){
        return devSlots[slotNumber];
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

    public void updateFaithTrack(List<Player> players) {
        for(Player player: players){
            if(player.getTurn()){
                if (player.getPlayerFaith() >= 24) {
                    //TODO ENDGAME
                }
                //needed first getPersonalBoard()
                player.getPersonalBoard().getFaithTrack().checkPopeTile(player, players);
            }
        }

    }

}