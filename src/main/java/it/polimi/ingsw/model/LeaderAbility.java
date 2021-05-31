package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidLayerNumberException;
import it.polimi.ingsw.exceptions.TooManyLeaderAbilitiesException;

/**
 * Implements a generic leader ability
 */
public abstract class LeaderAbility {

    /**
     * Activates the leader ability, executing its effects
     * @param player the player that activated the ability
     * @throws TooManyLeaderAbilitiesException if the player already has two leader abilities
     */
    public abstract void activate(Player player) throws TooManyLeaderAbilitiesException, InvalidLayerNumberException;
}