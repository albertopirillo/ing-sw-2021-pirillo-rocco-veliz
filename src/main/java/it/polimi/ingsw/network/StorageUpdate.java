package it.polimi.ingsw.network;

import it.polimi.ingsw.client.PlayerInterface;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Resource;

import java.util.List;

public class StorageUpdate extends ServerUpdate {
    private final List<Player> playerList;
    private final List<List<DepotSetting>> settingsList;
    private final List<Resource> strongboxList;

    public StorageUpdate(String activePlayer, boolean lastUpdate, List<Player> playerList, List<List<DepotSetting>> settingsList, List<Resource> strongboxList) {
        super(activePlayer, lastUpdate);
        this.playerList = playerList;
        this.settingsList = settingsList;
        this.strongboxList = strongboxList;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public List<List<DepotSetting>> getSettingsList() {
        return settingsList;
    }

    public List<Resource> getStrongboxList() {
        return strongboxList;
    }

    @Override
    public void update(PlayerInterface playerInterface) {
        //playerInterface.updateStorages(this);
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < playerList.size(); i++) {
            string.append(playerList.get(i).getNickname()).append("'s storages:\n");
            string.append("Strongbox: ").append(strongboxList.get(i)).append("\n");
            string.append("Depot:\n ").append(settingsList.get(i)).append("\n");
        }
        return string.toString();
    }
}
