package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.DepotSetting;

import java.util.ArrayList;
import java.util.List;

public class PlayerController {

    private final MasterController controller;

    public PlayerController(MasterController controller) {
        this.controller = controller;
    }

    public void basicProduction(ResourceType input1, ResourceType input2, ResourceType output, Resource fromDepot, Resource fromStrongbox) {
        try {
            Player activePlayer = controller.getGame().getActivePlayer();
            activePlayer.basicProduction(input1, input2, output, fromDepot, fromStrongbox);
            controller.getGame().updateDevSlots();
            controller.getGame().showFaithTrack();
        } catch (CostNotMatchingException | NotEnoughSpaceException | CannotContainFaithException | NotEnoughResException | NegativeResAmountException | InvalidKeyException e) {
            controller.setException(e);
            controller.getGame().showClientError(controller.getClientError());
        } finally {
            controller.getGame().notifyEndOfUpdates();
        }
    }

    public void extraProduction(AbilityChoice choice, Resource fromDepot, Resource fromStrongbox, ResourceType res) {
        try {
            Player activePlayer = controller.getGame().getActivePlayer();
            activePlayer.extraProduction(choice, fromDepot, fromStrongbox, res);
            controller.getGame().updateDevSlots();
            controller.getGame().showFaithTrack();
        } catch (CostNotMatchingException | InvalidAbilityChoiceException | NotEnoughSpaceException | NoLeaderAbilitiesException | CannotContainFaithException | NotEnoughResException | NegativeResAmountException | InvalidKeyException e) {
            controller.setException(e);
            controller.getGame().showClientError(controller.getClientError());
        } finally {
            controller.getGame().notifyEndOfUpdates();
        }
    }

    //Calls the player method and then DepotController to handle the resource placement in the depot
    public void insertMarble(int position, AbilityChoice choice, int amount1, int amount2) {
        try {
            Player activePlayer = controller.getGame().getActivePlayer();
            Resource output = activePlayer.insertMarble(position, choice, amount1, amount2);
            ResourceController resourceController = controller.getResourceController();
            resourceController.getTempRes().setToHandle(output);
            controller.getGame().updateMarketTray();
            controller.getGame().updateTempResource(resourceController.getTempRes());
        } catch (NegativeResAmountException | InvalidKeyException | InvalidAbilityChoiceException | NoLeaderAbilitiesException | CostNotMatchingException e) {
            controller.setException(e);
            controller.getGame().showClientError(controller.getClientError());
        } finally {
            controller.getGame().notifyEndOfUpdates();
        }
    }

    public void buyDevCard(int level, CardColor color, int numSlot, AbilityChoice choice, Resource fromDepot, Resource fromStrongbox) {
        try {
            Player activePlayer = controller.getGame().getActivePlayer();
            activePlayer.buyDevCard(level, color, numSlot, choice, fromDepot, fromStrongbox);
            controller.getGame().updateMarket();
            controller.getGame().updateDevSlots();
            controller.getGame().updateStorage();
        } catch (CannotContainFaithException | NotEnoughSpaceException | NegativeResAmountException | DeckEmptyException | CostNotMatchingException | NotEnoughResException | InvalidKeyException | NoLeaderAbilitiesException | InvalidAbilityChoiceException | DevSlotEmptyException | InvalidNumSlotException e) {
            controller.setException(e);
            controller.getGame().showClientError(controller.getClientError());
        } finally {
            controller.getGame().notifyEndOfUpdates();
        }
    }
    
    public void placeResource(Resource toDiscard, List<DepotSetting> toPlace) {
        try {
            controller.getResourceController().handleResource(toDiscard, toPlace);
            controller.getGame().updateStorage();
            controller.getGame().showFaithTrack();
        } catch (NotEnoughResException | NotEnoughSpaceException | CannotContainFaithException | NegativeResAmountException | InvalidKeyException | InvalidResourceException | WrongDepotInstructionsException | LayerNotEmptyException | InvalidLayerNumberException | AlreadyInAnotherLayerException e) {
            controller.setException(e);
            controller.getGame().showClientError(controller.getClientError());
        } finally {
            controller.getGame().notifyEndOfUpdates();
        }
    }

    public void endTurn() {
        try { //Check exception and resource handling
            if (!controller.getResourceController().getTempRes().isEmpty()) throw new CannotEndTurnException("There are still resources to be placed");
            controller.getGame().nextTurn();
            controller.getGame().updateMarketTray();
            controller.getGame().updateMarket();
        } catch (CannotEndTurnException | NegativeResAmountException | InvalidKeyException e) {
            controller.setException(e);
            controller.getGame().showClientError(controller.getClientError());
        } finally {
            controller.getGame().notifyEndOfUpdates();
        }
    }

    public void useLeader(int index, LeaderAction choice) {
        Game game = controller.getGame();
        try {
            Player activePlayer = game.getActivePlayer();
            activePlayer.useLeader(index, choice);
            game.showLeaderCards();
            if (choice == LeaderAction.DISCARD) game.showFaithTrack();
        } catch (TooManyLeaderAbilitiesException | CostNotMatchingException | InvalidLayerNumberException | NoLeaderAbilitiesException | NegativeResAmountException | InvalidKeyException | LeaderAbilityAlreadyActive e) {
            controller.setException(e);
            game.showClientError(controller.getClientError());
        } finally {
            game.notifyEndOfUpdates();
        }
    }

    public void reorderDepot(int fromLayer, int toLayer, int amount) {
        try {
            Player activePlayer = controller.getGame().getActivePlayer();
            activePlayer.reorderDepot(fromLayer, toLayer, amount);
            controller.getGame().updateStorage();
        } catch (InvalidResourceException | LayerNotEmptyException | NotEnoughSpaceException | InvalidLayerNumberException | CannotContainFaithException | NotEnoughResException | NegativeResAmountException e) {
            controller.setException(e);
            controller.getGame().showClientError(controller.getClientError());
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
            for(Integer i : numSlots){
                card = personalBoard.getSlot(i).getTopCard();
                devCards.add(card);
                total = total.sum(card.getProdPower().getInput());
            }
            BasicStrategies.checkAndCompare(depot, strongbox, fromDepot, fromStrongbox, total);
            activePlayer.activateProduction(devCards);
            depot.retrieveRes(fromDepot);
            strongbox.retrieveRes(fromStrongbox);
            controller.getGame().updateStorage();
            controller.getGame().showFaithTrack();
        } catch (CostNotMatchingException | NotEnoughResException | NegativeResAmountException | InvalidKeyException | DevSlotEmptyException | NotEnoughSpaceException | CannotContainFaithException e) {
            controller.setException(e);
            controller.getGame().showClientError(controller.getClientError());
        } finally {
            controller.getGame().notifyEndOfUpdates();
        }
    }
}