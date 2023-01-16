package dtu.gruppe10.ChanceCard;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static java.util.Collections.shuffle;
import static org.junit.Assert.*;

public class ControllerChanceCardTest extends TestCase {
    @Before
    public void init(){
        controller = new ControllerChanceCard();
    }
    ControllerChanceCard controller;


    @Test
    public void testDrawMethod() {
        // Creates an instance of ControllerChanceCard
        ControllerChanceCard controller = new ControllerChanceCard();
        // Calls the draw method and store the result in a variable
        ChanceCard drawnCard = controller.drawFirstCard();
        // Assert that the ID of the drawn card is as expected
        assertEquals(29, drawnCard.getID());

    }

    @Test
    public void testShuffleMethod(){
        controller = new ControllerChanceCard();
            ChanceCard[] originalCards = new ChanceCard[]{
                    new MoveToCard(1, 2),
                    new MoveToCard(3, 2),
                    new MoveToCard(4, 2),
                    new MoveToCard(5, 2),
            };
            ChanceCard[] shuffledCards = originalCards.clone();
            controller.bland(shuffledCards);

            // Assert that the original and shuffled arrays are not the same
            assertFalse(Arrays.equals(originalCards, shuffledCards));

            // Assert that the elements in the shuffled array are the same as the original array
            Arrays.sort(originalCards);
            Arrays.sort(shuffledCards);
            assertArrayEquals(originalCards, shuffledCards);
        }

}