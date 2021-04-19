package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.*;

import java.util.List;

public class PlayerController {

    private final Controller controller;

    public PlayerController(Controller controller) {
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

    public void buyDevCard(Player player, int level, CardColor color, AbilityChoice choice, Resource fromDepot, Resource fromStrongbox) {
        try {
            player.buyDevCard(level, color, choice, fromDepot, fromStrongbox);
        } catch (CannotContainFaithException | NotEnoughSpaceException | NegativeResAmountException | DeckEmptyException | CostNotMatchingException | NotEnoughResException | InvalidKeyException | NoLeaderAbilitiesException | InvalidAbilityChoiceException e) {
            controller.setException(e);
        }
    }
}