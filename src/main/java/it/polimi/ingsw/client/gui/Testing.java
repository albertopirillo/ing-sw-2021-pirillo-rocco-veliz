package it.polimi.ingsw.client.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

@Deprecated
public class Testing extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        //Load FXML and set the scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/market_tray.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);

        //Load CSS
        String css = Util.getCSS("main");
        scene.getStylesheets().add(css);

        stage.centerOnScreen();
        stage.show();
    }
}
