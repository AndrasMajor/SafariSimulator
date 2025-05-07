import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import safariSimulator.main.Models.Entity.Animal.Herbivore.Zebra;
import safariSimulator.main.Models.Point;
import safariSimulator.main.Models.Tile.Tile;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ZebraTest {

    private Zebra zebra;

    @BeforeEach
    public void setUp() {
        zebra = new Zebra(new Point());
    }

    @Test
    public void testHerbivore() {
        assertTrue(zebra.isHerbivore());
    }

    @Test
    public void testAge() {
        assertEquals(0, zebra.getAge());
        zebra.ageUp();
        assertEquals(1, zebra.getAge());
    }

    @Test
    public void testHealthModification() {
        assertEquals(500, zebra.getHealth());
        zebra.decreaseHealth(200);
        assertEquals(300, zebra.getHealth());
        zebra.decreaseHealth(400);
        assertEquals(0, zebra.getHealth());
        zebra.increaseHealth(50);
        assertEquals(50, zebra.getHealth());
        zebra.increaseHealth(100);
        assertEquals(100, zebra.getHealth());
    }

    @Test
    public void testFoodLevel() {
        assertEquals(100, zebra.getFoodLevel());
        zebra.decreaseFoodLevel(30);
        assertEquals(70, zebra.getFoodLevel());
        zebra.decreaseFoodLevel(100);
        assertEquals(0, zebra.getFoodLevel());
    }

    @Test
    public void testWaterLevel() {
        assertEquals(100, zebra.getWaterLevel());
        zebra.decreaseWaterLevel(20);
        assertEquals(80, zebra.getWaterLevel());
        zebra.decreaseWaterLevel(100);
        assertEquals(0, zebra.getWaterLevel());
        zebra.drink();
        assertEquals(30, zebra.getWaterLevel());
        zebra.drink();
        zebra.drink();
        zebra.drink();
        zebra.drink();
        zebra.drink();
        zebra.drink();
        zebra.drink();
        assertEquals(100, zebra.getWaterLevel());
    }

    @Test
    public void testWaterMemoryUpdate() {
        Tile tile = new Tile(new Point(0, 0), 100);
        assertTrue(zebra.getWaterMemory().isEmpty());
        zebra.updateWaterMemory(tile);
        List<Tile> memory = zebra.getWaterMemory();
        assertEquals(1, memory.size());
        assertTrue(memory.contains(tile));
        zebra.updateWaterMemory(tile);
        assertEquals(1, memory.size());
    }

    @Test
    public void testGrassMemoryUpdate() {
        Tile tile = new Tile(new Point(1, 1), 100);
        assertTrue(zebra.getGrassMemory().isEmpty());
        zebra.updateGrassMemory(tile);
        List<Tile> memory = zebra.getGrassMemory();
        assertEquals(1, memory.size());
        assertTrue(memory.contains(tile));
        zebra.updateGrassMemory(tile);
        assertEquals(1, memory.size());
    }

    @Test
    public void testLeaderAssignment() {
        Zebra leaderZebra = new Zebra(new Point(5, 5));
        assertNull(zebra.getLeader());
        zebra.setLeader(leaderZebra);
        assertEquals(leaderZebra, zebra.getLeader());
    }

    @Test
    public void testMaxAgeAccess() {
        assertEquals(500, zebra.getMaxAge());
    }

    @Test
    public void testFood(){
        assertEquals(100, zebra.getFoodLevel());
        zebra.decreaseFoodLevel(20);
        assertEquals(80, zebra.getFoodLevel());
        zebra.decreaseFoodLevel(100);
        assertEquals(0, zebra.getFoodLevel());
        zebra.graze();
        assertEquals(30, zebra.getFoodLevel());
        zebra.graze();
        zebra.graze();
        zebra.graze();
        zebra.graze();
        zebra.graze();
        assertEquals(100, zebra.getFoodLevel());
    }
}
