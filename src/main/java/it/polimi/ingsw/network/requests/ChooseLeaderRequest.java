package it.polimi.ingsw.network.requests;

import it.polimi.ingsw.controller.MasterController;

public class ChooseLeaderRequest extends Request {

    private final int firstCard;
    private final int secondCard;
    private String player;

    public ChooseLeaderRequest(int firstCard, int secondCard) {
        super();
        this.firstCard = firstCard;
        this.secondCard = secondCard;
    }

    public void setPlayer(String nickname){
        this.player = nickname;
    }

    public void activateRequest(MasterController masterController) {
        masterController.getSetupController().setInitialLeaderCards(firstCard, secondCard, player);
    }
}