package it.polimi.ingsw.network.updates;

import it.polimi.ingsw.client.PlayerInterface;
import it.polimi.ingsw.model.FaithTrack;

import java.util.Map;

public class FaithTrackUpdate extends ServerUpdate {
    private final Map<String, FaithTrack> faithTrackMap;

    public FaithTrackUpdate(String activePlayer, Map<String, FaithTrack> faithTrackMap) {
        super(activePlayer);
        this.faithTrackMap = faithTrackMap;
    }

    public Map<String, FaithTrack> getFaithTrackMap() {
        return faithTrackMap;
    }

    @Override
    public void update(PlayerInterface playerInterface) {
        playerInterface.updateFaithTrack(this);
    }
}
