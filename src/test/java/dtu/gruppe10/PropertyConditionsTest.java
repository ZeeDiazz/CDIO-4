package dtu.gruppe10;

import dtu.gruppe10.FieldTypes.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SuppressWarnings("SpellCheckingInspection")
public class PropertyConditionsTest {

    @Before
    public void initialize() {
        Property field = new Property("Rødovrevej", 1200, PropertyColor.GREEN, 50, 250);
    }

    @Test
    public void propertyDoesNotHaveOwnerAtFirst() {
        Property field = new Property("Rødovrevej", 1200, PropertyColor.GREEN, 50, 250);
        assertEquals(null, field.getOwner());
    }

    @Test
    public void playerBecomesOwnerWhenBuyingProperty() {
        Player player = new Player("Ziggy", 10000, 1);
        Property field = new Property("Rødovrevej", 1200, PropertyColor.GREEN, 50, 250);
        field.buyProperty(player);
        assertTrue(field.getOwner() == player);
    }

    @Test
    public void originalBuyerIsOwnerEvenIfNewPlayerTriesToBuy() {
        Player player = new Player("Ziggy", 10000, 1);
        Player player2 = new Player("Leon", 12000, 2);
        Property field = new Property("Rødovrevej", 1200, PropertyColor.GREEN, 50, 250);
        field.buyProperty(player);
        field.buyProperty(player2);
        assertTrue(field.getOwner() == player);
    }

    @Test
    public void playerPaysForPropertyWhenBuying() {
        Player player = new Player("Ziggy", 10000, 1);
        Property field = new Property("Rødovrevej", 1200, PropertyColor.GREEN, 50, 250);
        field.buyProperty(player);
        assertEquals(10000 - 1200, player.getBalance());
    }

    @Test
    public void secondPlayerWhoTriesToBuyDoesNotPayWhenPropertyIsOwnedBuyAnotherPlayer() {
        Player player = new Player("Ziggy", 10000, 1);
        Player player2 = new Player("Leon", 12000, 2);
        Property field = new Property("Rødovrevej", 1200, PropertyColor.GREEN, 50, 250);
        field.buyProperty(player);
        field.buyProperty(player2);
        assertEquals(12000, player2.getBalance());
    }


    @Test
    public void rentIsEqualRent0() {
        int rent0 = 583;
        Property field = new Property("Rødovrevej", 1200, PropertyColor.GREEN, rent0, 250);
        assertEquals(rent0, field.getCurrentRent());
    }

    @Test
    public void playerBuysPropertyWhenLandingOnFieldAndNoOneOwnsProperty() {
        Player player = new Player("Ziggy", 10000, 1);
        Property field = new Property("Rødovrevej", 1200, PropertyColor.GREEN, 50, 250);
        field.whenLandedOn(player);
        assertEquals(field.getOwner(), player);
        assertEquals(8800, player.getBalance());
    }

    @Test
    public void playerPaysRentToOwnerWhenLandingOnFieldThatsAlreadyOwnedByAnotherPlayer() {
        Player player = new Player("Ziggy", 10000, 1);
        Player player2 = new Player("Leon", 12000, 2);
        int rent0ForProperty = 500;
        Property field = new Property("Rødovrevej", 1200, PropertyColor.GREEN, rent0ForProperty, 250);
        field.whenLandedOn(player);
        field.whenLandedOn(player2);
        assertEquals(10000 - 1200 + 500, player.getBalance());
        assertEquals(12000 - 500, player2.getBalance());
    }

    @Test
    public void playerCantBuyIfPlayerHasNotLandedOnField() {
        Player player = new Player("Ziggy", 10000, 1);
        Property field = new Property("Rødovrevej", 1200, PropertyColor.GREEN, 50, 250);
        field.buyProperty(player);
        assertEquals(field.getOwner(), null);
        assertEquals(10000, player.getBalance());

    }


}
