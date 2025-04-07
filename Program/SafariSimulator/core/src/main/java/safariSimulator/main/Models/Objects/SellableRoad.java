package safariSimulator.main.Models.Objects;

import safariSimulator.main.Models.Point;

public class SellableRoad extends Road {

    public RoadBuildType roadType;

    public SellableRoad(Point pos, RoadBuildType type) {
        super(pos, type.getFrom(), type.getTo());
        this.roadType = type;
        this.sellable = true;
    }

    public void rotateClockwise() {
        roadType = roadType.next();
        this.direction = new RoadDirection[]{roadType.getFrom(), roadType.getTo()};
    }
}
