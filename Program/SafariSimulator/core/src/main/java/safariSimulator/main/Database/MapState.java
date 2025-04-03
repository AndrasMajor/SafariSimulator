package safariSimulator.main.Database;

import safariSimulator.main.Models.Entity.Entity;
import safariSimulator.main.Models.Map;
import safariSimulator.main.Models.Objects.Object;
import safariSimulator.main.Models.Tile.Tile;

import java.time.LocalDateTime;
import java.util.List;

public class MapState {
    public List<Tile> tiles;
    public List<Object> objects;
    public List<Entity> entities;
    public int money;
    public String timeString;
    public String savingFileName;
    public String level;

    public MapState(Map map) {
        tiles = map.getTiles();
        objects = map.getObjects();
        entities = map.getEntities();
        money = map.money;
        // timeString = map.time.toString();
        savingFileName = map.savingFileName;
        level = map.level;
    }
    public MapState() {}
}
