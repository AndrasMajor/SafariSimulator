import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import safariSimulator.main.Models.Entity.Animal.Herbivore.Zebra;
import safariSimulator.main.Models.Entity.Human.Poacher;
import safariSimulator.main.Models.Map;
import safariSimulator.main.Models.Objects.Plant;
import safariSimulator.main.Models.Objects.PlantType;
import safariSimulator.main.Models.Point;
import safariSimulator.main.Models.Tile.Tile;

import static org.junit.jupiter.api.Assertions.*;

public class MapTest {
    Map map;

    @BeforeEach
    public void setUp() {
        map = new Map();
        map.tiles.add(new Tile(new Point(0, 0), 100));
        map.level = "easy";
    }

    @Test
    public void testConstructor_propertiesAreCorrect() {
        assertNotNull(map);
        //assertTrue(m.money == 1000);
        assertTrue(map.getEntities().isEmpty());
        assertTrue(map.getObjects().isEmpty());
        assertTrue(map.getTiles().size() == 1);
        assertTrue(map.level.equals("easy"));
    }

    @Test
    public void testGenerateMap_mapGenerated() {
        map.generateMap();
        assertFalse(map.getTiles().isEmpty());
    }

    @Test
    public void testGetPoacher_weHaveAPoacher() {
        assertTrue(map.getPoacher() == null);
        map.entities.add(new Zebra());
        map.entities.add((new Poacher(map, new Point(0, 0))));
        assertTrue(map.getPoacher() != null);
    }

    @Test
    public void testBuyObject_weHaveAnObject() {
        map.buyObject(new Plant(new Point(0, 0), PlantType.Bush));
        assertFalse(map.getObjects().isEmpty());
    }

    @Test
    public void testBuyEntity_successful() {
        Zebra zebra = new Zebra(new Point(0, 0));
        zebra.price = 100;
        map.money = 200;

        int result = map.buyEntity(zebra);

        assertEquals(1, result);
        assertTrue(map.getEntities().contains(zebra));
        assertEquals(100, map.money);
    }

    @Test
    public void testBuyEntity_insufficientFunds() {
        Zebra zebra = new Zebra(new Point(0, 0));
        zebra.price = 300;
        map.money = 200;

        int result = map.buyEntity(zebra);

        assertEquals(0, result);
        assertFalse(map.getEntities().contains(zebra));
        assertEquals(200, map.money);
    }

    @Test
    public void testBuyObject_successful() {
        Plant plant = new Plant(new Point(0, 0), null);
        plant.price = 50;
        map.money = 100;

        int result = map.buyObject(plant);

        assertEquals(1, result);
        assertTrue(map.getObjects().contains(plant));
        assertEquals(50, map.money);
    }

    @Test
    public void testSellEntity_refundsMoney() {
        Zebra zebra = new Zebra(new Point(0, 0));
        zebra.price = 150;
        map.money = 100;
        map.entities.add(zebra);

        map.sellEntity(zebra);

        assertFalse(map.getEntities().contains(zebra));
        assertEquals(250, map.money);
    }

    @Test
    public void testGetTileAt_validCoordinates() {
        Tile tile = new Tile(new Point(2, 3), 80);
        map.tiles.add(tile);

        Tile result = map.getTileAt(2, 3);

        assertEquals(tile, result);
    }

    @Test
    public void testGetCurrentAverageRating_defaultValue() {
        double rating = map.getCurrentAverageRating();
        assertEquals(2.5, rating);
    }
}
