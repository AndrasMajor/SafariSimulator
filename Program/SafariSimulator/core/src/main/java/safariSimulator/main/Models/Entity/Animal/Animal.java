package safariSimulator.main.Models.Entity.Animal;

import safariSimulator.main.Models.Entity.Animal.Herbivore.Herbivore;
import safariSimulator.main.Models.Map;
import safariSimulator.main.Models.Tile.Tile;
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

    // The health status of the animal
    private int health;

    // The current food level of the animal
    protected int foodLevel;

    // The current water level of the animal
    private int waterLevel;

    // The maximum age the animal can reach
    protected int maxAge;

    // Memory of visited tiles
    private List<Tile> memory = new ArrayList<Tile>();

    // The leader of the animal (if part of a herd or pack)
    private Animal leader;


    /**
     * Constructor that initializes the animal with a given position.
     * The animal starts at age 0, with a randomly assigned gender.
     *
     * @param pos   The initial position of the animal.
     */
    public Animal(Point pos) {
        super(pos);
        this.age = 0;
        this.health = 100;  // Default full health
        this.foodLevel = 0;  // Default full food level
        this.waterLevel = 100;  // Default full water level
        this.leader = null;
    }
    public Animal() {}


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

    public abstract boolean isHerbivore();


    public void move(Map map) {
        List<Tile> tiles = map.getTiles();
        List<Entity> entities = map.getEntities();
        Point currentPos = this.getPos();
        List<Tile> nearbyTiles = getNearbyTiles(currentPos, tiles);
        Tile currentTile = getCurrentTile(tiles);

        Tile targetTile = null;
        Entity targetEntity = null;

        if (waterLevel < 50) {
            // Search for water-adjacent land tile
            for (Tile tile : nearbyTiles) {
                if (tile.getHealth() != -1 && isNextToWater(tile, tiles)) {
                    targetTile = tile;
                    break;
                }
            }
        } else if (foodLevel < 50 && this.isHerbivore()) {
            // Search for grass
            for (Tile tile : nearbyTiles) {
                if (tile.getHealth() > 0) {
                    targetTile = tile;
                    break;
                }
            }
        } else if (foodLevel < 50 && !this.isHerbivore()) {
            // Predator searches for prey
            for (Entity entity : entities) {
                if (entity instanceof Animal && ((Animal) entity).isHerbivore()
                    && isInRange(currentPos, entity.getPos())) {
                    targetEntity = entity;
                    break;
                }
            }
        }

        if (targetTile != null) {
            moveStepTowards(targetTile.getPos(), tiles);
            if (waterLevel < 50 && isNextToWater(currentTile, tiles)) {
                drink();
            }
            if(this.isHerbivore() && foodLevel < 50  && currentTile.getHealth() > 0) {
                ((Herbivore) this).graze();
                currentTile.setHealth(currentTile.getHealth() - 20);
            }
        } else if (targetEntity != null) {
            moveStepTowards(targetEntity.getPos(), tiles);
        } else {
            moveRandomly(tiles);
        }
    }

    public Tile getCurrentTile(List<Tile> tiles) {
        for (Tile tile : tiles) {
            if (tile.getPos().getX() == this.getPos().getX()
                && tile.getPos().getY() == this.getPos().getY()) {
                return tile;
            }
        }
        return null;
    }

    private void moveStepTowards(Point target, List<Tile> tiles) {
        Point current = this.getPos();
        int dx = Integer.compare(target.getX(), current.getX());
        int dy = Integer.compare(target.getY(), current.getY());

        Point newPos = new Point(current.getX() + dx, current.getY() + dy);

        if (isValidMove(newPos, tiles)) {
            this.setPos(newPos);
        }
    }

    private boolean isValidMove(Point pos, List<Tile> tiles) {
        for (Tile tile : tiles) {
            if (tile.getPos().getY() == pos.getY() && tile.getPos().getX() == pos.getX()) {
                return tile.getHealth() != -1; // Cannot move onto water
            }
        }
        return false;
    }

    private boolean isNextToWater(Tile tile, List<Tile> tiles) {
        int x = tile.getPos().getX();
        int y = tile.getPos().getY();

        Point[] neighbors = {
            new Point(x - 1, y), // bal
            new Point(x + 1, y), // jobb
            new Point(x, y - 1), // le
            new Point(x, y + 1)  // fel
        };

        for (Point neighborPos : neighbors) {
            for (Tile t : tiles) {
                if (t.getPos().getX() == neighborPos.getX()
                    && t.getPos().getY() == neighborPos.getY()
                    && t.getHealth() == -1) {
                    return true;
                }
            }
        }
        return false;
    }


    private List<Tile> getNearbyTiles(Point pos, List<Tile> tiles) {
        List<Tile> nearby = new ArrayList<>();
        for (Tile tile : tiles) {
            if (Math.abs(tile.getPos().getX() - pos.getX()) <= 2 &&
                Math.abs(tile.getPos().getY() - pos.getY()) <= 2) {
                nearby.add(tile);
            }
        }
        return nearby;
    }

    private boolean isInRange(Point p1, Point p2) {
        return Math.abs(p1.getX() - p2.getX()) <= 2 && Math.abs(p1.getY() - p2.getY()) <= 2;
    }

    private void moveTo(Point target) {
        this.setPos(target);
    }

    private void moveRandomly(List<Tile> tiles) {
        Random rand = new Random();
        Point newPos;
        do {
            int dx = rand.nextInt(3) - 1;
            int dy = rand.nextInt(3) - 1;
            newPos = new Point(this.getPos().getX() + dx, this.getPos().getY() + dy);
        } while (!isValidMove(newPos, tiles));
        this.setPos(newPos);
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
