package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.FullCardDeckException;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class GameTest {

    @Test
    public void gameTest() {
        //TODO: implement here
    }

    @Test
    public void calculatePointsTest() {
        //TODO: implement here
    }

    @Test
    public void giveLeaderCardsTest() {
        //TODO: implement here
    }

    @RepeatedTest(5)
    public void giveInkwellTest() throws FullCardDeckException {
        int playerAmount = 3;
        Player player1 = new Player("a");
        Player player2 = new Player("b");
        Player player3 = new Player("c");
        //Player player4 = new Player("d");
        ArrayList<Player> playersList = new ArrayList<>();
        playersList.add(player1);
        playersList.add(player2);
        playersList.add(player3);
        //playersList.add(player4);
        Game game = new Game(playerAmount, playersList);

        int true_amount = 0, false_amount = 0;
        for(Player p: game.getPlayers()) {
            if(p.getInkwell()) true_amount++;
            else false_amount++;
        }
        assertEquals(1, true_amount);
        assertEquals(playerAmount - 1, false_amount);
    }

    @Test
    public void giveResourcesTest() {
        //TODO: implement here
    }

    @RepeatedTest(5)
    public void nextTurnTest() throws FullCardDeckException {
        int playerAmount = 4;
        Player player1 = new Player("a");
        Player player2 = new Player("b");
        Player player3 = new Player("c");
        Player player4 = new Player("d");
        ArrayList<Player> playersList = new ArrayList<>();
        playersList.add(player1);
        playersList.add(player2);
        playersList.add(player3);
        playersList.add(player4);
        Game game = new Game(playerAmount, playersList);
        Player lastActive = game.getActivePlayer();
        int lastIndex = playersList.indexOf(lastActive);

        game.nextTurn();
        assertNotEquals(lastActive, game.getActivePlayer());
        assertEquals((lastIndex + 1) % playerAmount, playersList.indexOf(game.getActivePlayer()));
    }
}
