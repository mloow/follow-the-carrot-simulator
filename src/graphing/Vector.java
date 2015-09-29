package graphing;

/**
 * Created by marcus on 2015-09-21.
 */
public class Vector {

    private double x;
    private double y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector(Point point1, Point point2) {
        x = point2.getX() - point1.getX();
        y = point2.getY() - point1.getY();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double norm() {
        return Math.hypot(x, y);
    }

    public Vector normalize() {
        return new Vector(x/norm(), y/norm());
    }

    public Vector scale(double scalar) {
        return new Vector(x*scalar, y*scalar);
    }

    public double dotProduct(Vector vector) {
        return x*vector.x + y*vector.y;
    }

    public Vector turn(double rad) {

        double tX = x * Math.cos(rad) - y * Math.sin(rad);
        double tY = x * Math.sin(rad) + y * Math.cos(rad);

        return new Vector(tX, tY);

    }

    @Override
    public String toString() {
        return String.format("(%.1f, %.1f)", x, y);
    }

    public Point pointsTo() {
        return new Point(x, y);
    }
}
