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

    public void basicProduction(Player player, ResourceType input1, ResourceType input2, ResourceType output, Resource fromDepot, Resource fromStrongbox) {
        try {
            player.basicProduction(input1, input2, output, fromDepot, fromStrongbox);
        } catch (CostNotMatchingException | NotEnoughSpaceException | CannotContainFaithException | NotEnoughResException | NegativeResAmountException | InvalidKeyException e) {
            controller.setException(e);
        }
    }

    public void extraProduction(Player player, AbilityChoice choice, Resource fromDepot, Resource fromStrongbox) {
        try {
            player.extraProduction(choice, fromDepot, fromStrongbox);
        } catch (CostNotMatchingException | InvalidAbilityChoiceException | NotEnoughSpaceException | NoLeaderAbilitiesException | CannotContainFaithException | NotEnoughResException | NegativeResAmountException | InvalidKeyException e) {
            controller.setException(e);
        }
    }

    //Calls the player method and then DepotController to handle the resource placement in the depot
    public void takeResources(Player player, int position, AbilityChoice choice, int amount1, int amount2, List<DepotSetting> settings) {
        try {
            Resource output = player.takeResources(position, choice, amount1, amount2);
            controller.getDepotController().handleDepot(player, output, settings);
        } catch (NegativeResAmountException | InvalidKeyException | InvalidAbilityChoiceException | NoLeaderAbilitiesException | WrongDepotInstructionsException | CostNotMatchingException e) {
            controller.setException(e);
        }
    }

    public void buyDevCard(Player player, int level, CardColor color, int numSlot, AbilityChoice choice, Resource fromDepot, Resource fromStrongbox) {
        try {
            player.buyDevCard(level, color, numSlot, choice, fromDepot, fromStrongbox);
        } catch (CannotContainFaithException | NotEnoughSpaceException | NegativeResAmountException | DeckEmptyException | CostNotMatchingException | NotEnoughResException | InvalidKeyException | NoLeaderAbilitiesException | InvalidAbilityChoiceException | DevSlotEmptyException | InvalidNumSlotException e) {
            controller.setException(e);
        }
    }

    public void activateProduction(Player player, Resource fromDepot, Resource fromStrongbox, List<Integer> numSlots) {
        // TODO: Player player = controller.getGame().getCurrentPlayer()
        PersonalBoard personalBoard = player.getPersonalBoard();
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
            player.activateProduction(devCards);
            depot.retrieveRes(fromDepot);
            strongbox.retrieveRes(fromStrongbox);
        } catch (CostNotMatchingException | NotEnoughResException | NegativeResAmountException | InvalidKeyException | DevSlotEmptyException | NotEnoughSpaceException | CannotContainFaithException e) {
            controller.setException(e);
        }
    }
}