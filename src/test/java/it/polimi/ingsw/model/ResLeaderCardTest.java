package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.NegativeResAmountException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ResLeaderCardTest {

    @Test
    void canBeActivated() throws NegativeResAmountException {
        Resource resource1 = new Resource(1,2,3,4);
        LeaderAbility ability = new DiscountAbility(ResourceType.COIN, 2);
        ResLeaderCard res = new ResLeaderCard(2, ability ,resource1);
        //stub
        Player p1 = new Player("Aldair");
        Player p2 = new Player("Albe");
        Player p3 = new Player("Riccà");
        Resource player1 = new Resource(0,1,1,0);
        Resource player2 = new Resource(1,2,3,4);
        Resource player3 = new Resource(1,1,5,10);
        p1.getPersonalBoard().getStrongbox().addResources(player1);
        p2.getPersonalBoard().getStrongbox().addResources(player2);
        p3.getPersonalBoard().getStrongbox().addResources(player3);

        assertFalse(res.canBeActivated(p1));
        assertTrue(res.canBeActivated(p2));
        assertFalse(res.canBeActivated(p3));
    }
}