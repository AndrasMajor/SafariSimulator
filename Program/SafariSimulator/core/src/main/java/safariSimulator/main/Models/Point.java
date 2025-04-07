package safariSimulator.main.Models;

/**
 * Represents a point in a 2D coordinate system.
 */
public class Point {

    // X-coordinate of the point
    private int x;

    // Y-coordinate of the point
    private int y;

    /**
     * Constructor that initializes the point with the given coordinates.
     *
     * @param x The x-coordinate of the point.
     * @param y The y-coordinate of the point.
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Default constructor that initializes the point at (0,0).
     */
    public Point() {
        this.x = 0;
        this.y = 0;
    }

    /**
     * Gets the x-coordinate of the point.
     *
     * @return The x-coordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * Sets the x-coordinate of the point.
     *
     * @param x The new x-coordinate.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Gets the y-coordinate of the point.
     *
     * @return The y-coordinate.
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the y-coordinate of the point.
     *
     * @param y The new y-coordinate.
     */
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Point other = (Point) obj;
        return x == other.x && y == other.y;
    }

    @Override
    public int hashCode() {
        return 31 * x + y;
    }


}

