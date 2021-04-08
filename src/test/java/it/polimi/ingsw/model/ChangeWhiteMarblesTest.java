package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChangeWhiteMarblesTest {

    @Test
    void activate() {
        //stub
        Game game = new Game(); //Stub
        Player player = new Player(false, "abc", game, 1, 1);
        Resource resource1 = new Resource(1,2,3,4);
        LeaderAbility ability = new ChangeWhiteMarbles(ResourceType.FAITH);
        ResLeaderCard res = new ResLeaderCard(1, ability ,resource1);

        assertNull(player.getResStrategy());
        res.getSpecialAbility().activate(player);
        assertNotNull(player.getResStrategy());
        assertEquals(player.getResStrategy().getResType()[0].getResourceType(), ResourceType.FAITH);

        new ResLeaderCard(2, new ChangeWhiteMarbles(ResourceType.COIN), resource1).getSpecialAbility().activate(player);
        //correct add to player
        assertEquals(player.getResStrategy().getResType()[0].getResourceType(), ResourceType.FAITH);
        assertEquals(player.getResStrategy().getResType()[1].getResourceType(), ResourceType.COIN);

    }
}