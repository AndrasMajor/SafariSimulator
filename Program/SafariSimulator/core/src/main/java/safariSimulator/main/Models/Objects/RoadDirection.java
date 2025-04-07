package safariSimulator.main.Models.Objects;
import safariSimulator.main.Models.Point;

public enum RoadDirection {
    NORTH, EAST, SOUTH, WEST;

    public Point move(Point point) {
        return switch (this) {
            case NORTH -> new Point(point.getX(), point.getY() + 1);
            case EAST  -> new Point(point.getX() + 1, point.getY());
            case SOUTH -> new Point(point.getX(), point.getY() - 1);
            case WEST  -> new Point(point.getX() - 1, point.getY());
        };
    }
}
