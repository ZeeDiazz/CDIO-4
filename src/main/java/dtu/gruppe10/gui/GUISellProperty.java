package dtu.gruppe10.gui;

import dtu.gruppe10.Game;
import dtu.gruppe10.board.Board;

import javax.swing.*;

public class GUISellProperty extends JFrame{
    private GUIPlayer[] players;
    private GUIBalances balances;
    private Game game;
    private Board board;
    public GUISellProperty(GUIPlayer[] players, GUIBalances balances){
        super("Sell Property");
        this.players = players;
        this.balances = balances;

        //makes a new window
        setSize(500, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }
}
