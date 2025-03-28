package safariSimulator.main.Models.Objects;

import safariSimulator.main.Models.Point;

public abstract class Object {
    public Point position;
    public int price;

    public Object(Point point) {
        this.position = point;
    }
    public Object() {}

    public Point getPos() {
        Point out = new Point(this.position.getX(), this.position.getY());
        return out;
    }
}
