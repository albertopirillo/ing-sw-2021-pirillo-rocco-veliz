package it.polimi.ingsw.client.gui;

import javafx.scene.control.Alert;

/**
 * ErrorAlert used to display the error received with ErrorUpdate
 */
public class ErrorAlert extends Alert {

    /**
     * Creates a new ErrorAlert
     * @param errorMsg the message to show
     */
    public ErrorAlert(String errorMsg) {
        super(AlertType.ERROR);
        this.setTitle("Error");
        this.setHeaderText("Received an error message from the server");
        this.setContentText(errorMsg);
    }
}
