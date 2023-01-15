package dtu.gruppe10.gui;

import dtu.gruppe10.Game;
import dtu.gruppe10.Player;
import dtu.gruppe10.board.Board;
import dtu.gruppe10.board.fields.Field;
import dtu.gruppe10.board.fields.PropertyField;
import dtu.gruppe10.board.fields.StreetField;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GUISellProperty extends JFrame{
    private GUIPlayer[] players;
    private GUIBalances balances;
    private Game game;
    private Board board;
    private JComboBox<Field> propertySelector;
    private JComboBox<GUIPlayer> buyerSelector;
    private JTextField priceField;
    private JButton sellButton;
    public GUISellProperty(GUIPlayer[] players, GUIBalances balances){
        super("Sell Property");

        this.players = players;
        this.balances = balances;

        //makes a new window
        setSize(500, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //JPanel
        JPanel sellPanel = new JPanel();
        sellPanel.setLayout(new BoxLayout(sellPanel, BoxLayout.PAGE_AXIS));

        //Creates a label for property selection
        JLabel propertySelectLabel = new JLabel("Select Property:");
        sellPanel.add(propertySelectLabel);

        //Creates a box for properties that seller owns
        propertySelector = new JComboBox<>();
        GUIPlayer currentPlayer = players[game.getCurrentPlayerTurn()];
        for (PropertyField property : board.getProperty(currentPlayer.ID)) {
            if (property.getOwner().ID == currentPlayer.ID) {
                propertySelector.addItem(property);
            }
        }
        sellPanel.add(propertySelector);

        //Creates a label for choosing a buyer
        JLabel buyerSelectLabel = new JLabel("Select Buyer:");
        sellPanel.add(buyerSelectLabel);


        //Creates a box for buyers
        buyerSelector = new JComboBox<>();
        for (GUIPlayer player : players) {
            if (player.ID != currentPlayer.ID) {
                buyerSelector.addItem(player);
            }
        }
        sellPanel.add(buyerSelector);

        //Creates a label for price input
        JLabel priceLabel = new JLabel("Price:");
        sellPanel.add(priceLabel);

        //Players and write a price
        priceField = new JTextField();
        sellPanel.add(priceField);

        //Creates a sell button
        JButton sellButton = new JButton("Sell");
        sellButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int price = Integer.parseInt(priceField.getText());
                GUIPlayer buyer = (GUIPlayer) buyerSelector.getSelectedItem();
                PropertyField property = (PropertyField) propertySelector.getSelectedItem();
                if (balances.getPlayerBalance(buyer.ID) < price) {
                    JOptionPane.showMessageDialog(null, "The Buyer does not have enough money.", "Invalid balance", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (property == null || buyer == null || price <= 0) {
                    JOptionPane.showMessageDialog(null, "Please select a price above 0.", "Invalid input", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (game.getCurrentPlayer().ID != currentPlayer.ID) {
                    JOptionPane.showMessageDialog(null, "It's not your turn, wait for your turn.", "Invalid turn", JOptionPane.ERROR_MESSAGE);
                    return;
                }


                balances.playerLostMoney(buyer.ID, price);
                balances.playerMadeMoney(currentPlayer.ID, price);

                //Need to make the buyer the owner
                setOwnerId(property.ID,buyer.ID);

                JOptionPane.showMessageDialog(null, "Property sold!", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }
        });
        sellPanel.add(sellButton);
        add(sellPanel);

    }
    private void setOwnerId(int fieldId, int newOwnerId) {
        PropertyField property = (PropertyField) board.getFieldAt(fieldId);
        property.setOwnerId(newOwnerId);
    }
}
