package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.ResourceType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SetupController {

    private final MasterController controller;
    private String firstPlayer;

    public SetupController(MasterController controller) {

        this.controller = controller;
    }

    public void setupGame(List<String> nicknames, int numPlayers){
        List <Player> players = new ArrayList<>();
        for (String nickname : nicknames){
            Player player = new Player(nickname);
            players.add(player);
        }
        Game game = this.controller.getGame();
        game.setPlayerAmount(numPlayers);
        game.setPlayers(players);
        game.startGame();
        firstPlayer = game.giveInkwell();
    }


    public void placeInitialResource(Map<ResourceType, Integer> res, int numPlayer, String nickname) {
        Player activePlayer = controller.getGame().getPlayer(nickname);
        try {
            List<ResourceType> resources = new ArrayList<>();
            resources.addAll(res.keySet());
            switch (numPlayer){
                case 1:
                    activePlayer.getPersonalBoard().getDepot().modifyLayer(1, resources.get(0),1);
                    break;
                case 2:
                    activePlayer.getPersonalBoard().getDepot().modifyLayer(1, resources.get(0),1);
                    activePlayer.addPlayerFaith(1);
                    break;
                case 3:
                    if(resources.size()==1) {
                        activePlayer.getPersonalBoard().getDepot().modifyLayer(2, resources.get(0), 1);
                    }else{
                        activePlayer.getPersonalBoard().getDepot().modifyLayer(1, resources.get(0), 1);
                        activePlayer.getPersonalBoard().getDepot().modifyLayer(2, resources.get(1), 1);
                    }
                    break;
            }
            System.out.println("Totale risorse player + "+numPlayer+": "+activePlayer.getAllResources());//testing
        } catch (NegativeResAmountException | CannotContainFaithException | LayerNotEmptyException | NotEnoughSpaceException | InvalidLayerNumberException | AlreadyInAnotherLayerException | InvalidResourceException | InvalidKeyException e) {
            controller.setException(e);
        }
    }
    //TODO: chooseLeaderCards

}