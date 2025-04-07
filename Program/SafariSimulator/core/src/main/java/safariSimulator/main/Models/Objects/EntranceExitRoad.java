package safariSimulator.main.Models.Objects;

import safariSimulator.main.Models.Point;

public class EntranceExitRoad extends Road {
    public final boolean isEntrance;

    public EntranceExitRoad(Point point, boolean isEntrance) {
        super(point, RoadDirection.EAST, RoadDirection.WEST);
        this.isEntrance = isEntrance;
        this.sellable = false;
        this.price = 0;
    }
}
