package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;

public class MasterController {

    private final ClientError clientError;
    private final PlayerController playerController;
    private final ResourceController resourceController;
    private final RequestController requestController;
    private final SetupController setupController;
    private final Game game;

    public MasterController(Game game) {
        this.clientError = new ClientError();
        this.playerController = new PlayerController(this);
        this.resourceController = new ResourceController(this);
        this.requestController = new RequestController(this);
        this.setupController = new SetupController(this);
        this.game = game;
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

    public ResourceController getDepotController() {
        return resourceController;
    }

    public RequestController getRequestController() {
        return requestController;
    }

    public SetupController getSetupController() {
        return setupController;
    }

    public Game getGame() {
        return game;
    }

}