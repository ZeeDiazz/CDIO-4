package dtu.gruppe10.gui;

import dtu.gruppe10.Account;
import dtu.gruppe10.ChanceCard.*;
import dtu.gruppe10.Jail;
import dtu.gruppe10.Player;
import dtu.gruppe10.board.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GUIChanceCard extends JPanel {
        private JLabel cardLabel;
        private ChanceCard card;
        private Player player;
        private Account account;
        private Inventory inventory;

    public GUIChanceCard(Player player, Account account, Inventory inventory) {
        this.player = player;
        this.account = account;
        this.inventory = inventory;

        this.setPreferredSize(new Dimension(200, 300));
        cardLabel = new JLabel();
        cardLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(cardLabel);
        }

        public void updateCard(ChanceCard card) {
            this.card = card;
            if(card instanceof MoveCard) {
                cardLabel.setText("Move " + ((MoveCard) card).getMoveAmount() + " Fields");
            }
            else if(card instanceof MoveToCard) {
                cardLabel.setText("Move to Field " + ((MoveToCard) card).getPositionIndex());
            }
            else if(card instanceof BankMoneyCard) {
                cardLabel.setText("Collect $" + ((BankMoneyCard) card).getAmount() + " from the bank");
            }
            else if(card instanceof GoToJailCard) {
                cardLabel.setText("Go to jail");
            }
            else if(card instanceof GetOutOfJailFreeCard) {
                cardLabel.setText("Get out of jail free card");
            }
            else {
                cardLabel.setText("Unknown card");
            }
        }
}
