package it.polimi.ingsw.controller;

public class Controller {

    private final ClientError clientError;
    private final PlayerController playerController;
    private final DepotController depotController;

    public Controller() {
        this.clientError = new ClientError();
        this.playerController = new PlayerController(this);
        this.depotController = new DepotController(this);
    }

    public String getError() {
        return this.clientError.getError();
    }

    public void setException(Throwable exception) {
        this.clientError.setException(exception);
    }

    public PlayerController getPlayerController() {
        return playerController;
    }

    public DepotController getDepotController() {
        return depotController;
    }
}