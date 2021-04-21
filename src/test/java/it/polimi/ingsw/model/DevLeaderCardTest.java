package it.polimi.ingsw.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.exceptions.InvalidKeyException;
import it.polimi.ingsw.exceptions.NegativeResAmountException;
import it.polimi.ingsw.utils.LeaderAbilityDeserializer;
import it.polimi.ingsw.utils.LeaderCardJsonDeserializer;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

class DevLeaderCardTest {

    @Test
    void parserTest() throws FileNotFoundException{
        //register adapter
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(LeaderAbility.class, new LeaderAbilityDeserializer());
        builder.registerTypeAdapter(LeaderCard.class, new LeaderCardJsonDeserializer());
        Gson gson = builder.create();
        Type listType = new TypeToken<List<LeaderCard>>(){}.getType();
        //leaders contains the 16 leader cards...
        List<LeaderCard> leaders = gson.fromJson(new JsonReader(new FileReader("src/main/resources/LeaderCardsConfig.json")), listType);

    }
    @Test
    void canBeActivated() throws NegativeResAmountException, InvalidKeyException {

        //parse JSon
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
        ArrayList<LeaderCard> leader = new ArrayList<>();
        ArrayList<LeaderDevCost> requires1 = new ArrayList<>();
        requires1.add(new LeaderDevCost(CardColor.YELLOW, 2, 1));
        ArrayList<LeaderDevCost> requires2 = new ArrayList<>();
        requires2.add(new LeaderDevCost(CardColor.BLUE, 2, 1));
        Resource r = new Resource();
        r.addResource(ResourceType.SHIELD, 1);
        Resource r1 = new Resource();
        r1.addResource(ResourceType.FAITH, 2);
        Resource r2 = new Resource();
        r2.addResource(ResourceType.SERVANT, 1);

        //leaders cards
        leader.add(new DevLeaderCard(4,
                new ExtraProduction(new ProductionPower(r, r1))
                , requires1));
        leader.add(new DevLeaderCard(4,
                new ExtraProduction(new ProductionPower(r2, r1))
                , requires2));

        //player's dev cards,stub
        List<DevelopmentCard> devs = new ArrayList<>();
        Resource res1 = new Resource();
        Resource res2 = new Resource();
        Resource res3 = new Resource();
        Resource res4 = new Resource();
        res1.addResource(ResourceType.SHIELD, 1);
        res2.addResource(ResourceType.FAITH, 1);
        res3.addResource(ResourceType.COIN, 3);
        res3.addResource(ResourceType.STONE, 2);
        devs.add(new DevelopmentCard(1, new Resource(0, 0, 2, 0),
                CardColor.YELLOW, 2, new ProductionPower(res1, res2)));
        devs.add(new DevelopmentCard(3, new Resource(0, 0, 0, 3),
                CardColor.PURPLE, 3, new ProductionPower(
                res1,
                new Resource(1, 0, 1, 1))));
        devs.add(new DevelopmentCard(3, res3,
                CardColor.BLUE, 1, new ProductionPower(res4,
                new Resource(0, 1, 1, 1))));

        /*assertTrue(leader.get(0).canBeActivated(devs));
        devs.remove(0); //TODO: new tests
        assertFalse(leader.get(0).canBeActivated(devs));*/
    }
}