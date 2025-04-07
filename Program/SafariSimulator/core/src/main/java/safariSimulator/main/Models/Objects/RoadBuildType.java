package safariSimulator.main.Models.Objects;

public enum RoadBuildType {
    STRAIGHT_NS(RoadDirection.NORTH, RoadDirection.SOUTH),
    STRAIGHT_WE(RoadDirection.WEST, RoadDirection.EAST),
    TURN_NE(RoadDirection.NORTH, RoadDirection.EAST),
    TURN_NW(RoadDirection.NORTH, RoadDirection.WEST),
    TURN_SE(RoadDirection.SOUTH, RoadDirection.EAST),
    TURN_SW(RoadDirection.SOUTH, RoadDirection.WEST);

    private final RoadDirection from;
    private final RoadDirection to;

    RoadBuildType(RoadDirection from, RoadDirection to) {
        this.from = from;
        this.to = to;
    }

    public RoadDirection getFrom() {
        return from;
    }

    public RoadDirection getTo() {
        return to;
    }

    public RoadBuildType next() {
        int nextOrdinal = (this.ordinal() + 1) % RoadBuildType.values().length;
        return RoadBuildType.values()[nextOrdinal];
    }
}
