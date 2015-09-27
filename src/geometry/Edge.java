package geometry;

import graphing.Point;
import graphing.Vector;

/**
 * Created by marcus on 2015-09-18.
 */
public class Edge {

    public Vertex start;
    public Vertex end;

    public Edge(Vertex start, Vertex end) {

        this.start = start;
        this.end = end;
    }

    public double getLength() {
        return start.distanceTo(end);
    }

    public boolean contains(Vertex v) {
        return v.equals(start) || v.equals(end);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Edge) {
            return (((Edge)obj).start.equals(start) && ((Edge)obj).end.equals(end))
                    || (((Edge)obj).start.equals(end) && ((Edge)obj).end.equals(start));
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "[" + start.toString() + ", " + end.toString() + "]";
    }

    public Point getClosestPointTo(Point point) {

        if(start.equals(end)) {
            return start.toPoint();
        }
        Vertex vertex = new Vertex(point.getX(), point.getY());
        Point closest;
        Triangle triangle = new Triangle(start, end, vertex);
        Vector v = new Vector(start.toPoint(), end.toPoint());
        Vector w = new Vector(start.toPoint(), point);

        double c1 = w.dotProduct(v);
        double c2 = v.dotProduct(v);
        double b = c1/c2;

        double angleInStart;
        double angleInEnd;


        angleInStart = triangle.getAngleInVertex(start, Triangle.SUGGESTED_ERROR);
        angleInEnd = triangle.getAngleInVertex(end, Triangle.SUGGESTED_ERROR);

        if (angleInStart >= 90) {
            closest = start.toPoint();
        } else if (angleInEnd >= 90) {
            closest = end.toPoint();
        } else if (angleInStart == 0 && angleInEnd == 0) {

            if(c1 < 0) {
                closest = start.toPoint();
            } else {
                closest = end.toPoint();
            }

        } else {
            Point pB = v.scale(b).pointsTo();
            closest = new Point(start.toPoint().getX() + pB.getX(), start.toPoint().getY() + pB.getY());
        }

        return closest;
    }

    public Point getPointAlongEdgeAtDistance(double distance) {

        if(start.equals(end)) {
            return end.toPoint();
        }

        double ratio = distance / getLength();

        double x = ratio * (end.x - start.x);
        double y = ratio * (end.y - start.y);

        return new Point(start.x + x, start.y + y);

    }
}
