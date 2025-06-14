package safariSimulator.main.Models.Objects;

import safariSimulator.main.Models.Point;

public class Plant extends Object {
    public PlantType type;


    public Plant(Point point, PlantType type) {
        super(point);
        this.type = type;
        if(this.type == PlantType.Tree) this.price = 40;
        else this.price = 30;
    }
    public Plant() {}
}
