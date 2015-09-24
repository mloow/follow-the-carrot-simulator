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

    @Override
    public String toString() {
        return String.format("(%f, %f)", x, y);
    }
}
