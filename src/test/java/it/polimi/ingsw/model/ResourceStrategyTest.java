package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.FullCardDeckException;
import it.polimi.ingsw.exceptions.InvalidKeyException;
import it.polimi.ingsw.exceptions.NegativeResAmountException;
import it.polimi.ingsw.exceptions.TooManyLeaderAbilitiesException;
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
        ChangeWhiteMarblesAbility changeWhiteMarbles = new ChangeWhiteMarblesAbility(ResourceType.COIN);

        player.addResourceStrategy(changeWhiteMarbles);
        player.addResourceStrategy(changeWhiteMarbles);
        assertThrows(TooManyLeaderAbilitiesException.class,
                () -> player.addResourceStrategy(changeWhiteMarbles));

    }

    @Test
    void standardTest() throws NegativeResAmountException, InvalidKeyException, FullCardDeckException {
        Player player = new Player( "abc");
        Game game = new MultiGame(true);
        player.setGame(game);

        //assertThrows(NoLeaderAbilitiesException.class, () -> player.insertMarble(3, AbilityChoice.FIRST, 3,5));

        Resource output1 = player.insertMarble(1);
        assertEquals(new Resource(1,0,0,1), output1);
        assertEquals(0, player.getPlayerFaith());
    }

    @Test
    void standard2Test() throws NegativeResAmountException, InvalidKeyException, FullCardDeckException {
        Player player = new Player( "abc");
        Game game = new MultiGame(true);
        player.setGame(game);

        Resource output2 = player.insertMarble(5);
        assertEquals(new Resource(2,0,2,0), output2);
        assertEquals(0, player.getPlayerFaith());
    }

    @Test
    void faithTest() throws FullCardDeckException, NegativeResAmountException, InvalidKeyException {
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
    void singleAbilityTest() throws FullCardDeckException, TooManyLeaderAbilitiesException, NegativeResAmountException, InvalidKeyException {
        Player player = new Player( "abc");
        Game game = new MultiGame(true);
        player.setGame(game);
        ChangeWhiteMarblesAbility ability1 = new ChangeWhiteMarblesAbility(ResourceType.STONE);
        ability1.activate(player);

        Resource output = player.insertMarble(3);
        Resource check = new Resource(0,0,0,0);
        check.modifyValue(ResourceType.SHIELD, 1);
        check.modifyValue(ResourceType.COIN, 1);
        check.modifyValue(ResourceType.STONE, 1);

        //  PURPLE  PURPLE  YELLOW  YELLOW
        //  GREY  GREY  BLUE  BLUE
        //  WHITE  WHITE  WHITE  WHITE
        //  Remaining marble = RED
        assertEquals(check, output);
        assertEquals(0, player.getPlayerFaith());
    }

    @Test
    void doubleAbilityTest() throws FullCardDeckException, TooManyLeaderAbilitiesException, NegativeResAmountException, InvalidKeyException {
        Player player = new Player( "abc");
        Game game = new MultiGame(true);
        player.setGame(game);
        ChangeWhiteMarblesAbility ability1 = new ChangeWhiteMarblesAbility(ResourceType.COIN);
        ChangeWhiteMarblesAbility ability2 = new ChangeWhiteMarblesAbility(ResourceType.SHIELD);
        ability1.activate(player);
        ability2.activate(player);

        Resource output = player.insertMarble(4);
        Resource check = new Resource(0,0,0,0);
        check.addResource(ResourceType.ALL, 4);
        //  PURPLE  PURPLE  YELLOW  YELLOW
        //  GREY  GREY  BLUE  BLUE
        //  WHITE  WHITE  WHITE  WHITE
        //  Remaining marble = RED
        assertEquals(check, output);
        assertEquals(0, player.getPlayerFaith());
    }
}