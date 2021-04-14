package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.*;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private static int victoryPoints;

    private boolean hasInkwell;

    private final String nickname;

    private boolean isHisTurn;

    private final PersonalBoard personalBoard;

    private Game game;

    private LeaderCard[] leaderCards;

    private final List<LeaderAbility> activeLeaderAbilities;

    private final ResourceStrategy resStrategy;

    private final DevCardsStrategy devStrategy;

    private final ProductionStrategy prodStrategy;

    private static int playerFaith;

    public Player(boolean hasInkwell, String nickname) {
        this.hasInkwell = hasInkwell;
        this.nickname = nickname;
        this.isHisTurn = false;
        Player.playerFaith = 0;
        Player.victoryPoints = 0;
        this.personalBoard = new PersonalBoard();
        this.leaderCards = new LeaderCard[2];
        this.activeLeaderAbilities = new ArrayList<>();
        this.resStrategy = new ResourceStrategy();
        this.devStrategy = new DevCardsStrategy();
        this.prodStrategy = new ProductionStrategy();
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

    public void setGame(Game game) {
        this.game = game;
    }

    public PersonalBoard getPersonalBoard() {
        return personalBoard;
    }

    //TODO: only for testing
    public void setLeaderCards(int index, LeaderCard card) {
        this.leaderCards[index] = card;
    }

    public void addResourceStrategy(ChangeWhiteMarbles newStrategy) throws TooManyLeaderAbilitiesException {
        this.resStrategy.addAbility(newStrategy);
    }

    public void addDevCardsStrategy(Discount newStrategy) throws TooManyLeaderAbilitiesException {
        this.devStrategy.addAbility(newStrategy);
    }

    public void addProductionStrategy(ExtraProduction newStrategy) throws TooManyLeaderAbilitiesException {
        this.prodStrategy.addAbility(newStrategy);
    }

    public Resource getAllResources() throws NegativeResAmountException, InvalidKeyException {
        Resource depotRes = personalBoard.getDepot().queryAllRes();
        Resource strongboxRes = personalBoard.getStrongbox().queryAllRes();
        return depotRes.sum(strongboxRes);
    }

    public Resource takeResources(int position, AbilityChoice choice, int amount1, int amount2) throws NegativeResAmountException, InvalidKeyException, InvalidChoiceException, InvalidAbilityChoiceException, NoLeaderAbilitiesException {
        if (choice == AbilityChoice.STANDARD) return BasicStrategies.takeResources(this, position);
        else return this.resStrategy.takeResources(this, position, choice, amount1, amount2);
    }

    public void buyDevCard(int level, CardColor color, AbilityChoice choice, Resource fromDepot, Resource fromStrongbox) throws CannotContainFaithException, NotEnoughSpaceException, NegativeResAmountException, DeckEmptyException, CostNotMatchingException, NotEnoughResException, InvalidKeyException, NoLeaderAbilitiesException, InvalidAbilityChoiceException {
        DevelopmentCard card = this.getGame().getMarket().getCard(level, color);
        if (choice == AbilityChoice.STANDARD) BasicStrategies.buyDevCard(this, level, color, card.getCost(), fromDepot, fromStrongbox);
        else this.devStrategy.buyDevCard(this, level, color, choice, fromDepot, fromStrongbox);
    }

    public void basicProduction(ResourceType input1, ResourceType input2, ResourceType output, Resource fromDepot, Resource fromStrongbox) throws CostNotMatchingException, NotEnoughSpaceException, CannotContainFaithException, NotEnoughResException, NegativeResAmountException, InvalidKeyException {
        BasicStrategies.basicProduction(this, input1, input2, output, fromDepot, fromStrongbox);
    }

    public void extraProduction(AbilityChoice choice, Resource fromDepot, Resource fromStrongbox) throws NoLeaderAbilitiesException, InvalidAbilityChoiceException, CostNotMatchingException, NotEnoughSpaceException, CannotContainFaithException, NotEnoughResException, NegativeResAmountException, InvalidKeyException {
        this.prodStrategy.extraProduction(this, choice, fromDepot, fromStrongbox);
    }

    public void activateProduction(DevelopmentCard[] cards) {
        // TODO implement here
    }

    public void useLeader(int index, LeaderAction choice) throws TooManyLeaderAbilitiesException, LeaderAbilityAlreadyActive, InvalidLayerNumberException {
        LeaderCard leader =  this.leaderCards[index];
        if (leader.isActive()) throw new LeaderAbilityAlreadyActive();

        /*TODO: discard
        //if (choice == LeaderAction.DISCARD) personalBoard.getFaithTrack().setPosition( + 1):
        // Deletes used LeaderCard, what about victory points??
        */
        /*else*/

        LeaderAbility ability = leader.getSpecialAbility();
        ability.activate(this);
        leader.activate();
        this.activeLeaderAbilities.add(ability);
    }

    public List<LeaderAbility> getActiveLeaderAbilities() {
        return this.activeLeaderAbilities;
    }

    public void chooseLeaderCard(int first, int second) {
        this.leaderCards = new LeaderCard[]{
            this.leaderCards[first], this.leaderCards[second]
        };
    }

    public void setLeaderCards(LeaderCard[] leaderCards) {
        this.leaderCards = leaderCards;
    }

    public LeaderCard[] getLeaderCards() {
        return this.leaderCards;
    }
}