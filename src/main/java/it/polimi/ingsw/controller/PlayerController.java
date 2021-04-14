package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.*;

import java.util.List;

public class PlayerController {

    public void basicProduction(Player player, ResourceType input1, ResourceType input2, ResourceType output, Resource fromDepot, Resource fromStrongbox) {
        try {
            player.basicProduction(input1, input2, output, fromDepot, fromStrongbox);
        } catch (CostNotMatchingException e) {
            e.printStackTrace();
        } catch (NotEnoughSpaceException e) {
            e.printStackTrace();
        } catch (CannotContainFaithException e) {
            e.printStackTrace();
        } catch (NotEnoughResException e) {
            e.printStackTrace();
        } catch (NegativeResAmountException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
    }

    public void extraProduction(Player player, AbilityChoice choice, Resource fromDepot, Resource fromStrongbox) {
        try {
            player.extraProduction(choice, fromDepot, fromStrongbox);
        } catch (CostNotMatchingException e) {
            e.printStackTrace();
        } catch (InvalidAbilityChoiceException e) {
            e.printStackTrace();
        } catch (NotEnoughSpaceException e) {
            e.printStackTrace();
        } catch (NoLeaderAbilitiesException e) {
            e.printStackTrace();
        } catch (CannotContainFaithException e) {
            e.printStackTrace();
        } catch (NotEnoughResException e) {
            e.printStackTrace();
        } catch (NegativeResAmountException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
    }

    //Calls the player method and then DepotController to handle the resource placement in the depot
    public void takeResources(Player player, int position, AbilityChoice choice, int amount1, int amount2, List<DepotSetting> settings) {
        try {
            Resource output = player.takeResources(position, choice, amount1, amount2);
            DepotController.handleDepot(player, output, settings);
        } catch (NegativeResAmountException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidChoiceException e) {
            e.printStackTrace();
        } catch (InvalidAbilityChoiceException e) {
            e.printStackTrace();
        } catch (NoLeaderAbilitiesException e) {
            e.printStackTrace();
        } catch (WrongDepotInstructionsException e) {
            e.printStackTrace();
        }
    }

    public void buyDevCard(Player player, int level, CardColor color, AbilityChoice choice, Resource fromDepot, Resource fromStrongbox) {
        try {
            player.buyDevCard(level, color, choice, fromDepot, fromStrongbox);
        } catch (CannotContainFaithException e) {
            e.printStackTrace();
        } catch (NotEnoughSpaceException e) {
            e.printStackTrace();
        } catch (NegativeResAmountException e) {
            e.printStackTrace();
        } catch (DeckEmptyException e) {
            e.printStackTrace();
        } catch (CostNotMatchingException e) {
            e.printStackTrace();
        } catch (NotEnoughResException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoLeaderAbilitiesException e) {
            e.printStackTrace();
        } catch (InvalidAbilityChoiceException e) {
            e.printStackTrace();
        }
    }
}