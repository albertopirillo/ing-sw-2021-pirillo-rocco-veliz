package it.polimi.ingsw.client.model;

import it.polimi.ingsw.model.DevelopmentSlot;
import it.polimi.ingsw.model.FaithTrack;
import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.network.updates.DevSlotsUpdate;
import it.polimi.ingsw.network.updates.FaithTrackUpdate;
import it.polimi.ingsw.network.updates.LeaderUpdate;

import java.util.List;
import java.util.Map;

public class PersonalBoardModel {
    private final ClientModel clientModel;

    private Map<String, List<DevelopmentSlot>> devSlotMap;
    private Map<String, FaithTrack> faithTrackInfoMap;
    private Map<String, List<LeaderCard>> leaderMap;

    public PersonalBoardModel(ClientModel clientModel) {
        this.clientModel = clientModel;
    }

    public Map<String, List<DevelopmentSlot>> getDevSlotMap() {
        return devSlotMap;
    }

    public Map<String, FaithTrack> getFaithTrackInfoMap() {
        return faithTrackInfoMap;
    }

    public Map<String, List<LeaderCard>> getLeaderMap() {
        return leaderMap;
    }

    public void saveDevSlots(DevSlotsUpdate update) {
        this.devSlotMap = update.getDevSlotMap();
    }

    public void saveFaithTrack(FaithTrackUpdate update) {
        this.faithTrackInfoMap = update.getFaithTrackInfoMap();
    }

    public void saveLeaderCards(LeaderUpdate update) {
        this.leaderMap = update.getLeaderMap();
    }
}
