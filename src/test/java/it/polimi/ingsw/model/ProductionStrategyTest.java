package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductionStrategyTest {

    @Test
    public void addTest() throws TooManyLeaderAbilitiesException {
        Player player = new Player( "abc");
        Resource input = new Resource(0,2,3,0);
        Resource output = new Resource(1,1,2,0);
        ProductionPower productionPower = new ProductionPower(input, output);
        ExtraProductionAbility extraProduction = new ExtraProductionAbility(productionPower);

        player.addProductionStrategy(extraProduction);
        player.addProductionStrategy(extraProduction);
        assertThrows(TooManyLeaderAbilitiesException.class, () -> player.addProductionStrategy(extraProduction));
    }

    @Test
    public void standardAbilityTest() throws InvalidResourceException, LayerNotEmptyException, NotEnoughSpaceException, InvalidLayerNumberException, CannotContainFaithException, AlreadyInAnotherLayerException, NegativeResAmountException, CostNotMatchingException, NotEnoughResException {
        Player player = new Player( "abc");
        Depot depot = player.getPersonalBoard().getDepot();
        Strongbox strongbox = player.getPersonalBoard().getStrongbox();

        depot.modifyLayer(2, ResourceType.COIN, 2);
        strongbox.addResources(new Resource(3,3,3,3));

        Resource fromDepot = new Resource(0,2, 0, 0);
        Resource fromStrongbox = new Resource(0,0,0,0);

        player.basicProduction(ResourceType.COIN, ResourceType.COIN, ResourceType.SERVANT, fromDepot, fromStrongbox);
        assertEquals(new Resource(0,0,0,0), depot.queryAllRes());
        assertEquals(new Resource(0,0,0,1), strongbox.queryAllTempRes());

        depot.modifyLayer(2, ResourceType.SERVANT, 2);
        fromDepot = new Resource(0,0,0,1);
        fromStrongbox = new Resource(0,1,0,0);

        player.basicProduction(ResourceType.COIN, ResourceType.SERVANT, ResourceType.SERVANT, fromDepot, fromStrongbox);
        strongbox.transferTempRes();
        assertEquals(new Resource(0,0,0,1), depot.queryAllRes());
        assertEquals(new Resource(3,2,3,5), strongbox.queryAllRes());

        Resource fromDepot2 = new Resource(0,1,0,0);
        Resource fromStrongbox2 = new Resource(1,0,0,0);
        assertThrows(NotEnoughResException.class,
                () -> player.basicProduction(ResourceType.COIN, ResourceType.STONE, ResourceType.SHIELD, fromDepot2, fromStrongbox2));
    }

    @Test
    public void oneAbilityTest() throws TooManyLeaderAbilitiesException, CannotContainFaithException, NegativeResAmountException, InvalidResourceException, LayerNotEmptyException, NotEnoughSpaceException, InvalidLayerNumberException, AlreadyInAnotherLayerException, CostNotMatchingException, InvalidAbilityChoiceException, NoLeaderAbilitiesException, NotEnoughResException {
        Player player = new Player( "abc");
        Resource input = new Resource(0,2,3,0);
        Resource output = new Resource(1,1,2,0);
        ProductionPower productionPower = new ProductionPower(input, output);
        ExtraProductionAbility extraProduction = new ExtraProductionAbility(productionPower);

        Depot depot = player.getPersonalBoard().getDepot();
        Strongbox strongbox = player.getPersonalBoard().getStrongbox();
        Resource fromDepot = new Resource(0,0,3,0);
        Resource fromStrongbox = new Resource(0,2,0,0);

        assertThrows(NoLeaderAbilitiesException.class,
                        () -> player.extraProduction(AbilityChoice.FIRST, fromDepot, fromStrongbox, ResourceType.COIN));

        player.addProductionStrategy(extraProduction);
        strongbox.addResources(new Resource(0, 2, 0, 0));
        depot.modifyLayer(3, ResourceType.SHIELD, 3);

        player.extraProduction(AbilityChoice.FIRST, fromDepot, fromStrongbox, ResourceType.COIN);

        strongbox.transferTempRes();
        assertEquals(new Resource(1,2,2,0), strongbox.queryAllRes());
        assertEquals(new Resource(0,0,0,0), depot.queryAllRes());

        assertThrows(InvalidAbilityChoiceException.class,
                () -> player.extraProduction(AbilityChoice.SECOND, fromDepot, fromStrongbox, ResourceType.COIN));
        assertThrows(InvalidAbilityChoiceException.class,
                () -> player.extraProduction(AbilityChoice.BOTH, fromDepot, fromStrongbox, ResourceType.COIN));
    }

    @Test
    public void twoAbilitiesTest() throws TooManyLeaderAbilitiesException, CostNotMatchingException, InvalidAbilityChoiceException, NotEnoughSpaceException, NoLeaderAbilitiesException, CannotContainFaithException, NotEnoughResException, NegativeResAmountException, InvalidResourceException, LayerNotEmptyException, InvalidLayerNumberException, AlreadyInAnotherLayerException {
        Player player = new Player( "abc");
        Resource input1 = new Resource(0,2,3,0);
        Resource output1 = new Resource(1,1,2,0);
        ProductionPower productionPower1 = new ProductionPower(input1, output1);
        ExtraProductionAbility extraProduction1 = new ExtraProductionAbility(productionPower1);

        Resource input2 = new Resource(0,1,1,3);
        Resource output2 = new Resource(1,0,4,0);
        ProductionPower productionPower2 = new ProductionPower(input2, output2);
        ExtraProductionAbility extraProduction2 = new ExtraProductionAbility(productionPower2);

        player.addProductionStrategy(extraProduction1);
        player.addProductionStrategy(extraProduction2);

        Depot depot = player.getPersonalBoard().getDepot();
        Strongbox strongbox = player.getPersonalBoard().getStrongbox();
        Resource fromDepot = new Resource(0,1,1,0);
        Resource fromStrongbox = new Resource(0,0,0,3);

        depot.modifyLayer(1, ResourceType.COIN, 1);
        depot.modifyLayer(2, ResourceType.SHIELD, 2);
        strongbox.addResources(new Resource(0,1,2,5));

        player.extraProduction(AbilityChoice.SECOND, fromDepot, fromStrongbox, ResourceType.COIN);
        assertEquals(new Resource(0,0,1,0), depot.queryAllRes());
        strongbox.transferTempRes();
        assertEquals(new Resource(1,2,6,2), strongbox.queryAllRes());
    }
}