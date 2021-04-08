package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiscountTest {

    @Test
    void activate() {
        //stub
        Game game = new Game(); //Stub
        Player player = new Player(false, "abc", game, 1, 1);
        Resource resource1 = new Resource(1,2,3,4);
        LeaderAbility ability = new Discount(ResourceType.COIN, 2);

        ResLeaderCard res = new ResLeaderCard(2, ability ,resource1);

        assertNull(player.getDevStrategy());
        res.getSpecialAbility().activate(player);
        assertNotNull(player.getDevStrategy());
        assertEquals(player.getDevStrategy().getDiscounts()[0].getResource(), ResourceType.COIN);
        assertEquals(player.getDevStrategy().getDiscounts()[0].getAmount(), 2);

        new ResLeaderCard(2, new Discount(ResourceType.FAITH, 1) ,resource1).getSpecialAbility().activate(player);
        //correct add to player
        assertEquals(player.getDevStrategy().getDiscounts()[0].getResource(), ResourceType.COIN);
        assertEquals(player.getDevStrategy().getDiscounts()[0].getAmount(), 2);
        assertEquals(player.getDevStrategy().getDiscounts()[1].getResource(), ResourceType.FAITH);
        assertEquals(player.getDevStrategy().getDiscounts()[1].getAmount(), 1);
    }
}