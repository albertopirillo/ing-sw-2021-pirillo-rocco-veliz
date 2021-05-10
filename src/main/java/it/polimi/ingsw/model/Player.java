package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.*;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private int victoryPoints;
    private boolean hasInkwell;
    private final String nickname;
    private transient boolean isHisTurn;
    private final PersonalBoard personalBoard;
    private transient Game game;
    private List<LeaderCard> leaderCards;
    private transient final List<LeaderAbility> activeLeaderAbilities;
    private transient final ResourceStrategy resStrategy;
    private transient final DevCardsStrategy devStrategy;
    private transient final ProductionStrategy prodStrategy;

    public Player(String nickname) {
        this.nickname = nickname;
        this.isHisTurn = false;
        this.hasInkwell = false;
        this.victoryPoints = 0;
        this.personalBoard = new PersonalBoard(this);
        this.leaderCards = new ArrayList<>();
        this.activeLeaderAbilities = new ArrayList<>();
        this.resStrategy = new ResourceStrategy();
        this.devStrategy = new DevCardsStrategy();
        this.prodStrategy = new ProductionStrategy();
    }

    public boolean getInkwell() {
        return this.hasInkwell;
    }

    public int getPlayerFaith() {
        return this.getPersonalBoard().getFaithTrack().getPlayerFaith();
    }

    public void addPlayerFaith(int amount) throws NegativeResAmountException, InvalidKeyException {
        this.getPersonalBoard().getFaithTrack().addPlayerFaith(amount);
    }

    public int getVictoryPoints() {return victoryPoints;}

    public void addVictoryPoints(int amount){
        this.victoryPoints = this.victoryPoints + amount;
    }

    public void setInkwell(boolean bool) {
        this.hasInkwell = bool;
    }

    public String getNickname() {
        return nickname;
    }

    public void setTurn(boolean isHisTurn) { this.isHisTurn = isHisTurn; }

    public boolean getTurn() { return this.isHisTurn; }

    public Game getGame() {
        return this.game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public PersonalBoard getPersonalBoard() {
        return personalBoard;
    }

    public List<LeaderAbility> getActiveLeaderAbilities() {
        return this.activeLeaderAbilities;
    }

    public void setLeaderCards(List<LeaderCard> leaderCards) {
        this.leaderCards = new ArrayList<>(leaderCards);
    }

    //TODO: only for testing
    public void addLeaderCard(LeaderCard card) {
        this.leaderCards.add(card);
    }

    //TODO: only for testing
    public void setLeaderCard(int index, LeaderCard card) {
        this.leaderCards.set(index, card);
    }

    public List<LeaderCard> getLeaderCards() {
        return new ArrayList<>(this.leaderCards);
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

    public Resource insertMarble(int position, AbilityChoice choice, int amount1, int amount2) throws NegativeResAmountException, InvalidKeyException, InvalidAbilityChoiceException, NoLeaderAbilitiesException, CostNotMatchingException {
        if (choice == AbilityChoice.STANDARD) {
            return BasicStrategies.insertMarble(this, position);
        } else {
            return this.resStrategy.insertMarble(this, position, choice, amount1, amount2);
        }
    }

    public void buyDevCard(int level, CardColor color, int numSlot, AbilityChoice choice, Resource fromDepot, Resource fromStrongbox) throws CannotContainFaithException, NotEnoughSpaceException, NegativeResAmountException, DeckEmptyException, CostNotMatchingException, NotEnoughResException, InvalidKeyException, NoLeaderAbilitiesException, InvalidAbilityChoiceException, DevSlotEmptyException, InvalidNumSlotException {
        DevelopmentCard card = this.getGame().getMarket().getCard(level, color);
        if(!getPersonalBoard().getSlot(numSlot).canBeAdded(card)) throw new InvalidNumSlotException();
        if (choice == AbilityChoice.STANDARD) BasicStrategies.buyDevCard(this, level, color, numSlot, card.getCost(), fromDepot, fromStrongbox);
        else this.devStrategy.buyDevCard(this, level, color, numSlot, choice, fromDepot, fromStrongbox);
        //If the player has bought his seventh DevCard, the game is over
        if(this.getPersonalBoard().getAllCards().size() == 7) this.game.lastTurn(true);
    }

    public void basicProduction(ResourceType input1, ResourceType input2, ResourceType output, Resource fromDepot, Resource fromStrongbox) throws CostNotMatchingException, NotEnoughSpaceException, CannotContainFaithException, NotEnoughResException, NegativeResAmountException, InvalidKeyException {
        BasicStrategies.basicProduction(this, input1, input2, output, fromDepot, fromStrongbox);
    }

    public void extraProduction(AbilityChoice choice, Resource fromDepot, Resource fromStrongbox, ResourceType res) throws NoLeaderAbilitiesException, InvalidAbilityChoiceException, CostNotMatchingException, NotEnoughSpaceException, CannotContainFaithException, NotEnoughResException, NegativeResAmountException, InvalidKeyException {
        this.prodStrategy.extraProduction(this, choice, fromDepot, fromStrongbox, res);
    }

    public void activateProduction(List<DevelopmentCard> cards) throws NegativeResAmountException, InvalidKeyException, NotEnoughResException {
        for(DevelopmentCard devCard: cards){
            getPersonalBoard().getStrongbox().addResources(devCard.getProdPower().getOutput());
        }
    }

    public void useLeader(int index, LeaderAction choice) throws TooManyLeaderAbilitiesException, LeaderAbilityAlreadyActive, InvalidLayerNumberException, NegativeResAmountException, InvalidKeyException, CostNotMatchingException, NoLeaderAbilitiesException {
        if (index < 0 || index >= leaderCards.size()) {
            throw new NoLeaderAbilitiesException("The selected leader card does not exist");
        }
        LeaderCard leader = this.leaderCards.get(index);
        if (leader.isActive()) throw new LeaderAbilityAlreadyActive();
        if (choice == LeaderAction.DISCARD) {
            this.addPlayerFaith(1);
            //Victory points of discarded cards are not taken into account
            this.leaderCards.remove(leader);
        } else{
            if(leader.canBeActivated(this)) {
                leader.activate();
                LeaderAbility ability = leader.getSpecialAbility();
                ability.activate(this);
                this.activeLeaderAbilities.add(ability);
                this.victoryPoints = this.victoryPoints + leader.getVictoryPoints();
            } else {
                throw new CostNotMatchingException("LeaderCard requirements not satisfied");
            }
        }
}

    public void discardRes(Resource resource) throws CannotContainFaithException, NegativeResAmountException, InvalidKeyException {
        this.personalBoard.getDepot().discardRes(this, resource);
    }

    public void reorderDepot(int fromLayer, int toLayer, int amount) throws InvalidResourceException, LayerNotEmptyException, NotEnoughSpaceException, InvalidLayerNumberException, CannotContainFaithException, NotEnoughResException, NegativeResAmountException {
        Depot depot = this.personalBoard.getDepot();
        depot.moveResources(fromLayer, toLayer, amount);
    }
}
