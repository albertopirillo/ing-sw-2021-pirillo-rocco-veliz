package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExtraProductionTest {

    @Test
    void activate() throws TooManyLeaderAbilitiesException, InvalidLayerNumberException, LeaderAbilityAlreadyActive, NegativeResAmountException, InvalidKeyException, CostNotMatchingException {
        //stub game = null
        Player player = new Player( "abc");
        Resource resource1 = new Resource(0,0,0,0);
        Resource resource2 = new Resource(0,0,0,0);
        LeaderAbility ability = new ExtraProduction(new ProductionPower(resource1, resource2));
        ResLeaderCard res1 = new ResLeaderCard(1, ability ,resource1);
        ResLeaderCard res2 = new ResLeaderCard(3, ability ,resource1);

        player.addLeaderCards(res1);
        player.addLeaderCards(res2);
        assertTrue(player.getActiveLeaderAbilities().isEmpty());

        player.useLeader(0, LeaderAction.USE_ABILITY);
        assertEquals(1, player.getActiveLeaderAbilities().size());
        assertEquals(ability, player.getActiveLeaderAbilities().get(0));

        assertThrows(LeaderAbilityAlreadyActive.class, () -> player.useLeader(0, LeaderAction.USE_ABILITY));

        player.useLeader(1, LeaderAction.USE_ABILITY);
        assertEquals(2, player.getActiveLeaderAbilities().size());
        assertEquals(ability, player.getActiveLeaderAbilities().get(1));

    }
}