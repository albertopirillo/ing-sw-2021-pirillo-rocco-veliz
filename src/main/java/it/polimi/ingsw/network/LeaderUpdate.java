package it.polimi.ingsw.network;

import it.polimi.ingsw.client.PlayerInterface;
import it.polimi.ingsw.model.Player;

import java.util.List;

public class LeaderUpdate extends ServerUpdate {

    private final List<Player> playerList;
    private final List<Integer> leaderIDs;
    private final List<Boolean> abilityActive;

    public LeaderUpdate(String activePlayer, boolean lastUpdate, List<Player> playerList, List<Integer> leaderIDs, List<Boolean> abilityActive) {
        super(activePlayer, lastUpdate);
        this.playerList = playerList;
        this.leaderIDs = leaderIDs;
        this.abilityActive = abilityActive;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public List<Integer> getLeaderIDs() {
        return leaderIDs;
    }

    public List<Boolean> getAbilityActive() {
        return abilityActive;
    }

    @Override
    public void update(PlayerInterface playerInterface) {
        playerInterface.updateLeaderCards(this);
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < playerList.size(); i++) {
            string.append(playerList.get(i).getNickname()).append("'s leader cards:\n");
            for (int j = 0; j < leaderIDs.size(); j++) {
                string.append(/*TODO: read from JSON for card j + */ "active: " + abilityActive.get(j));
            }
        }
        return string.toString();
    }
}
