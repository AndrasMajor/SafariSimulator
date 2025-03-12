package safariSimulator.main.Models.Biome.Tile;

import safariSimulator.main.Models.Entity.Entity;

public class Tile {
    private int x;
    private int y;
    private Entity entity;
    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
        this.entity = null;
    }
    public boolean hasEntity() {
        return this.entity != null;
    }
    public Entity getEntity() {
        return this.entity;
    }
    public void setEntity(Entity entity) {
        this.entity = entity;
    }
}
