package safariSimulator.main.Models.MapGeneration;

import java.util.Random;

public class MapGenerator {
    public static final int SIZE = 50;
    private static final double SCALE = 0.0002; // Smoother terrain

    private final int[][] tileMap; // Stores values: -1 (Water), 0 (Grass), 100 (Dry)

    public MapGenerator() {
        double[][] noiseMap = generatePerlinNoise(SIZE, SIZE, SCALE);
        this.tileMap = generateTileMap(noiseMap);
    }

    private double[][] generatePerlinNoise(int width, int height, double scale) {
        PerlinNoise noise = new PerlinNoise(new Random().nextInt());
        double[][] map = new double[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                map[x][y] = noise.noise(x * scale, y * scale);
            }
        }
        return map;
    }

    private int[][] generateTileMap(double[][] noiseMap) {
        int[][] tileMap = new int[SIZE][SIZE];

        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                double value = noiseMap[x][y];

                if (value < -0.6) {
                    tileMap[x][y] = -1; // Water
                } else if (value < -0.5) {
                    tileMap[x][y] = 0;  // Grass
                } else {
                    tileMap[x][y] = 100; // Dry land
                }
            }
        }

        return tileMap;
    }

    public int[][] getTileMap() {
        return tileMap;
    }

    // Simple console test
    public void printMap() {
        for (int y = 0; y < SIZE; y += 50) { // Print every 50 rows for better readability
            for (int x = 0; x < SIZE; x += 50) { // Print every 50 columns
                System.out.print(getSymbol(tileMap[x][y]) + " ");
            }
            System.out.println();
        }
    }

    private char getSymbol(int value) {
        return switch (value) {
            case -1 -> 'W'; // Water
            case 0 -> 'G';  // Grass
            case 100 -> 'D'; // Dry land
            default -> '?';
        };
    }
}
