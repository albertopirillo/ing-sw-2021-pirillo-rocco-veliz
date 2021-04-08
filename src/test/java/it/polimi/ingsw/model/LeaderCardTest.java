package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidKeyException;
import it.polimi.ingsw.exceptions.NegativeResAmountException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LeaderCardTest {

    @Test
    void getSpecialAbility() throws NegativeResAmountException, InvalidKeyException {
        //parser JSON leaderCards
        /*List<LeaderCard> leaders = new ArrayList<>();
        leaders.add(new ResLeaderCard(3, new ExtraSlot(ResourceType.STONE),
                new Resource().addResource(ResourceType.COIN, 5)));
        leaders.add(new ResLeaderCard(3, new ExtraSlot(ResourceType.SERVANT),
                new Resource().addResource(ResourceType.STONE, 5)));
        leaders.add(new ResLeaderCard(3, new ExtraSlot(ResourceType.SHIELD),
                new Resource().addResource(ResourceType.SERVANT, 5)));
        leaders.add(new ResLeaderCard(3, new ExtraSlot(ResourceType.COIN),
                new Resource().addResource(ResourceType.SHIELD, 5)));*/
    }
}