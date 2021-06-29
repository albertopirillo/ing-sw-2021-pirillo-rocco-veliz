package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Implements a generic player</p>
 * <p>Can be used to access its personal board, its faith points and victory points</p>
 * <p>Stores references to the data of active leader abilities</p>
 */
public class Player {

    /**
     * The current victory points of the player
     */
    private int victoryPoints;
    /**
     * Whether this player was the first player or not
     */
    private boolean hasInkwell;
    /**
     * The nickname of this player
     */
    private final String nickname;
    /**
     * Whether this player it's currently the active player or not
     */
    private boolean isHisTurn;
    /**
     * Reference to the personal board of the player
     */
    private final PersonalBoard personalBoard;
    /**
     * Reference to the game the player is playing in
     */
    private Game game;
    /**
     * List of all the leader cards of the player
     */
    private List<LeaderCard> leaderCards;
    /**
     * List of the currently active leader abilities of the player
     */
    private final List<LeaderAbility> activeLeaderAbilities;
    /**
     * Reference to the player's data about ChangeWhiteMarbleAbility
     */
    private final ResourceStrategy resStrategy;
    /**
     * Reference to the player's data about DiscountAbility
     */
    private final DevCardsStrategy devStrategy;
    /**
     * Reference to the player's data about ExtraProductionAbility
     */
    private final ProductionStrategy prodStrategy;

    /**
     * Constructs a new object, initializing everything
     * @param nickname the nickname of the player
     */
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

    /**
     * Returns a list with all active ChangeWhiteMarblesAbility
     * @return a list of abilities
     */
    public List<ResourceType> getResTypesAbility(){
        return this.resStrategy.getResType();
    }

    /**
     * Whether the player has the inkwell or not
     * @return true if it has the inkwell, false otherwise
     */
    public boolean getInkwell() {
        return this.hasInkwell;
    }

    /**
     * Gets the current faith points of the player
     * @return an int representing the faith points
     */
    public int getPlayerFaith() {
        return this.getPersonalBoard().getFaithTrack().getPlayerFaith();
    }

    /**
     * Adds faith points to the player
     * @param amount the amount of points to add
     */
    public void addPlayerFaith(int amount) {
        for (int i=0; i<amount; i++){
            this.getPersonalBoard().getFaithTrack().addPlayerFaith(1);
            this.getPersonalBoard().updateFaithTrack(this.game.getPlayersList());
        }
    }

    /**
     * Gets the current victory points of the player
     * @return an int representing the victory points
     */
    public int getVictoryPoints() {return this.victoryPoints;}

    /**
     * Adds victory points to the player
     * @param amount the amount of points to add
     */
    public void addVictoryPoints(int amount){
        this.victoryPoints = this.victoryPoints + amount;
    }

    /**
     * Sets the inkwell
     * @param bool true if this player has the inkwell
     */
    public void setInkwell(boolean bool) {
        this.hasInkwell = bool;
    }

    /**
     * Gets the nickname of the player
     * @return the nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Sets if this player is the current active player
     * @param isHisTurn true if it is the current active player
     */
    public void setTurn(boolean isHisTurn) { this.isHisTurn = isHisTurn; }

    /**
     * Whether the player is the current active player or not
     * @return true if it is the current active player, false otherwise
     */
    public boolean getTurn() { return this.isHisTurn; }

    /**
     * Gets the Game the player is playing into
     * @return the game
     */
    public Game getGame() {
        return this.game;
    }

    /**
     * Sets the Game the player is playing into
     * @param game the game
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * Gets the player personal board
     * @return a reference to the personal board
     */
    public PersonalBoard getPersonalBoard() {
        return personalBoard;
    }

    /**
     * Gets the list of all the currently active leader abilities
     * @return the a list of abilities
     */
    public List<LeaderAbility> getActiveLeaderAbilities() {
        return this.activeLeaderAbilities;
    }

    /**
     * Sets the leader cards of the player
     * @param leaderCards the leader cards of the player
     */
    public void setLeaderCards(List<LeaderCard> leaderCards) {
        this.leaderCards = new ArrayList<>(leaderCards);
    }

    /**
     * <p>Testing only</p>
     * <p>Adds only one leader card to the player</p>
     * @param card the leader card to add
     */
    public void addLeaderCard(LeaderCard card) {
        this.leaderCards.add(card);
    }

