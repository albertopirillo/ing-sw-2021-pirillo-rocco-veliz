package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.MasterController;
import it.polimi.ingsw.exceptions.FullCardDeckException;
import it.polimi.ingsw.model.AbilityChoice;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.MultiGame;
import it.polimi.ingsw.model.Resource;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ServerTest {

    @Test
    public void handleRequestTest() throws IOException, FullCardDeckException {
        Server server = new Server();
        Game game = new MultiGame(true);
        MasterController masterController = new MasterController(game);
        server.setMasterController(masterController);
        Processable request = new InsertMarbleRequest(1, AbilityChoice.STANDARD, 0,0);
        request.process(server);

        Resource output = masterController.getResourceController().getToHandle();
        assertEquals(new Resource(1,0,0,1), output);
        assertEquals(0, game.getActivePlayer().getPlayerFaith());
    }

    @Test
    public void handleInputTest() {
        //TODO: implement here
    }

    @Test
    public void handleMessageTest() {
        //TODO: implement here
    }

    @Test
    public void runTest() {
        //TODO: implement here
    }

    @Test
    public void closeClientConnectionTEst() {
        //TODO: implement here
    }
}