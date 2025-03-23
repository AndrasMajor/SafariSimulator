package safariSimulator.main.Models.Objects;

import safariSimulator.main.Models.Point;

public abstract class Object {
    private Point position;
    public int price;

    public Object(Point point) {
        this.position = point;
    }

    public Point getPos() {
        Point out = new Point(this.position.getX(), this.position.getY());
        return out;
    }
}
