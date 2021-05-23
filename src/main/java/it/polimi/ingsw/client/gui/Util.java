package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.model.ResourceType;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * Contains useful methods to work with GUI
 */
public abstract class Util {

    /**
     * The mainClass of JavaFX, specified in pom.xml
     */
    public static Class<GUI> mainClass = GUI.class;

    /**
     * Wrapper to load an .fxml file
     * @param fileName  the name of the file, with no path or extension
     * @return  a FXMLLoader Object associated the loaded file
     */
    public static FXMLLoader loadFXML(String fileName) {
       return new FXMLLoader(mainClass.getResource("/fxml/" + fileName + ".fxml"));
    }

    /**
     * Wrapper to load a .css file
     * @param fileName  the name of the file, with no path or extension
     * @return  a String representing the correct file path
     */
    public static String getCSS(String fileName) {
        return Objects.requireNonNull(mainClass.getResource("/css/" + fileName + ".css")).toExternalForm();
    }

    /**
     * Returns the image that corresponds to the given resource
     * @param resourceType  the resource type to get the image
     * @return  an Image object of the requested resource type
     */
    public static Image resToImage(ResourceType resourceType) {
        String path = "/png/res/" + resourceType.name() + ".png";
        return new Image(Objects.requireNonNull(mainClass.getResourceAsStream(path)));
    }

    /**
     * Returns the ResourceType that corresponds to the given image, if any
     * @param image the image to get the ResourceType from
     * @return  a ResourceType if the image represent a ResourceType, null otherwise
     */
    public static ResourceType imageToRes(Image image) {
        if (image == null) return null;
        String path = image.getUrl();
        if (path.contains("stone")) return ResourceType.STONE;
        else if (path.contains("coin")) return ResourceType.COIN;
        else if (path.contains("shield")) return ResourceType.SHIELD;
        else if (path.contains("servant")) return ResourceType.SERVANT;
        else return null;
    }

    /**
     * Gets the current Stage of a given Event
     * @param event the Event to get the Stage from
     * @return  a Stage object representing the current Stage
     */
    public static Stage getStageFromEvent(ActionEvent event) {
        Scene lastScene = ((Node)(event.getSource())).getScene();
        return (Stage)lastScene.getWindow();
    }
}
