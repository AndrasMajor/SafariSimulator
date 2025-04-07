package safariSimulator.main.Models.Objects;

import safariSimulator.main.Models.Point;

public abstract class Road extends Object {
    public RoadDirection[] direction;
    public boolean sellable;

    public Road(Point point, RoadDirection from, RoadDirection to) {
        super(point);
        direction = new RoadDirection[]{from, to};
        this.price = 80;
    }

    public Road() {}
}
