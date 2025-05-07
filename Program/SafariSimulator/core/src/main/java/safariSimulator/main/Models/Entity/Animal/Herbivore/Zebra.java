package safariSimulator.main.Models.Entity.Animal.Herbivore;

import safariSimulator.main.Models.Point;

/**
 * Represents a Zebra, a type of Herbivore animal in the safari simulator.
 * The Zebra class extends the Herbivore class and inherits all its properties and behaviors.
 * It defines specific characteristics such as the maximum age and speed of a zebra.
 */
public class Zebra extends Herbivore {

    /**
     * Constructor that initializes a zebra with a given position.
     * It also sets the maximum age of the zebra to 30 years and its speed to 8.
     *
     * @param pos The initial position of the zebra.
     */
    public Zebra(Point pos) {
        super(pos);
        this.maxAge = 500;  // Sets the maximum age of the zebra to 30 years
        this.speed = 8;    // Sets the speed of the zebra to 8 units
        this.price = 50;
    }
    public Zebra() {}
}
