package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.DepotSetting;
import it.polimi.ingsw.network.requests.ChangeMarblesRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>The controller that handles all the actions a player can perform</p>
 * <p>Every action has its own method</p>
 * <p>Ensures that the player isn't performing more than one main action per turn</p>
 */
public class PlayerController {

    /**
     * Reference to the associated MasterController
     */
    private final MasterController controller;
    /**
     * Specifies if the player has already performed a main action this turn
     */
    private boolean mainActionDone;
    /**
     * Set to false to make the player perform one action per turn, like the real game
     */
    private boolean testing = false;

    /**
     * Constructs a new PlayerController
     * @param controller the associated MainController
     */
    public PlayerController(MasterController controller) {
        this.controller = controller;
        this.mainActionDone = false;
    }

    /**
     * <p>Activates the testing mode of an already existing controller</p>
     * <p>Should be used only in testing</p>
     * @param testing true to activate the testing mode
     */
    public void setTesting(boolean testing) {
        this.testing = testing;
    }


    /**
     * <p>Takes resources from the Market tray</p>
     * <p>Places those resources into TempResource, to be handled by ResourceController</p>
     * <p>If the player has two ChangeWhiteMarbleAbility activated, asks him instructions</p>
     * @param position  the position of the grid where the remaining marble should be inserted
     */
    public void insertMarble(int position) {
        try {
            if (this.mainActionDone) throw new MainActionException();
            Player activePlayer = controller.getGame().getActivePlayer();
            Resource output = activePlayer.insertMarble(position);
            ResourceController resourceController = controller.getResourceController();
            resourceController.getTempRes().setToHandle(output);
            if (!testing) {
                this.mainActionDone = true;
                controller.getGame().setMainActionDone();
            }
            if(output.hasAllResources()){
                controller.getGame().updateMarketTray();
                controller.getGame().updateTempMarbles(output.getValue(ResourceType.ALL));
            }
            else {
                controller.getGame().updateMarketTray();
                controller.getGame().updateStorages();
                controller.getGame().updateTempRes();
            }
            controller.resetException();
        } catch (NegativeResAmountException | InvalidKeyException | MainActionException e) {
            controller.setException(e);
            controller.getGame().updateClientError(controller.getClientError());
            controller.getGame().notifyEndOfUpdates();
        }
    }

