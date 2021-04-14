package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidKeyException;
import it.polimi.ingsw.exceptions.NegativeResAmountException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class LeaderCardTest {

    @Test
    void getSpecialAbility() throws NegativeResAmountException, InvalidKeyException {
        //parser JSON leaderCards
        List<LeaderCard> leader = new ArrayList<>();
        Resource r5 = new Resource();
        r5.addResource(ResourceType.COIN,5);
        Resource r6 = new Resource();
        r6.addResource(ResourceType.STONE,5);
        Resource r7 = new Resource();
        r7.addResource(ResourceType.SERVANT,5);
        Resource r8 = new Resource();
        r8.addResource(ResourceType.SHIELD,5);
        leader.add(new ResLeaderCard(3, new ExtraSlot(ResourceType.STONE),
                r5));
        leader.add(new ResLeaderCard(3, new ExtraSlot(ResourceType.SERVANT),
                r6));
        leader.add(new ResLeaderCard(3, new ExtraSlot(ResourceType.SHIELD),
                r7));
        leader.add(new ResLeaderCard(3, new ExtraSlot(ResourceType.COIN),
                r8));
    }
}