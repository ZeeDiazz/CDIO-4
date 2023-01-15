package dtu.gruppe10.ChanceCard;

import junit.framework.TestCase;
import org.junit.Test;

import static org.junit.Assert.*;

public class InventoryTest extends TestCase {
    @Test
    public void testIfPlayerHasGetOutOfJailFreeCard() {
        Inventory inventory = new Inventory();
        GetOutOfJailFreeCard card = new GetOutOfJailFreeCard(1);

        inventory.addChanceCard(card);
        assertTrue(inventory.hasGetOutOfJailFreeCard());

    }
    @Test
    public void testIfPlayerHasUsedGetOutOfJail(){
        Inventory inventory = new Inventory();
        GetOutOfJailFreeCard card = new GetOutOfJailFreeCard(1);

        inventory.addChanceCard(card);
        assertTrue(inventory.hasGetOutOfJailFreeCard());

        inventory.useGetOutOfJailFreeCard();
        assertFalse(inventory.hasGetOutOfJailFreeCard());
    }
}