    /**
     * <p>Testing only</p>
     * <p>Sets one of the player's leader cards</p>
     * @param index the index of the leader card to set
     * @param card the leader card to set
     */
    public void setLeaderCard(int index, LeaderCard card) {
        this.leaderCards.set(index, card);
    }

    /**
     * Gets the list of all the player's leader cards
     * @return a list of leader cards
     */
    public List<LeaderCard> getLeaderCards() {
        return new ArrayList<>(this.leaderCards);
    }

    /**
     * Adds a new ChangeWhiteMarble ability to the player
     * @param newStrategy the ability to be added
     * @throws TooManyLeaderAbilitiesException if the player already has two abilities
     */
    public void addResourceStrategy(ChangeWhiteMarblesAbility newStrategy) throws TooManyLeaderAbilitiesException {
        this.resStrategy.addAbility(newStrategy);
    }
    /**
     * Adds a new Discount ability to the player
     * @param newStrategy the ability to be added
     * @throws TooManyLeaderAbilitiesException if the player already has two abilities
     */
    public void addDevCardsStrategy(DiscountAbility newStrategy) throws TooManyLeaderAbilitiesException {
        this.devStrategy.addAbility(newStrategy);
    }
    /**
     * Adds a new ExtraProduction ability to the player
     * @param newStrategy the ability to be added
     * @throws TooManyLeaderAbilitiesException if the player already has two abilities
     */
    public void addProductionStrategy(ExtraProductionAbility newStrategy) throws TooManyLeaderAbilitiesException {
        this.prodStrategy.addAbility(newStrategy);
    }

    /**
     * Gets all the resources the player has in the strongbox and in the depot
     * @return a Resource object that contains all the resources
     */
    public Resource getAllResources() throws NegativeResAmountException {
        Resource depotRes = personalBoard.getDepot().queryAllRes();
        Resource strongboxRes = personalBoard.getStrongbox().queryAllRes();
        return depotRes.sum(strongboxRes);
    }

    /**
     * Returns a copy of all the temp resources store in the strongbox.
     * @return  a copy of all the temp resources stored in the strongbox
     */
    public Resource getAllTempResources() {
        return personalBoard.getStrongbox().queryAllTempRes();
    }

    /**
     * If two ChangeWhiteMarbleAbility are active, changes the white marbles into new marbles
     * @param amount1 the amount of marbles to change using the first ability
     * @param amount2 the amount of marbles to change using the first ability
     * @param toHandle reference to the temporary resources taken from the market
     */
    public void changeWhiteMarbles(int amount1, int amount2, Resource toHandle) throws NegativeResAmountException, NoLeaderAbilitiesException {
        this.resStrategy.changeWhiteMarbles(this, amount1, amount2, toHandle);
    }

    /**
     * Takes resources from the Market tray and places it in TempResource
     * @param position  the position of the grid where the remaining marble should be inserted
     * @return the resources taken from the Market
     */
    public Resource insertMarble(int position) throws NegativeResAmountException, InvalidKeyException {
        return this.resStrategy.insertMarble(this, position);
    }

    /**
     * <p>Buys a card from the Market and places it into a development slot</p>
     * <p>If the player bought its 7th card, ends the game</p>
     * @param level the level of the card to buy
     * @param color the color of the card to buy
     * @param numSlot the number of the dev slot where the player wants to place the card
     * @param choice which leader abilities should be used, if any
     * @param fromDepot the amount of resources the player is paying from the depot
     * @param fromStrongbox the amount of resources the player is paying from the strongbox
     * @throws InvalidNumSlotException if the chosen development slot is already full
     */
    public void buyDevCard(int level, CardColor color, int numSlot, AbilityChoice choice, Resource fromDepot, Resource fromStrongbox) throws CannotContainFaithException, NotEnoughSpaceException, NegativeResAmountException, DeckEmptyException, CostNotMatchingException, NotEnoughResException, InvalidKeyException, NoLeaderAbilitiesException, InvalidAbilityChoiceException, DevSlotEmptyException, InvalidNumSlotException {
        DevelopmentCard card = this.getGame().getMarket().getCard(level, color);
        if(!getPersonalBoard().getSlot(numSlot).canBeAdded(card)) throw new InvalidNumSlotException();
        if (choice == AbilityChoice.STANDARD) BasicStrategies.buyDevCard(this, level, color, numSlot, card.getCost(), fromDepot, fromStrongbox);
        else this.devStrategy.buyDevCard(this, level, color, numSlot, choice, fromDepot, fromStrongbox);
        //If the player has bought his seventh DevCard, the game is over
        if(this.getPersonalBoard().getAllCards().size() == 7) {
            this.game.lastTurn(true);
        }
    }

