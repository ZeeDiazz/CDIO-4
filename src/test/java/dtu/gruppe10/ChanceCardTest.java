package dtu.gruppe10;

import static org.junit.Assert.*;

public class ChanceCardTest {
    public void testChanceCardWithoutChoiceIsNotChoiceCard() {
        ChanceCard card = new ChanceCard();
        card.addAction(new ChanceCardAction(ChanceCardEvent.MOVE_TO, 1));
        card.addAction(new ChanceCardAction(ChanceCardEvent.MOVE_TO, 1));

    }

}