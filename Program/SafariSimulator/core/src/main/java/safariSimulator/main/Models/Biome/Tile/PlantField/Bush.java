package safariSimulator.main.Models.Biome.Tile.PlantField;

public class Bush extends PlantField {
    private static final int BUSH_MAX_HP = 100;
    private static final int STEALTH = 5;

    public Bush(int x, int y) {
        super(x, y, BUSH_MAX_HP);
    }
}
