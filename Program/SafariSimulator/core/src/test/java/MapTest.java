import org.junit.jupiter.api.Test;
import safariSimulator.main.Models.Entity.Animal.Animal;
import safariSimulator.main.Models.Entity.Animal.Carnivore.Lion;
import safariSimulator.main.Models.Entity.Jeep;
import safariSimulator.main.Models.Map;
import safariSimulator.main.Models.Objects.Plant;
import safariSimulator.main.Models.Objects.PlantType;
import safariSimulator.main.Models.Point;

import static org.junit.jupiter.api.Assertions.*;

public class MapTest {
    Map m = new Map("easy");

    @Test
    public void testConstructor() {
        assertNotNull(m);
        assertTrue(m.money == 1000);
        assertTrue(m.getEntities().isEmpty());
        assertTrue(m.getObjects().isEmpty());
        assertTrue(m.getTiles().size() == 0);
        assertTrue(m.level.equals("easy"));
    }

    @Test
    public void testGenerateMap() {
        m.generateMap();
        assertFalse(m.getTiles().isEmpty());
    }

    @Test
    public void testBuyEntity() {
        m.buyEntity(new Jeep(new Point(0, 0)));
        assertFalse(m.getEntities().isEmpty());
    }

    @Test
    public void testBuyObject() {
        m.buyObject(new Plant(new Point(0, 0), PlantType.Bush));
        assertFalse(m.getObjects().isEmpty());
    }
}
