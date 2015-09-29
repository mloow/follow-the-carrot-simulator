package graphing;

/**
 * Created by marcus on 2015-09-21.
 */
public class Point {

    private double x;
    private double y;

    public Point(double x, double y) {

        this.x = x;
        this.y = y;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }


    public double getDistanceTo(Point point) {
        return Math.hypot(x - point.x, y- point.y);
    }

    public boolean isWithinDistanceTo(double distance, Point point) {
        return this.getDistanceTo(point) < distance;
    }

    @Override
    public String toString() {
        return String.format("(%.1f, %.1f)", x, y);
    }

    public double getAngleTo(Point point) {
        return Math.atan2(point.y - y, point.x - x);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Point) {
            return (((Point) obj).x == x && ((Point) obj).y == y);
        } else {
            return false;
        }
    }

}
