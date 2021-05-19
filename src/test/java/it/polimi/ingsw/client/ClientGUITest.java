package it.polimi.ingsw.client;

import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.network.DepotSetting;
import it.polimi.ingsw.network.updates.StorageUpdate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientGUITest {

    private static ClientGUI gui;

    @BeforeAll
    public static void setup() {
        new Thread(() -> {
            Client client = new Client();
            gui = new ClientGUI(client);
            gui.setup();
        }).start();
    }

    @Test
    public void storageTest() {
        Map<String, Resource> strongboxMap = new HashMap<>();
        strongboxMap.put("Player 1", new Resource(1, 2, 4, 3));
        strongboxMap.put("Player 2", new Resource(3, 4, 5, 3));

        List<DepotSetting> setting1 = new ArrayList<>();
        setting1.add(new DepotSetting(1, ResourceType.COIN, 1));
        setting1.add(new DepotSetting(2, ResourceType.SHIELD, 2));
        setting1.add(new DepotSetting(3, ResourceType.SERVANT, 1));
        List<DepotSetting> setting2 = new ArrayList<>();
        setting2.add(new DepotSetting(1, null, 0));
        setting2.add(new DepotSetting(2, ResourceType.STONE, 1));
        setting2.add(new DepotSetting(3, ResourceType.SERVANT, 3));

        Map<String, List<DepotSetting>> depotMap = new HashMap<>();
        depotMap.put("Player 1", setting1);
        depotMap.put("Player 2", setting2);

        StorageUpdate update = new StorageUpdate("a", depotMap, strongboxMap);
    }
}
