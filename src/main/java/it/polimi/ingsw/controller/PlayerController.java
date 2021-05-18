package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.DepotSetting;
import it.polimi.ingsw.network.requests.ChangeMarblesRequest;

import java.util.ArrayList;
import java.util.List;

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
    private boolean testing = true;

    public PlayerController(MasterController controller) {
        this.controller = controller;
        this.mainActionDone = false;
    }

    public void setTesting(boolean testing) {
        this.testing = testing;
    }

    public void basicProduction(ResourceType input1, ResourceType input2, ResourceType output, Resource fromDepot, Resource fromStrongbox) {
        try {
            Player activePlayer = controller.getGame().getActivePlayer();
            activePlayer.basicProduction(input1, input2, output, fromDepot, fromStrongbox);
            controller.getGame().updateStorages();
            controller.resetException();
            if (!testing) this.mainActionDone = true;
        } catch (CostNotMatchingException | NotEnoughSpaceException | CannotContainFaithException | NotEnoughResException | NegativeResAmountException | InvalidKeyException e) {
            controller.setException(e);
            controller.getGame().updateClientError(controller.getClientError());
        } finally {
            controller.getGame().notifyEndOfUpdates();
        }
    }

    public void extraProduction(AbilityChoice choice, Resource fromDepot, Resource fromStrongbox, ResourceType res) {
        try {
            Player activePlayer = controller.getGame().getActivePlayer();
            activePlayer.extraProduction(choice, fromDepot, fromStrongbox, res);
            //controller.getGame().updateFaithTrack();
            //controller.getGame().updateDevSlots();
            controller.resetException();
        } catch (CostNotMatchingException | InvalidAbilityChoiceException | NotEnoughSpaceException | NoLeaderAbilitiesException | CannotContainFaithException | NotEnoughResException | NegativeResAmountException | InvalidKeyException e) {
            controller.setException(e);
            controller.getGame().updateClientError(controller.getClientError());
        } finally {
            controller.getGame().notifyEndOfUpdates();
        }
    }

    //Calls the player method and then DepotController to handle the resource placement in the depot
    public void insertMarble(int position) {
        try {
            if (this.mainActionDone) throw new MainActionException();
            Player activePlayer = controller.getGame().getActivePlayer();
            Resource output = activePlayer.insertMarble(position);
            ResourceController resourceController = controller.getResourceController();
            resourceController.getTempRes().setToHandle(output);
            if(output.hasAllResources()){
                controller.getGame().updateTempMarbles(output.getValue(ResourceType.ALL));
            }
            else {
                controller.getGame().updateMarketTray();
                //controller.getGame().updateStorages();
                controller.getGame().updateTempRes();
            }
            controller.resetException();
            if (!testing) this.mainActionDone = true;
        } catch (NegativeResAmountException | InvalidKeyException | MainActionException e) {
            controller.setException(e);
            controller.getGame().updateClientError(controller.getClientError());
            controller.getGame().notifyEndOfUpdates();
        }
    }

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

    public void buyDevCard(int level, CardColor color, int numSlot, AbilityChoice choice, Resource fromDepot, Resource fromStrongbox) {
        try {
            if (this.mainActionDone) throw new MainActionException();
            Player activePlayer = controller.getGame().getActivePlayer();
            activePlayer.buyDevCard(level, color, numSlot, choice, fromDepot, fromStrongbox);
            controller.getGame().updateMarket();
            controller.getGame().updateDevSlots();
            controller.getGame().updateStorages();
            controller.resetException();
            if(!testing) this.mainActionDone = true;
        } catch (CannotContainFaithException | NotEnoughSpaceException | NegativeResAmountException | DeckEmptyException | CostNotMatchingException | NotEnoughResException | InvalidKeyException | NoLeaderAbilitiesException | InvalidAbilityChoiceException | DevSlotEmptyException | InvalidNumSlotException | MainActionException e) {
            controller.setException(e);
            controller.getGame().updateClientError(controller.getClientError());
        } finally {
            controller.getGame().notifyEndOfUpdates();
        }
    }
    
    public void placeResource(Resource toDiscard, List<DepotSetting> toPlace) {
        try {
            controller.resetException();
            controller.getResourceController().handleResource(toDiscard, toPlace);
            //controller.getGame().updateFaithTrack();
            controller.getGame().updateStorages();
            //controller.resetException();
            controller.getGame().notifyEndOfUpdates();
        } catch (NotEnoughSpaceException | CannotContainFaithException | NegativeResAmountException | InvalidKeyException | InvalidResourceException | WrongDepotInstructionsException | LayerNotEmptyException | InvalidLayerNumberException | AlreadyInAnotherLayerException e) {
            controller.setException(e);
            controller.getGame().updateClientError(controller.getClientError());
            controller.getGame().updateTempRes();
        }
    }

    public void endTurn() {
        try { //Check exception and resource handling
            if (!controller.getResourceController().getTempRes().isEmpty()) {
                throw new CannotEndTurnException("There are still resources to be placed");
            }
            if (!testing && !this.mainActionDone) {
                throw new MainActionException("You have to perform an action before ending the turn");
            }
            this.mainActionDone = false;
            controller.getGame().nextTurn();
            controller.resetException();
        } catch (CannotEndTurnException | NegativeResAmountException | InvalidKeyException | MainActionException e) {
            controller.setException(e);
            controller.getGame().updateClientError(controller.getClientError());
        } finally {
            controller.getGame().notifyEndOfUpdates();
        }
    }

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
        } catch (TooManyLeaderAbilitiesException | CostNotMatchingException | InvalidLayerNumberException | NoLeaderAbilitiesException | NegativeResAmountException | InvalidKeyException | LeaderAbilityAlreadyActive e) {
            controller.setException(e);
            game.updateClientError(controller.getClientError());
        } finally {
            game.notifyEndOfUpdates();
        }
    }

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

    public void activateProduction(Resource fromDepot, Resource fromStrongbox, List<Integer> numSlots) {

        Player activePlayer = controller.getGame().getActivePlayer();
        PersonalBoard personalBoard = activePlayer.getPersonalBoard();
        List<DevelopmentCard> devCards = new ArrayList<>();
        Resource total = new Resource(0,0,0,0);
        Depot depot = personalBoard.getDepot();
        Strongbox strongbox = personalBoard.getStrongbox();
        DevelopmentCard card;
        try {
            if(this.mainActionDone) throw new MainActionException();
            for(Integer i : numSlots){
                card = personalBoard.getSlot(i).getTopCard();
                devCards.add(card);
                total = total.sum(card.getProdPower().getInput());
            }
            BasicStrategies.checkAndCompare(depot, strongbox, fromDepot, fromStrongbox, total);
            activePlayer.activateProduction(devCards);
            depot.retrieveRes(fromDepot);
            strongbox.retrieveRes(fromStrongbox);
            //controller.getGame().updateFaithTrack();
            controller.getGame().updateStorages();
            controller.resetException();
            if(!testing) this.mainActionDone = true;
        } catch (CostNotMatchingException | NotEnoughResException | NegativeResAmountException | InvalidKeyException | DevSlotEmptyException | NotEnoughSpaceException | CannotContainFaithException | MainActionException e) {
            controller.setException(e);
            controller.getGame().updateClientError(controller.getClientError());
        } finally {
            controller.getGame().notifyEndOfUpdates();
        }
    }

}