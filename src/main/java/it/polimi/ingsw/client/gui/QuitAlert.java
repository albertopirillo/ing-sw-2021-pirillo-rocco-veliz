package it.polimi.ingsw.client.gui;

import javafx.scene.control.Alert;

/**
 * QuitAlert with a default message shown when a player wants to quit the game
 */
public class QuitAlert extends Alert {

    /**
     * Creates a new personalized QuitAlert
     */
    public QuitAlert() {
        super(AlertType.CONFIRMATION);
        this.setTitle("Quit");
        this.setHeaderText("You're about to quit");
        this.setContentText("All data will be lost");
    }
}
