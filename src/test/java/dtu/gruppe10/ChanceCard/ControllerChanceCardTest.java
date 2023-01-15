package dtu.gruppe10.ChanceCard;

import dtu.gruppe10.Player;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Collections.shuffle;
import static org.junit.Assert.*;

public class ControllerChanceCardTest extends TestCase {

    @Test
    public void testDrawMethod() {
        // Creates an instance of ControllerChanceCard
        ControllerChanceCard controller = new ControllerChanceCard();
        // Calls the draw method and store the result in a variable
        ParentChanceCard drawnCard = controller.draw(1);
        // Assert that the ID of the drawn card is as expected
        assertEquals(29, drawnCard.getID());
    }
    @Test
    public void testShuffleMethod(ParentChanceCard[] chanceCards){
            ParentChanceCard[] originalCards = new ParentChanceCard[]{
                    new MoveToCard(1, new int[]{5}),
                    new MoveToCard(2, new int[]{10}),
                    new MoveToCard(3, new int[]{15}),
                    new MoveToCard(4, new int[]{20})
            };
            ParentChanceCard[] shuffledCards = originalCards.clone();
            bland(shuffledCards);

            // Assert that the original and shuffled arrays are not the same
            assertFalse(Arrays.equals(originalCards, shuffledCards));

            // Assert that the elements in the shuffled array are the same as the original array
            Arrays.sort(originalCards);
            Arrays.sort(shuffledCards);
            assertArrayEquals(originalCards, shuffledCards);
        }
    @Test
    public void shuffle_shouldShuffleCards() {
        ParentChanceCard[] cards = {
                new MoveToCard(1, new int[] {1}),
                new GoToJailCard(2),
                new MoveCard(3, 5)
        };
        ParentChanceCard[] shuffledCards = Arrays.copyOf(cards, cards.length);
        bland(shuffledCards);
        boolean isShuffled = false;
        for(int i = 0; i < cards.length; i++){
            if(cards[i] != shuffledCards[i]){
                isShuffled = true;
                break;
            }
        }
        assertTrue(isShuffled);
    }


}