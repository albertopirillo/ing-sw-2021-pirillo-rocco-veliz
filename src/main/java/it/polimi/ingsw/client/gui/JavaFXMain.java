package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.Client;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
        loadCSS("main");
        loadCSS("personalBoard");
        loadCSS("setup");
        loadCSS("solo");
        loadCSS("customCheckBox");
        loadCSS("customMenuLabel");
        loadCSS("market");

        //Set window title and icon
        myStage.setTitle("Master of Renaissance");
        Image icon = new Image("/png/generic/inkwell.png");
        myStage.getIcons().add(icon);

        //Set the MainController
        synchronized (lock) {
            mainController = loader.getController();
            System.out.println("[JavaFX] GUI started");
            System.out.println("[JavaFX] MainController ready");

            //Set and initialize the SetupController
            SetupController setupController = (SetupController) changeScene("setup");
            System.out.println("[JavaFX] SetupController ready");
            mainController.setSetupController(setupController);
            setupController.setMainController(mainController);

            //Notifies Client's constructor
            lock.notifyAll();
        }

        //Popup on quitting
        stage.setOnCloseRequest(this::quitPopup);

        myStage.centerOnScreen();

        //Wait to connect to the server before showing the GUI
        synchronized (lock) {
            while(Client.getSocket() == null) {
                lock.wait();
            }
            myStage.show();
        }
    }

    private void loadCSS(String fileName) {
        String css = Util.getCSS(fileName);
        scene.getStylesheets().add(css);
    }

    /**
     * <p>Changes the main scene, by loading a .fxml</p>
     * <p>Returns the controller of that scene (explicit cast needed)</p>
     * @param fxml  the file to get the new scene from
     * @return  an Object representing the controller associated to the new scene
     */
    public static Object changeScene(String fxml) {
        try {
            FXMLLoader loader = Util.loadFXML(fxml);
            Parent root = loader.load();
            scene.setRoot(root);
            return loader.getController();
        } catch(IOException e){
            e.printStackTrace();
            return null;
        }
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
            e.printStackTrace();
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

    private void quitPopup(Event event) {
        event.consume();
        QuitAlert alert = new QuitAlert();
        Optional<ButtonType> button = alert.showAndWait();
        if (button.isPresent()) {
            if (button.get() == ButtonType.OK) {
                Platform.exit();
            }
        }
    }
}

