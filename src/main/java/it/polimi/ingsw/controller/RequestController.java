package it.polimi.ingsw.controller;

import it.polimi.ingsw.network.requests.Request;

/**
 * The controller that handles and activates the Requests sent by the Client
 */
public class RequestController {

    /**
     * The associated MainController
     */
    private final MasterController masterController;

    /**
     * Constructs a new ResourceController
     * @param masterController the associated MainController
     */
    public RequestController(MasterController masterController) {
        this.masterController = masterController;
    }

    /**
     * Gets the associated MainController
     * @return the MainController object
     */
    public MasterController getMasterController() {
        return this.masterController;
    }

    /**
     * Activates a Request received from the Client
     * @param request the Request to activate
     */
    public void processRequest(Request request) {
        request.activateRequest(masterController);
    }
}