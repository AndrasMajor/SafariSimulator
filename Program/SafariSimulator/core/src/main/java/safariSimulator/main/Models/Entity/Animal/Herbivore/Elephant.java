package safariSimulator.main.Models.Entity.Animal.Herbivore;

import safariSimulator.main.Models.Point;

/**
 * Represents an Elephant, a type of Herbivore animal in the safari simulator.
 * The Elephant class extends the Herbivore class and inherits all its properties and behaviors.
 * It defines specific characteristics such as the maximum age and speed of an elephant.
 */
public class Elephant extends Herbivore {

    /**
     * Constructor that initializes an elephant with a given position.
     * It also sets the maximum age of the elephant to 70 years and its speed to 10.
     *
     * @param pos The initial position of the elephant.
     */
    public Elephant(Point pos) {
        super(pos);
        this.maxAge = 70;  // Sets the maximum age of the elephant to 70 years
        this.speed = 10;   // Sets the speed of the elephant to 10 units
    }
}
