package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.DevSlotEmptyException;
import it.polimi.ingsw.exceptions.InvalidNumSlotException;
import it.polimi.ingsw.exceptions.NegativeResAmountException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DevLeaderCardTest {

    @Test
    void canBeActivated() throws NegativeResAmountException, InvalidNumSlotException, DevSlotEmptyException {
        //stub
        Resource r = new Resource(0,0,1,0);
        Resource r1 = new Resource(1,0,0,0);

        //Test require amount = 1
        ArrayList<LeaderDevCost> requires1 = new ArrayList<>();
        requires1.add(new LeaderDevCost(CardColor.YELLOW, 2, 1));
        requires1.add(new LeaderDevCost(CardColor.BLUE, 1, 1));

        //leaders card
        LeaderCard leaderCard = new DevLeaderCard(4, new ExtraProductionAbility(
                new ProductionPower(r, r1)),
                requires1);

        //player stub
        //stub devCards
        Resource res1 = new Resource(1,2,3,4);
        Resource res2 = new Resource(1,2,3,1);
        DevelopmentCard dev1 = new DevelopmentCard(10, res1, CardColor.BLUE, 1, new ProductionPower(res1, res2), "");
        DevelopmentCard dev2 = new DevelopmentCard(10, res1, CardColor.BLUE, 2, new ProductionPower(res1, res2), "");
        DevelopmentCard dev3 = new DevelopmentCard(11, res2, CardColor.BLUE, 1, new ProductionPower(res2, res1), "");
        DevelopmentCard dev4 = new DevelopmentCard(11, res2, CardColor.YELLOW, 2, new ProductionPower(res2, res1), "");
        DevelopmentCard dev5 = new DevelopmentCard(11, res2, CardColor.GREEN, 3, new ProductionPower(res2, res1), "");
        DevelopmentCard dev6 = new DevelopmentCard(11, res2, CardColor.GREEN, 3, new ProductionPower(res1, res2), "");
        DevelopmentCard dev7 = new DevelopmentCard(11, res2, CardColor.YELLOW, 1, new ProductionPower(res2, res1), "");

        Player player = new Player("pippo");
        PersonalBoard p = player.getPersonalBoard();
        //player has not devCards
        assertFalse(leaderCard.canBeActivated(player));
        //add correctly devCards
        p.addDevCard(dev1, 0);
        p.addDevCard(dev2,0);
        p.addDevCard(dev3,2);
        assertFalse(leaderCard.canBeActivated(player));
        p.addDevCard(dev4,2);
        assertTrue(leaderCard.canBeActivated(player));

        //Test require amount = 2
        ArrayList<LeaderDevCost> requires2 = new ArrayList<>();
        requires2.add(new LeaderDevCost(CardColor.GREEN, 3, 2));
        LeaderCard leader2 = new DevLeaderCard(4, new ExtraProductionAbility(
                new ProductionPower(r, r1)),
                requires2);

        p.addDevCard(dev5, 2);
        assertFalse(leader2.canBeActivated(player));
        p.addDevCard(dev6, 0);
        assertTrue(leader2.canBeActivated(player));

        //Test require no level only amount
        ArrayList<LeaderDevCost> requires3 = new ArrayList<>();
        requires3.add(new LeaderDevCost(CardColor.YELLOW, 2));
        requires3.add(new LeaderDevCost(CardColor.GREEN, 1));
        LeaderCard leader3 = new DevLeaderCard(4, new ExtraProductionAbility(
                new ProductionPower(r, r1)),
                requires3);
        assertFalse(leader3.canBeActivated(player));
        p.addDevCard(dev7,1);
        assertTrue(leader3.canBeActivated(player));
    }
}