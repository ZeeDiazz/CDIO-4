package dtu.gruppe10.board.fields;

import dtu.gruppe10.ChanceCard.ChanceCard;
import dtu.gruppe10.ChanceCard.ControllerChanceCard;
import dtu.gruppe10.Player;

import java.util.Queue;

public class ChanceField extends Field {
    private ControllerChanceCard controller;

    public ChanceField(int id) {
        super(id, FieldType.CHANCE);
        this.controller = new ControllerChanceCard();

    }

    public ChanceCard draw() {
        return this.controller.draw();
    }

}
