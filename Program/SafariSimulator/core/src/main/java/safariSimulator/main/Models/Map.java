package safariSimulator.main.Models;

import safariSimulator.main.Models.Entity.Animal.Animal;
import safariSimulator.main.Models.Entity.Entity;
import safariSimulator.main.Models.Entity.Jeep;
import safariSimulator.main.Models.MapGeneration.MapGenerator;
import safariSimulator.main.Models.Objects.Plant;
import safariSimulator.main.Models.Objects.PlantType;
import safariSimulator.main.Models.Tile.Tile;
import safariSimulator.main.Models.Objects.Object;

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class Map {

    private List<Tile> tiles;
    private List<Object> objects;
    private List<Entity> entities;
    public int money;
    public LocalDateTime time;

    // CONSTRUCTURES -----------------------
    public Map() {
        tiles = new ArrayList<Tile>();
        objects = new ArrayList<Object>();
        entities = new ArrayList<Entity>();
        money = 1000;
        time = LocalDateTime.now();
    }
    public Map(List<Tile> tiles, List<Object> objects, List<Entity> entities, int money, LocalDateTime time) {
        this.tiles = tiles;
        this.objects = objects;
        this.entities = entities;
        this.money = money;
        this.time = time;
    }
    // -------------------------------------

    // MAP GENERATING ----------------------

    public void generateMap(){
        MapGenerator mapGenerator = new MapGenerator();
        int[][] mapnumbers = mapGenerator.getTileMap();

        for (int i = 0; i < mapGenerator.SIZE; i++)
        {
            for(int j = 0; j < mapGenerator.SIZE; j++){
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


    // -------------------------------------

    // SHOP FUNCTIONS ----------------------
    public int buyEntity(Entity entity) {
        if (this.money >= entity.price) {
            if (entity instanceof Jeep) {
                entity.setPos(new Point(0, 0));
            } else {
                List<Tile> nonWaterTiles = new ArrayList<>();
                Random random = new Random();

                for (Tile tile : tiles) {
                    if (tile.getHealth() >= 0) {
                       nonWaterTiles.add(tile);
                    }
                }
                if (!nonWaterTiles.isEmpty()) {
                    Random random2 = new Random();
                    entity.setPos(nonWaterTiles.get(random2.nextInt(nonWaterTiles.size())).getPos());
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

    public int buyObject(Object object){
        if(this.money >= object.price){
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
                break;
            }
        }
    }

    public void buyGrass(int x, int y) {
        for (Tile tile : tiles) {
            if (tile.getPos().getX() == x && tile.getPos().getY() == y) {
                tile.setHealth(100);
                break;
            }
        }
    }

    public void sellEntity(Animal animal){
        this.entities.remove(animal);
        this.money += animal.price;
    }

    public void sellObject(Object object){
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
            objects.add(new Plant(selectedTile.getPos(), PlantType.Bush));
        }

        for (int i = 0; i < treeCount; i++) {
            if (grassTiles.isEmpty()) break;
            int index = random.nextInt(grassTiles.size());
            Tile selectedTile = grassTiles.remove(index);
            objects.add(new Plant(selectedTile.getPos(), PlantType.Tree));
        }
    }

    public int getWidth() {
        return (int) Math.sqrt(tiles.size());
    }

    public int getHeight() {
        return (int) Math.sqrt(tiles.size());
    }
    // -------------------------------------





}
