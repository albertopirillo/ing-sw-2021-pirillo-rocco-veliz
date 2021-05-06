package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameTest {

    @Test
    public void emptyScoreTest() throws FullCardDeckException, NegativeResAmountException, InvalidKeyException {
        Game game = new MultiGame(true);
        Map<Player, Integer> finalScores = game.computeFinalScore();
        for(Player p: finalScores.keySet())
            assertEquals(0, finalScores.get(p));
    }

    @Test
    public void scoreMultiTest() throws FullCardDeckException, NegativeResAmountException, InvalidKeyException, InvalidResourceException, LayerNotEmptyException, NotEnoughSpaceException, InvalidLayerNumberException, CannotContainFaithException, AlreadyInAnotherLayerException {
        Player player1 = new Player("a");
        player1.addVictoryPoints(15);
        player1.addPlayerFaith(18);
        Player player2 = new Player("b");
        Depot depot2 = player2.getPersonalBoard().getDepot();
        depot2.modifyLayer(1, ResourceType.COIN, 1);
        depot2.modifyLayer(2, ResourceType.SHIELD, 2);
        Player player3 = new Player("c");
        player3.addVictoryPoints(2);
        player3.addPlayerFaith(0);
        Depot depot3 = player3.getPersonalBoard().getDepot();
        depot3.modifyLayer(1, ResourceType.COIN, 1);
        depot3.modifyLayer(2, ResourceType.SHIELD, 2);
        depot3.modifyLayer(3, ResourceType.SERVANT, 3);
        Player player4 = new Player("d");
        player4.addPlayerFaith(7);
        Strongbox strongbox4 = player4.getPersonalBoard().getStrongbox();
        strongbox4.addResources(new Resource(5,10,4,3));

        List<Player> playerList = new ArrayList<>();
        playerList.add(player1);
        playerList.add(player2);
        playerList.add(player3);
        playerList.add(player4);
        Game game = new MultiGame(4, playerList);

        Map<Player, Integer> finalScores = game.computeFinalScore();
        assertEquals(27, finalScores.get(player1));
        assertEquals(0, finalScores.get(player2));
        assertEquals(3, finalScores.get(player3));
        assertEquals(6, finalScores.get(player4));
    }

    @Test
    public void finalPosTest() throws FullCardDeckException {
        Game game = new MultiGame(true);
        assertEquals(0, game.finalPositionVP(0));
        assertEquals(0, game.finalPositionVP(2));
        assertEquals(1, game.finalPositionVP(3));
        assertEquals(1, game.finalPositionVP(5));
        assertEquals(2, game.finalPositionVP(6));
        assertEquals(4, game.finalPositionVP(9));
        assertEquals(4, game.finalPositionVP(11));
        assertEquals(6, game.finalPositionVP(12));
        assertEquals(9, game.finalPositionVP(15));
        assertEquals(12, game.finalPositionVP(18));
        assertEquals(16, game.finalPositionVP(21));
        assertEquals(16, game.finalPositionVP(22));
        assertEquals(20, game.finalPositionVP(24));
    }

    @Test
    public void scoreSoloTest() throws FullCardDeckException, InvalidResourceException, LayerNotEmptyException, NotEnoughSpaceException, InvalidLayerNumberException, CannotContainFaithException, AlreadyInAnotherLayerException, NegativeResAmountException, TooManyLeaderAbilitiesException, CostNotMatchingException, NoLeaderAbilitiesException, InvalidKeyException, LeaderAbilityAlreadyActive {
        Player player = new Player("a");
        Game game = new SoloGame(player);
        player.setGame(game);
        Depot depot = player.getPersonalBoard().getDepot();
        Strongbox strongbox = player.getPersonalBoard().getStrongbox();

        player.addVictoryPoints(3); //Gives 3 points
        player.addPlayerFaith(14); //Gives 6 points

        //Gives 3 points
        depot.modifyLayer(3, ResourceType.STONE, 2);
        strongbox.addResources(new Resource(5, 4, 3, 2));

        LeaderAbility ability = new Discount(ResourceType.SHIELD, 1);
        Resource cost = new Resource(2,2,2,2);
        LeaderCard leader = new ResLeaderCard(5, ability, cost);
        player.addLeaderCard(leader);
        player.useLeader(0, LeaderAction.USE_ABILITY); //Gives 5 points

        Map<Player, Integer> finalScores = game.computeFinalScore();
        assertEquals(17, finalScores.get(player));
    }
}

