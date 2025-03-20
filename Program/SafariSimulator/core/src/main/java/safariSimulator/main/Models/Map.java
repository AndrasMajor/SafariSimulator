package safariSimulator.main.Models;

import safariSimulator.main.Models.Entity.Animal.Animal;
import safariSimulator.main.Models.Entity.Entity;
import safariSimulator.main.Models.MapGeneration.MapGenerator;
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
    // -------------------------------------

    // SHOP FUNCTIONS ----------------------
    public int buyAnimal(Animal animal){
        if(this.money >= animal.price){
            entities.add(animal);
            this.money -= animal.price;
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

    public void sellAnimal(Animal animal){
        this.entities.remove(animal);
        this.money += animal.price;
    }

    public void sellObject(Object object){
        this.objects.remove(object);
        this.money += object.price;
    }
    // -------------------------------------





}
