package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ResourceStrategyTest {

    //  PURPLE  PURPLE  YELLOW  YELLOW
    //  GREY  GREY  BLUE  BLUE
    //  WHITE  WHITE  WHITE  WHITE
    //  Remaining marble = RED

    @Test
    void addAbility() throws TooManyLeaderAbilitiesException {
        Player player = new Player( "abc");
        ChangeWhiteMarbles changeWhiteMarbles = new ChangeWhiteMarbles(ResourceType.COIN);

        player.addResourceStrategy(changeWhiteMarbles);
        player.addResourceStrategy(changeWhiteMarbles);
        assertThrows(TooManyLeaderAbilitiesException.class,
                () -> player.addResourceStrategy(changeWhiteMarbles));

    }

    @Test
    void standardTest() throws InvalidAbilityChoiceException, NoLeaderAbilitiesException, NegativeResAmountException, InvalidKeyException, FullCardDeckException, CostNotMatchingException {
        Player player = new Player( "abc");
        Game game = new Game(true);
        player.setGame(game);

        assertThrows(NoLeaderAbilitiesException.class, () -> player.takeResources(3, AbilityChoice.FIRST, 3,5));

        Resource output1 = player.takeResources(1, AbilityChoice.STANDARD, 0, 0);
        Resource check1 = new Resource();
        check1.addResource(ResourceType.STONE, 1);
        check1.addResource(ResourceType.SERVANT, 1);
        assertEquals(check1, output1);
        assertEquals(0, player.getPlayerFaith());

        System.out.println(player.getGame().getMarket().getMarketTray());
    }

    @Test
    void standard2Test() throws InvalidAbilityChoiceException, NoLeaderAbilitiesException, NegativeResAmountException, InvalidKeyException, FullCardDeckException, CostNotMatchingException {
        Player player = new Player( "abc");
        Game game = new Game(true);
        player.setGame(game);

        Resource output2 = player.takeResources(5, AbilityChoice.STANDARD, 2, 3);
        Resource check2 = new Resource();
        check2.addResource(ResourceType.STONE, 2);
        check2.addResource(ResourceType.SHIELD, 2);
        assertEquals(check2, output2);
        assertEquals(0, player.getPlayerFaith());
    }

    @Test
    void faithTest() throws FullCardDeckException, InvalidAbilityChoiceException, NoLeaderAbilitiesException, NegativeResAmountException, InvalidKeyException, CostNotMatchingException {
        Player player = new Player( "abc");
        Game game = new Game(true);
        player.setGame(game);
        player.takeResources(1, AbilityChoice.STANDARD, 0, 0);

        //  PURPLE  GREY  YELLOW  YELLOW
        //  GREY  WHITE  BLUE  BLUE
        //  WHITE  RED  WHITE  WHITE
        //  Remaining marble = PURPLE
        //Resource output = player.takeResources()

        Resource output2 = player.takeResources(1, AbilityChoice.STANDARD, 0, 0);
        Resource check2 = new Resource();
        check2.addResource(ResourceType.STONE, 1);
        assertEquals(check2, output2);
        assertEquals(1, player.getPlayerFaith());
    }

    @Test
    void singleAbilityTest() throws FullCardDeckException, TooManyLeaderAbilitiesException, InvalidAbilityChoiceException, NoLeaderAbilitiesException, NegativeResAmountException, InvalidKeyException, CostNotMatchingException {
        Player player = new Player( "abc");
        Game game = new Game(true);
        player.setGame(game);
        ChangeWhiteMarbles ability1 = new ChangeWhiteMarbles(ResourceType.STONE);
        ChangeWhiteMarbles ability2 = new ChangeWhiteMarbles(ResourceType.FAITH);
        ability1.activate(player);
        ability2.activate(player);

        Resource output = player.takeResources(3, AbilityChoice.SECOND, 0, 1);
        Resource check = new Resource();
        check.addResource(ResourceType.SHIELD, 1);
        check.addResource(ResourceType.COIN, 1);

        assertEquals(check, output);
        assertEquals(1, player.getPlayerFaith());
    }

    @Test
    void doubleAbilityTest() throws FullCardDeckException, TooManyLeaderAbilitiesException, InvalidAbilityChoiceException, NoLeaderAbilitiesException, NegativeResAmountException, InvalidKeyException, CostNotMatchingException {
        Player player = new Player( "abc");
        Game game = new Game(true);
        player.setGame(game);
        ChangeWhiteMarbles ability1 = new ChangeWhiteMarbles(ResourceType.COIN);
        ChangeWhiteMarbles ability2 = new ChangeWhiteMarbles(ResourceType.SHIELD);
        ability1.activate(player);
        ability2.activate(player);

        Resource output = player.takeResources(4, AbilityChoice.BOTH, 1, 3);
        Resource check = new Resource();
        check.addResource(ResourceType.COIN, 1);
        check.addResource(ResourceType.SHIELD, 3);

        assertEquals(check, output);
        assertEquals(0, player.getPlayerFaith());
    }
}