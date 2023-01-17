package dtu.gruppe10.ChanceCard;

import dtu.gruppe10.App;
import dtu.gruppe10.Player;
import dtu.gruppe10.board.Board;
import dtu.gruppe10.board.fields.Field;
import dtu.gruppe10.board.fields.StreetField;

public class PerHouseMoneyCard extends MoneyCard {
    protected int amountPerHotel;

    PerHouseMoneyCard(int ID, int amountPerHouse, int amountPerHotel) {
        super(ID, amountPerHouse);

        this.amountPerHotel = amountPerHotel;
    }

    public int getPayAmount(Player player, Board board) {
        int totalHouseCount = 0;
        int totalHotelCount = 0;

        for (Field field : board.getFields()) {
            if (!(field instanceof StreetField street)) {
                continue;
            }
            if (!street.getOwner().equals(player)) {
                continue;
            }

            if (street.getHouseCount() == 5) {
                totalHotelCount++;
            }
            else {
                totalHouseCount += street.getHouseCount();
            }
        }

        return totalHouseCount * amount + totalHotelCount * amountPerHotel;
    }
}

