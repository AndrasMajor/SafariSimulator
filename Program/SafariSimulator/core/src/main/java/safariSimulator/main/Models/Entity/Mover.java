package safariSimulator.main.Models.Entity;

import safariSimulator.main.Models.Point;

public class Mover {
    public Point start;
    public Point end;
    public float progress; // 0.0 → 1.0
    public float duration; // in seconds

    public Mover(Point start, Point end, float duration) {
        this.start = start;
        this.end = end;
        this.duration = duration;
        this.progress = 0;
    }

    public void update(float delta) {
        progress += delta / duration;
        if (progress > 1f) progress = 1f;
    }

    public boolean isComplete() {
        return progress >= 1f;
    }

    public float getInterpolatedX() {
        return start.getX() + (end.getX() - start.getX()) * progress;
    }

    public float getInterpolatedY() {
        return start.getY() + (end.getY() - start.getY()) * progress;
    }

    public float getAngleDeg() {
        float dx = end.getX() - start.getX();
        float dy = end.getY() - start.getY();
        return (float)Math.toDegrees(Math.atan2(dy, dx));
    }

}
