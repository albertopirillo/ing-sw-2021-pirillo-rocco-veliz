package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class DevLeaderCard extends LeaderCard {

    private final List<LeaderDevCost> requires;

    public DevLeaderCard(int victoryPoints, LeaderAbility specialAbility, ArrayList<LeaderDevCost> requires) {
        super(victoryPoints, specialAbility);
        this.requires = requires;
    }

    public boolean canBeActivated(Player player) {
        //TODO: implement here
        return false;
    }

    //TODO: delete
    /*public boolean canBeActivated(List<DevelopmentCard> playerCards) {
        boolean check;
        for (LeaderDevCost require : requires){
            if(require.getLevel()==0) {
                check = playerCards.stream().filter(e -> e.getType() == require.getColor()).count() >= require.getAmount();
            }else {
                check = playerCards.stream().filter(e -> e.getType() == require.getColor() && e.getLevel() == require.getLevel()).count() >= require.getAmount();
            }
            if (!check) return false;
        }
        return true;

    }*/
}