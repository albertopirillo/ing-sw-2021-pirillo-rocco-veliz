package it.polimi.ingsw.controller;

import it.polimi.ingsw.network.requests.Request;

public class RequestController {

    private final MasterController masterController;

    public RequestController(MasterController masterController) {
        this.masterController = masterController;
    }

    public MasterController getMasterController() {
        return this.masterController;
    }

    public void processRequest(Request request) {
        request.activateRequest(masterController);
    }
}