package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * Object required to notify an error to the Client
 */
public class ClientError implements Serializable, Cloneable {
    /**
     * The exception that will be notified
     */
    private Throwable exception;

    /**
     * Constructor initialize the exception to null, because no error happened yet
     */
    public ClientError() {
        this.exception = null;
    }

    /**
     * Sets the exception
     * @param exception the exception to set
     */
    public void setException(Throwable exception) {
        this.exception = exception;
    }

    /**
     * Resets the exception, to call when the Client reads the error
     */
    public void reset() {
        this.exception = null;
    }

    /**
     * Gets the error message contained in the exception
     * @return a String representing the error message
     */
    public String getError() {
        return this.exception != null ? this.exception.getMessage() : "Result: OK";
    }

    @Override
    public ClientError clone() {
        ClientError clone = null;
        try {
            clone = (ClientError) super.clone();
            clone.exception = this.exception;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return clone;
    }
}
