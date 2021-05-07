package it.polimi.ingsw.model;

import java.io.Serializable;

public class ClientError implements Serializable {
    private Throwable exception;

    public ClientError() {
        this.exception = null;
    }

    public void setException(Throwable exception) {
        this.exception = exception;
    }

    public String getError() {
        return this.exception != null ? this.exception.getMessage() : "Result: OK";
    }
}