    /**
     * <p>If two ChangeWhiteMarbleAbility are active, changes the white marbles into new marbles</p>
     * <p>Uses instructions provided with a Client Request</p>
     * <p>Updates TempResource accordingly</p>
     * @param changeMarblesRequest the Client Request with the instructions
     */
    public void changeWhiteMarbles(ChangeMarblesRequest changeMarblesRequest){
        ResourceController resourceController = controller.getResourceController();
        Resource toHandle = resourceController.getTempRes().getToHandle();
        try {
            Player activePlayer = controller.getGame().getActivePlayer();
            int amount1 = changeMarblesRequest.getAmount1();
            int amount2 = changeMarblesRequest.getAmount2();
            if(toHandle.getValue(ResourceType.ALL) != amount1 + amount2) throw new CostNotMatchingException("The number of white marbles does not match");
            activePlayer.changeWhiteMarbles(amount1, amount2, toHandle);
            controller.getGame().updateTempRes();
            controller.resetException();
        } catch (InvalidKeyException | NegativeResAmountException | CostNotMatchingException | NoLeaderAbilitiesException e) {
            controller.setException(e);
            controller.getGame().updateClientError(controller.getClientError());
            try {
                controller.getGame().updateTempMarbles(toHandle.getValue(ResourceType.ALL));
            } catch (InvalidKeyException invalidKeyException) {
                invalidKeyException.printStackTrace();
            }
        }
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
     */
    public void buyDevCard(int level, CardColor color, int numSlot, AbilityChoice choice, Resource fromDepot, Resource fromStrongbox) {
        try {
            if (this.mainActionDone) throw new MainActionException();
            Player activePlayer = controller.getGame().getActivePlayer();
            activePlayer.buyDevCard(level, color, numSlot, choice, fromDepot, fromStrongbox);
            controller.getGame().updateMarket();
            controller.getGame().updateDevSlots();
            controller.getGame().updateStorages();
            controller.resetException();
            if(!testing)  {
                this.mainActionDone = true;
                controller.getGame().setMainActionDone();
            }
        } catch (CannotContainFaithException | NotEnoughSpaceException | NegativeResAmountException | DeckEmptyException | CostNotMatchingException | NotEnoughResException | InvalidKeyException | NoLeaderAbilitiesException | InvalidAbilityChoiceException | DevSlotEmptyException | InvalidNumSlotException | MainActionException e) {
            controller.setException(e);
            controller.getGame().updateClientError(controller.getClientError());
        } finally {
            controller.getGame().notifyEndOfUpdates();
        }
    }

    /**
     * <p>Places into the Depot the resources taken from the market</p>
     * <p>Calls ResourceController's methods</p>
     * @param toDiscard – all the resources that player want to discards
     * @param toPlace – data structure that tell where to place the taken resources
     * @param fullDepot – whether the request is coming from a CLI or a GUI
     */
    public void placeResource(Resource toDiscard, List<DepotSetting> toPlace, boolean fullDepot) {
        try {
            controller.resetException();
            controller.getResourceController().handleResource(toDiscard, toPlace, fullDepot);
            controller.getGame().updateFaithTrack();
            controller.getGame().updateStorages();
            controller.getGame().notifyEndOfUpdates();
        } catch (NotEnoughSpaceException | CannotContainFaithException | NegativeResAmountException | InvalidKeyException | InvalidResourceException | WrongDepotInstructionsException | LayerNotEmptyException | InvalidLayerNumberException | AlreadyInAnotherLayerException e) {
            controller.setException(e);
            controller.getGame().updateClientError(controller.getClientError());
            controller.getGame().updateTempRes();
            controller.getGame().updateStorages();
        }
    }

    /**
     * <p>Ends the turn, updating the active player</p>
     * <p>Checks if the player performed its main action</p>
     * <p>Checks if there are TempResources to be placed</p>
     * <p>Moves the resources from the tempStrongbox to the Strongbox</p>
     */
    public void endTurn() {
        try {
            controller.getGame().getActivePlayer().getPersonalBoard().transferResources();

            if (!controller.getResourceController().getTempRes().isEmpty()) {
                throw new CannotEndTurnException("There are still resources to be placed");
            }
            if (!testing && !this.mainActionDone) {
                throw new MainActionException("You have to perform an action before ending the turn");
            }
            this.mainActionDone = false;
            controller.getGame().getActivePlayer().getPersonalBoard().updateFaithTrack(controller.getGame().getPlayersList());
            controller.getGame().nextTurn();
            controller.resetException();
        } catch (CannotEndTurnException | NegativeResAmountException | MainActionException e) {
            controller.setException(e);
            controller.getGame().updateClientError(controller.getClientError());
        } finally {
            if (!(controller.getGame().getLastTurn() && controller.getGame().getActivePlayer().getInkwell())) {
                controller.getGame().notifyEndOfUpdates();
            }

        }
    }

    /**
     * Activates a leader ability or discards a leader card
     * @param index which leader card should be activated/discarded
     * @param choice whether the card should be activated or discarded
     */
    public void useLeader(int index, LeaderAction choice) {
        Game game = controller.getGame();
        try {
            Player activePlayer = game.getActivePlayer();
            activePlayer.useLeader(index, choice);
            game.updateLeaderCards();
            if (choice == LeaderAction.DISCARD) {
                game.updateFaithTrack();
            }
            controller.resetException();
        } catch (TooManyLeaderAbilitiesException | CostNotMatchingException | InvalidLayerNumberException | NoLeaderAbilitiesException | NegativeResAmountException | LeaderAbilityAlreadyActive e) {
            controller.setException(e);
            game.updateClientError(controller.getClientError());
        } finally {
            game.notifyEndOfUpdates();
        }
    }

    /**
     * Reorders the content of the depot, swapping resources from its layers
     * @param fromLayer the layer to take the resources from
     * @param toLayer the layer to put the resources in
     * @param amount the amount of resources to move
     */
    public void reorderDepot(int fromLayer, int toLayer, int amount) {
        try {
            Player activePlayer = controller.getGame().getActivePlayer();
            activePlayer.reorderDepot(fromLayer, toLayer, amount);
            controller.getGame().updateStorages();
            controller.resetException();
        } catch (InvalidResourceException | LayerNotEmptyException | NotEnoughSpaceException | InvalidLayerNumberException | CannotContainFaithException | NotEnoughResException | NegativeResAmountException e) {
            controller.setException(e);
            controller.getGame().updateClientError(controller.getClientError());
        } finally {
            controller.getGame().notifyEndOfUpdates();
        }
    }

    /**
     * Reorganizes the content of the Depot, swapping resources from its layers
     * @param settings the list of settings to rebase the Depot from
     */
    public void reorderDepot(List<DepotSetting> settings) {
        try {
            Player activePlayer = controller.getGame().getActivePlayer();
            activePlayer.getPersonalBoard().getDepot().setFromDepotSetting(settings);
            controller.getGame().updateStorages();
            controller.resetException();
        } catch (WrongDepotInstructionsException e) {
            controller.setException(e);
            controller.getGame().updateClientError(controller.getClientError());
            controller.getGame().updateStorages();
        } finally {
            controller.getGame().notifyEndOfUpdates();
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
    public void basicProduction(ResourceType input1, ResourceType input2, ResourceType output, Resource fromDepot, Resource fromStrongbox) {
        try {
            if (this.mainActionDone) throw new MainActionException();
            Player activePlayer = controller.getGame().getActivePlayer();
            activePlayer.basicProduction(input1, input2, output, fromDepot, fromStrongbox);
            controller.getGame().updateStorages();
            controller.resetException();
            if (!testing) {
                this.mainActionDone = true;
                controller.getGame().setProductionDone();
                controller.getGame().setMainActionDone();
            }
        } catch (MainActionException | CostNotMatchingException | NotEnoughSpaceException | CannotContainFaithException | NotEnoughResException | NegativeResAmountException e) {
            controller.setException(e);
            controller.getGame().updateClientError(controller.getClientError());
        } finally {
            controller.getGame().notifyEndOfUpdates();
        }
    }

    /**
     * <p>Performs an extra production (from a leader card ability)</p>
     * <p>Places its output into the strongbox</p>
     * @param choice – specifies which leader ability should be used
     * @param fromDepot – the amount of resources the player is paying from the depot
     * @param fromStrongbox – the amount of resources the player is paying from the strongbox
     * @param res – the resource type the player wants to receive
     */
    public void extraProduction(AbilityChoice choice, Resource fromDepot, Resource fromStrongbox, ResourceType res) {
        try {
            Player activePlayer = controller.getGame().getActivePlayer();
            activePlayer.extraProduction(choice, fromDepot, fromStrongbox, res);
            controller.getGame().updateStorages();
            controller.getGame().updateFaithTrack();
            controller.resetException();
        } catch (CostNotMatchingException | InvalidAbilityChoiceException | NotEnoughSpaceException | NoLeaderAbilitiesException | CannotContainFaithException | NotEnoughResException | NegativeResAmountException e) {
            controller.setException(e);
            controller.getGame().updateClientError(controller.getClientError());
        } finally {
            controller.getGame().notifyEndOfUpdates();
        }
    }


    /**
     * <p>Actives the production power of the specified dev cards</p>
     * <p>Places the outputs into the strongbox</p>
     * @param fromDepot the resources the player wants to pay from the depot
     * @param fromStrongbox the resources the player wants to pay from the strongbox
     * @param numSlots a list of index of slots to activate the production from
     */
    public void activateProduction(Resource fromDepot, Resource fromStrongbox, List<Integer> numSlots) {
        Player activePlayer = controller.getGame().getActivePlayer();
        PersonalBoard personalBoard = activePlayer.getPersonalBoard();
        List<DevelopmentCard> devCards = new ArrayList<>();
        Resource total = new Resource(0,0,0,0);
        Depot depot = personalBoard.getDepot();
        Strongbox strongbox = personalBoard.getStrongbox();
        DevelopmentCard card;
        try {
            for (Integer i : numSlots){
                card = personalBoard.getSlot(i).getTopCard();
                devCards.add(card);
                total = total.sum(card.getProdPower().getInput());
            }
            BasicStrategies.checkAndCompare(depot, strongbox, fromDepot, fromStrongbox, total);
            activePlayer.activateProduction(devCards);
            depot.retrieveRes(fromDepot);
            strongbox.retrieveRes(fromStrongbox);
            controller.getGame().updateFaithTrack();
            controller.getGame().updateStorages();
            controller.resetException();
            if(!testing) {
                this.mainActionDone = true;
                controller.getGame().setSecondProductionDone();
                controller.getGame().setMainActionDone();
            }
        } catch (CostNotMatchingException | NotEnoughResException | NegativeResAmountException | DevSlotEmptyException | NotEnoughSpaceException | CannotContainFaithException e) {
            controller.setException(e);
            controller.getGame().updateClientError(controller.getClientError());
        } finally {
            controller.getGame().notifyEndOfUpdates();
        }
    }
}