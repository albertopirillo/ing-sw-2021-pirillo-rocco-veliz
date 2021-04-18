package it.polimi.ingsw.controller;

public class ClientError {
    private Throwable exception;

    public ClientError() {
        this.exception = null;
    }

    public void setException(Throwable exception) {
        this.exception = exception;
    }

    public String getError() {
        if (this.exception == null) return "Result: OK";
        else { //Has to reset its state
            String message = this.exception.getMessage();
            this.exception = null;
            return message;
        }
    }
}
