package geometry;

import graphing.Point;

/**
 * Created by marcus on 2015-09-18.
 */
public class Vertex {

    public double x;
    public double y;

    public Vertex(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vertex(Point point) {
        x = point.getX();
        y = point.getY();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Vertex) {
            return (((Vertex) obj).x == x && ((Vertex) obj).y == y);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public double distanceTo(Vertex v) {
        return Math.hypot(x - v.x, y- v.y);
    }

    public Point toPoint() {
        return new Point(x, y);
    }
}
