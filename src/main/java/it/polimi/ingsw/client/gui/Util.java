package it.polimi.ingsw.client.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.Objects;

/**
 * Contains useful methods to work with GUI
 */
public abstract class Util {

    /**
     * The mainClass of JavaFX, specified in pom.xml
     */
    public static Class<Launcher> mainClass = Launcher.class;

    /**
     * Wrapper to load an .fxml file
     * @param fileName  the name of the file, with no path or extension
     * @return  a Parent Object representing the loaded file
     * @throws IOException  if the file doesnt exists
     */
    public static Parent loadFXML(String fileName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Launcher.class.getResource("/fxml/" + fileName + ".fxml"));
        return fxmlLoader.load();
    }

    /**
     * Wrapper to load a .css file
     * @param fileName  the name of the file, with no path or extension
     * @return  a String representing the correct file path
     */
    public static String getCSS(String fileName) {
        return Objects.requireNonNull(mainClass.getResource("/css/" + fileName + ".css")).toExternalForm();
    }
}
