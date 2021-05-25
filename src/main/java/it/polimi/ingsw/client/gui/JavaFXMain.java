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
import java.util.List;
import java.util.Optional;

public class JavaFXMain extends Application {

    /**
     * The current main scene
     */
    private static Scene scene;
    /**
     * The current stage
     */
    private static Stage myStage;
    /**
     * The scene associated with the main.fxml file
     */
    private static Parent mainScene;
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
        mainScene = root;
        scene = new Scene(root);
        myStage = stage;
        myStage.setScene(scene);
        myStage.setResizable(false);

        //Load CSS
        String css = Util.getCSS("main");
        scene.getStylesheets().add(css);

        //Set window title and icon
        myStage.setTitle("Master of Renaissance");
        Image icon = new Image("/png/generic/inkwell.png");
        myStage.getIcons().add(icon);

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

        //Popup on quitting
        //stage.setOnCloseRequest(event -> quitPopup(stage, event));

        myStage.centerOnScreen();
        myStage.show();
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

    /**
     * <p>Initializes the main scene</p>
     * <p>Creates a tab for each player</p>
     * <p>Resizes the current stage</p>
     * @param playerList  the list of the players of the game
     */
    public static void initMainScene(List<String> playerList) {
        scene.setRoot(mainScene);
        resizeStage(1643, 862);
        try {
            mainController.init(playerList);
        } catch (IOException e) {
            System.out.println("fxml file not found");
        }
    }

    /**
     * <p>Used to resize the current windows</p>
     * <p>Should be called after setRoot for better visuals</p>
     * @param width the new width of the window
     * @param height the new height of the window
     */
    public static void resizeStage(double width, double height) {
        myStage.setWidth(width);
        myStage.setHeight(height);
        myStage.centerOnScreen();
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

