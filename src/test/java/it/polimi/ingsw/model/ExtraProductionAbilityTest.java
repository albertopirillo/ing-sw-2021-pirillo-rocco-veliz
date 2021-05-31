package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExtraProductionAbilityTest {

    @Test
    void activate() throws TooManyLeaderAbilitiesException, InvalidLayerNumberException, LeaderAbilityAlreadyActive, NegativeResAmountException, InvalidKeyException, CostNotMatchingException {
        //stub game = null
        Player player = new Player( "abc");
        Resource resource1 = new Resource(0,0,0,0);
        Resource resource2 = new Resource(0,0,0,0);
        LeaderAbility ability = new ExtraProductionAbility(new ProductionPower(resource1, resource2));
        ResLeaderCard res1 = new ResLeaderCard(1, ability ,resource1);
        ResLeaderCard res2 = new ResLeaderCard(3, ability ,resource1);

        player.addLeaderCard(res1);
        player.addLeaderCard(res2);
        assertTrue(player.getActiveLeaderAbilities().isEmpty());

        try {
            player.useLeader(0, LeaderAction.USE_ABILITY);
        } catch (NoLeaderAbilitiesException e) {
            e.printStackTrace();
        }
        assertEquals(1, player.getActiveLeaderAbilities().size());
        assertEquals(ability, player.getActiveLeaderAbilities().get(0));

        assertThrows(LeaderAbilityAlreadyActive.class, () -> player.useLeader(0, LeaderAction.USE_ABILITY));

        try {
            player.useLeader(1, LeaderAction.USE_ABILITY);
        } catch (NoLeaderAbilitiesException e) {
            e.printStackTrace();
        }
        assertEquals(2, player.getActiveLeaderAbilities().size());
        assertEquals(ability, player.getActiveLeaderAbilities().get(1));

    }
}