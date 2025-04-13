import org.junit.jupiter.api.Test;
import safariSimulator.main.Models.Entity.Animal.Animal;
import safariSimulator.main.Models.Entity.Animal.Carnivore.Lion;
import safariSimulator.main.Models.Entity.Animal.Herbivore.Zebra;
import safariSimulator.main.Models.Entity.Human.Poacher;
import safariSimulator.main.Models.Entity.Jeep;
import safariSimulator.main.Models.Map;
import safariSimulator.main.Models.Objects.Plant;
import safariSimulator.main.Models.Objects.PlantType;
import safariSimulator.main.Models.Point;

import static org.junit.jupiter.api.Assertions.*;

public class MapTest {
    Map m = new Map("easy");

    @Test
    public void testConstructor_proertiesAreCorrect() {
        assertNotNull(m);
        //assertTrue(m.money == 1000);
        assertTrue(m.getEntities().isEmpty());
        assertTrue(m.getObjects().isEmpty());
        assertTrue(m.getTiles().size() == 0);
        assertTrue(m.level.equals("easy"));
    }

    @Test
    public void testGenerateMap_mapGenerated() {
        m.generateMap();
        assertFalse(m.getTiles().isEmpty());
    }

    @Test
    public void testGetPoacher_weHaveAPoacher() {
        assertTrue(m.getPoacher() == null);
        m.entities.add(new Zebra());
        m.entities.add((new Poacher(m, new Point(0, 0))));
        assertTrue(m.getPoacher() != null);
    }

    @Test
    public void testBuyObject_weHaveAnObject() {
        m.buyObject(new Plant(new Point(0, 0), PlantType.Bush));
        assertFalse(m.getObjects().isEmpty());
    }
}
