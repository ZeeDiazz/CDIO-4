package dtu.gruppe10;

import junit.framework.TestCase;

public class PrisonFieldTest extends TestCase {

    public void testIfPlayerIsInPrison() {
        Player player = new Player("Felix", 12345, 2);

        player.setField("PrisonField");

        assertEquals("PrisonField", player.getField());

        assertTrue(player.isInPrison());

    }
    public void testIfPlayerIsNotInPrison(){
        Player player = new Player ("Felix", 12345, 3);

        player.setField("Rødovrevej");

        AssertFalse(player.isInPrison());
    }


    public void testInPrisonEffect() {
        Player player = new Player("Felix", 123456, 1);
        player.setField("Prison");

        field.setEffect("something");

        assertEquals("something", field.getEffect());
    }

    public void testIfPlayerCanMoveInPrison() {
        Player player = new Player ("Felix", 1234567, 3);

        player.setField("Prison");

        field.setEffect("NoMovement");

        assertEquals("NoMovement", field.getEffect());
    }
}