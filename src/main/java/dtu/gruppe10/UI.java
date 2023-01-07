package dtu.gruppe10;

import dtu.gruppe10.FieldTypes.Field;

public interface UI {

    // Spiller
    void displayNameOnPlayer(Player player);

    void displayPlayerAccount(Account playerAccount);

    void displayPlayerColor(Player playerColor);

    void displayPlayers(Player[] players);

    void displayPlayerAccounts(Account[] accounts);

    // Ejendomme
    void displayPrice(Field fieldPrice);

    void displayPropertyName(Field fieldName);

    void displayPropertyLogo(Field field);

    void displayProperties(Field[] fields);

    void displayInformationAboutRentOnProperty(Field field);

    void displayOwnerOfProperty(Field fieldOwner);

    void displayPledgedProperty(Field field);

    void displayPledgedProperties(Field[] pledgedFields);

    // Game, Diverse

    void displayGameText();

    void buttonToRollDice(Player player);

    // Property-klassen har en metoden der sørger for købet af en ejendom. Metoden har selv en
    // spiller som paramter.

    void buttonToBuyProperty(Field field);

    void buttonToBuildHouses(Field field);

    void buttonToSellHouses(Field field);

    void buttonToRefuseBuyingProperty(Field field);

    void buttonToInitiateTradeWithPlayer(Player player, Player playerToTradeWith);

    void buttonToRollDiceInJail(Player player);

    void buttonToPayJailBail(Player player);

    // Tænker også at det blot skal være Field-klassen der klarer dette
    void buttonToPledgeProperty(Field field);

    void buttonToEndTurn(Player player);


}
