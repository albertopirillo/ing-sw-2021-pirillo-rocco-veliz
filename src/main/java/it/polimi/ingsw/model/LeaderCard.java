package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidKeyException;
import it.polimi.ingsw.exceptions.NegativeResAmountException;

import java.io.Serializable;

public abstract class LeaderCard extends Card implements Serializable {

    private final int id;
    private final String img ;
    private final LeaderAbility specialAbility;

    private boolean isActive;

    //json initialization
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

    public abstract boolean canBeActivated(Player player) throws NegativeResAmountException, InvalidKeyException;
}