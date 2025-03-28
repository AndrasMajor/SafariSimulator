package safariSimulator.main.Models.Entity.Animal.Carnivore;

import safariSimulator.main.Models.Entity.Animal.Animal;
import safariSimulator.main.Models.Objects.RoadDirection;
import safariSimulator.main.Models.Point;

/**
 * Represents a Carnivore animal in the safari simulator.
 * Carnivores are animals that primarily hunt other animals for food.
 * This class extends the Animal class and inherits all its properties and behaviors.
 */
public abstract class Carnivore extends Animal {

    /**
     * Constructor that initializes a carnivore with a given position.
     * It calls the constructor of the parent Animal class to initialize basic animal properties.
     *
     * @param pos   The initial position of the carnivore.
     */
    public Carnivore(Point pos) {
        super(pos);
    }
    public Carnivore() {}


    public void hunt(){
        this.foodLevel = Math.min(this.foodLevel + 10, 100);
    }
}

