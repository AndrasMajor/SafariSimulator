import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import safariSimulator.main.Models.Entity.Animal.Carnivore.Lion;
import safariSimulator.main.Models.Entity.Animal.Herbivore.Zebra;
import safariSimulator.main.Models.Point;
import safariSimulator.main.Models.Tile.Tile;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class LionTest {

    private Lion lion;

    @BeforeEach
    public void setUp() {
        lion = new Lion(new Point());
    }

    @Test
    public void testHerbivore() {
        assertTrue(lion.isHerbivore());
    }

    @Test
    public void testAge() {
        assertEquals(0, lion.getAge());
        lion.ageUp();
        assertEquals(1, lion.getAge());
    }

    @Test
    public void testHealthModification() {
        assertEquals(500, lion.getHealth());
        lion.decreaseHealth(200);
        assertEquals(300, lion.getHealth());
        lion.decreaseHealth(400);
        assertEquals(0, lion.getHealth());
        lion.increaseHealth(50);
        assertEquals(50, lion.getHealth());
        lion.increaseHealth(100);
        assertEquals(100, lion.getHealth());
    }

    @Test
    public void testFoodLevel() {
        assertEquals(100, lion.getFoodLevel());
        lion.decreaseFoodLevel(30);
        assertEquals(70, lion.getFoodLevel());
        lion.decreaseFoodLevel(100);
        assertEquals(0, lion.getFoodLevel());
    }

    @Test
    public void testWaterLevel() {
        assertEquals(100, lion.getWaterLevel());
        lion.decreaseWaterLevel(20);
        assertEquals(80, lion.getWaterLevel());
        lion.decreaseWaterLevel(100);
        assertEquals(0, lion.getWaterLevel());
        lion.drink();
        assertEquals(30, lion.getWaterLevel());
        lion.drink();
        lion.drink();
        lion.drink();
        lion.drink();
        lion.drink();
        lion.drink();
        lion.drink();
        assertEquals(100, lion.getWaterLevel());
    }

    @Test
    public void testWaterMemoryUpdate() {
        Tile tile = new Tile(new Point(0, 0), 0);
        assertTrue(lion.getWaterMemory().isEmpty());
        lion.updateWaterMemory(tile);
        List<Tile> memory = lion.getWaterMemory();
        assertEquals(1, memory.size());
        assertTrue(memory.contains(tile));
        lion.updateWaterMemory(tile);
        assertEquals(1, memory.size());
    }


    @Test
    public void testLeaderAssignment() {
        Lion leaderlion = new Lion(new Point(5, 5));
        assertNull(lion.getLeader());
        lion.setLeader(leaderlion);
        assertEquals(leaderlion, lion.getLeader());
    }

    @Test
    public void testMaxAgeAccess() {
        assertEquals(600, lion.getMaxAge());
    }

    @Test
    public void testFood(){
        assertEquals(100, lion.getFoodLevel());
        lion.decreaseFoodLevel(20);
        assertEquals(80, lion.getFoodLevel());
        lion.decreaseFoodLevel(100);
        assertEquals(0, lion.getFoodLevel());
        lion.hunt(new Zebra());
        assertEquals(30, lion.getFoodLevel());
        lion.hunt(new Zebra());
        lion.hunt(new Zebra());
        lion.hunt(new Zebra());
        lion.hunt(new Zebra());
        lion.hunt(new Zebra());
        assertEquals(100, lion.getFoodLevel());
    }

    @Test
    public void testIsAlive(){
        assertTrue(lion.isAlive());
        lion.decreaseHealth(500);
        assertFalse(lion.isAlive());
        lion.increaseHealth(100);
        assertTrue(lion.isAlive());
    }

}
