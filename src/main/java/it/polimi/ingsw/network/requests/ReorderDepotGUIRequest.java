package it.polimi.ingsw.network.requests;

import it.polimi.ingsw.controller.MasterController;
import it.polimi.ingsw.network.DepotSetting;

import java.util.List;

public class ReorderDepotGUIRequest extends Request{
    private final List<DepotSetting> settingList;

    public ReorderDepotGUIRequest(List<DepotSetting> settingList) {
        super();
        this.settingList = settingList;
    }

    @Override
    public void activateRequest(MasterController masterController) {
        masterController.getPlayerController().reorderDepot(settingList);
    }
}
