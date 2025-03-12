package safariSimulator.main.Models;

import safariSimulator.main.Models.Entity.Animal.Animal;
import safariSimulator.main.Models.Entity.Entity;
import safariSimulator.main.Models.Tile.Tile;
import safariSimulator.main.Models.Objects.Object;

import java.time.*;
import java.util.List;

public class Map {

    private List<Tile> tiles;
    private List<Object> objects;
    private List<Entity> entities;
    public int money;
    public LocalDateTime time;

    public Map() {

    }

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





}
