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
        Game game = new MultiGame(true);
        player.setGame(game);

        //assertThrows(NoLeaderAbilitiesException.class, () -> player.insertMarble(3, AbilityChoice.FIRST, 3,5));

        Resource output1 = player.insertMarble(1);
        assertEquals(new Resource(1,0,0,1), output1);
        assertEquals(0, player.getPlayerFaith());
    }

    @Test
    void standard2Test() throws InvalidAbilityChoiceException, NoLeaderAbilitiesException, NegativeResAmountException, InvalidKeyException, FullCardDeckException, CostNotMatchingException {
        Player player = new Player( "abc");
        Game game = new MultiGame(true);
        player.setGame(game);

        Resource output2 = player.insertMarble(5);
        assertEquals(new Resource(2,0,2,0), output2);
        assertEquals(0, player.getPlayerFaith());
    }

    @Test
    void faithTest() throws FullCardDeckException, InvalidAbilityChoiceException, NoLeaderAbilitiesException, NegativeResAmountException, InvalidKeyException, CostNotMatchingException {
        Player player = new Player( "abc");
        Game game = new MultiGame(true);
        player.setGame(game);
        player.insertMarble(1);

        //  PURPLE  GREY  YELLOW  YELLOW
        //  GREY  WHITE  BLUE  BLUE
        //  WHITE  RED  WHITE  WHITE
        //  Remaining marble = PURPLE
        //Resource output = player.takeResources()

        Resource output2 = player.insertMarble(1);
        assertEquals(new Resource(1,0,0,0), output2);
        assertEquals(1, player.getPlayerFaith());
    }

    @Test
    void singleAbilityTest() throws FullCardDeckException, TooManyLeaderAbilitiesException, InvalidAbilityChoiceException, NoLeaderAbilitiesException, NegativeResAmountException, InvalidKeyException, CostNotMatchingException {
        Player player = new Player( "abc");
        Game game = new MultiGame(true);
        player.setGame(game);
        ChangeWhiteMarbles ability1 = new ChangeWhiteMarbles(ResourceType.STONE);
        ability1.activate(player);

        Resource output = player.insertMarble(3);
        Resource check = new Resource();
        check.addResource(ResourceType.SHIELD, 1);
        check.addResource(ResourceType.COIN, 1);
        check.addResource(ResourceType.STONE, 1);

        //  PURPLE  PURPLE  YELLOW  YELLOW
        //  GREY  GREY  BLUE  BLUE
        //  WHITE  WHITE  WHITE  WHITE
        //  Remaining marble = RED
        assertEquals(check, output);
        assertEquals(0, player.getPlayerFaith());
    }

    @Test
    void doubleAbilityTest() throws FullCardDeckException, TooManyLeaderAbilitiesException, InvalidAbilityChoiceException, NoLeaderAbilitiesException, NegativeResAmountException, InvalidKeyException, CostNotMatchingException {
        Player player = new Player( "abc");
        Game game = new MultiGame(true);
        player.setGame(game);
        ChangeWhiteMarbles ability1 = new ChangeWhiteMarbles(ResourceType.COIN);
        ChangeWhiteMarbles ability2 = new ChangeWhiteMarbles(ResourceType.SHIELD);
        ability1.activate(player);
        ability2.activate(player);

        Resource output = player.insertMarble(4);
        Resource check = new Resource();
        check.addResource(ResourceType.ALL, 4);
        //  PURPLE  PURPLE  YELLOW  YELLOW
        //  GREY  GREY  BLUE  BLUE
        //  WHITE  WHITE  WHITE  WHITE
        //  Remaining marble = RED
        assertEquals(check, output);
        assertEquals(0, player.getPlayerFaith());
    }
}