package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidLayerNumberException;
import it.polimi.ingsw.exceptions.TooManyLeaderAbilitiesException;

public abstract class LeaderAbility {

    public abstract void activate(Player player) throws TooManyLeaderAbilitiesException, InvalidLayerNumberException;
}