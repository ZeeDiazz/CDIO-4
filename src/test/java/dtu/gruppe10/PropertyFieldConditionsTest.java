package dtu.gruppe10;

import dtu.gruppe10.fields.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class PropertyFieldConditionsTest {
    PropertyField field;
    @Before
    public void initialize() {
        field = new PropertyField("Rødovrevej", 1200, StreetColor.GREEN, 50, 250);
    }

    @Test
    public void propertyDoesNotHaveOwnerAtFirst() {
        assertEquals(null, field.getOwner());
    }

    @Test
    public void playerBecomesOwnerWhenBuyingProperty() {
        Player player = new Player("Ziggy", 10000, 1);
        PropertyField field = new PropertyField("Rødovrevej", 1200, StreetColor.GREEN, 50, 250);
        field.buyProperty(player);
        assertTrue(field.getOwner() == player);
    }

    @Test
    public void originalBuyerIsOwnerEvenIfNewPlayerTriesToBuy() {
        Player player = new Player("Ziggy", 10000, 1);
        Player player2 = new Player("Leon", 12000, 2);
        PropertyField field = new PropertyField("Rødovrevej", 1200, StreetColor.GREEN, 50, 250);
        field.buyProperty(player);
        field.buyProperty(player2);
        assertTrue(field.getOwner() == player);
    }

    @Test
    public void playerPaysForPropertyWhenBuying() {
        Player player = new Player("Ziggy", 10000, 1);
        PropertyField field = new PropertyField("Rødovrevej", 1200, StreetColor.GREEN, 50, 250);
        field.buyProperty(player);
        assertEquals(10000 - 1200, player.Account.getBalance());
    }

    @Test
    public void secondPlayerWhoTriesToBuyDoesNotPayWhenPropertyIsOwnedBuyAnotherPlayer() {
        Player player = new Player("Ziggy", 10000, 1);
        Player player2 = new Player("Leon", 12000, 2);
        PropertyField field = new PropertyField("Rødovrevej", 1200, StreetColor.GREEN, 50, 250);
        field.buyProperty(player);
        field.buyProperty(player2);
        assertEquals(12000, player2.Account.getBalance());
    }


    @Test
    public void rentIsEqualRent0() {
        int rent0 = 583;
        PropertyField field = new PropertyField("Rødovrevej", 1200, StreetColor.GREEN, rent0, 250);
        assertEquals(rent0, field.getCurrentRent());
    }

    @Test
    public void playerBuysPropertyWhenLandingOnFieldAndNoOneOwnsProperty() {
        Player player = new Player("Ziggy", 10000, 1);
        PropertyField field = new PropertyField("Rødovrevej", 1200, StreetColor.GREEN, 50, 250);
        field.whenLandedOn(player);
        assertEquals(field.getOwner(), player);
        assertEquals(8800, player.Account.getBalance());
    }

    @Test
    public void playerPaysRentToOwnerWhenLandingOnFieldThatsAlreadyOwnedByAnotherPlayer() {
        Player player = new Player("Ziggy", 10000, 1);
        Player player2 = new Player("Leon", 12000, 2);
        int rent0ForProperty = 500;
        PropertyField field = new PropertyField("Rødovrevej", 1200, StreetColor.GREEN, rent0ForProperty, 250);
        field.whenLandedOn(player);
        field.whenLandedOn(player2);
        assertEquals(10000 - 1200 + 500, player.Account.getBalance());
        assertEquals(12000 - 500, player2.Account.getBalance());
    }

    @Test
    public void playerCantBuyIfPlayerHasNotLandedOnField() {
        Player player = new Player("Ziggy", 10000, 1);
        PropertyField field = new PropertyField("Rødovrevej", 1200, StreetColor.GREEN, 50, 250);
        field.buyProperty(player);
        assertEquals(field.getOwner(), null);
        assertEquals(10000, player.Account.getBalance());

    }

    @Test
    public void breweryFieldHandleRentCorrectlyWhenLandingOnField() {
        Player player = new Player("Ziggy", 10000, 1);
        Player player2 = new Player("Felix", 5000, 1);
        int rent0 = 50;
        BreweryField field = new BreweryField("Rødovrevej", 1200, StreetColor.GREEN, rent0, 100);
        int diceSum = 10;
        field.whenLandedOn(player);
        field.whenLandedOn(player2,diceSum);
        assertEquals((10000-1200)+rent0*diceSum, player.Account.getBalance());
        assertEquals(5000-rent0*diceSum, player2.Account.getBalance());
    }

    @Test
    public void allPropertyTypesPaysRentCorrectly(){
        StreetField street = new StreetField("Rødovrevej",1200, StreetColor.RED,100,0,0,0,0,0);
        FerryField ferry = new FerryField("Helsingør - Helsingborg",4000, StreetColor.GREEN,500,0,0,0);
        BreweryField brewery = new BreweryField("Tuborg Squash",3000, StreetColor.ORANGE,100,0);
        Player player1 = new Player("Ziggy", 10000, 1);
        Player player2 = new Player("Felix", 5000, 1);
        // Spiller 1 køber alle tre typer felter
        street.whenLandedOn(player1);
        ferry.whenLandedOn(player1);
        brewery.whenLandedOn(player1);

        // Spiller 2 lander på alle typer felter
        // Tester samtidigt om lejen er håndteret korrekt ved efter hver type felt, spiller 2 lander på

        // Street
        street.whenLandedOn(player2);
        int balanceAfterBuyingFields = 10000-1200-4000-3000;
        assertEquals(balanceAfterBuyingFields+100,player1.Account.getBalance());
        assertEquals(5000-100,player2.Account.getBalance());

        // Ferry
        ferry.whenLandedOn(player2);
        assertEquals(balanceAfterBuyingFields+100+500,player1.Account.getBalance());
        assertEquals(5000-100-500,player2.Account.getBalance());

        // Brewery
        int diceSum = 10;
        brewery.whenLandedOn(player2,diceSum);
        assertEquals(balanceAfterBuyingFields+100+500+(100*10),player1.Account.getBalance());
        assertEquals(5000-100-500-(100*10),player2.Account.getBalance());
    }



}
