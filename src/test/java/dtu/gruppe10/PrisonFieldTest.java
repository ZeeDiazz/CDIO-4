package dtu.gruppe10;

import junit.framework.TestCase;

public class PrisonFieldTest extends TestCase {

    public void testIfPlayerIsInPrison() {
        Player player = new Player("Felix", 12345, 2);

        player.setPosition(31);

        assertEquals(31, player.getPosition());

        assertTrue(player.InPrison());

    }
    public void testIfPlayerIsNotInPrison(){
        Player player = new Player ("Felix", 12345, 3);

        player.setPosition(2);

        assertFalse(player.InPrison());
    }


    public void testInPrisonEffect() {
        Player player = new Player("Felix", 123456, 1);
        player.setPosition(31);

        //field.setEffect("something");

        //assertEquals(31, field.getEffect());
    }

    public void testIfPlayerCanMoveInPrison() {
        Player player = new Player ("Felix", 1234567, 3);

        //player.setField("Prison");

        //field.setEffect("NoMovement");

        //assertEquals("NoMovement", field.getEffect());
    }
}