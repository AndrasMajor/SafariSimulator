package safariSimulator.main.Models.Biome.Tile.PlantField;

import safariSimulator.main.Models.Biome.Tile.Tile;

public abstract class PlantField extends Tile {
    private int hp;
    private int maxHp;

    public PlantField(int x, int y, int maxHp) {
        super(x, y);
        this.maxHp = maxHp;
        this.hp = maxHp;
    }

    public void feed(int amount) {
        this.hp = Math.max(0, this.hp - amount);
    }

    public void heal(int amount) {
        this.hp = Math.min(this.maxHp, this.hp + amount);
    }

    public boolean isAlive() {
        return this.hp > 0;
    }

    public void transform() {
        //TODO: Implement transformation logic
    }
}
