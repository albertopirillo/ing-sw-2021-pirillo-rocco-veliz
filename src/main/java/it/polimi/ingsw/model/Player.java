package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidChoiceException;
import it.polimi.ingsw.exceptions.InvalidKeyException;
import it.polimi.ingsw.exceptions.NegativeResAmountException;

import java.util.Map;

public class Player {

    private static int victoryPoints;

    private boolean hasInkwell;

    private final String nickname;

    private boolean isHisTurn;

    private final PersonalBoard personalBoard;

    private final Game game;

    private LeaderCard[] leaderCards;

    private BaseResourceStrategy resStrategy;

    private BaseDevCardsStrategy devStrategy;

    private BaseProductionStrategy prodStrategy;

    private static int playerFaith;


    public Player(boolean hasInkwell, String nickname, Game game, int playerFaith, int victoryPoints) {
        this.hasInkwell = hasInkwell;
        this.nickname = nickname;
        this.isHisTurn = false;
        this.game = game;
        Player.playerFaith = playerFaith;
        Player.victoryPoints = victoryPoints;
        personalBoard = new PersonalBoard();
        leaderCards = new LeaderCard[2];
        //resStrategy = new BaseResourceStrategy();
        //devStrategy = new BaseDevCardsStrategy();
        //prodStrategy = new BaseProductionStrategy();
    }

    public boolean getInkwell() {
        return hasInkwell;
    }

    public static int getPlayerFaith() { return playerFaith; }

    public int getVictoryPoints() {return victoryPoints;}
    public static void setVictoryPoints(int faithTrackPoints){
        victoryPoints = victoryPoints + faithTrackPoints;
    }

    public void setInkwell(boolean bool) {
        hasInkwell = bool;
    }

    public String getNickname() {
        return nickname;
    }

    public Game getGame() {
        return this.game;
    }

    public PersonalBoard getPersonalBoard() {
        return personalBoard;
    }

    public void changeResourceStrategy (ResourceStrategy newStrategy) {
        if(this.resStrategy!=null){
            this.resStrategy.addAbility(newStrategy);
        }
        else {
            this.resStrategy = newStrategy;
        }
    }

    public void changeDevCardsStrategy (DevCardsStrategy newStrategy) {
        if(this.devStrategy!=null){
            this.devStrategy.addAbility(newStrategy);
        }
        else {
            this.devStrategy = newStrategy;
        }
    }
    public void changeProductionStrategy (ProductionStrategy newStrategy) {
        if(this.prodStrategy!=null){
            this.prodStrategy.addAbility(newStrategy);
        }
        else {
            this.prodStrategy = newStrategy;
        }
    }
    //testing
    public BaseDevCardsStrategy getDevStrategy(){
        return this.devStrategy;
    }
    //testing
    public BaseResourceStrategy getResStrategy() {
        return resStrategy;
    }
    //testing
    public BaseProductionStrategy getProdStrategy() {
        return prodStrategy;
    }

    public Resource getAllResources() throws NegativeResAmountException, InvalidKeyException {
        Map<ResourceType, Integer> depotRes = personalBoard.getDepot().queryAllRes().getAllRes();
        Map<ResourceType, Integer> strongboxRes = personalBoard.getStrongbox().queryAllRes();
        return new Resource(depotRes.get(ResourceType.STONE) + strongboxRes.get(ResourceType.STONE),
                             depotRes.get(ResourceType.COIN) + strongboxRes.get(ResourceType.COIN),
                            depotRes.get(ResourceType.SHIELD) + strongboxRes.get(ResourceType.SHIELD),
                           depotRes.get(ResourceType.SERVANT) + strongboxRes.get(ResourceType.SERVANT));
    }

    public void takeResources(int position, boolean standard, int choice) throws NegativeResAmountException, InvalidKeyException, InvalidChoiceException {
        Resource resources = resStrategy.takeResources(position, game.getMarket(), standard, choice);
        Depot depot = personalBoard.getDepot();
        //TODO: Player should now decide where to put the resources by calling depot.modifyLayer()
    }

    public void buyDevCard(DevelopmentCard devCard) {
        //devStrategy.buyDevCard()
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
        /*TODO: discard
        //if (choice == LeaderAction.DISCARD) personalBoard.getFaithTrack().setPosition( + 1):
        // Deletes used LeaderCard
        //TODO: activate ability
        else card.getLeaderAbility().activate();
        */
    }

    public LeaderCard[] chooseLeaderCard(LeaderCard[] leader) {
        // TODO implement here
        return null;
    }

}