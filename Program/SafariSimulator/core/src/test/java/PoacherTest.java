import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import safariSimulator.main.Models.Entity.Animal.Animal;
import safariSimulator.main.Models.Entity.Animal.Carnivore.Lion;
import safariSimulator.main.Models.Entity.Animal.Herbivore.Zebra;
import safariSimulator.main.Models.Entity.Human.Keeper;
import safariSimulator.main.Models.Entity.Human.Poacher;
import safariSimulator.main.Models.Entity.Human.Visitor;
import safariSimulator.main.Models.Entity.Jeep;
import safariSimulator.main.Models.Map;
import safariSimulator.main.Models.Objects.Plant;
import safariSimulator.main.Models.Objects.PlantType;
import safariSimulator.main.Models.Point;

import static org.junit.jupiter.api.Assertions.*;

public class PoacherTest {
    public static Map map = new Map();
    public static Poacher poacher = new Poacher();

    @BeforeAll
    public static void setUp() {
        poacher.pos = new Point(1, 1);
        map.entities.add(poacher);
    }

    @Test
    public void TestChooseTarget_AlwaysChooseAnimal() {
        map.entities.add(new Keeper());
        map.entities.add(new Visitor());
        map.entities.add(new Jeep());
        map.entities.add(new Zebra());

        assertNull(poacher.target);
        poacher.chooseTarget(map.entities);
        assertNotNull(poacher.target);
        assertTrue(poacher.target instanceof Animal);
    }

    @Test
    public void TestIsPoacherVisible_WhenNotInViewRange() {
        Keeper keeper = new Keeper(new Point(12, 12));
        assertFalse(poacher.isVisible);
        keeper.isPoacherInShootingRange(map.entities);
        assertFalse(poacher.isVisible);
    }
    @Test
    public void TestIsPoacherVisible_WhenInViewRange() {
        Keeper keeper = new Keeper(new Point(11, 11));
        assertFalse(poacher.isVisible);
        keeper.isPoacherInShootingRange(map.entities);
        assertTrue(poacher.isVisible);
    }
}
