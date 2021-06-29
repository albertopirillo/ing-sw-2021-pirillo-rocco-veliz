package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.DevSlotEmptyException;
import it.polimi.ingsw.exceptions.InvalidNumSlotException;
import it.polimi.ingsw.exceptions.NegativeResAmountException;

import java.util.ArrayList;
import java.util.List;

/**
 * The player's personal board object
 */
public class PersonalBoard {

    /**
     * The three slots of Development cards
     */
    private DevelopmentSlot[] devSlots;
    /**
     * The reference to the Depot
     */
    private Depot depot;
    /**
     * The reference to the strongbox
     */
    private final Strongbox strongbox;
    /**
     * The reference to the faith track;
     */
    private final FaithTrack faithTrack;

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
    } //Only testing

    public DevelopmentSlot[] getDevSlots() {
        return devSlots;
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

    public void transferResources() throws NegativeResAmountException {
        strongbox.transferTempRes();
    }

    /**
     * Return a list of all Development cards that the player has
     * @return a list of all Development cards that the player has, this method is called to check if one player has 7 or more cards(condition to end the game)
     */
    public List<DevelopmentCard> getAllCards() {
        List<DevelopmentCard> cardList = new ArrayList<>();
        for (DevelopmentSlot slot : devSlots) {
            if(slot.numberOfElements()>0) cardList.addAll(slot.getCards());
        }
        return cardList;
    }

    /**
     * Add a Development Card to the player's slot passed in the parameter
     * @param devCard the Development cards just taken from the Market
     * @param slotNumber the number of slot, from o to two
     * @throws InvalidNumSlotException if the card can not add to the slot
     */
    public void addDevCard(DevelopmentCard devCard, int slotNumber) throws DevSlotEmptyException, InvalidNumSlotException {
        if(this.devSlots[slotNumber].canBeAdded(devCard))
            this.devSlots[slotNumber].addCard(devCard);
        else throw new InvalidNumSlotException();
    }

    public void updateFaithTrack(List<Player> players) {
        for(Player player: players){
            player.getPersonalBoard().getFaithTrack().checkPopeTile(player, players);
        }
    }
}