import safariSimulator.main.Models.MapGeneration.MapGenerator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MapGeneratorTest {

    @Test
    public void testTileMapGeneration() {
        MapGenerator generator = new MapGenerator();
        int[][] map = generator.getTileMap();

        // Check dimensions
        assertEquals(MapGenerator.SIZE, map.length, "Map width should be 50");
        assertEquals(MapGenerator.SIZE, map[0].length, "Map height should be 50");

        boolean hasWater = false;
        boolean hasGrass = false;
        boolean hasDry = false;

        for (int x = 0; x < MapGenerator.SIZE; x++) {
            for (int y = 0; y < MapGenerator.SIZE; y++) {
                int tile = map[x][y];

                // Validate tile values
                assertTrue(tile == -1 || tile == 0 || tile == 100,
                    "Invalid tile value: " + tile + " at (" + x + "," + y + ")");

                // Track tile types for diversity
                if (tile == -1) hasWater = true;
                if (tile == 0) hasDry = true;
                if (tile == 100) hasGrass = true;
            }
        }

        // Ensure all tile types exist
        assertTrue(hasWater, "Map should contain water tiles (-1)");
        assertTrue(hasGrass, "Map should contain grass tiles (100)");
        assertTrue(hasDry, "Map should contain dry land tiles (0)");
    }
}
