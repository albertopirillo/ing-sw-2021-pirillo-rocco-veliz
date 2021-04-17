package it.polimi.ingsw.exceptions;

public class LayerNotEmptyException extends Throwable {

    //Default constructor with default message
    public LayerNotEmptyException() {
        super("There is already another Resource type in the selected depot's layer");
    }

    //Custom constructor to set a custom message
    public LayerNotEmptyException(String customMessage) {
        super(customMessage);
    }

}
