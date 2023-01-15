package dtu.gruppe10.ChanceCard;

import dtu.gruppe10.Player;
import junit.framework.TestCase;
import org.junit.Test;

import static org.junit.Assert.*;

public class ControllerChanceCardTest extends TestCase {

    @Test
    public void testDrawMethod() {
        // Create an instance of ControllerChanceCard
        ControllerChanceCard controller = new ControllerChanceCard();
        // Call the draw method and store the result in a variable
        ParentChanceCard drawnCard = controller.draw(1);
        // Assert that the ID of the drawn card is as expected
        assertEquals(29, drawnCard.getID());
    }


}