import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import safariSimulator.main.Models.Point;
import safariSimulator.main.Models.Tile.Tile;

import static org.junit.jupiter.api.Assertions.*;

public class TileTest {
    private Tile tile;
    private Point testPoint;

    @BeforeEach
    public void setUp() {
        testPoint = new Point(3, 5);
        tile = new Tile(testPoint, 100);
    }

    @Test
    public void testConstructorWithParams() {
        assertEquals(testPoint, tile.getPos());
        assertEquals(100, tile.getHealth());
    }

    @Test
    public void testDefaultConstructor() {
        Tile defaultTile = new Tile();
        assertNull(defaultTile.getPos());
        assertEquals(0, defaultTile.getHealth());
    }

    @Test
    public void testGetHealth() {
        assertEquals(100, tile.getHealth());
    }

    @Test
    public void testSetHealth() {
        tile.setHealth(75);
        assertEquals(75, tile.getHealth());
    }

    @Test
    public void testGetPos() {
        assertEquals(testPoint, tile.getPos());
    }
}
