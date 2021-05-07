package it.polimi.ingsw.network.updates;

import it.polimi.ingsw.client.PlayerInterface;
import it.polimi.ingsw.model.FaithTrack;

import java.util.Map;

public class FaithTrackUpdate extends ServerUpdate {
    private final Map<String, FaithTrack> faithTrackInfoMap;

    public FaithTrackUpdate(String activePlayer, Map<String, FaithTrack> faithTrackInfoMap) {
        super(activePlayer);
        this.faithTrackInfoMap = faithTrackInfoMap;
    }

    @Override
    public void update(PlayerInterface playerInterface) {
        playerInterface.updateFaithTrack(this);
    }

    public Map<String, FaithTrack> getFaithTrackInfoMap(){
        return faithTrackInfoMap;
    }
}
