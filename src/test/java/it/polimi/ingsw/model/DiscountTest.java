package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidLayerNumberException;
import it.polimi.ingsw.exceptions.LeaderAbilityAlreadyActive;
import it.polimi.ingsw.exceptions.TooManyLeaderAbilitiesException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiscountTest {

    @Test
    void activate() throws TooManyLeaderAbilitiesException, InvalidLayerNumberException, LeaderAbilityAlreadyActive {
        //stub game = null
        Player player = new Player(false, "abc");
        Resource resource1 = new Resource(1,2,3,4);
        LeaderAbility ability = new Discount(ResourceType.COIN, 2);
        ResLeaderCard res1 = new ResLeaderCard(2, ability ,resource1);
        ResLeaderCard res2 = new ResLeaderCard(3, ability ,resource1);
        ResLeaderCard res3 = new ResLeaderCard(1, ability ,resource1);

        player.setLeaderCards(0, res1);
        player.setLeaderCards(1, res2);
        assertTrue(player.getActiveLeaderAbilities().isEmpty());

        player.useLeader(0, LeaderAction.USE_ABILITY);
        assertEquals(1, player.getActiveLeaderAbilities().size());
        assertEquals(ability, player.getActiveLeaderAbilities().get(0));

        assertThrows(LeaderAbilityAlreadyActive.class, () -> player.useLeader(0, LeaderAction.USE_ABILITY));

        player.useLeader(1, LeaderAction.USE_ABILITY);
        assertEquals(2, player.getActiveLeaderAbilities().size());
        assertEquals(ability, player.getActiveLeaderAbilities().get(1));

        player.setLeaderCards(0, res3);
        assertThrows(TooManyLeaderAbilitiesException.class, () -> player.useLeader(0, LeaderAction.USE_ABILITY));
    }
}