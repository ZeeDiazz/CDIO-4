package dtu.gruppe10.ChanceCard;

import dtu.gruppe10.App;
import dtu.gruppe10.Player;
import dtu.gruppe10.board.fields.Field;
import dtu.gruppe10.board.fields.StreetField;

public class PerHouseMoneyCard extends MoneyCard {
    int amountPerHouse;
    int amountPerHotel;
    Player player;

    PerHouseMoneyCard(int ID, int amountPerHouse, int amountPerHotel) {
        super(ID);
        this.amountPerHouse = amountPerHouse;
        this.amountPerHotel = amountPerHotel;
    }


    public int amountOfHouses(Player player) {
        int houses = 0;

        for (Field field : App.game.Board.getFields()) {
            if (!(field instanceof StreetField)) {
            } else if (((StreetField) field).getOwner() == player) {
                if (!(((StreetField) field).getHouseCount() == 5)) {
                    houses = houses + (((StreetField) field).getHouseCount());
                }
            }
        }
        return houses;
    }

    public int amountOfHotels(Player player) {
        int hotels = 0;

        for (Field field : App.game.Board.getFields()) {
            if (!(field instanceof StreetField)) {
            } else if (((StreetField) field).getOwner() == player) {
                if (((StreetField) field).getHouseCount() == 5) {
                    hotels = hotels + 1;
                }
            }
        }
        return hotels;
    }


    public int getAmount(Player player) {

        int houses = amountOfHouses(player) * amountPerHouse;
        int hotels = amountOfHotels(player) * amountPerHotel;

        return houses * hotels;
    }
}

