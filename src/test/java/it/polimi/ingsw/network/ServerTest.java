package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.MasterController;
import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.MultiGame;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.network.requests.InsertMarbleRequest;
import it.polimi.ingsw.network.requests.Request;
import it.polimi.ingsw.server.Server;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ServerTest {

    @Test
    public void handleRequestTest() throws IOException, FullCardDeckException{
        Server server = new Server();
        Game game = new MultiGame(true);
        game.giveInkwell();
        MasterController masterController = new MasterController(game);
        server.setMasterController(masterController);
        Request request = new InsertMarbleRequest(2);
        //  PURPLE  PURPLE  YELLOW  YELLOW
        //  GREY  GREY  BLUE  BLUE
        //  WHITE  WHITE  WHITE  WHITE
        //  Remaining marble = RED

        masterController.processRequest(request);
        Resource output = masterController.getResourceController().getTempRes().getToHandle();
        Resource check = new Resource(0,1,1,0);
        assertEquals(check, output);
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