package safariSimulator.main.Models.Biome.Tile.PlantField;

public class Grass extends PlantField {
    private static final int GRASS_MAX_HP = 50;
    private static final int STEALTH = 0;

    public Grass(int x, int y) {
        super(x, y, GRASS_MAX_HP);
    }
}
