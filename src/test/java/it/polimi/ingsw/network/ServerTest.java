package it.polimi.ingsw.network;

import it.polimi.ingsw.exceptions.FullCardDeckException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class ServerTest {

    //TODO: this test fails when a server is already running
    @Test
    public void handleRequestTest() throws IOException, FullCardDeckException{
        /*Server server = new Server();
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
        assertEquals(0, game.getActivePlayer().getPlayerFaith());*/
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