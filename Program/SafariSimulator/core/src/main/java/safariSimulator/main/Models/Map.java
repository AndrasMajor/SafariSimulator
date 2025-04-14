package safariSimulator.main.Models;

import safariSimulator.main.Database.MapState;
import safariSimulator.main.Models.Entity.Animal.Animal;
import safariSimulator.main.Models.Entity.Animal.Carnivore.Hyena;
import safariSimulator.main.Models.Entity.Animal.Carnivore.Lion;
import safariSimulator.main.Models.Entity.Animal.Herbivore.Elephant;
import safariSimulator.main.Models.Entity.Animal.Herbivore.Zebra;
import safariSimulator.main.Models.Entity.Entity;
import safariSimulator.main.Models.Entity.Human.Keeper;
import safariSimulator.main.Models.Entity.Human.Poacher;
import safariSimulator.main.Models.Entity.Human.Tourist;
import safariSimulator.main.Models.Entity.Jeep;
import safariSimulator.main.Models.MapGeneration.MapGenerator;
import safariSimulator.main.Models.Objects.EntranceExitRoad;
import safariSimulator.main.Models.Objects.Plant;
import safariSimulator.main.Models.Objects.PlantType;
import safariSimulator.main.Models.Tile.Tile;
import safariSimulator.main.Models.Objects.Object;
import safariSimulator.main.Models.Objects.Road;
import safariSimulator.main.Models.Objects.RoadDirection;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Map {

    public List<Tile> tiles;
    private List<Object> objects;
    public List<Entity> entities;
    private GameClock gameClock;
    public int money;
    public String savingFileName;
    public String level;
    private ScheduledExecutorService scheduler;
    public int grassPrice = 10;
    public int waterPrice = 10;
    private int totalTouristsThisWeek = 0;
    public boolean won = false;
    public boolean lost = false;


    // tourist based vars
    private static final double DEFAULT_RATING = 2.5;
    private List<Jeep> availableJeeps = new ArrayList<>();
    private List<Double> weeklyRatings = new ArrayList<>();
    private int ticketPriceLevel = 1; // 1: 20, 2: 40, 3: 80, 4: 160
    private boolean raisePrice = false;
    private boolean lowerPrice = false;
    private int touristWeekCounter = 0;
    private Random random = new Random();
    private long totalGameHours = 0; // Accumulates in-game hours
    private final List<Tourist> waitingTouristList = new ArrayList<>();
    private int nextTouristId = 0;
    private final List<Double> currentWeekRatings = new ArrayList<>();
    private double cumulativeRating = 2.5;
    private int ratingCount = 1;






    // CONSTRUCTORS -----------------------
    public Map() {
        tiles = new ArrayList<>();
        objects = new ArrayList<>();
        entities = new ArrayList<>();
        money = 2500;
        level = "default";
        gameClock = new GameClock(LocalDateTime.now());
        initScheduler();
    }

    public Map(String level) {
        tiles = new ArrayList<>();
        objects = new ArrayList<>();
        entities = new ArrayList<>();
        money = 2500;
        this.level = level;
        gameClock = new GameClock(LocalDateTime.now());
        initScheduler();
    }

    public Map(MapState mapState) {
        tiles = mapState.tiles;
        objects = mapState.objects;
        entities = mapState.entities;
        money = mapState.money;
        gameClock = new GameClock(LocalDateTime.parse(mapState.timeString));
        savingFileName = mapState.savingFileName;
        level = mapState.level;
        initScheduler();
    }
    // -------------------------------------

    // MAP GENERATION ----------------------
    public void generateMap() {
        MapGenerator mapGenerator = new MapGenerator();
        int[][] mapnumbers = mapGenerator.getTileMap();

        for (int i = 0; i < mapGenerator.SIZE; i++) {
            for (int j = 0; j < mapGenerator.SIZE; j++) {
                tiles.add(new Tile(new Point(i, j), mapnumbers[i][j]));
            }
        }
    }

    public void placeEntranceAndExit() {
        Random random = new Random();
        int maxAttempts = 100;

        for (int attempt = 0; attempt < maxAttempts; attempt++) {
            int y = random.nextInt(50);  // pick row

            boolean pathClear = true;
            for (int x = 0; x < 50; x++) {
                Tile tile = getTileAt(x, y);
                if (tile == null || tile.getHealth() == -1) {
                    pathClear = false;
                    break;
                }

                for (Object obj : getObjects()) {
                    if (obj.getPos().equals(tile.getPos()) && obj instanceof Plant) {
                        pathClear = false;
                        break;
                    }
                }

                if (!pathClear) break;
            }

            if (pathClear) {
                // Place entrance on left
                Point leftPoint = new Point(0, y);
                objects.add(new EntranceExitRoad(new Point(0, y), true));  // entrance


                // Place exit on right
                Point rightPoint = new Point(49, y);
                objects.add(new EntranceExitRoad(new Point(49, y), false)); // exit

                return;
            }
            System.out.println("couldnt place");
        }

        System.out.println("Failed to place entrance/exit after " + maxAttempts + " attempts.");
    }

    public boolean areEntranceAndExitConnected() {
        Point entrance = null;
        Point exit = null;

        for (Object obj : objects) {
            if (obj instanceof EntranceExitRoad road) {
                if (road.isEntrance) entrance = road.getPos();
                else exit = road.getPos();
            }
        }

        if (entrance == null || exit == null) return false;

        return isConnectedRecursive(entrance, null, exit, new HashSet<>());
    }


    private boolean isConnectedRecursive(Point current, Point previous, Point target, Set<Point> visited) {
        if (!isWithinBounds(current) || visited.contains(current)) return false;
        if (current.equals(target)) return true;

        visited.add(current);

        Object currentObj = getObjectAt(current);
        if (!(currentObj instanceof Road currentRoad)) return false;

        for (RoadDirection dir : currentRoad.direction) {
            Point next = dir.move(current);
            if (next.equals(previous)) continue;

            Object nextObj = getObjectAt(next);
            if (nextObj instanceof Road || (nextObj instanceof EntranceExitRoad && next.equals(target))) {
                if (isConnectedRecursive(next, current, target, visited)) return true;
            }
        }

        return false;
    }


    private boolean isWithinBounds(Point p) {
        return p.getX() >= 0 && p.getX() < 50 && p.getY() >= 0 && p.getY() < 50;
    }

    private Object getObjectAt(Point pos) {
        for (Object obj : objects) {
            if (obj.getPos().equals(pos)) return obj;
        }
        return null;
    }
    private long lastJeepLaunchGameHours = -100;

    private void tickOneHour() {
        totalGameHours++;

        // Handle tourist week
        if (totalGameHours % 168 == 0) {
            handleTouristWeek();
        }

        if (totalGameHours % 24 == 0) {
            spawnTouristsForDay();
        }


        // Move all entities once per hour
        moveEntitiesOnce();

        // Launch jeeps if 5 in-game hours passed
        if (totalGameHours - lastJeepLaunchGameHours >= 5) {
            if (checkLaunchJeep()) {
                lastJeepLaunchGameHours = totalGameHours;
            }
        }

        Iterator<Tourist> iterator = waitingTouristList.iterator();
        while (iterator.hasNext()) {
            Tourist tourist = iterator.next();
            tourist.waitHours++;
            if (tourist.waitHours >= 168) {
                currentWeekRatings.add(0.0); // dropped with 0 rating
                iterator.remove();
            }
        }

    }


    private void spawnTouristsForDay() {
        double avg = ratingCount > 0 ? cumulativeRating / ratingCount : 2.5;

        int baseMin = 1 + (int)(avg * 1.5);  // e.g., 1–9
        int baseMax = 3 + (int)(avg * 2.0);  // e.g., 3–13
        int arriving = baseMin + random.nextInt(baseMax - baseMin + 1);

        for (int i = 0; i < arriving; i++) {
            waitingTouristList.add(new Tourist(nextTouristId++));
            totalTouristsThisWeek++;
        }

        System.out.println("New tourists today: " + arriving + " (Avg rating: " + avg + ")");
    }

    public double getCurrentAverageRating() {
        return ratingCount > 0 ? cumulativeRating / ratingCount : 0.0;
    }








    // -------------------------------------

    // LIST GETTERS ------------------------
    public List<Tile> getTiles() {
        return new ArrayList<>(tiles);
    }

    public List<Object> getObjects() {
        return new ArrayList<>(objects);
    }

    public List<Entity> getEntities() {
        return new ArrayList<>(entities);
    }

    public Tile getTileAt(int x, int y) {
        for (Tile tile : tiles) {
            if (tile.getPos().getX() == x && tile.getPos().getY() == y) {
                return tile;
            }
        }
        return null;
    }

    public LocalDateTime getTime() {
        return gameClock.getCurrentTime();
    }



    // -------------------------------------

    // SETTERS -----------------------------

    public void setObjects(List<Object> objects) {
        this.objects = objects;
    }

    //--------------------------------------
    public Point getEntrancePosition() {
        for (Object obj : objects) {
            if (obj instanceof EntranceExitRoad road && road.isEntrance) {
                return road.getPos();
            }
        }
        return null;
    }

    // SHOP FUNCTIONS ----------------------
    public int buyEntity(Entity entity) {
        if (this.money >= entity.price)
        {
            List<Tile> nonWaterTiles = new ArrayList<>();
            for (Tile tile : tiles) {
                if (tile.getHealth() >= 0) {
                    nonWaterTiles.add(tile);
                }
            }
            if (!nonWaterTiles.isEmpty()) {
                Random random = new Random();
                entity.setPos(nonWaterTiles.get(random.nextInt(nonWaterTiles.size())).getPos());
            } else {
                return -1;
            }


            if (entity instanceof Jeep jeep) {
                availableJeeps.add(jeep);
            } else {
                entities.add(entity);
            }

            this.money -= entity.price;
            return 1;
        }
        return 0;
    }
    public int buyObject(Object object) {
        if (this.money >= object.price) {
            objects.add(object);
            this.money -= object.price;
            return 1;
        }
        return 0;
    }

    public void buyWater(int x, int y) {
        for (Tile tile : tiles) {
            if (tile.getPos().getX() == x && tile.getPos().getY() == y) {
                tile.setHealth(-1);
                this.money -= this.waterPrice;
                break;
            }
        }
    }

    public void buyGrass(int x, int y) {
        for (Tile tile : tiles) {
            if (tile.getPos().getX() == x && tile.getPos().getY() == y) {
                tile.setHealth(100);
                this.money -= this.grassPrice;
                break;
            }
        }
    }

    public void sellKeeper(Keeper keeper) {
        this.entities.remove(keeper);
    }

    public void sellEntity(Entity entity) {
        this.entities.remove(entity);
        this.money += entity.price;
    }

    public void sellObject(Object object) {
        this.objects.remove(object);
        this.money += object.price;
    }

    public void generatePlants() {
        List<Tile> grassTiles = new ArrayList<>();
        Random random = new Random();

        for (Tile tile : tiles) {
            if (tile.getHealth() > 0) {
                grassTiles.add(tile);
            }
        }

        int totalGrass = grassTiles.size();
        int bushCount = (int) (totalGrass * 0.1);
        int treeCount = (int) (totalGrass * 0.1);

        for (int i = 0; i < bushCount; i++) {
            int index = random.nextInt(grassTiles.size());
            Tile selectedTile = grassTiles.remove(index);
            selectedTile.setHealth(selectedTile.getHealth() + 50);
            objects.add(new Plant(selectedTile.getPos(), PlantType.Bush));
        }

        for (int i = 0; i < treeCount; i++) {
            if (grassTiles.isEmpty()) break;
            int index = random.nextInt(grassTiles.size());
            Tile selectedTile = grassTiles.remove(index);
            selectedTile.setHealth(selectedTile.getHealth() + 100);
            objects.add(new Plant(selectedTile.getPos(), PlantType.Tree));
        }
    }

    public int getWidth() {
        return (int) Math.sqrt(tiles.size());
    }

    public int getHeight() {
        return (int) Math.sqrt(tiles.size());
    }

    public boolean isPaused() {
        return gameClock.isPaused();
    }
    // -------------------------------------

    private void initScheduler() {
        scheduler = Executors.newScheduledThreadPool(1);
        final long intervalMillis = 1000;

        scheduler.scheduleAtFixedRate(() -> {
            if (gameClock.isPaused()) return;

            long hoursPerTick = gameClock.getIncrementPerTick().toHours();
            for (int i = 0; i < hoursPerTick; i++) {
                tickOneHour();
            }
        }, 0, intervalMillis, TimeUnit.MILLISECONDS);
    }


    private void moveEntitiesOnce() {
        Point zebraPos = null;
        Point lionPos = null;
        Point elephantPos = null;
        Point hyenaPos = null;

        for (Entity entity : new ArrayList<>(entities)) {
            if (entity instanceof Animal animal) {
                setLeader(); // group sync
                animal.move(this);
                if (!animal.isAlive()) {
                    entities.remove(animal);
                }else {
                    if (animal instanceof Zebra) zebraPos = animal.getPos();
                    else if (animal instanceof Lion) lionPos = animal.getPos();
                    else if (animal instanceof Elephant) elephantPos = animal.getPos();
                    else if (animal instanceof Hyena) hyenaPos = animal.getPos();
                }
            } else if (entity instanceof Jeep jeep) {
                jeep.move(this);
            } else if (entity instanceof Poacher poacher) {
                boolean success = poacher.move(this);
                if (success) break;
            } else if (entity instanceof Keeper keeper) {
                if (keeper.isPoacherInRange(this.entities)||(keeper.targetPoint.getX() == 0 && keeper.targetPoint.getY() == 0 ) || (keeper.pos.getX() == keeper.targetPoint.getX() && keeper.pos.getY() == keeper.targetPoint.getY())) {
                    keeper.chooseTargetPoint(this.tiles, this.entities);
                }
                keeper.move(this);
                this.money -= keeper.price;
                if (keeper.isPoacherInShootingRange(this.entities)) {
                    if (keeper.keeperWonInFight()) {
                        Poacher poacher = getPoacher();
                        if (poacher.capturedAnimal != null) {
                            poacher.capturedAnimal.pos.setX(poacher.pos.getX());
                            poacher.capturedAnimal.pos.setY(poacher.pos.getY());
                            entities.add(poacher.capturedAnimal);
                        }
                        entities.remove(poacher);
                    }
                    else entities.remove(keeper);
                    break;
                }

            }
        }

        if (shouldSpawnPoacher()) {
            entities.add(new Poacher(this, setStartPointForPoacher(this.getTiles())));
        }

        checkLaunchJeep();

        boolean[] breedable = breed(); // zebra, lion, elephant, hyena
        Random rand = new Random();

        if (breedable[0] && zebraPos != null && rand.nextDouble() < 0.1) {
            entities.add(new Zebra(new Point(zebraPos.getX(), zebraPos.getY())));
        }
        if (breedable[1] && lionPos != null && rand.nextDouble() < 0.1) {
            entities.add(new Lion(new Point(lionPos.getX(), lionPos.getY())));
        }
        if (breedable[2] && elephantPos != null && rand.nextDouble() < 0.1) {
            entities.add(new Elephant(new Point(elephantPos.getX(), elephantPos.getY())));
        }
        if (breedable[3] && hyenaPos != null && rand.nextDouble() < 0.1) {
            entities.add(new Hyena(new Point(hyenaPos.getX(), hyenaPos.getY())));
        }

    }

    private boolean shouldSpawnPoacher() {
        boolean alreadyExists = entities.stream().anyMatch(e -> e instanceof Poacher);
        if (alreadyExists) return false;

        long animalCount = entities.stream()
            .filter(e -> e instanceof Animal && ((Animal) e).isAlive())
            .count();

        if (animalCount == 0) return false;

        double spawnChance = Math.min(animalCount / 10.0, 1.0);
        return random.nextDouble() < spawnChance;
    }




    private boolean checkLaunchJeep() {
        if (waitingTouristList.size() <= 0 || availableJeeps.isEmpty()) return false;

        Jeep jeep = availableJeeps.remove(0);
        int toBoard = Math.min(4, waitingTouristList.size());
        jeep.startTour(ticketPriceLevel, toBoard);
        for (int i = 0; i < toBoard && !waitingTouristList.isEmpty(); i++) {
            waitingTouristList.remove(0);
        }
        for (int i = 0; i < toBoard && !waitingTouristList.isEmpty(); i++) {
            waitingTouristList.remove(0);
        }


        Point entrance = getEntrancePosition();
        if (entrance != null) {
            jeep.setPos(entrance);
            jeep.roadNumber = findRoadNumber(entrance);
            entities.add(jeep);
        }

        return true;
    }


    private Point setStartPointForPoacher(List<Tile> tiles) {
        Tile startTile;
        do {
            int randInt = new Random().nextInt(tiles.size());
            startTile = tiles.get(randInt);
        } while ((startTile.pos.getX() != 0 && startTile.pos.getY() != 0) || startTile.health == -1);
        return startTile.pos;
    }


    public void stopScheduler() {
        if (scheduler != null) {
            scheduler.shutdown();
        }
        if (gameClock != null) {
            gameClock.stop();
        }
    }

    // GAME CLOCK CONTROLS -----------------
    public void pauseGameClock() {
        gameClock.pause();
    }

    public void resumeGameClock() {
        gameClock.resume();
    }

    public void setSpeedToHourPerSecond() {
        gameClock.setSpeedToHourPerSecond();
    }

    public void setSpeedToDayPerSecond() {
        gameClock.setSpeedToDayPerSecond();
    }

    public void setSpeedToWeekPerSecond() {
        gameClock.setSpeedToWeekPerSecond();
    }


    private Animal selectLeader(Animal animal) {
        Animal oldest = null;

        for (Entity entity : entities) {
            if (!(entity instanceof Animal)) continue;

            Animal other = (Animal) entity;

            if (animal.getClass().equals(other.getClass())) {
                if (oldest == null || other.getAge() > oldest.getAge()) {
                    oldest = other;
                }
            }
        }

        return oldest;
    }

    private void setLeader() {
        for (Entity entity : entities) {
            if (entity instanceof Animal animal) {
                Animal leader = selectLeader(animal);

                if (animal == leader) {
                    animal.setLeader(null);
                } else {
                    animal.setLeader(leader);
                }
            }
        }
    }


    // Tourist logic

    private void handleTouristWeek() {
        double averageRating = currentWeekRatings.isEmpty() ? DEFAULT_RATING :
            currentWeekRatings.stream().mapToDouble(r -> r).average().orElse(DEFAULT_RATING);

        weeklyRatings.add(averageRating);
        currentWeekRatings.clear();

        if (totalTouristsThisWeek >= (100 * ticketPriceLevel) && averageRating >= 3.5) {
            raisePrice = true;
        } else {
            raisePrice = false;
        }

        lowerPrice = averageRating < 2.5;
        totalTouristsThisWeek = 0; // ✅ Reset weekly count

        System.out.println("Weekly average rating: " + averageRating);
    }





    public int findRoadNumber(Point pos)
    {
        for (Object obj : objects) {
            if (obj instanceof Road road) {
                if (road.getPos().equals(pos)) {
                    return road.roadNumber;
                }
            }
        }
        return -1;
    }

    public void autoNumberRoadsFromEntrance() {
        Point entrance = null;

        // Step 1: find entrance
        for (Object obj : objects) {
            if (obj instanceof EntranceExitRoad road && road.isEntrance) {
                entrance = road.getPos();
                break;
            }
        }
        if (entrance == null) {
            System.out.println("Entrance not found.");
            return;
        }

        // Step 2: reset all road numbers
        for (Object obj : objects) {
            if (obj instanceof Road road) {
                road.roadNumber = -1;
            }
        }

        // Step 3: BFS to number roads
        Queue<Point> queue = new LinkedList<>();
        Set<Point> visited = new HashSet<>();

        queue.add(entrance);
        visited.add(entrance);

        int counter = 0;

        while (!queue.isEmpty()) {
            Point current = queue.poll();
            Object obj = getObjectAt(current);
            if (!(obj instanceof Road road)) continue;

            road.roadNumber = counter++;

            for (RoadDirection dir : road.direction) {
                Point next = dir.move(current);
                if (!visited.contains(next)) {
                    Object nextObj = getObjectAt(next);
                    if (nextObj instanceof Road || nextObj instanceof EntranceExitRoad) {
                        visited.add(next);
                        queue.add(next);
                    }
                }
            }
        }

        // Step 4: reposition jeeps to match stored roadNumber
        for (Entity entity : entities) {
            if (entity instanceof Jeep jeep) {
                for (Object obj : objects) {
                    if (obj instanceof Road road && road.roadNumber == jeep.roadNumber) {
                        jeep.setPos(road.getPos());
                        break;
                    }
                }
            }
        }

        System.out.println("Auto-numbered roads from entrance.");
    }

    public GameClock getGameClock() {
        return gameClock;
    }

    public int getMaxRoadNumber() {
        int max = -1;
        for (Object obj : objects) {
            if (obj instanceof Road road) {
                max = Math.max(max, road.roadNumber);
            }
        }
        return max;
    }

    public void increaseTicketPrice() {
        if (raisePrice && ticketPriceLevel < 4) {
            ticketPriceLevel++;
            raisePrice = false;
            lowerPrice = false;

            // ✅ Reset average tracking
            cumulativeRating = DEFAULT_RATING;
            ratingCount = 1;

            System.out.println("Ticket price increased to level " + ticketPriceLevel);
        }
    }

    public void decreaseTicketPrice() {
        if (lowerPrice && ticketPriceLevel > 1) {
            ticketPriceLevel--;
            raisePrice = false;
            lowerPrice = false;

            // ✅ Reset average tracking
            cumulativeRating = DEFAULT_RATING;
            ratingCount = 1;

            System.out.println("Ticket price decreased to level " + ticketPriceLevel);
        }
    }




    public int getAvailableJeepCount() {
        return availableJeeps.size();
    }

    public int getWaitingTouristCount() {
        return waitingTouristList.size();
    }

    public int getTouristCountOnTour() {
        return entities.stream()
            .filter(e -> e instanceof Jeep)
            .mapToInt(e -> ((Jeep)e).getPassengerCount())
            .sum();
    }

    public boolean canIncreaseTicketPrice() {
        return raisePrice && ticketPriceLevel < 4;
    }

    public boolean canDecreaseTicketPrice() {
        return lowerPrice && ticketPriceLevel > 1;
    }

    public void completeJeepTour(Jeep jeep) {
        double rating = jeep.endTour(); // Calculates rating based on tour experience
        entities.remove(jeep);
        availableJeeps.add(jeep);

        // Track rating
        currentWeekRatings.add(rating);
        cumulativeRating += rating;
        ratingCount++;

        System.out.println("Tour ended. Rating: " + String.format("%.2f", rating));
    }


    public Poacher getPoacher() {
        Poacher poacher = null;
        for(Entity entity : entities) {
            if (entity instanceof Poacher) {
                poacher = (Poacher) entity;
                break;
            }
        }
        return poacher;
    }

    public boolean[] breed() {
        int zebraCount = 0;
        int lionCount = 0;
        int elephantCount = 0;
        int hyenaCount = 0;

        for (Entity e : entities) {
            if(e instanceof Animal) {
                int age = ((Animal) e).getAge();
                int maxAge = ((Animal) e).getMaxAge();

                if (age >= maxAge / 2) {
                    if (e instanceof Zebra) zebraCount++;
                    else if (e instanceof Lion) lionCount++;
                    else if (e instanceof Elephant) elephantCount++;
                    else if (e instanceof Hyena) hyenaCount++;
                }
            }

        }

        boolean zebraBreedable = zebraCount >= 2;
        boolean lionBreedable = lionCount >= 2;
        boolean elephantBreedable = elephantCount >= 2;
        boolean hyenaBreedable = hyenaCount >= 2;

        return new boolean[] { zebraBreedable, lionBreedable, elephantBreedable, hyenaBreedable };
    }


    public float getTotalTouristsThisWeek() {
        return totalTouristsThisWeek;
    }

    public int isPlayerWinning() {
        switch (level) {
            case "easy":
                if (money >= 10000) return 1;
                else if (money <= 0) return -1;
                else return 0;
            case "medium":
                if (money >= 15000) return 1;
                else if (money <= 0) return -1;
                else return 0;
            case "hard":
                if (money >= 20000) return 1;
                else if (money <= 0) return -1;
                else return 0;
            default:
                return 0;
        }
    }
}
