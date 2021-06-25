package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.DevSlotEmptyException;
import it.polimi.ingsw.exceptions.InvalidNumSlotException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersonalBoardTest {

    @Test
    public void getAllCards() throws InvalidNumSlotException, DevSlotEmptyException {
        //stub devCards
        Resource res1 = new Resource(1,2,3,4);
        Resource res2 = new Resource(1,2,3,1);
        DevelopmentCard dev1 = new DevelopmentCard(10, res1, CardColor.BLUE, 1, new ProductionPower(res1, res2), "");
        DevelopmentCard dev2 = new DevelopmentCard(10, res1, CardColor.BLUE, 2, new ProductionPower(res1, res2), "");
        DevelopmentCard dev3 = new DevelopmentCard(11, res2, CardColor.BLUE, 1, new ProductionPower(res2, res1), "");

        Player player = new Player("pippo");
        PersonalBoard p = player.getPersonalBoard();
        //player has not devCards
        List<DevelopmentCard> allCards = p.getAllCards();
        assertNotNull(allCards);
        assertEquals(0, allCards.size());
        //added devCard correctly.. slot 1 is empty
        assertEquals(0, p.getAllCards().size());
        p.addDevCard(dev1, 0);
        p.addDevCard(dev2,0);
        assertEquals(2, p.getAllCards().size());
        p.addDevCard(dev3,2);
        allCards = p.getAllCards();
        assertNotNull(allCards);
        assertEquals(3, allCards.size());
        assertEquals(dev2, allCards.get(0));
        assertEquals(dev1, allCards.get(1));
        assertEquals(dev3, allCards.get(2));
    }

    @Test
    public void addDevCard() throws InvalidNumSlotException, DevSlotEmptyException {
        //stub devCards
        Resource res1 = new Resource(1,2,3,4);
        Resource res2 = new Resource(1,2,3,1);
        DevelopmentCard dev1 = new DevelopmentCard(10, res1, CardColor.BLUE, 1, new ProductionPower(res1, res2), "");
        DevelopmentCard dev2 = new DevelopmentCard(10, res1, CardColor.GREEN, 2, new ProductionPower(res1, res2), "");
        DevelopmentCard dev3 = new DevelopmentCard(11, res2, CardColor.YELLOW, 2, new ProductionPower(res2, res1), "");
        DevelopmentCard dev4 = new DevelopmentCard(11, res2, CardColor.PURPLE, 3, new ProductionPower(res2, res1), "");
        Player player = new Player("pippo");
        PersonalBoard p = player.getPersonalBoard();
        assertThrows(DevSlotEmptyException.class, () -> p.addDevCard(dev2,0));
        assertThrows(DevSlotEmptyException.class, () -> p.addDevCard(dev3,0));
        p.addDevCard(dev1,0);
        assertEquals(dev1,p.getSlot(0).getTopCard());
        assertThrows(InvalidNumSlotException.class, () -> p.addDevCard(dev4,0));
        p.addDevCard(dev2,0);
        assertEquals(dev2,p.getSlot(0).getTopCard());
        assertEquals(2, p.getSlot(0).numberOfElements());
        assertEquals(0, p.getSlot(1).numberOfElements());
        assertEquals(0, p.getSlot(2).numberOfElements());
        p.addDevCard(dev1,1);
        p.addDevCard(dev1,2);
        assertEquals(dev1,p.getSlot(1).getTopCard());
        assertEquals(dev1,p.getSlot(2).getTopCard());
        assertThrows(InvalidNumSlotException.class, () -> p.addDevCard(dev4,1));
        assertThrows(InvalidNumSlotException.class, () -> p.addDevCard(dev4,2));
        assertEquals(1, p.getSlot(1).numberOfElements());
        assertEquals(1, p.getSlot(2).numberOfElements());
        assertEquals(4, p.getAllCards().size());
    }

}