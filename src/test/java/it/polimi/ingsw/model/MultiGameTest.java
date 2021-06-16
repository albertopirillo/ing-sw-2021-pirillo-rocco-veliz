package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.FullCardDeckException;
import it.polimi.ingsw.exceptions.NegativeResAmountException;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class MultiGameTest {

    @Test
    public void computeRanksTest() throws FullCardDeckException {
        MultiGame game = new MultiGame(true);
        Map<Player, Integer> map = new HashMap<>();
        Player player1 = new Player("a");
        Player player2 = new Player("b");
        Player player3 = new Player("c");
        Player player4 = new Player("d");
        map.put(player1, 15);
        map.put(player4, 0);
        map.put(player3, 20);
        map.put(player2, 10);

        List<Player> sortedPlayer = game.computeRanks(map);
        assertEquals(player3, sortedPlayer.get(0));
        assertEquals(player1, sortedPlayer.get(1));
        assertEquals(player2, sortedPlayer.get(2));
        assertEquals(player4, sortedPlayer.get(3));
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
        Game game = new MultiGame(playerAmount, playersList);

        int true_amount = 0, false_amount = 0;
        for(Player p: game.getPlayersList()) {
            if(p.getInkwell()) true_amount++;
            else false_amount++;
        }
        assertEquals(1, true_amount);
        assertEquals(playerAmount - 1, false_amount);
    }

    @RepeatedTest(5)
    public void nextTurnTest() throws FullCardDeckException, NegativeResAmountException {
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
        Game game = new MultiGame(playerAmount, playersList);
        Player lastActive = game.getActivePlayer();
        int lastIndex = playersList.indexOf(lastActive);

        game.nextTurn();
        assertNotEquals(lastActive, game.getActivePlayer());
        assertEquals((lastIndex + 1) % playerAmount, playersList.indexOf(game.getActivePlayer()));
    }
}
