import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import safariSimulator.main.Models.Entity.Animal.Herbivore.Elephant;
import safariSimulator.main.Models.Point;
import safariSimulator.main.Models.Tile.Tile;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ElephantTest {

    private Elephant elephant;

    @BeforeEach
    public void setUp() {
        elephant = new Elephant(new Point());
    }

    @Test
    public void testHerbivore() {
        assertTrue(elephant.isHerbivore());
    }

    @Test
    public void testAge() {
        assertEquals(0, elephant.getAge());
        elephant.ageUp();
        assertEquals(1, elephant.getAge());
    }

    @Test
    public void testHealthModification() {
        assertEquals(500, elephant.getHealth());
        elephant.decreaseHealth(200);
        assertEquals(300, elephant.getHealth());
        elephant.decreaseHealth(400);
        assertEquals(0, elephant.getHealth());
        elephant.increaseHealth(50);
        assertEquals(50, elephant.getHealth());
        elephant.increaseHealth(100);
        assertEquals(100, elephant.getHealth());
    }

    @Test
    public void testFoodLevel() {
        assertEquals(100, elephant.getFoodLevel());
        elephant.decreaseFoodLevel(30);
        assertEquals(70, elephant.getFoodLevel());
        elephant.decreaseFoodLevel(100);
        assertEquals(0, elephant.getFoodLevel());
    }

    @Test
    public void testWaterLevel() {
        assertEquals(100, elephant.getWaterLevel());
        elephant.decreaseWaterLevel(20);
        assertEquals(80, elephant.getWaterLevel());
        elephant.decreaseWaterLevel(100);
        assertEquals(0, elephant.getWaterLevel());
        elephant.drink();
        assertEquals(30, elephant.getWaterLevel());
        elephant.drink();
        elephant.drink();
        elephant.drink();
        elephant.drink();
        elephant.drink();
        elephant.drink();
        elephant.drink();
        assertEquals(100, elephant.getWaterLevel());
    }

    @Test
    public void testWaterMemoryUpdate() {
        Tile tile = new Tile(new Point(0, 0), 100);
        assertTrue(elephant.getWaterMemory().isEmpty());
        elephant.updateWaterMemory(tile);
        List<Tile> memory = elephant.getWaterMemory();
        assertEquals(1, memory.size());
        assertTrue(memory.contains(tile));
        elephant.updateWaterMemory(tile);
        assertEquals(1, memory.size());
    }

    @Test
    public void testGrassMemoryUpdate() {
        Tile tile = new Tile(new Point(1, 1), 100);
        assertTrue(elephant.getGrassMemory().isEmpty());
        elephant.updateGrassMemory(tile);
        List<Tile> memory = elephant.getGrassMemory();
        assertEquals(1, memory.size());
        assertTrue(memory.contains(tile));
        elephant.updateGrassMemory(tile);
        assertEquals(1, memory.size());
    }

    @Test
    public void testLeaderAssignment() {
        Elephant leaderElephant = new Elephant(new Point(5, 5));
        assertNull(elephant.getLeader());
        elephant.setLeader(leaderElephant);
        assertEquals(leaderElephant, elephant.getLeader());
    }

    @Test
    public void testMaxAgeAccess() {
        assertTrue(elephant.getMaxAge() > 0);
    }
}
