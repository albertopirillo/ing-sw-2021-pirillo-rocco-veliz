package it.polimi.ingsw.network.updates;

import it.polimi.ingsw.client.PlayerInterface;
import it.polimi.ingsw.model.LeaderCard;

import java.util.List;
import java.util.Map;

public class LeaderUpdate extends ServerUpdate {

    private final Map<String, List<LeaderCard>> leaderMap;

    public LeaderUpdate(String activePlayer, Map<String, List<LeaderCard>> leaderMap) {
        super(activePlayer);
        this.leaderMap = leaderMap;
    }


    public Map<String, List<LeaderCard>> getLeaderMap() {
        return leaderMap;
    }

    @Override
    public void update(PlayerInterface playerInterface) {
        playerInterface.updateLeaderCards(this);
    }

//    @Override TODO
//    public String toString() {
//        StringBuilder string = new StringBuilder();
//        for (int i = 0; i < playerList.size(); i++) {
//            string.append(playerList.get(i).getNickname()).append("'s leader cards:\n");
//            for (int j = 0; j < leaderIDs.size(); j++) {
//                string.append(/*TODO: read from JSON for card j + */ "active: " + abilityActive.get(j));
//            }
//        }
//        return string.toString();
//    }
}
