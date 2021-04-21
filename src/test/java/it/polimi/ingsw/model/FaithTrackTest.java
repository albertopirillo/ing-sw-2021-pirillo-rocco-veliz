package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class FaithTrackTest {

    @Test
    public void trackTest() {

        List<Player> players = new ArrayList<>();
        for(int i=0; i<3; i++){
            Player player = new Player( "player"+ i);
            if(player.getNickname().equals("player2")){ player.setTurn(player, true); }
            players.add(player);
        }

        players.get(0).addPlayerFaith(4);
        players.get(1).addPlayerFaith(5);
        players.get(2).addPlayerFaith(8);
        players.get(2).getPersonalBoard().updateFaithTrack(players);

        players.get(0).addPlayerFaith(8);
        players.get(1).addPlayerFaith(6);
        players.get(2).addPlayerFaith(8);
        players.get(2).getPersonalBoard().updateFaithTrack(players);

        players.get(0).addPlayerFaith(3);
        players.get(1).addPlayerFaith(9);
        players.get(2).addPlayerFaith(8);
        players.get(2).getPersonalBoard().updateFaithTrack(players);

        assertTrue(players.get(0).getPersonalBoard().getFaithTrack().getPopeFavorBySection(VaticanReportSection.GROUP_ONE).isReported());
        assertFalse(players.get(0).getPersonalBoard().getFaithTrack().getPopeFavorBySection(VaticanReportSection.GROUP_ONE).isFaceUp());
        assertTrue(players.get(0).getPersonalBoard().getFaithTrack().getPopeFavorBySection(VaticanReportSection.GROUP_TWO).isReported());
        assertTrue(players.get(0).getPersonalBoard().getFaithTrack().getPopeFavorBySection(VaticanReportSection.GROUP_TWO).isFaceUp());
        assertTrue(players.get(0).getPersonalBoard().getFaithTrack().getPopeFavorBySection(VaticanReportSection.GROUP_THREE).isReported());
        assertFalse(players.get(0).getPersonalBoard().getFaithTrack().getPopeFavorBySection(VaticanReportSection.GROUP_THREE).isFaceUp());
        assertTrue(players.get(1).getPersonalBoard().getFaithTrack().getPopeFavorBySection(VaticanReportSection.GROUP_ONE).isReported());
        assertTrue(players.get(1).getPersonalBoard().getFaithTrack().getPopeFavorBySection(VaticanReportSection.GROUP_ONE).isFaceUp());
        assertTrue(players.get(1).getPersonalBoard().getFaithTrack().getPopeFavorBySection(VaticanReportSection.GROUP_TWO).isReported());
        assertFalse(players.get(1).getPersonalBoard().getFaithTrack().getPopeFavorBySection(VaticanReportSection.GROUP_TWO).isFaceUp());
        assertTrue(players.get(1).getPersonalBoard().getFaithTrack().getPopeFavorBySection(VaticanReportSection.GROUP_THREE).isReported());
        assertTrue(players.get(1).getPersonalBoard().getFaithTrack().getPopeFavorBySection(VaticanReportSection.GROUP_THREE).isFaceUp());
        assertTrue(players.get(2).getPersonalBoard().getFaithTrack().getPopeFavorBySection(VaticanReportSection.GROUP_ONE).isReported());
        assertTrue(players.get(2).getPersonalBoard().getFaithTrack().getPopeFavorBySection(VaticanReportSection.GROUP_ONE).isFaceUp());
        assertTrue(players.get(2).getPersonalBoard().getFaithTrack().getPopeFavorBySection(VaticanReportSection.GROUP_TWO).isReported());
        assertTrue(players.get(2).getPersonalBoard().getFaithTrack().getPopeFavorBySection(VaticanReportSection.GROUP_TWO).isFaceUp());
        assertTrue(players.get(2).getPersonalBoard().getFaithTrack().getPopeFavorBySection(VaticanReportSection.GROUP_THREE).isReported());
        assertTrue(players.get(2).getPersonalBoard().getFaithTrack().getPopeFavorBySection(VaticanReportSection.GROUP_THREE).isFaceUp());
        assertEquals(3, players.get(0).getVictoryPoints());
        assertEquals(6, players.get(1).getVictoryPoints());
        assertEquals(9, players.get(2).getVictoryPoints());
    }
}

