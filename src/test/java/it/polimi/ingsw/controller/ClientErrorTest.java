package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.AlreadyInAnotherLayerException;
import it.polimi.ingsw.model.ClientError;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ClientErrorTest {

    @Test
    public void defaultTest() {
        ClientError clientError = new ClientError();
        clientError.setException(new AlreadyInAnotherLayerException());
        String message = "You can't place the same type of Resource in two different depot's layers";
        assertEquals(message, clientError.getError());
    }

    @Test
    public void customTest() {
        ClientError clientError = new ClientError();
        String message = "This is a custom error message";
        clientError.setException(new AlreadyInAnotherLayerException(message));
        assertEquals(message, clientError.getError());
    }

    @Test
    public void noMessageTest() {
        ClientError clientError = new ClientError();
        clientError.setException(new Exception());
        assertNull(clientError.getError());
    }
}