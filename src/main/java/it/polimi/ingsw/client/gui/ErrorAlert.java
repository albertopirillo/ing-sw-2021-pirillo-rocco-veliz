package it.polimi.ingsw.client.gui;

import javafx.scene.control.Alert;

public class ErrorAlert extends Alert {

    public ErrorAlert(String errorMsg) {
        super(AlertType.ERROR);
        this.setTitle("Error");
        this.setHeaderText("Received an error message from the server");
        this.setContentText(errorMsg);
    }
}
