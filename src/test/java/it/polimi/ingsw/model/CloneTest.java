package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.FullCardDeckException;
import it.polimi.ingsw.exceptions.NegativeResAmountException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class CloneTest {

    @Test
    public void abstractLeaderAbilityCloneTest() {
        LeaderAbility ability1 = new ChangeWhiteMarblesAbility(ResourceType.STONE);
        LeaderAbility ability2 = ability1.clone();
        assertNotEquals(ability1, ability2);
    }

    @Test
    public void leaderAbilityCloneTest() {
        ExtraSlotAbility ability1 = new ExtraSlotAbility(ResourceType.STONE);
        ExtraSlotAbility ability2 = (ExtraSlotAbility) ability1.clone();
        assertNotEquals(ability1, ability2);
        assertEquals(ability1.getResource(), ability2.getResource());
    }

    @Test
    public void actionTokenCloneTest() throws FullCardDeckException {
        SoloGame game = new SoloGame(true);
        DiscardDevCardsToken token1 = new DiscardDevCardsToken(game, CardColor.GREEN);
        DiscardDevCardsToken token2 = (DiscardDevCardsToken) token1.clone();
        assertNotEquals(token1, token2);
        //Correct: deep copy of game is not made, because it is transient
        assertEquals(token1.getGame(), token2.getGame());
        assertEquals(token1.getID(), token2.getID());
        assertEquals(token1.toString(), token2.toString());
    }

    @Test
    public void faithTrackCloneTest() {
        FaithTrack track1 = new FaithTrack();
        FaithTrack track2 = track1.clone();
        assertNotEquals(track1, track2);
        assertEquals(track1.getPlayerFaith(), track2.getPlayerFaith());
        assertEquals(track1.getBlackCrossPosition(), track2.getBlackCrossPosition());
        assertEquals(track1.toString(), track2.toString());
        assertNotEquals(track1.getPopeFavorCards(), track2.getPopeFavorCards());
    }

    @Test
    public void resLeaderCardCloneTest() {
        Resource cost = new Resource(1,2,3,0);
        LeaderAbility ability = new ChangeWhiteMarblesAbility(ResourceType.STONE);
        ResLeaderCard card1 = new ResLeaderCard(1, ability, cost);
        ResLeaderCard card2 = (ResLeaderCard) card1.clone();
        assertNotEquals(card1, card2);
        assertNotEquals(card1.getSpecialAbility(), card2.getSpecialAbility());
        assertEquals(card1.getId(), card2.getId());
        assertEquals(card1.toString(), card2.toString());
        assertEquals(card1.getVictoryPoints(), card2.getVictoryPoints());
     }

    @Test
    public void devLeaderCardCloneTest() {
         LeaderAbility ability = new ChangeWhiteMarblesAbility(ResourceType.STONE);
        List<LeaderDevCost> requires = new ArrayList<>();
        requires.add(new LeaderDevCost(CardColor.GREEN, 1, 1));
        DevLeaderCard card1 = new DevLeaderCard(1, ability, requires);
        DevLeaderCard card2 = (DevLeaderCard) card1.clone();
        assertNotEquals(card1, card2);
        assertNotEquals(card1.getSpecialAbility(), card2.getSpecialAbility());
        assertEquals(card1.getId(), card2.getId());
        assertEquals(card1.getVictoryPoints(), card2.getVictoryPoints());
        assertEquals(card1.toString(), card2.toString());
        assertEquals(card1.getImg(), card2.getImg());
    }

    @Test
    public void prodPowerCloneTest() throws NegativeResAmountException {
        Resource input = new Resource();
        input.addResource(ResourceType.SHIELD, 2);
        Resource output = new Resource();
        output.addResource(ResourceType.COIN, 5);
        ProductionPower prod1 = new ProductionPower(input, output);
        ProductionPower prod2 = prod1.clone();
        assertNotEquals(prod1, prod2);
        //They are equal because Resource class has an overridden equals()
        assertEquals(prod1.getInput(), prod2.getInput());
        assertEquals(prod1.getOutput(), prod2.getOutput());
        assertEquals(prod1.toString(), prod2.toString());
    }
}
