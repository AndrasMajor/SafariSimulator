package safariSimulator.main.Models.Entity.Animal.Carnivore;

import safariSimulator.main.Models.Point;

/**
 * Represents a Lion, a type of Carnivore animal in the safari simulator.
 * The Lion class extends the Carnivore class and inherits all its properties and behaviors.
 * It defines specific characteristics such as the maximum age of a lion.
 */
public class Lion extends Carnivore {

    /**
     * Constructor that initializes a lion with a given position and speed.
     * It also sets the maximum age of the lion to 20 years and the speed of the lion to 15.
     *
     * @param pos   The initial position of the lion
     */
    public Lion(Point pos) {
        super(pos);
        this.maxAge = 200; // Sets the maximum  age of the lion to 20 years
        this.speed = 15; // Sets the speed of the lion 15
        this.price = 100;
    }
    public Lion() {}
}
