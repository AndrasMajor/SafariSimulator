package safariSimulator.main.Models.Entity.Animal.Carnivore;

import safariSimulator.main.Models.Point;

/**
 * Represents a Hyena, a type of Carnivore animal in the safari simulator.
 * The Hyena class extends the Carnivore class and inherits all its properties and behaviors.
 * It defines specific characteristics such as the maximum age of a hyena.
 */
public class Hyena extends Carnivore {

    /**
     * Constructor that initializes a hyena with a given position.
     * It also sets the maximum age of the hyena to 25 years and the speed.
     *
     * @param pos   The initial position of the hyena.
     */
    public Hyena(Point pos) {
        super(pos); // Calls the constructor of the parent Carnivore class
        this.maxAge = 25;   // Sets the maximum age of the hyena to 25 years
        this.speed = 10; //Sets the speed of the hyena to 10
    }
}
