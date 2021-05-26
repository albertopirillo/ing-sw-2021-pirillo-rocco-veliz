package it.polimi.ingsw.client.gui;

import javafx.scene.control.Alert;

public class QuitAlert extends Alert {

    public QuitAlert() {
        super(AlertType.CONFIRMATION);
        this.setTitle("Quit");
        this.setHeaderText("You're about to quit");
        this.setContentText("All data will be lost");
    }
}
