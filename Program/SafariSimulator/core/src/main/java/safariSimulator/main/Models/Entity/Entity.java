package safariSimulator.main.Models.Entity;

import safariSimulator.main.Models.Point;

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
        this.pos = pos;
    }

    /**
     * Moves the entity.
     */
    public void move() {
        // TODO: Implement movement logic in subclasses
    }
}

