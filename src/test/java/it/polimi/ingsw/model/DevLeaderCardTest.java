package it.polimi.ingsw.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.exceptions.DevSlotEmptyException;
import it.polimi.ingsw.exceptions.InvalidNumSlotException;
import it.polimi.ingsw.exceptions.NegativeResAmountException;
import it.polimi.ingsw.utils.LeaderAbilityDeserializer;
import it.polimi.ingsw.utils.LeaderCardJsonDeserializer;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DevLeaderCardTest {

    @Test
    void parserTest() {
        //register adapter
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(LeaderAbility.class, new LeaderAbilityDeserializer());
        builder.registerTypeAdapter(LeaderCard.class, new LeaderCardJsonDeserializer());
        Gson gson = builder.create();
        Type listType = new TypeToken<List<LeaderCard>>(){}.getType();
        //leaders contains the 16 leader cards...
        //List<LeaderCard> leaders = gson.fromJson(new JsonReader(new FileReader("src/main/resources/json/LeaderCardsConfig.json")), listType);

    }
    @Test
    void canBeActivated() throws NegativeResAmountException, InvalidNumSlotException, DevSlotEmptyException {

        //parse GSON
        /*
        ArrayList<LeaderCard> leader = new ArrayList<>();
        ArrayList<LeaderDevCost> requires = new ArrayList<>();
        requires.add(new LeaderDevCost(CardColor.YELLOW,1));
        requires.add(new LeaderDevCost(CardColor.GREEN,1));
        ArrayList<LeaderDevCost> requires1 = new ArrayList<>();
        requires1.add(new LeaderDevCost(CardColor.BLUE,1));
        requires1.add(new LeaderDevCost(CardColor.PURPLE,1));
        ArrayList<LeaderDevCost> requires2 = new ArrayList<>();
        requires2.add(new LeaderDevCost(CardColor.GREEN,1));
        requires2.add(new LeaderDevCost(CardColor.BLUE,1));
        ArrayList<LeaderDevCost> requires3 = new ArrayList<>();
        requires3.add(new LeaderDevCost(CardColor.YELLOW,1));
        requires3.add(new LeaderDevCost(CardColor.PURPLE,1));
        ArrayList<LeaderDevCost> requires4 = new ArrayList<>();
        requires4.add(new LeaderDevCost(CardColor.YELLOW,2));
        requires4.add(new LeaderDevCost(CardColor.BLUE,1));
        ArrayList<LeaderDevCost> requires5 = new ArrayList<>();
        requires5.add(new LeaderDevCost(CardColor.GREEN,2));
        requires5.add(new LeaderDevCost(CardColor.PURPLE,1));
        ArrayList<LeaderDevCost> requires6 = new ArrayList<>();
        requires6.add(new LeaderDevCost(CardColor.BLUE,2));
        requires6.add(new LeaderDevCost(CardColor.YELLOW,1));
        ArrayList<LeaderDevCost> requires7 = new ArrayList<>();
        requires7.add(new LeaderDevCost(CardColor.PURPLE,2));
        requires7.add(new LeaderDevCost(CardColor.GREEN,1));
        ArrayList<LeaderDevCost> requires8 = new ArrayList<>();
        requires8.add(new LeaderDevCost(CardColor.YELLOW,2,1));
        Resource r = new Resource();
        r.addResource(ResourceType.SHIELD,1);
        Resource r1 = new Resource();
        r1.addResource(ResourceType.FAITH,2);
        ArrayList<LeaderDevCost> requires9 = new ArrayList<>();
        requires9.add(new LeaderDevCost(CardColor.BLUE,2,1));
        Resource r2 = new Resource();
        r2.addResource(ResourceType.SERVANT,1);
        ArrayList<LeaderDevCost> requires10 = new ArrayList<>();
        requires10.add(new LeaderDevCost(CardColor.PURPLE,2,1));
        Resource r3 = new Resource();
        r3.addResource(ResourceType.STONE,1);
        ArrayList<LeaderDevCost> requires11 = new ArrayList<>();
        requires11.add(new LeaderDevCost(CardColor.GREEN,2,1));
        Resource r4 = new Resource();
        r4.addResource(ResourceType.COIN,1);
        //res leader cards
        Resource r5 = new Resource();
        r5.addResource(ResourceType.COIN,5);
        Resource r6 = new Resource();
        r6.addResource(ResourceType.STONE,5);
        Resource r7 = new Resource();
        r7.addResource(ResourceType.SERVANT,5);
        Resource r8 = new Resource();
        r8.addResource(ResourceType.SHIELD,5);
        leader.add(new ResLeaderCard(3, new ExtraSlot(ResourceType.STONE),
                r5));
        leader.add(new ResLeaderCard(3, new ExtraSlot(ResourceType.SERVANT),
                r6));
        leader.add(new ResLeaderCard(3, new ExtraSlot(ResourceType.SHIELD),
                r7));
        leader.add(new ResLeaderCard(3, new ExtraSlot(ResourceType.COIN),
                r8));

        leader.add(new DevLeaderCard(2,
                new Discount(ResourceType.SERVANT, 1)
                , requires));
        leader.add(new DevLeaderCard(2,
                new Discount(ResourceType.SHIELD, 1)
                , requires1));
        leader.add(new DevLeaderCard(2,
                new Discount(ResourceType.STONE, 1)
                , requires2));
        leader.add(new DevLeaderCard(2,
                new Discount(ResourceType.COIN, 1)
                , requires3));
        leader.add(new DevLeaderCard(5,
                new ChangeWhiteMarbles(ResourceType.SERVANT)
                , requires4));
        leader.add(new DevLeaderCard(5,
                new ChangeWhiteMarbles(ResourceType.SHIELD)
                , requires5));
        leader.add(new DevLeaderCard(5,
                new ChangeWhiteMarbles(ResourceType.STONE)
                , requires6));
        leader.add(new DevLeaderCard(5,
                new ChangeWhiteMarbles(ResourceType.COIN)
                , requires7));
        leader.add(new DevLeaderCard(4,
                new ExtraProduction(new ProductionPower(r,r1))
                , requires8));
        leader.add(new DevLeaderCard(4,
                new ExtraProduction(new ProductionPower(r2,r1))
                , requires9));
        leader.add(new DevLeaderCard(4,
                new ExtraProduction(new ProductionPower(r3,r1))
                , requires10));
        leader.add(new DevLeaderCard(4,
                new ExtraProduction(new ProductionPower(r4,r1))
                , requires11));

        //System.out.println(new Gson().toJson(leader));
        */


        //stub
        Resource r = new Resource(0,0,1,0);
        Resource r1 = new Resource(1,0,0,0);

        //Test require amount = 1
        ArrayList<LeaderDevCost> requires1 = new ArrayList<>();
        requires1.add(new LeaderDevCost(CardColor.YELLOW, 2, 1));
        requires1.add(new LeaderDevCost(CardColor.BLUE, 1, 1));

        //leaders card
        LeaderCard leaderCard = new DevLeaderCard(4, new ExtraProductionAbility(
                new ProductionPower(r, r1)),
                requires1);

        //player stub
        //stub devCards
        Resource res1 = new Resource(1,2,3,4);
        Resource res2 = new Resource(1,2,3,1);
        DevelopmentCard dev1 = new DevelopmentCard(10, res1, CardColor.BLUE, 1, new ProductionPower(res1, res2), "");
        DevelopmentCard dev2 = new DevelopmentCard(10, res1, CardColor.BLUE, 2, new ProductionPower(res1, res2), "");
        DevelopmentCard dev3 = new DevelopmentCard(11, res2, CardColor.BLUE, 1, new ProductionPower(res2, res1), "");
        DevelopmentCard dev4 = new DevelopmentCard(11, res2, CardColor.YELLOW, 2, new ProductionPower(res2, res1), "");
        DevelopmentCard dev5 = new DevelopmentCard(11, res2, CardColor.GREEN, 3, new ProductionPower(res2, res1), "");
        DevelopmentCard dev6 = new DevelopmentCard(11, res2, CardColor.GREEN, 3, new ProductionPower(res1, res2), "");
        DevelopmentCard dev7 = new DevelopmentCard(11, res2, CardColor.YELLOW, 1, new ProductionPower(res2, res1), "");

        Player player = new Player("pippo");
        PersonalBoard p = player.getPersonalBoard();
        //player has not devCards
        assertFalse(leaderCard.canBeActivated(player));
        //add corretly devCards
        p.addDevCard(dev1, 0);
        p.addDevCard(dev2,0);
        p.addDevCard(dev3,2);
        assertFalse(leaderCard.canBeActivated(player));
        p.addDevCard(dev4,2);
        assertTrue(leaderCard.canBeActivated(player));

        //Test require amount = 2
        ArrayList<LeaderDevCost> requires2 = new ArrayList<>();
        requires2.add(new LeaderDevCost(CardColor.GREEN, 3, 2));
        LeaderCard leader2 = new DevLeaderCard(4, new ExtraProductionAbility(
                new ProductionPower(r, r1)),
                requires2);

        p.addDevCard(dev5, 2);
        assertFalse(leader2.canBeActivated(player));
        p.addDevCard(dev6, 0);
        assertTrue(leader2.canBeActivated(player));

        //Test require no level only amount
        ArrayList<LeaderDevCost> requires3 = new ArrayList<>();
        requires3.add(new LeaderDevCost(CardColor.YELLOW, 2));
        requires3.add(new LeaderDevCost(CardColor.GREEN, 1));
        LeaderCard leader3 = new DevLeaderCard(4, new ExtraProductionAbility(
                new ProductionPower(r, r1)),
                requires3);
        assertFalse(leader3.canBeActivated(player));
        p.addDevCard(dev7,1);
        assertTrue(leader3.canBeActivated(player));
    }
}