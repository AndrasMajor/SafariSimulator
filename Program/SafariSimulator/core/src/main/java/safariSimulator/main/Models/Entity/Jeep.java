package safariSimulator.main.Models.Entity;

import safariSimulator.main.Models.Entity.Animal.Animal;
import safariSimulator.main.Models.Map;
import safariSimulator.main.Models.Objects.Road;
import safariSimulator.main.Models.Point;

import java.util.List;

/**
 * Represents a Jeep in the safari simulator.
 * Handles tourist simulation directly without using Tourist class.
 */
public class Jeep extends Entity {

    private final int maintenanceCost = 50;
    private final int range = 6;

    private int impressLevel;
    private double rating; // Average rating of this tour
    private int passengerCount = 0;
    private Point previousPos;
    public int roadNumber;

    public Jeep(Point pos) {
        super(pos);
        this.price = 50;
        this.rating = 0;
    }

    /**
     * Start a new tour with tourists of given impress level and count.
     */
    public void startTour(int impressLevel, int passengerCount) {
        this.impressLevel = impressLevel;
        this.passengerCount = passengerCount;
        this.rating = 0; // Reset for new tour
    }

    /**
     * Ends the tour, returning the average rating gathered.
     */
    public double endTour() {
        double finalRating = Math.min(5, rating);
        this.passengerCount = 0;
        this.rating = 0;
        return finalRating;
    }

    public void incrementHappiness(int value) {
        double increase = value * 0.1;
        this.rating = Math.min(5, this.rating + increase);
    }

    public int getPassengerCount() {
        return passengerCount;
    }

    public int getMaintenanceCost() {
        return maintenanceCost;
    }

    public double getRating() {
        return rating;
    }

    private int getNumberOfAnimalsInSight(Map map) {
        int count = 0;
        for (Entity entity : map.getEntities()) {
            if (entity instanceof Animal) {
                if (Math.abs(entity.getPos().getX() - this.pos.getX()) <= range &&
                    Math.abs(entity.getPos().getY() - this.pos.getY()) <= range) {
                    count++;
                }
            }
        }
        return count;
    }
    /**
     * Moves the Jeep to the next road if available.
     * If there are animals in sight, it will not move.
     */
    public void move(Map map) {
        if (this.isMoving()) return;

        this.roadNumber = map.findRoadNumber(this.pos); // keep in sync
        // Check end of tour first
        if (map.findRoadNumber(this.pos) == map.getMaxRoadNumber()) {
            map.completeJeepTour(this);
            return;
        }

        int animalsInRange = getNumberOfAnimalsInSight(map);
        if (animalsInRange > 0 && passengerCount > 0 && rating < 5) {
            rating += (0.5 * animalsInRange) / (impressLevel + 1);
            rating = Math.min(5, rating);
        } else {
            pos_change(map);
        }
    }


    public void pos_change(Map map) {
        Point nextRoad = searchForNextRoad(map);
        if (nextRoad != null && !nextRoad.equals(this.pos)) {
            this.previousPos = this.pos;
            this.setPos(nextRoad); // Triggers mover animation
        }
    }


    public Point searchForNextRoad(Map map) {
        int roadNumber = map.findRoadNumber(this.pos);
        for (Object obj : map.getObjects()) {
            if (obj instanceof Road road) {
                if (Math.abs(road.getPos().getX() - this.pos.getX()) <= 1 &&
                    Math.abs(road.getPos().getY() - this.pos.getY()) <= 1 &&
                    road.roadNumber == roadNumber + 1) {
                    return road.getPos();
                }
            }
        }
        return null;
    }
}
