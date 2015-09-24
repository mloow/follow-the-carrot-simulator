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


    public double distanceTo(Point point) {
        return Math.hypot(x - point.x, y- point.y);
    }

    @Override
    public String toString() {
        return String.format("(%f, %f)", x, y);
    }

    public double angleTo(Point point) {
        return Math.atan2(point.y - y, point.x - x);
    }
}
