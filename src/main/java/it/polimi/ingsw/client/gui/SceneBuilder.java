package it.polimi.ingsw.client.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public class SceneBuilder extends Application {

    private List<String> playerList;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        //Load FXML
        FXMLLoader loader = Util.loadFXML("main");
        /*MainController mainController = new MainController(null);
        loader.setController(mainController);*/
        Parent root = loader.load();

        stage.setResizable(false);
        Scene scene = new Scene(root);
        stage.setScene(scene);

        //Load CSS
        String css = Util.getCSS("main");
        scene.getStylesheets().add(css);

        //Windows title and image
        stage.setTitle("Master of Renaissance");
        Image icon = new Image("/png/generic/inkwell.png");
        stage.getIcons().add(icon);

        //Tab creation
        this.playerList = new ArrayList<>();
        this.playerList.add("Player 1");
        this.playerList.add("Player 2");
        this.playerList.add("Player 3");
        MainController mainController = loader.getController();

        //Popup on quitting
       /* stage.setOnCloseRequest(event -> {
            event.consume();
            quit(stage);
        });*/

        mainController.init(this.playerList);
        mainController.depotTest();
        /*clientGUI.setGuiController(mainController);*/
        stage.show();
    }

    /**
     * Sets the player list for the current game
     * @param playerList a list of String representing the players' nicknames
     */
    public void setPlayerList(List<String> playerList) {
        this.playerList = playerList;
    }
}
