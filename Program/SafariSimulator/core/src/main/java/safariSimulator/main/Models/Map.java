package safariSimulator.main.Models;

import safariSimulator.main.Database.MapState;
import safariSimulator.main.Models.Entity.Animal.Animal;
import safariSimulator.main.Models.Entity.Entity;
import safariSimulator.main.Models.Entity.Human.Poacher;
import safariSimulator.main.Models.Entity.Jeep;
import safariSimulator.main.Models.MapGeneration.MapGenerator;
import safariSimulator.main.Models.Objects.Plant;
import safariSimulator.main.Models.Objects.PlantType;
import safariSimulator.main.Models.Tile.Tile;
import safariSimulator.main.Models.Objects.Object;
import safariSimulator.main.Models.GameClock;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
    public int grassPrice = 30;
    public int waterPrice = 30;

    // CONSTRUCTORS -----------------------
    public Map() {
        tiles = new ArrayList<>();
        objects = new ArrayList<>();
        entities = new ArrayList<>();
        money = 1000;
        level = "default";
        gameClock = new GameClock(LocalDateTime.now());
        initScheduler();
    }

    public Map(String level) {
        tiles = new ArrayList<>();
        objects = new ArrayList<>();
        entities = new ArrayList<>();
        money = 1000;
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
    public void setTiles(List<Tile> tiles) {
        this.tiles = tiles;
    }

    public void setObjects(List<Object> objects) {
        this.objects = objects;
    }

    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }

    //--------------------------------------

    // SHOP FUNCTIONS ----------------------
    public int buyEntity(Entity entity) {
        if (this.money >= entity.price) {
            if (entity instanceof Jeep) {
                entity.setPos(new Point(0, 0));
            } else {
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
            }

            entities.add(entity);
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

    public void sellEntity(Animal animal) {
        this.entities.remove(animal);
        this.money += animal.price;
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
        scheduler.scheduleAtFixedRate(this::moveAnimals, 0, 1000, TimeUnit.MILLISECONDS);
        /// EZT KOMMENTELD KI HA NEM AKAROD HOGY JÖJJÖN POACHER
        scheduler.scheduleAtFixedRate(this::movePoacher, 0, 1000, TimeUnit.MILLISECONDS);
    }

    private void moveAnimals() {
        if (gameClock.isPaused()) return;

        long hours = gameClock.getIncrementPerTick().toHours();

        // Fallback to at least one movement if it's 0 (safety)
        int ticks = (int) Math.max(1, hours);

        for (int i = 0; i < ticks; i++) {
            for (Entity entity : entities) {
                if (entity instanceof Animal) {
                    setLeader();
                    ((Animal) entity).move(this);
                    System.out.println(((Animal) entity).getLeader());
                    System.out.println(((Animal) entity).getWaterLevel());
                    System.out.println(((Animal) entity).getFoodLevel());
                    if ( !((Animal) entity).isAlive()) {
                        entities.remove(entity);
                        break; // Exit the loop to avoid ConcurrentModificationException
                    }
                }
            }
        }
    }

    private void movePoacher() {
        if (gameClock.isPaused()) return;

        long hours = gameClock.getIncrementPerTick().toHours();
        int ticks = (int) Math.max(1, hours);

        for (int i = 0; i < ticks; i++) {
            if (addPoacher()) {
                for (Entity entity : entities) {
                    if (entity instanceof Poacher) {
                        boolean isHunted = ((Poacher) entity).move(this);
                        if (isHunted) break;
                    }
                }
            }
        }
    }

    private boolean addPoacher() {
        for (Entity entity : entities) {
            if (entity instanceof Poacher) {
                return true;
            }
        }
        int animalCount = (int) entities.stream().filter(e -> e instanceof Animal && ((Animal) e).isAlive()).count();
        if (animalCount == 0) return false;

        double baseChance = 1;
        double maxChance = 1;
        double spawnChance = Math.min(animalCount / 10.0 * baseChance, maxChance);

        Random random = new Random();
        if (random.nextDouble() < spawnChance) {
            entities.add(new Poacher(this, setStartPointForPoacher(this.getTiles())));
            return true;
        }
        return false;
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

    public void resetClockSpeed() {
        gameClock.resetSpeed();
    }
    // -------------------------------------

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
}
