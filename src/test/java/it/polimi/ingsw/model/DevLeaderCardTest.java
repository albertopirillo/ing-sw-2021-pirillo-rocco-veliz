package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidKeyException;
import it.polimi.ingsw.exceptions.NegativeResAmountException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DevLeaderCardTest {

    @Test
    void canBeActivated() throws NegativeResAmountException, InvalidKeyException {
        int level = 3;
        int amount = 2;
        LeaderAbility ability = new Discount(ResourceType.COIN, 2);
        DevLeaderCard devLeaderCard = new DevLeaderCard(1, ability, CardColor.PURPLE, level, amount);
        //stub
        List<DevelopmentCard> devs = new ArrayList<>();
        Resource res1 = new Resource();
        Resource res2 = new Resource();
        Resource res3 = new Resource();
        Resource res4 = new Resource();
        res1.addResource(ResourceType.COIN, 1);
        res2.addResource(ResourceType.FAITH, 1);
        res3.addResource(ResourceType.COIN, 3);
        res3.addResource(ResourceType.STONE,2);
        devs.add(new DevelopmentCard(1, new Resource(0,0,2,0),
                CardColor.PURPLE, 3, new ProductionPower(res1, res2)));
        devs.add(new DevelopmentCard(3, new Resource(0,0,0,3),
                CardColor.PURPLE, 3, new ProductionPower(
                res1,
                new Resource(1,0,1,1))));
        devs.add(new DevelopmentCard(3, res3,
                CardColor.BLUE, 1, new ProductionPower(res4,
                new Resource(0,1,1,1))));
        assertTrue(devLeaderCard.canBeActivated(devs));
        devs.remove(0);
        assertFalse(devLeaderCard.canBeActivated(devs));
    }
}