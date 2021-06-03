package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.ClientError;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.TempResource;
import it.polimi.ingsw.utils.ANSIColor;

/**
 * <p>Main class of the controller package</p>
 * <p>Can be used as a gateway to all controllers</p>
 * <p>Can be used to access the Model via the Game class</p>
 * <p>Handles client error messages</p>
 * */
public class MasterController {

    /**
     * Reference to the clientError object, that handles exceptions
     */
    private final ClientError clientError;
    /**
     * The corresponding PlayerController
     */
    private final PlayerController playerController;
    /**
     * The corresponding ResourceController
     */
    private final ResourceController resourceController;
    /**
     * The corresponding RequestController
     */
    private final RequestController requestController;
    /**
     * The corresponding SetupController
     */
    private final SetupController setupController;
    /**
     * The game associated with this controller
     */
    private final Game game;

    /**
     * <p>Constructs a new MasterController</p>
     * <p>Initializes all the other controllers</p>
     * @param game the game associated with this controller
     */
    public MasterController(Game game) {
        this.clientError = new ClientError();
        TempResource tempRes = new TempResource();
        this.playerController = new PlayerController(this);
        this.resourceController = new ResourceController(this, tempRes);
        this.requestController = new RequestController(this);
        this.setupController = new SetupController(this);
        this.game = game;
    }

    /**
     * Gets the ClientError object
     * @return the ClientError object
     */
    public ClientError getClientError() {
        return clientError;
    }

    /**
     * Gets the error message contained in ClientError
     * @return the error message
     */
    public String getError() {
        return this.clientError.getError();
    }

    /**
     * Sets an exception to ClientError
     * @param exception the exception to set
     */
    public void setException(Throwable exception) {
        this.clientError.setException(exception);
        System.out.println(ANSIColor.RED + "[SERVER] Exception: " + exception.getMessage() + ANSIColor.RESET);
    }

    /**
     * Resets the exception contained in ClientError
     */
    public void resetException() {
        this.clientError.reset();
    }

    /**
     * Gets the corresponding PlayerController
     * @return the PlayerController object
     */
    public PlayerController getPlayerController() {
        return playerController;
    }

    /**
     * Gets the corresponding ResourceController
     * @return the ResourceController object
     */
    public ResourceController getResourceController() {
        return resourceController;
    }

    /**
     * Gets the corresponding RequestController
     * @return the RequestController object
     */
    public RequestController getRequestController() {
        return requestController;
    }

    /**
     * Gets the corresponding SetupController
     * @return the SetupController object
     */
    public SetupController getSetupController() {
        return setupController;
    }

    /**
     * Gets the associated game
     * @return the game object
     */
    public Game getGame() {
        return game;
    }

}