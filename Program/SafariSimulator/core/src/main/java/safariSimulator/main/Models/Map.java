package safariSimulator.main.Models;

import safariSimulator.main.Models.Entity.Animal.Animal;
import safariSimulator.main.Models.Entity.Entity;
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
    public int[][] generateMap() {
        int size = 500;
        int seed = 12345;
        double scale = 0.05;

        int[] permutation = generatePermutation(seed);

        int[][] map = new int[size][size];

        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                double noiseValue = noise(permutation, x * scale, y * scale);
                if (noiseValue < -0.1) {
                    map[x][y] = -1;  // Víz
                } else if (noiseValue < 0.05) {
                    map[x][y] = 0;   // Föld
                } else {
                    map[x][y] = 100;   // Fű
                }
            }
        }
        return map;
    }

    private int[] generatePermutation(int seed) {
        Random rand = new Random(seed);
        int[] p = new int[256];

        for (int i = 0; i < 256; i++) {
            p[i] = i;
        }

        for (int i = 0; i < 256; i++) {
            int j = rand.nextInt(256);
            int tmp = p[i];
            p[i] = p[j];
            p[j] = tmp;
        }

        int[] permutation = new int[512];
        System.arraycopy(p, 0, permutation, 0, 256);
        System.arraycopy(p, 0, permutation, 256, 256);

        return permutation;
    }

    private double noise(int[] permutation, double x, double y) {

        int xi = (int) Math.floor(x) & 255;
        int yi = (int) Math.floor(y) & 255;
        double xf = x - Math.floor(x);
        double yf = y - Math.floor(y);

        double u = fade(xf);
        double v = fade(yf);

        int aa = permutation[permutation[xi] + yi];
        int ab = permutation[permutation[xi] + yi + 1];
        int ba = permutation[permutation[xi + 1] + yi];
        int bb = permutation[permutation[xi + 1] + yi + 1];

        double x1 = lerp(grad(aa, xf, yf), grad(ba, xf - 1, yf), u);
        double x2 = lerp(grad(ab, xf, yf - 1), grad(bb, xf - 1, yf - 1), u);
        return lerp(x1, x2, v);
    }

    private double fade(double x) {
        return x * x * x * (x * (x * 6 - 15) + 10);
    }

    private double lerp(double a, double b, double x) {
        return a + x * (b - a);
    }

    private double grad(int hash, double x, double y) {
        int h = hash & 7;
        double u = h < 4 ? x : y;
        double v = h < 4 ? y : x;
        return ((h & 1) == 0 ? u : -u) + ((h & 2) == 0 ? v : -v);
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
