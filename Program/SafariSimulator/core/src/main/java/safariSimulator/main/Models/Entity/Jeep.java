package safariSimulator.main.Models.Entity;

import safariSimulator.main.Models.Entity.Human.Visitor;
import safariSimulator.main.Models.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Jeep in the safari simulator.
 * The Jeep is an entity that can transport visitors and has a maintenance cost.
 */
public class Jeep extends Entity {

    // The maintenance cost of the Jeep
    private final int maintenanceCost = 50;

    // The list of visitors currently in the Jeep
    private List<Visitor> passengers = new ArrayList<>();

    /**
     * Constructor that initializes the Jeep with a given position.
     *
     * @param pos   The initial position of the Jeep.
     */
    public Jeep(Point pos) {
        super(pos);
        this.speed = 10;
    }

    /**
     * Picks up a list of visitors and sets them as passengers.
     *
     * @param passengers The list of visitors to pick up.
     */
    public void pickUp(List<Visitor> passengers) {
        this.passengers = passengers;
    }

    /**
     * Drops off all passengers by clearing the passenger list.
     */
    public void dropOff() {
        this.passengers.clear();
    }
}
