package it.polimi.ingsw.client;

import it.polimi.ingsw.controller.MasterController;
import it.polimi.ingsw.controller.PlayerController;
import it.polimi.ingsw.exceptions.FullCardDeckException;
import it.polimi.ingsw.exceptions.TooManyLeaderAbilitiesException;
import it.polimi.ingsw.model.*;
import org.junit.jupiter.api.Test;

public class InsertMarbleTest {

    @Test
    public void tempMarblesTest() throws FullCardDeckException, TooManyLeaderAbilitiesException {
        Game game = new MultiGame(true);
        MasterController controller = new MasterController(game);
        PlayerController playerController = controller.getPlayerController();
        Player activePlayer = game.getActivePlayer();
        activePlayer.setGame(game);
        //  PURPLE  PURPLE  YELLOW  YELLOW
        //  GREY  GREY  BLUE  BLUE
        //  WHITE  WHITE  WHITE  WHITE
        //  Remaining marble = RED
        ChangeWhiteMarblesAbility changeWhiteMarbles = new ChangeWhiteMarblesAbility(ResourceType.SHIELD);
        changeWhiteMarbles.activate(activePlayer);
        ChangeWhiteMarblesAbility changeWhiteMarbles2 = new ChangeWhiteMarblesAbility(ResourceType.COIN);
        changeWhiteMarbles2.activate(activePlayer);

        playerController.insertMarble(0);
        Resource output = controller.getResourceController().getTempRes().getToHandle();
        /* OUTPUT ALL x1 SERVANT x1 STONE x1 */
        System.out.println(output);
        //TempMarblesUpdate update = new TempMarblesUpdate(activePlayer.getNickname(), output.getValue(ResourceType.ALL), activePlayer.getResTypesAbility());
        //update.update(cli);
        //Kill test to exit

    }
}
