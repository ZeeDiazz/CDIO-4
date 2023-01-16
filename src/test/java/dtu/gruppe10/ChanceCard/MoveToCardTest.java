import dtu.gruppe10.ChanceCard.MoveToCard;
import dtu.gruppe10.Player;
import dtu.gruppe10.board.Board;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class MoveToCardTest extends TestCase {
    private MoveToCard moveToCard;
    private Player player;
    private Board board;

    @Before
    public void setUp() {
        moveToCard = new MoveToCard(1, 5);;
    }

    @Test
    public void testGetPositionIndex() {
        int expectedPositionIndex = 5;
        int actualPositionIndex = moveToCard.getPositionIndex();
        assertEquals(expectedPositionIndex, actualPositionIndex);
    }
}