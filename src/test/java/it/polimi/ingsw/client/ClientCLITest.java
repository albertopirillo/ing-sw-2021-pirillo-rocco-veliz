package it.polimi.ingsw.client;

import it.polimi.ingsw.exceptions.InvalidKeyException;
import it.polimi.ingsw.exceptions.NegativeResAmountException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.DepotSetting;
import it.polimi.ingsw.network.updates.DevSlotsUpdate;
import it.polimi.ingsw.network.updates.ErrorUpdate;
import it.polimi.ingsw.network.updates.StorageUpdate;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientCLITest {

    @Test
    public void storageTest() {
        Client client = new SocketClient();
        UserInterface cli = new ClientCLI(client);
        cli.setNickname("a");

        Map<String, Resource> strongboxMap =  new HashMap<>();
        strongboxMap.put("a", new Resource(1,2,4,3));
        strongboxMap.put("b", new Resource(3,4,5,3));

        List<DepotSetting> setting1 = new ArrayList<>();
        setting1.add(new DepotSetting(1, ResourceType.COIN, 1));
        setting1.add(new DepotSetting(2, ResourceType.SHIELD, 2));
        setting1.add(new DepotSetting(3, ResourceType.SERVANT, 1));
        List<DepotSetting> setting2 = new ArrayList<>();
        setting2.add(new DepotSetting(1, null, 0));
        setting2.add(new DepotSetting(2, ResourceType.STONE, 1));
        setting2.add(new DepotSetting(3, ResourceType.SERVANT, 3));

        Map<String, List<DepotSetting>> depotMap = new HashMap<>();
        depotMap.put("a", setting1);
        depotMap.put("b", setting2);

        StorageUpdate update = new StorageUpdate("a", depotMap, strongboxMap);
        cli.updateStorages(update);
    }

    @Test
    public void errorTest() {
        Client client = new SocketClient();
        UserInterface cli = new ClientCLI(client);
        ClientError clientError = new ClientError();

        clientError.setException(new InvalidKeyException());
        ErrorUpdate update = new ErrorUpdate("a", clientError);
        cli.displayError(update);

        clientError.setException(new Exception("Print a generic error message"));
        update = new ErrorUpdate("a", clientError);
        cli.displayError(update);
    }

    @Test
    public void devSlotTest() {
        Client client = new SocketClient();
        UserInterface cli = new ClientCLI(client);
        cli.setNickname("a");
        ProductionPower prodPower = new ProductionPower(new Resource(0,1,2,3),
                new Resource(1,2,3,4));

        DevelopmentSlot slot1 = new DevelopmentSlot();
        slot1.addCard(new DevelopmentCard(1,
                new Resource(0, 1, 2, 3),
                CardColor.GREEN,1, prodPower, ""));
        slot1.addCard(new DevelopmentCard(3,
                new Resource(0, 1, 2, 3),
                CardColor.GREEN,2, prodPower, ""));
        slot1.addCard(new DevelopmentCard(3,
                new Resource(0, 1, 2, 3),
                CardColor.GREEN,3, prodPower, ""));

        DevelopmentSlot slot2 = new DevelopmentSlot();
        slot2.addCard(new DevelopmentCard(2,
                new Resource(1,2,3,4),
                CardColor.PURPLE, 1, prodPower, ""));

        DevelopmentSlot slot3 = new DevelopmentSlot();
        slot3.addCard(new DevelopmentCard(3,
                new Resource(0, 1, 2, 3),
                CardColor.BLUE,1, prodPower, ""));

        List<DevelopmentSlot> devSlotList1 = new ArrayList<>();
        devSlotList1.add(slot1);
        devSlotList1.add(slot2);
        devSlotList1.add(slot3);

        List<DevelopmentSlot> devSlotList2 = new ArrayList<>();
        devSlotList2.add(slot1);
        devSlotList2.add(slot2);
        devSlotList2.add(slot3);

        Map<String, List<DevelopmentSlot>> devSlotMap = new HashMap<>();
        devSlotMap.put("a", devSlotList1);
        devSlotMap.put("b", devSlotList2);

        DevSlotsUpdate update = new DevSlotsUpdate("a", devSlotMap);
        cli.updateDevSlots(update);
    }

    @Test
    public void devCardTest() {
        ProductionPower prodPower = new ProductionPower(new Resource(0,1,2,3),
                new Resource(1,2,3,4));

        System.out.println(new DevelopmentCard(1,
                new Resource(0, 1, 2, 3),
                CardColor.GREEN,1, prodPower, ""));
    }

    @Test
    public void resTest() throws NegativeResAmountException {
        Resource resource1 = new Resource(0,0,0,0);
        System.out.println("1 " + resource1);
        Resource resource2 = new Resource(3,2,1,0);
        System.out.println("2 " + resource2);
        Resource resource3 = new Resource(0,2,0,3);
        System.out.println("3 " + resource3);
        Resource resource4 = new Resource();
        System.out.println("4 " + resource4);
        Resource resource5 = new Resource();
        resource5.addResource(ResourceType.STONE, 2);
        resource5.addResource(ResourceType.FAITH, 3);
        resource5.addResource(ResourceType.COIN, 0);
        System.out.println("5 " + resource5);
       }
}
