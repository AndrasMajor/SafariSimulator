package safariSimulator.main.Models.Entity.Animal;

import safariSimulator.main.Models.Biome.Tile.Tile;
import safariSimulator.main.Models.Entity.Entity;
import safariSimulator.main.Models.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents an abstract Animal in the safari simulator.
 * Animals have age, gender, health, food and water levels, a maximum lifespan, memory of tiles, and an optional leader.
 */
public abstract class Animal extends Entity {

    // The age of the animal
    private int age;

    // The gender of the animal (randomly assigned)
    private Gender gender;

    // The health status of the animal
    private int health;

    // The current food level of the animal
    private int foodLevel;

    // The current water level of the animal
    private int waterLevel;

    // The maximum age the animal can reach
    protected int maxAge;

    // Memory of visited tiles
    private List<Tile> memory = new ArrayList<Tile>();

    // The leader of the animal (if part of a herd or pack)
    private Animal leader;

    // Random object for generating gender
    private static final Random random = new Random();

    /**
     * Constructor that initializes the animal with a given position.
     * The animal starts at age 0, with a randomly assigned gender.
     *
     * @param pos   The initial position of the animal.
     */
    public Animal(Point pos) {
        super(pos);
        this.age = 0;
        this.gender = random.nextBoolean() ? Gender.MALE : Gender.FEMALE;
        this.health = 100;  // Default full health
        this.foodLevel = 100;  // Default full food level
        this.waterLevel = 100;  // Default full water level
    }

    /**
     * Gets the age of the animal.
     *
     * @return The current age of the animal.
     */
    public int getAge() {
        return this.age;
    }

    /**
     * Increases the age of the animal.
     * If the animal reaches maxAge, it may die (to be handled in the game logic).
     */
    public void ageUp() {
        if (this.age < this.maxAge) {
            this.age++;
        }
    }

    /**
     * Gets the gender of the animal.
     *
     * @return The gender of the animal.
     */
    public Gender getGender() {
        return this.gender;
    }

    /**
     * Gets the health of the animal.
     *
     * @return The health level.
     */
    public int getHealth() {
        return this.health;
    }

    /**
     * Reduces the animal's health by a given amount.
     * If health reaches 0, the animal may die (to be handled in game logic).
     *
     * @param amount The amount to decrease health.
     */
    public void decreaseHealth(int amount) {
        this.health = Math.max(0, this.health - amount);
    }

    /**
     * Increases the animal's health by a given amount.
     * Cannot exceed 100.
     *
     * @param amount The amount to increase health.
     */
    public void increaseHealth(int amount) {
        this.health = Math.min(100, this.health + amount);
    }

    /**
     * Gets the current food level.
     *
     * @return The food level of the animal.
     */
    public int getFoodLevel() {
        return this.foodLevel;
    }

    /**
     * Reduces the food level of the animal over time.
     * If food level reaches 0, health may start decreasing (to be handled in game logic).
     *
     * @param amount The amount to decrease food level.
     */
    public void decreaseFoodLevel(int amount) {
        this.foodLevel = Math.max(0, this.foodLevel - amount);
    }

    /**
     * Gets the current water level.
     *
     * @return The water level of the animal.
     */
    public int getWaterLevel() {
        return this.waterLevel;
    }

    /**
     * Reduces the water level of the animal over time.
     * If water level reaches 0, health may start decreasing (to be handled in game logic).
     *
     * @param amount The amount to decrease water level.
     */
    public void decreaseWaterLevel(int amount) {
        this.waterLevel = Math.max(0, this.waterLevel - amount);
    }

    /**
     * Increases the animal's water level when it drinks.
     * The water level is capped at 100 to prevent overflow.
     */
    public void drink() {
        this.waterLevel = Math.min(100, this.waterLevel + 20);
    }

    /**
     * Gets the maximum age of the animal.
     *
     * @return The maximum lifespan.
     */
    public int getMaxAge() {
        return this.maxAge;
    }

    /**
     * Gets the memory of visited tiles.
     *
     * @return The list of tiles the animal remembers.
     */
    public List<Tile> getMemory() {
        return this.memory;
    }

    /**
     * Sets the leader of the animal.
     *
     * @param leader The leader animal.
     */
    public void setLeader(Animal leader) {
        this.leader = leader;
    }

    /**
     * Gets the leader of the animal.
     *
     * @return The leader of the animal, or null if it has none.
     */
    public Animal getLeader() {
        return this.leader;
    }


    @Override
    public void move() {
        // TODO: Implement movement logic for animals
    }

    /**
     * Checks if the animal is alive.
     * The animal is considered alive if its health is greater than 0.
     *
     * @return True if the animal is alive (health > 0), otherwise false.
     */
    public boolean isAlive(){
        return this.health > 0;
    }


    public void breed(){
        //TODO: Implement the breeding logic for animals
    }

}
