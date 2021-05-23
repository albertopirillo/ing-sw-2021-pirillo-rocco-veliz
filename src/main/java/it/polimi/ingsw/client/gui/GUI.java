package it.polimi.ingsw.client.gui;

import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GUI extends Application {

    private static Scene scene;
    private static MainController mainController;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = Util.loadFXML("main");
        Parent root = loader.load();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);

        //Load CSS
        String css = Util.getCSS("main");
        scene.getStylesheets().add(css);

        //Window title and icon
        stage.setTitle("Master of Renaissance");
        Image icon = new Image("/png/generic/inkwell.png");
        stage.getIcons().add(icon);

        mainController = loader.getController();
        System.out.println("[INFO] GUI started, controller set");

        //Fake player list and initialization
        List<String> playerList = new ArrayList<>();
        playerList.add("Player 1");
        playerList.add("Player 2");
        mainController.init(playerList);
        mainController.depotTest();

        //stage.setOnCloseRequest(event -> quitPopup(stage, event));

        stage.centerOnScreen();
        stage.show();

    }

    public static MainController getMainController() {
        return mainController;
    }

    public static void setRoot(String fxml) throws IOException {
        FXMLLoader loader = Util.loadFXML("fxml");
        scene.setRoot(loader.load());
    }

    public static void main(String[] args) {
        launch();
    }


    public static void startGUI() {
        new Thread(() -> main(null)).start();
    }

    private void quitPopup(Stage stage, Event event) {
        event.consume();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quit");
        alert.setHeaderText("You're about to quit");
        alert.setContentText("All data will be lost");
        Optional<ButtonType> button = alert.showAndWait();
        if (button.isPresent()) {
            if (button.get() == ButtonType.OK) {
                stage.close();
            }
        }
    }
}

