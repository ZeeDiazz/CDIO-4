package dtu.gruppe10.ChanceCard;

import org.junit.Test;

import static org.junit.Assert.*;

public class DrawTest {

        @Test
        public void testDraw() {
            ControllerChanceCard controller = new ControllerChanceCard();
            ChanceCard[] cards = new ChanceCard[3];
            cards[0] = new MoveToCard(5, 2);
            cards[1] = new MoveToCard(3, 2);
            cards[2] = new MoveToCard(10,2);
            Draw draw = new Draw();
            ChanceCard drawnCard = draw.drawRandom(cards);

            // check that the returned card is one of the cards in the array
            boolean found = false;
            for (ChanceCard card : cards) {
                if (card == drawnCard) {
                    found = true;
                    break;
                }
            }
            assertTrue(found);

        }

}