package safariSimulator.main.Models.Biome.Tile.Road;

import safariSimulator.main.Models.Biome.Tile.Tile;

public class Road extends Tile {
    private Direction direction;
    public Road(int x, int y, Direction direction) {
        super(x, y);
        this.direction = direction;
    }
    public Direction getDirection() {
        return direction;
    }
    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
