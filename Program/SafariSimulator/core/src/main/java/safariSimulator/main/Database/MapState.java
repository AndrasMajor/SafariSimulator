package safariSimulator.main.Database;

import safariSimulator.main.Models.Entity.Entity;
import safariSimulator.main.Models.Entity.Human.Tourist;
import safariSimulator.main.Models.Entity.Jeep;
import safariSimulator.main.Models.GameClock;
import safariSimulator.main.Models.Map;
import safariSimulator.main.Models.Objects.Object;
import safariSimulator.main.Models.Tile.Tile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ScheduledExecutorService;

public class MapState {
    public List<Tile> tiles;
    public List<Object> objects;
    public List<Entity> entities;
    public String gameClockTimeString;
    public float incrementPerTickSeconds;
    public boolean clockPaused;
    public int money;
    public String savingFileName;
    public String level;
    public int grassPrice;
    public int waterPrice;
    public int totalTouristsThisWeek;
    public boolean won;
    public boolean lost;


    // tourist based vars
    public List<Jeep> availableJeeps;
    public List<Double> weeklyRatings;
    public int ticketPriceLevel; // 1: 20, 2: 40, 3: 80, 4: 160
    public boolean raisePrice;
    public boolean lowerPrice;
    public int touristWeekCounter;
    public long totalGameHours; // Accumulates in-game hours
    public int nextTouristId;
    public double cumulativeRating;
    public int ratingCount;

    public MapState(Map map) {
        tiles = map.getTiles();
        objects = map.getObjects();
        entities = map.getEntities();
        gameClockTimeString = map.getGameClock().getCurrentTime().toString();
        incrementPerTickSeconds = map.getGameClock().getSecondsPerTick();
        clockPaused = map.getGameClock().isPaused();
        money = map.money;
        savingFileName = map.savingFileName;
        level = map.level;
        grassPrice = map.grassPrice;
        waterPrice = map.waterPrice;
        totalTouristsThisWeek = map.getTouristCountOnTour();
        won = map.won;
        lost = map.lost;

        availableJeeps = map.availableJeeps;
        weeklyRatings = map.weeklyRatings;
        ticketPriceLevel = map.ticketPriceLevel;
        raisePrice = map.raisePrice;
        lowerPrice = map.lowerPrice;
        touristWeekCounter = map.touristWeekCounter;
        totalGameHours = map.totalGameHours;
        nextTouristId = map.nextTouristId;
        cumulativeRating = map.cumulativeRating;
        ratingCount = map.ratingCount;
    }
    public MapState() {}
}
