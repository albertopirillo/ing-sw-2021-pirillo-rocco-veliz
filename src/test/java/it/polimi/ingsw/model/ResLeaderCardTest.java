package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

class ResLeaderCardTest {

    @Test
    void canBeActivated() {
        Resource resource1 = new Resource(1,2,3,4);
        LeaderAbility ability = new DiscountAbility(ResourceType.COIN, 2);
        ResLeaderCard res = new ResLeaderCard(2, ability ,resource1);
        //stub
        Resource player1 = new Resource(0,1,1,0);
        Resource player2 = new Resource(1,2,3,4);
        Resource player3 = new Resource(1,1,5,10);

        /*assertFalse(res.canBeActivated(player1));
        assertTrue(res.canBeActivated(player2)); //TODO: new tests
        assertFalse(res.canBeActivated(player3));*/
        //assertThrows(NotEnoughResException.class, () -> res.canBeActivated(null));
    }
}