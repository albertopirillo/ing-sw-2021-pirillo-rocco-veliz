package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.List;

/**
 * One of the two types of Leader Cards
 * Each DevLeaderCard has a LeaderDevCost object associated to the card's requirements and a Leader Ability that can be activated
 */
public class DevLeaderCard extends LeaderCard implements Serializable {

    /**
     * The list of requirements required to activate the leader card
     */
    private final List<LeaderDevCost> requirements;

    /**
     * Create a DevLeaderCard
     * @param victoryPoints card's victory points that each card has
     * @param specialAbility card's Leader Ability that each leader card has
     * @param requirements card's requirements required to activate the leader card
     */
    public DevLeaderCard(int victoryPoints, LeaderAbility specialAbility, List<LeaderDevCost> requirements) {
        super(victoryPoints, specialAbility);
        this.requirements = requirements;
    }

    public boolean canBeActivated(Player player) {
        List<DevelopmentCard> playerCards = player.getPersonalBoard().getAllCards();
        boolean check;
        for (LeaderDevCost requirement : requirements){
            if(requirement.getLevel()==0) {
                check = playerCards.stream().filter(e -> e.getType() == requirement.getColor()).count() >= requirement.getAmount();
            }else {
                check = playerCards.stream().filter(e -> e.getType() == requirement.getColor() && e.getLevel() == requirement.getLevel()).count() >= requirement.getAmount();
            }
            if (!check) return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\tRequires: ");
        for(LeaderDevCost leaderDevCost: requirements){
            sb.append("\n\t >\tColor: ").append(leaderDevCost.getColor());
            if (leaderDevCost.getLevel() == 0) {
                sb.append("\n\t\tLevel: ANY");
            }
            else {
                sb.append("\n\t\tLevel: ").append(leaderDevCost.getLevel());
            }
            sb.append("\n\t\tAmount: ").append(leaderDevCost.getAmount());
        }
        sb.append("\n\tAbility");
        sb.append(getSpecialAbility().toString());
        sb.append("\n\tVictoryPoints: ").append(getVictoryPoints());
        return sb.toString();
    }
}