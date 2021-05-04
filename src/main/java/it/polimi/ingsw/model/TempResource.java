package it.polimi.ingsw.model;

//This class temporary stores the resources obtained by insertMarble, waiting for the player to take them
//No logic is implemented here, this is just storage used by the ResourceController
public class TempResource {
    private Resource toHandle;

    public TempResource() {
        this.toHandle = null;
    }

    public void setToHandle(Resource toHandle) {
        this.toHandle = toHandle;
    }

    public Resource getToHandle() {
        return this.toHandle;
    }

    public boolean isEmpty() {
        return (this.toHandle == null);
    }
}
