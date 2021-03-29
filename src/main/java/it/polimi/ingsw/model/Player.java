package it.polimi.ingsw.model;

public class Player {

    public Player() {
    }

    private boolean hasInkwell;

    private String nickname;

    private boolean isHisTurn;

    private LeaderCard[] leaderCards;

    private Strategy[] strategies;


    public void takeResources(Resource resources) {
        // TODO implement here
    }

    public void buyDevCard(DevelopmentCard devCard) {
        // TODO implement here
    }

    public ResourceType extraProduction() {
        // TODO implement here
        return null;
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