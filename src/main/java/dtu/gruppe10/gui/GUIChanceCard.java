package dtu.gruppe10.gui;

import dtu.gruppe10.ChanceCard.*;

import javax.swing.*;
import java.awt.*;

public class GUIChanceCard extends JPanel {
    private JLabel cardLabel;
    private ChanceCard card;

    public GUIChanceCard() {
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
