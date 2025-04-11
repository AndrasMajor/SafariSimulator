package safariSimulator.main.Models.Entity.Animal;

import safariSimulator.main.Models.Entity.Animal.Carnivore.Carnivore;
import safariSimulator.main.Models.Entity.Animal.Herbivore.Herbivore;
import safariSimulator.main.Models.Entity.Mover;
import safariSimulator.main.Models.Map;
import safariSimulator.main.Models.Tile.Tile;
import safariSimulator.main.Models.Entity.Entity;
import safariSimulator.main.Models.Point;

import java.util.*;

/**
 * Represents an abstract Animal in the safari simulator.
 * Animals have age, gender, health, food and water levels, a maximum lifespan, memory of tiles, and an optional leader.
 */
public abstract class Animal extends Entity {

    // The age of the animal
    private int age;

    // The health status of the animal
    public int health;

    // The current food level of the animal
    protected int foodLevel;

    // The current water level of the animal
    private int waterLevel;

    // The maximum age the animal can reach
    protected int maxAge;

    // Memory of visited tiles
    private List<Tile> waterMemory = new ArrayList<Tile>();
    private List<Tile> grassMemory = new ArrayList<Tile>();

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
        this.health = 500;  // Default full health
        this.foodLevel = 100;  // Default full food level
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
            this.age += 1;
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
        this.waterLevel = Math.min(100, this.waterLevel + 30);
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
    public List<Tile> getWaterMemory() {
        return this.waterMemory;
    }
    public List<Tile> getGrassMemory() {return this.grassMemory;}

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

    public void updateWaterMemory(Tile currentTile) {
        if (!waterMemory.contains(currentTile)) {
            waterMemory.add(currentTile);
        }
    }
    public void updateGrassMemory(Tile currentTile) {
        if (!grassMemory.contains(currentTile)) {
            grassMemory.add(currentTile);
        }
    }


    public void move(Map map) {
        if (this.mover != null && !this.mover.isComplete()) {
            return; // Wait for the animation to finish
        }
        List<Tile> tiles = map.getTiles();
        List<Entity> entities = map.getEntities();
        Point currentPos = this.getPos();
        List<Tile> nearbyTiles = getNearbyTiles(currentPos, tiles);
        Tile currentTile = getCurrentTile(tiles);

        Tile targetTile = null;
        Entity targetEntity = null;

        if(waterLevel >= 90 && foodLevel >= 90 && leader == null) {
            targetTile = currentTile;
        }else if(waterLevel >= 50 && foodLevel >= 50 && leader != null) {
            targetTile = getClosestValidTileToLeader(tiles, entities);
        }


        if (waterLevel < 50) {
            // decrease the hp of the animal
            decreaseHealth(1);
            // Search for water-adjacent land tile
            if(!waterMemory.isEmpty()) {
                targetTile = waterMemory.getLast();
            }
            for (Tile tile : nearbyTiles) {
                if (tile.getHealth() != -1 && isNextToWater(tile, tiles)) {
                    targetTile = tile;
                    break;
                }
            }
        }
        if (foodLevel < 50 && this.isHerbivore()) {
            // decrease the hp of the animal
            decreaseHealth(1);

            if(!grassMemory.isEmpty()) {
                targetTile = grassMemory.getLast();
            }
            // Search for grass
            for (Tile tile : nearbyTiles) {
                if (tile.getHealth() > 0) {
                    targetTile = tile;
                    break;
                }
            }
        } else if (foodLevel < 50 && !this.isHerbivore()) {
            // decrease the hp of the animal
            decreaseHealth(1);
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
            moveTowardsWithPathfinding(targetTile.getPos(), tiles);
            if (waterLevel < 50 && isNextToWater(currentTile, tiles)) {
                drink();
                System.out.println("Slurp");
                waterMemory.add(currentTile);
            }
            if(this.isHerbivore() && foodLevel < 50  && currentTile.getHealth() > 0) {
                ((Herbivore) this).graze();
                System.out.println("Yumm Grass!");
                grassMemory.add(currentTile);
                currentTile.setHealth(currentTile.getHealth() - 20);
            }
        } else if (targetEntity != null) {
            moveTowardsWithPathfinding(targetEntity.getPos(), tiles);
            if(this.getPos().getX() == targetEntity.getPos().getX()
                && this.getPos().getY() == targetEntity.getPos().getY()) {
                System.out.println("Yumm Zebra/Elephant!");
                ((Carnivore) this).hunt(targetEntity);
            }
        } else {
            moveRandomly(tiles);
        }

        if(foodLevel >= 50 && waterLevel >= 50){
            increaseHealth(2);
        }
        decreaseFoodLevel(1);
        decreaseWaterLevel(1);
        ageUp();
    }

