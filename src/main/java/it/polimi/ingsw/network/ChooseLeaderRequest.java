package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.MasterController;

public class ChooseLeaderRequest extends Request {

    private final int firstCard;
    private final int secondCard;

    public ChooseLeaderRequest(int firstCard, int secondCard) {
        super();
        this.firstCard = firstCard;
        this.secondCard = secondCard;
    }

    public void activateRequest(MasterController masterController) {
        // TODO implement here
    }
}