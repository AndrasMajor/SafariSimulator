package safariSimulator.main.Models.Tile;

import safariSimulator.main.Models.Point;

public class Tile {
    public Point pos;
    public int health;

    public Tile(Point pos, int health) {
        this.pos = pos;
        this.health = health;
    }

}
