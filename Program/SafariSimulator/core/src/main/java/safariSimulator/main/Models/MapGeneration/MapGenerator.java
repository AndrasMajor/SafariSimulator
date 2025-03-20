package safariSimulator.main.Models.MapGeneration;

import java.util.Random;

import java.util.Random;

public class MapGenerator {
    public static final int SIZE = 50;
    private static final double SCALE = 0.1; // Slightly smoother terrain
    private final int[][] tileMap;

    public MapGenerator() {
        double[][] noiseMap = generatePerlinNoise(SIZE, SIZE, SCALE);
        this.tileMap = generateTileMap(noiseMap);
    }

    private static double[][] generatePerlinNoise(int width, int height, double scale) {
        PerlinNoise noise = new PerlinNoise(new Random().nextInt());
        double[][] map = new double[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                map[x][y] = noise.noise(x * scale, y * scale);
            }
        }
        return map;
    }

    private static int[][] generateTileMap(double[][] noiseMap) {
        int[][] tileMap = new int[SIZE][SIZE];
        Random rand = new Random();

        // Step 1: Generate base terrain
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                double value = noiseMap[x][y];

                if (value < -0.6) {
                    tileMap[x][y] = -1;  // Water
                } else if (value < -0.15) {
                    tileMap[x][y] = 100;  // Grass
                } else {
                    tileMap[x][y] = 0;  // Dry land
                }
            }
        }

        // Step 2: Ensure 2-3 Water Sources (Flood-Fill Style)
        placeWaterSources(tileMap, rand);

        return tileMap;
    }

    private static void placeWaterSources(int[][] tileMap, Random rand) {
        int waterSources = 0;
        while (waterSources < 6) {  // Ensure only 3 separate water bodies
            int x = rand.nextInt(SIZE);
            int y = rand.nextInt(SIZE);

            if (tileMap[x][y] == 0) {  // Only place water in dry areas
                int maxSize = rand.nextInt(8) + 8; // Random pond size (4 to 10)
                tileMap[x][y] = -1;
                expandWater(tileMap, x, y, rand, maxSize, 0);
                waterSources++;
            }
        }
    }

    private static void expandWater(int[][] tileMap, int x, int y, Random rand, int maxSize, int currentSize) {
        if (currentSize >= maxSize) return; // Stop expansion if max size is reached

        int[] dx = {0, 1, -1, 0}; // Right, Down, Left, Up
        int[] dy = {1, 0, 0, -1};

        for (int i = 0; i < 4; i++) { // Expand in 4 random directions
            if (rand.nextDouble() < 0.5) continue; // 50% chance to skip expansion

            int newX = x + dx[i];
            int newY = y + dy[i];

            if (newX >= 0 && newY >= 0 && newX < SIZE && newY < SIZE) {
                if (tileMap[newX][newY] == 0) { // Expand water only into dry areas
                    tileMap[newX][newY] = -1;
                    expandWater(tileMap, newX, newY, rand, maxSize, currentSize + 1);
                }
            }
        }

        // **Surround the pond with grass**
        for (int i = 0; i < 4; i++) {
            int grassX = x + dx[i];
            int grassY = y + dy[i];

            if (grassX >= 0 && grassY >= 0 && grassX < SIZE && grassY < SIZE) {
                if (tileMap[grassX][grassY] == 0) { // Convert dry land around water into grass
                    tileMap[grassX][grassY] = 100;
                }
            }
        }
    }

    // Method to get the generated tile map
    public int[][] getTileMap() {
        return tileMap;
    }
}