    /**
     * Performs a basic production and places its output in the strongbox
     * @param input1 the first resource type paid
     * @param input2 the second resource type paid
     * @param output the wanted output resource type
     * @param fromDepot the amount of resources the player is paying from the depot
     * @param fromStrongbox the amount of resources the player is paying from the strongbox
     */
    public void basicProduction(ResourceType input1, ResourceType input2, ResourceType output, Resource fromDepot, Resource fromStrongbox) throws CostNotMatchingException, NotEnoughSpaceException, CannotContainFaithException, NotEnoughResException, NegativeResAmountException {
        BasicStrategies.basicProduction(this, input1, input2, output, fromDepot, fromStrongbox);
    }

    /**
     *Performs an extra production (from a leader card ability) and places its output in the strongbox
     * @param choice specifies which leader ability should be used
     * @param fromDepot the amount of resources the player is paying from the depot
     * @param fromStrongbox the amount of resources the player is paying from the strongbox
     * @param res the resource type the player wants to receive
     */
    public void extraProduction(AbilityChoice choice, Resource fromDepot, Resource fromStrongbox, ResourceType res) throws NoLeaderAbilitiesException, InvalidAbilityChoiceException, CostNotMatchingException, NotEnoughSpaceException, CannotContainFaithException, NotEnoughResException, NegativeResAmountException {
        this.prodStrategy.extraProduction(this, choice, fromDepot, fromStrongbox, res);
    }

    /**
     * Actives the production power of the specified dev cards and places the outputs in the strongbox
     * @param cards a list of the dev cards to activate the production power
     */
    public void activateProduction(List<DevelopmentCard> cards) throws NegativeResAmountException {
        for(DevelopmentCard devCard: cards){
            getPersonalBoard().getStrongbox().addTempResources(devCard.getProdPower().getOutput());
        }
    }

    /**
     * Activates a leader ability or discards a leader card
     * @param index which leader card should be activated/discarded
     * @param choice whether the card should be activated or discarded
     * @throws LeaderAbilityAlreadyActive if the selected leader card is already active
     * @throws CostNotMatchingException if the player doesnt met the requirements to active the ability
     * @throws NoLeaderAbilitiesException if the selected leader card doesnt exist
     */
    public void useLeader(int index, LeaderAction choice) throws TooManyLeaderAbilitiesException, LeaderAbilityAlreadyActive, InvalidLayerNumberException, NegativeResAmountException, CostNotMatchingException, NoLeaderAbilitiesException {
        if (index < 0 || index >= leaderCards.size()) {
            throw new NoLeaderAbilitiesException("The selected leader card does not exist");
        }
        LeaderCard leader = this.leaderCards.get(index);
        if (leader.isActive()) throw new LeaderAbilityAlreadyActive("You cannot discard an active leader card");

        if (choice == LeaderAction.DISCARD) {
            this.addPlayerFaith(1);
            //Victory points of discarded cards are not taken into account
            this.leaderCards.remove(leader);
        }
        else {
            if(leader.canBeActivated(this)) {
                leader.activate();
                LeaderAbility ability = leader.getSpecialAbility();
                ability.activate(this);
                this.activeLeaderAbilities.add(ability);
                this.addVictoryPoints(leader.getVictoryPoints());
            } else {
                throw new CostNotMatchingException("LeaderCard requirements not satisfied");
            }
        }
    }

    /**
     * Discards resources, giving 1 faith points to the other players for every resource
     * @param resource the resources to be discarded
     */
    public void discardRes(Resource resource) throws CannotContainFaithException {
        this.personalBoard.getDepot().discardRes(this, resource);
    }

    /**
     * Reorders the content of the depot, swapping resources from its layers
     * @param fromLayer the layer to take the resources from
     * @param toLayer the layer to put the resources in
     * @param amount the amount of resources to move
     */
    public void reorderDepot(int fromLayer, int toLayer, int amount) throws InvalidResourceException, LayerNotEmptyException, NotEnoughSpaceException, InvalidLayerNumberException, CannotContainFaithException, NotEnoughResException, NegativeResAmountException {
        Depot depot = this.personalBoard.getDepot();
        depot.moveResources(fromLayer, toLayer, amount);
    }
}
