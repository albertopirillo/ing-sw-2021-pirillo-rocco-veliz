package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.DevSlotEmptyException;
import it.polimi.ingsw.exceptions.InvalidNumSlotException;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.List;

public class PersonalBoard {

    private SoloActionTokens[] soloActionTokens;
    private DevelopmentSlot[] devSlots;
    private Depot depot;
    private Strongbox strongbox;
    private FaithTrack faithTrack;

    public PersonalBoard(Player player) {
        this.depot = new ConcreteDepot();
        this.strongbox = new Strongbox(player);
        this.faithTrack = new FaithTrack();
        this.devSlots = new DevelopmentSlot[3];
        this.devSlots[0] = new DevelopmentSlot();
        this.devSlots[1] = new DevelopmentSlot();
        this.devSlots[2] = new DevelopmentSlot();
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

    public void addDevCard(DevelopmentCard devCard, int slotNumber) throws DevSlotEmptyException, InvalidNumSlotException {
        if(this.devSlots[slotNumber].canBeAdded(devCard))
            this.devSlots[slotNumber].addCard(devCard);
        else throw new InvalidNumSlotException();
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