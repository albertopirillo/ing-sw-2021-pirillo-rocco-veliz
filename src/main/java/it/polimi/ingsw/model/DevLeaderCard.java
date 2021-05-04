package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.List;

public class DevLeaderCard extends LeaderCard implements Serializable {

    private final List<LeaderDevCost> requires;

    public DevLeaderCard(int id, String img, int victoryPoints, LeaderAbility specialAbility, List<LeaderDevCost> requires) {
        super(id, img, victoryPoints, specialAbility);
        this.requires = requires;
    }

    public DevLeaderCard(int victoryPoints, LeaderAbility specialAbility, List<LeaderDevCost> requires) {
        super(victoryPoints, specialAbility);
        this.requires = requires;
    }

    @Override
    public LeaderCardType getLeaderCardType() {
        return LeaderCardType.DEV;
    }

    public List<LeaderDevCost> getRequires() {
        return requires;
    }

    public boolean canBeActivated(Player player) {
        List<DevelopmentCard> playerCards = player.getPersonalBoard().getAllCards();
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
    }



}