package it.polimi.ingsw.network;

import it.polimi.ingsw.client.PlayerInterface;
import it.polimi.ingsw.model.FaithTrack;

import java.util.Map;

public class FaithTrackUpdate extends ServerUpdate{
    private final Map<String, FaithTrack> faithTrackInfoMap;

    public FaithTrackUpdate(String activePlayer, boolean lastUpdate, Map<String, FaithTrack> faithTrackInfoMap) {
        super(activePlayer, lastUpdate);
        this.faithTrackInfoMap = faithTrackInfoMap;
    }

    @Override
    public void update(PlayerInterface playerInterface) {
        if(getActivePlayer().equals(playerInterface.getNickname())){
            playerInterface.showFaithTrack(this);
        }
    }

    public Map<String, FaithTrack> getFaithTrackInfoMap(){
        return faithTrackInfoMap;
    }
}
