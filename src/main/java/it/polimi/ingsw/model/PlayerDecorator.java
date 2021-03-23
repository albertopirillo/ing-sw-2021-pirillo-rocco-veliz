package it.polimi.ingsw.model;

import java.util.*;

public abstract class PlayerDecorator extends Player {

    public PlayerDecorator() {
    }

    protected Player player;


    public void takeResources(Resource[] resources) {
        // TODO implement here
    }

    public void buyDevCard(DevelopmentCard devCard) {
        // TODO implement here
    }

    public void activateProduction(DevelopmentCard[] cards) {
        // TODO implement here
    }

    public void useLeader(LeaderCard card, LeaderAction choice) {
        // TODO implement here
    }

    public LeaderCard[] chooseLeaderCard(LeaderCard[] leader) {
        // TODO implement here
        return null;
    }

}