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

public class JavaFXMain extends Application {

    /**
     * The current main scene
     */
    private static Scene scene;
    /**
     * The controller associated with the main scene
     */
    private static MainController mainController;
    /**
     * Lock item to make other threads wait for GUI initialization
     */
    public final static Object lock = new Object();

    /**
     * Gets the corresponding MainController
     * @return the static MainController
     */
    public static MainController getMainController() {
        return mainController;
    }

    /**
     * Starts the GUI in a new thread
     */
    public static void startGUI() {
        new Thread(Application::launch).start();
    }

    @Override
    public void start(Stage stage) throws Exception {
        //Load FXML and set the scene
        FXMLLoader loader = Util.loadFXML("main");
        Parent root = loader.load();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);

        //Load CSS
        String css = Util.getCSS("main");
        scene.getStylesheets().add(css);

        //Set window title and icon
        stage.setTitle("Master of Renaissance");
        Image icon = new Image("/png/generic/inkwell.png");
        stage.getIcons().add(icon);

        //Set the MainController
        synchronized (lock) {
            mainController = loader.getController();
            System.out.println("[JavaFX] GUI started");
            System.out.println("[JavaFX] Controller set: " + mainController);

            //Set and initialize the SetupController
            SetupController setupController = (SetupController) setRoot("setup");
            System.out.println("[JavaFX] SetupController set " + setupController);
            mainController.setSetupController(setupController);
            System.out.println("[JavaFX] SetupController in MainController " + mainController.getSetupController());
            setupController.setMainController(mainController);
            lock.notifyAll();
        }

        //Temporary player list and initialization
        //TODO: init should be done in ClientGUI
        List<String> playerList = new ArrayList<>();
        playerList.add("Player1");
        playerList.add("Player2");
        //playerList.add("Player3");

        mainController.init(playerList);


        //TODO: remove
        //mainController.depotTest();

        //Popup on quitting
        //stage.setOnCloseRequest(event -> quitPopup(stage, event));

        stage.centerOnScreen();
        stage.show();
    }

    /**
     * <p>Changes the main scene, by loading a .fxml</p>
     * <p>Returns the controller of that scene</p>
     * @param fxml  the file to get the new scene from
     * @throws IOException  if the specified .fxml doesnt exist
     * @return  an Object representing the controller associated to the new scene
     */
    public static Object setRoot(String fxml) throws IOException {
        FXMLLoader loader = Util.loadFXML(fxml);
        scene.setRoot(loader.load());
        return loader.getController();
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
