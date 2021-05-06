package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameTest {

    @Test
    public void emptySoloTest() throws FullCardDeckException, NegativeResAmountException, InvalidKeyException {
        Game game = new MultiGame(true);
        Map<Player, Integer> finalScores = game.computeFinalScore();
        for(Player p: finalScores.keySet())
            assertEquals(0, finalScores.get(p));
    }

    @Test
    public void scoreMultiTest() throws FullCardDeckException, NegativeResAmountException, InvalidKeyException, InvalidResourceException, LayerNotEmptyException, NotEnoughSpaceException, InvalidLayerNumberException, CannotContainFaithException, AlreadyInAnotherLayerException {
        Player player1 = new Player("a");
        player1.addVictoryPoints(15);
        Player player2 = new Player("b");
        Depot depot2 = player2.getPersonalBoard().getDepot();
        depot2.modifyLayer(1, ResourceType.COIN, 1);
        depot2.modifyLayer(2, ResourceType.SHIELD, 2);
        Player player3 = new Player("c");
        player3.addVictoryPoints(2);
        Depot depot3 = player3.getPersonalBoard().getDepot();
        depot3.modifyLayer(1, ResourceType.COIN, 1);
        depot3.modifyLayer(2, ResourceType.SHIELD, 2);
        depot3.modifyLayer(3, ResourceType.SERVANT, 3);
        Player player4 = new Player("d");
        Strongbox strongbox4 = player4.getPersonalBoard().getStrongbox();
        strongbox4.addResources(new Resource(5,10,4,3));

        List<Player> playerList = new ArrayList<>();
        playerList.add(player1);
        playerList.add(player2);
        playerList.add(player3);
        playerList.add(player4);
        Game game = new MultiGame(4, playerList);

        Map<Player, Integer> finalScores = game.computeFinalScore();
        assertEquals(15, finalScores.get(player1));
        assertEquals(0, finalScores.get(player2));
        assertEquals(3, finalScores.get(player3));
        assertEquals(4, finalScores.get(player4));
    }

    /*//TODO:
    @Test
    public void scoreSoloTest() throws FullCardDeckException, InvalidResourceException, LayerNotEmptyException, NotEnoughSpaceException, InvalidLayerNumberException, CannotContainFaithException, AlreadyInAnotherLayerException, NegativeResAmountException, TooManyLeaderAbilitiesException, CostNotMatchingException, NoLeaderAbilitiesException, InvalidKeyException, LeaderAbilityAlreadyActive {
        Player player = new Player("a");
        Game game = new SoloGame(player);
        player.setGame(game);
        Depot depot = player.getPersonalBoard().getDepot();
        Strongbox strongbox = player.getPersonalBoard().getStrongbox();

        player.addVictoryPoints(3);
        depot.modifyLayer(3, ResourceType.STONE, 2);
        strongbox.addResources(new Resource(5, 4, 3, 2));
        LeaderAbility ability = new Discount(ResourceType.SHIELD, 1);
        Resource cost = new Resource(2,2,2,2);
        LeaderCard leader = new ResLeaderCard(5,ability, cost);
        player.addLeaderCard(leader);
        player.useLeader(0, LeaderAction.USE_ABILITY);

        Map<Player, Integer> finalScores = game.computeFinalScore();
        assertEquals(11, finalScores.get(player));



    }*/
}

