package safariSimulator.main.Models.Objects;

import safariSimulator.main.Models.Point;

public class Road extends Object {
    public RoadDirection[] direction;

    public Road(Point point, RoadDirection from, RoadDirection to) {
        super(point);
        direction = new RoadDirection[]{from, to};
    }
}
