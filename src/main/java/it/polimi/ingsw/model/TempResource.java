package it.polimi.ingsw.model;

/**
 * <p>Temporary stores the resources taken from the Market, waiting for the player to place them</p>
 * <p>No logic is implemented here, this is just a storage used by the ResourceController</p>
 */
public class TempResource {
    /**
     * The resources to be placed
     */
    private Resource toHandle;

    /**
     * Constructs a new object and sets an empty resource
     */
    public TempResource() {
        this.toHandle = null;
    }

    /**
     * Sets the resources to be placed
     * @param toHandle the resources taken from the Market
     */
    public void setToHandle(Resource toHandle) {
        this.toHandle = toHandle;
        //System.out.println(ANSIColor.RED + "[MODEL] Update TempResources: " +toHandle + ANSIColor.RESET);
    }
    /**
     * Gets the resources to be placed
     * @return the resources
     */
    public Resource getToHandle() {
        return this.toHandle;
    }
    /**
     * Whether this storage is empty or not
     * @return true if it is empty, false otherwise
     */
    public boolean isEmpty() {
        return (this.toHandle == null);
    }
}
