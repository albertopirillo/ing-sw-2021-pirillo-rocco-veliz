package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.NegativeResAmountException;

import java.io.Serializable;

/**
 * Concept of Leader Card
 * Each leader card has a id, a path img, a Leader Ability
 * There are two types of Leader Cards: DevLeaderCard and ResLeaderCard
 */
public abstract class LeaderCard extends Card implements Serializable, Cloneable {
    /**
     * Card's identifier
     */
    private final int id;
    /**
     * Card's image path
     */
    private final String img;
    /**
     * Card's Leader Ability
     */
    private LeaderAbility specialAbility;
    /**
     * Whether this card was activated by the player or not
     */
    private boolean isActive;

    /**
     * Create a Leader Card
     * @param id card's identifier
     * @param img card's image path
     * @param victoryPoints card's victory points
     * @param specialAbility card's Leader Ability
     */
    public LeaderCard(int id, String img, int victoryPoints, LeaderAbility specialAbility) {
        super(victoryPoints);
        this.id = id;
        this.img = img;
        this.isActive = false;
        this.specialAbility = specialAbility;
    }

    public LeaderCard(int victoryPoints, LeaderAbility specialAbility) {
        super(victoryPoints);
        this.id = 0;
        this.img = "";
        this.isActive = false;
        this.specialAbility = specialAbility;
    }

    public boolean isActive() {
        return this.isActive;
    }

    /**
     * Set the card as activated
     */
    public void activate() {
        this.isActive = true;
    }

    public int getId(){
        return this.id;
    }

    public String getImg(){
        return this.img;
    }

    public LeaderAbility getSpecialAbility() {
        return specialAbility;
    }

    /**
     * *Check if the Leader Activity can be activated by the player
     * @param player player the player that want activate the leader card
     * @return true if the player satisfy the requirements, else otherwise
     */
    public abstract boolean canBeActivated(Player player) throws NegativeResAmountException;

    @Override
    public LeaderCard clone() {
        LeaderCard clone = null;
        try {
            clone = (LeaderCard) super.clone();
            clone.specialAbility = this.specialAbility.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return clone;
    }
}