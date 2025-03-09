package safariSimulator.main.Models.Entity.Animal.Herbivore;

import safariSimulator.main.Models.Entity.Animal.Animal;
import safariSimulator.main.Models.Point;

/**
 * Represents a Herbivore animal in the safari simulator.
 * Herbivores are animals that primarily feed on plants.
 * This class extends the Animal class and inherits all its properties and behaviors.
 */
public abstract class Herbivore extends Animal {

    /**
     * Constructor that initializes a herbivore with a given position.
     * It calls the constructor of the parent Animal class to initialize basic animal properties.
     *
     * @param pos The initial position of the herbivore.
     */
    public Herbivore(Point pos) {
        super(pos);
    }

    public void graze() {
        //TODO: Implement the grazing method for herbivores
    }
}
