package dtu.gruppe10.board;

import junit.framework.TestCase;
import org.junit.Test;

public class PlayerMovementTest extends TestCase {
    @Test
    public void testGetMoveAmount() {
        //Move Forward
        PlayerMovement playerMovement1 = PlayerMovement.ForwardMove(0,4);

        //Player moves from 0 to 4 (Moves 3 Fields)
        assertEquals(3, playerMovement1.getMoveAmount(4));

        //Move Backwards
        PlayerMovement playerMovement2 = PlayerMovement.BackwardMove(22,17);

        //Players moves from  22 to 17 (Moves 4 Fields)
        assertEquals(4, playerMovement2.getMoveAmount(29));

        //Move DirectMove
        PlayerMovement playerMovement3 = PlayerMovement.DirectMove(4,30);

        //Because you move directly to the field
        assertEquals(0,playerMovement3.getMoveAmount(34));

        // Move pass Start
        PlayerMovement playerMovement4 = PlayerMovement.ForwardMove(38,5);

        assertEquals(7,playerMovement4.getMoveAmount(43));

    }
    @Test
    public void testGetFieldIndexes() {
    }
    @Test
    public void testForwardMove() {
    }
    @Test
    public void testBackwardMove() {
    }
    @Test
    public void testDirectMove() {
    }
}