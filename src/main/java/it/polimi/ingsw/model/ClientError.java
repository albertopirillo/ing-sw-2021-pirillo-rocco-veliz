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

    public void reset() {
        this.exception = null;
    }

    public String getError() {
        /*if(this.exception == null){
            return "Result: OK";
        } else {
           String msg =  this.exception.getMessage();
           this.exception = null;
           return msg;
        }*/
        return this.exception != null ? this.exception.getMessage() : "Result: OK";
    }
}
