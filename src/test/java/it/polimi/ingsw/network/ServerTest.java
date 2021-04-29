package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.MasterController;
import it.polimi.ingsw.exceptions.FullCardDeckException;
import it.polimi.ingsw.exceptions.InvalidKeyException;
import it.polimi.ingsw.exceptions.NegativeResAmountException;
import it.polimi.ingsw.model.AbilityChoice;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.server.Server;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ServerTest {

    @Test
    public void handleRequestTest() throws IOException, FullCardDeckException, InvalidKeyException, NegativeResAmountException {
        Server server = new Server();
        Game game = new Game(true);
        game.giveInkwell();
        MasterController masterController = new MasterController(game);
        server.setMasterController(masterController);
        Processable request = new InsertMarbleRequest(1, AbilityChoice.STANDARD, 0,0);
        //request.process(server, null);//TODO Fixed connection
        masterController.processRequest((Request) request);

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