import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import safariSimulator.main.Models.Entity.Animal.Animal;
import safariSimulator.main.Models.Entity.Animal.Carnivore.Lion;
import safariSimulator.main.Models.Entity.Animal.Herbivore.Zebra;
import safariSimulator.main.Models.Entity.Human.Keeper;
import safariSimulator.main.Models.Entity.Human.Poacher;
import safariSimulator.main.Models.Entity.Jeep;
import safariSimulator.main.Models.Map;
import safariSimulator.main.Models.Objects.Plant;
import safariSimulator.main.Models.Objects.PlantType;
import safariSimulator.main.Models.Point;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class KeeperTest {
    public static Map map = new Map();
    public static Keeper keeper = new Keeper(new Point(1, 1));

    @BeforeAll
    public static void setUp() {
        map.entities.add(keeper);
    }

    @Test
    public void TestIsPoacherInRange_WhenNoPoacherOnMap() {
        assertFalse(keeper.isPoacherInRange(map.entities));
    }
    @Test
    public void TestIsPoacherInRange_WhenPoacherOnMap_NotInRange() {
        Poacher poacher = new Poacher();
        poacher.pos = new Point(20, 1);
        map.entities.add(poacher);
        assertFalse(keeper.isPoacherInRange(map.entities));
    }
    @Test
    public void TestIsPoacherInRange_WhenPoacherOnMap_InRange() {
        Poacher poacher = new Poacher();
        poacher.pos = new Point(2, 1);
        map.entities.add(poacher);
        assertTrue(keeper.isPoacherInRange(map.entities));
    }

    @Test
    public void isCarnivoreInShootingRange_WhenNotInShootingRange() {
        Lion lion = new Lion();
        lion.pos = new Point(10, 1);
        map.entities.add(lion);
        assertFalse(keeper.isCarnivoreInShootingRange(lion));
    }
    @Test
    public void isCarnivoreInShootingRange_WhenInShootingRange() {
        Lion lion = new Lion();
        lion.pos = new Point(2, 1);
        map.entities.add(lion);
        assertTrue(keeper.isCarnivoreInShootingRange(lion));
    }

    @Test
    public void TestShootCarnivore_KillItAndSellIt() {
        Lion lion = new Lion();
        lion.pos = new Point(2, 1);
        map.entities.add(lion);
        assertTrue(map.entities.contains(lion));
        keeper.shootCarnivore(lion, map);
        assertFalse(map.entities.contains(lion));
    }

    @Test
    public void isPoacherInShootingRange_WhenNotInViewRange() {
        Poacher poacher = new Poacher();
        poacher.pos = new Point(6, 1);
        map.entities.add(poacher);
        assertFalse(keeper.isPoacherInShootingRange(map.entities));
    }
    @Test
    public void isPoacherInShootingRange_WhenInViewRange() {
        Poacher poacher = new Poacher();
        poacher.pos = new Point(4, 1);
        map.entities.add(poacher);
        assertFalse(keeper.isPoacherInShootingRange(map.entities));
    }
}
