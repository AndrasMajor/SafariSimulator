package safariSimulator.main.Models.Biome.Tile.PlantField;

public class Tree extends PlantField {
    private static final int TREE_MAX_HP = 150;
    private static final int STEALTH = 10;

    public Tree(int x, int y) {
        super(x, y, TREE_MAX_HP);
    }
}
