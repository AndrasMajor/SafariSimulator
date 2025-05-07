import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import safariSimulator.main.Models.Entity.Animal.Carnivore.Hyena;
import safariSimulator.main.Models.Entity.Animal.Herbivore.Zebra;
import safariSimulator.main.Models.Point;
import safariSimulator.main.Models.Tile.Tile;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class HyenaTest {

    private Hyena hyena;

    @BeforeEach
    public void setUp() {
        hyena = new Hyena(new Point());
    }

    @Test
    public void testHerbivore() {
        assertTrue(hyena.isHerbivore());
    }

    @Test
    public void testAge() {
        assertEquals(0, hyena.getAge());
        hyena.ageUp();
        assertEquals(1, hyena.getAge());
    }

    @Test
    public void testHealthModification() {
        assertEquals(500, hyena.getHealth());
        hyena.decreaseHealth(200);
        assertEquals(300, hyena.getHealth());
        hyena.decreaseHealth(400);
        assertEquals(0, hyena.getHealth());
        hyena.increaseHealth(50);
        assertEquals(50, hyena.getHealth());
        hyena.increaseHealth(100);
        assertEquals(100, hyena.getHealth());
    }

    @Test
    public void testFoodLevel() {
        assertEquals(100, hyena.getFoodLevel());
        hyena.decreaseFoodLevel(30);
        assertEquals(70, hyena.getFoodLevel());
        hyena.decreaseFoodLevel(100);
        assertEquals(0, hyena.getFoodLevel());
    }

    @Test
    public void testWaterLevel() {
        assertEquals(100, hyena.getWaterLevel());
        hyena.decreaseWaterLevel(20);
        assertEquals(80, hyena.getWaterLevel());
        hyena.decreaseWaterLevel(100);
        assertEquals(0, hyena.getWaterLevel());
        hyena.drink();
        assertEquals(30, hyena.getWaterLevel());
        hyena.drink();
        hyena.drink();
        hyena.drink();
        hyena.drink();
        hyena.drink();
        hyena.drink();
        hyena.drink();
        assertEquals(100, hyena.getWaterLevel());
    }

    @Test
    public void testWaterMemoryUpdate() {
        Tile tile = new Tile(new Point(0, 0), 0);
        assertTrue(hyena.getWaterMemory().isEmpty());
        hyena.updateWaterMemory(tile);
        List<Tile> memory = hyena.getWaterMemory();
        assertEquals(1, memory.size());
        assertTrue(memory.contains(tile));
        hyena.updateWaterMemory(tile);
        assertEquals(1, memory.size());
    }


    @Test
    public void testLeaderAssignment() {
        Hyena leaderhyena = new Hyena(new Point(5, 5));
        assertNull(hyena.getLeader());
        hyena.setLeader(leaderhyena);
        assertEquals(leaderhyena, hyena.getLeader());
    }

    @Test
    public void testMaxAgeAccess() {
        assertEquals(400, hyena.getMaxAge());
    }

    @Test
    public void testFood(){
        assertEquals(100, hyena.getFoodLevel());
        hyena.decreaseFoodLevel(20);
        assertEquals(80, hyena.getFoodLevel());
        hyena.decreaseFoodLevel(100);
        assertEquals(0, hyena.getFoodLevel());
        hyena.hunt(new Zebra());
        assertEquals(30, hyena.getFoodLevel());
        hyena.hunt(new Zebra());
        hyena.hunt(new Zebra());
        hyena.hunt(new Zebra());
        hyena.hunt(new Zebra());
        hyena.hunt(new Zebra());
        assertEquals(100, hyena.getFoodLevel());
    }

    @Test
    public void testIsAlive(){
        assertTrue(hyena.isAlive());
        hyena.decreaseHealth(500);
        assertFalse(hyena.isAlive());
        hyena.increaseHealth(100);
        assertTrue(hyena.isAlive());
    }

}