    public List<Tile> getNearbyTiles(Point currentPos, List<Tile> tiles) {
        List<Tile> nearbyTiles = new ArrayList<>();

        int centerX = currentPos.getX();
        int centerY = currentPos.getY();
        // 7*7 square
        for (int dx = -3; dx <= 3; dx++) {
            for (int dy = -3; dy <= 3; dy++) {
                int nx = centerX + dx;
                int ny = centerY + dy;

                for (Tile tile : tiles) {
                    if (tile.getPos().getX() == nx && tile.getPos().getY() == ny) {
                        nearbyTiles.add(tile);
                        break;
                    }
                }
            }
        }

        return nearbyTiles;
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
            this.mover = new Mover(current, newPos, 0.7f); // Move over 0.4 seconds
            this.setPos(newPos); // Update logical position
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



    private boolean isInRange(Point p1, Point p2) {
        return Math.abs(p1.getX() - p2.getX()) <= 10 && Math.abs(p1.getY() - p2.getY()) <= 10;
    }


    private void moveRandomly(List<Tile> tiles) {
        Random rand = new Random();
        Point newPos;
        do {
            int dx = rand.nextInt(3) - 1;
            int dy = rand.nextInt(3) - 1;
            newPos = new Point(this.getPos().getX() + dx, this.getPos().getY() + dy);
        } while (!isValidMove(newPos, tiles));
        moveStepTowards(newPos, tiles);
    }


    /**
     * Checks if the animal is alive.
     * The animal is considered alive if its health is greater than 0.
     *
     * @return True if the animal is alive (health > 0), otherwise false.
     */
    public boolean isAlive(){
        return (this.getHealth() > 0 && this.getAge() < maxAge);
    }


    public void breed(){
        //TODO: Implement the breeding logic for animals
    }

    private Tile getClosestValidTileToLeader(List<Tile> tiles, List<Entity> entities) {
        if (leader == null) return null;

        Tile leaderTile = leader.getCurrentTile(tiles);
        List<Tile> nearbyTiles = getNearbyTiles(leaderTile.getPos(), tiles);

        Tile bestTile = null;
        double bestDistance = Double.MAX_VALUE;

        for (Tile tile : nearbyTiles) {
            if (tile.getHealth() == -1) continue;
            boolean occupied = false;


            for (Entity entity : entities) {
                if (entity.getPos().equals(tile.getPos())) {
                    occupied = true;
                    break;
                }
            }

            if (!occupied) {
                double dx = this.getPos().getX() - tile.getPos().getX();
                double dy = this.getPos().getY() - tile.getPos().getY();
                double distance = Math.sqrt(dx * dx + dy * dy);

                if (distance < bestDistance) {
                    bestDistance = distance;
                    bestTile = tile;
                }
            }
        }
        return bestTile;
    }


    public float getScale() {
        return 0.5f; // default (zebra)
    }


    /*Ideal path algorithm*/
    private List<Point> findPath(Point start, Point goal, List<Tile> tiles) {
        Set<Point> closedSet = new HashSet<>();
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingDouble(n -> n.fScore));
        java.util.Map<Point, Point> cameFrom = new HashMap<>();
        java.util.Map<Point, Double> gScore = new HashMap<>();

        gScore.put(start, 0.0);
        openSet.add(new Node(start, heuristic(start, goal)));

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();

            if (current.point.equals(goal)) {
                return reconstructPath(cameFrom, current.point);
            }

            closedSet.add(current.point);

            for (Point neighbor : getWalkableNeighbors(current.point, tiles)) {
                if (closedSet.contains(neighbor)) continue;

                double tentativeG = gScore.getOrDefault(current.point, Double.POSITIVE_INFINITY) + 1;

                if (tentativeG < gScore.getOrDefault(neighbor, Double.POSITIVE_INFINITY)) {
                    cameFrom.put(neighbor, current.point);
                    gScore.put(neighbor, tentativeG);
                    double fScore = tentativeG + heuristic(neighbor, goal);
                    openSet.add(new Node(neighbor, fScore));
                }
            }
        }

        return new ArrayList<>();
    }

    private List<Point> reconstructPath(java.util.Map<Point, Point> cameFrom, Point current) {
        List<Point> path = new ArrayList<>();
        while (cameFrom.containsKey(current)) {
            path.add(current);
            current = cameFrom.get(current);
        }
        Collections.reverse(path);
        return path;
    }

    private double heuristic(Point a, Point b) {
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY()); // Manhattan-távolság
    }

    private List<Point> getWalkableNeighbors(Point current, List<Tile> tiles) {
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};
        List<Point> neighbors = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            Point next = new Point(current.getX() + dx[i], current.getY() + dy[i]);
            if (isValidMove(next, tiles)) {
                neighbors.add(next);
            }
        }

        return neighbors;
    }

    public static class Node {
        Point point;
        double fScore;

        Node(Point point, double fScore) {
            this.point = point;
            this.fScore = fScore;
        }
    }

    private void moveTowardsWithPathfinding(Point target, List<Tile> tiles) {
        List<Point> path = findPath(this.getPos(), target, tiles);
        if (!path.isEmpty()) {
            Point nextStep = path.get(0);
            this.mover = new Mover(this.getPos(), nextStep, 0.7f);
            this.setPos(nextStep);
        }
    }



}
