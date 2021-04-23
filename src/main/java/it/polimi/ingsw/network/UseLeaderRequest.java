package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.MasterController;
import it.polimi.ingsw.model.LeaderAction;

public class UseLeaderRequest extends Request {

    private final int index;
    private final LeaderAction choice;

    public UseLeaderRequest(int index, LeaderAction choice) {
        super();
        this.index = index;
        this.choice = choice;
    }

    public void activateRequest(MasterController masterController) {
        masterController.getPlayerController().useLeader(index, choice);
    }
}