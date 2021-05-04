package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.LeaderCard;
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
        game.setPlayersList(players);
        game.startGame();
        firstPlayer = game.giveInkwell();
    }


    public void placeInitialResource(Map<ResourceType, Integer> res, int numPlayer, String nickname) {
        //Player activePlayer = controller.getGame().getPlayer(nickname);
        Player activePlayer = controller.getGame().getActivePlayer();
        if(!activePlayer.getNickname().equals(nickname)) return;
        try {
            List<ResourceType> resources = new ArrayList<>(res.keySet());
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
                        activePlayer.getPersonalBoard().getDepot().modifyLayer(2, resources.get(0), 2);
                    }else{
                        activePlayer.getPersonalBoard().getDepot().modifyLayer(1, resources.get(0), 1);
                        activePlayer.getPersonalBoard().getDepot().modifyLayer(2, resources.get(1), 1);
                    }
                    break;
            }
            Game game = controller.getGame();
            game.nextTurn();
            if(!game.getActivePlayer().getNickname().equals(firstPlayer))
                game.updateInitResources(numPlayer+1);
            else
                game.updateInitLeaderCards();
            System.out.println("Totale risorse player + "+numPlayer+": "+activePlayer.getAllResources());//testing
        } catch (NegativeResAmountException | CannotContainFaithException | LayerNotEmptyException | NotEnoughSpaceException | InvalidLayerNumberException | AlreadyInAnotherLayerException | InvalidResourceException | InvalidKeyException e) {
            controller.setException(e);
        }
    }

    public void setInitialLeaderCards(int firstCard, int secondCard, String nickname){
        Player activePlayer = controller.getGame().getActivePlayer();
        if(!activePlayer.getNickname().equals(nickname)) return;
        List<LeaderCard> finalLeaderCards = new ArrayList<>();
        finalLeaderCards.add(activePlayer.getLeaderCards().get(firstCard));
        finalLeaderCards.add(activePlayer.getLeaderCards().get(secondCard));
        activePlayer.setLeaderCards(finalLeaderCards);
        Game game = controller.getGame();
        try {
            game.nextTurn();
        } catch (NegativeResAmountException | InvalidKeyException e) {
            e.printStackTrace();
        }
        System.out.println("[CONTROLLER] Testing! Carte del player ora sono "+activePlayer.getLeaderCards().size());
        System.out.println("[CONTROLLER] Non sono ancora come stamparla");
        if(game.getActivePlayer().getNickname().equals(firstPlayer)) game.updateClientModel();
        else game.updateInitLeaderCards();
    }

}