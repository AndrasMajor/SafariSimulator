package safariSimulator.main.Models.Entity;

import safariSimulator.main.Models.Point;
import safariSimulator.main.Models.Tile.Tile;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an abstract entity in the safari simulator.
 * An entity has a position, a speed, and a destination position.
 */
public abstract class Entity {

    // The current position of the entity
    public Point pos;

    // The speed of the entity
    public int speed;

    // The destination position of the entity (not yet utilized in this class)
    public Point destPos;

    public int price;

    public Mover mover;

    public boolean isMoving() {
        return mover != null && !mover.isComplete();
    }

    /**
     * Constructor that initializes the entity with a given position.
     * @param pos The initial position of the entity.
     */
    public Entity(Point pos) {
        this.pos = pos;
    }
    public Entity() {}

    /**
     * Gets the current position of the entity.
     * @return The position of the entity.
     */
    public Point getPos() {
        return pos;
    }

    public void setPos(Point pos) {
        if (this.pos != null && !this.pos.equals(pos)) {
            // Start movement animation from current to new position
            this.mover = new Mover(this.pos, pos, 1f); // 0.3 seconds to move
        }
        this.pos = pos;
    }

    protected List<Tile> getNearbyTiles(Point pos, List<Tile> tiles) {
        List<Tile> nearby = new ArrayList<>();
        for (Tile tile : tiles) {
            if (Math.abs(tile.getPos().getX() - pos.getX()) <= 3 &&
                Math.abs(tile.getPos().getY() - pos.getY()) <= 3) {
                nearby.add(tile);
            }
        }
        return nearby;
    }


    public float getScale() {
        return 0.5f; // default (zebra)
    }
}

