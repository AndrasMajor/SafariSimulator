package safariSimulator.main.Models.Tile;

import safariSimulator.main.Models.Point;

public class Tile {
    public Point pos;
    public int health;

    public Tile(Point pos, int health) {
        this.pos = pos;
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Point getPos() {
        return pos;
    }

}
