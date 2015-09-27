package simulation;

import geometry.Edge;
import geometry.Vertex;
import graphing.Point;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by marcus on 2015-09-20.
 */
public class Path {

    private CopyOnWriteArrayList<Edge> edges;
    private Vertex lastAddedVertex = null;

    public Path() {
        edges = new CopyOnWriteArrayList<>();
    }

    public Vertex getLastAddedVertex() {
        return lastAddedVertex;
    }

    public void concatPath(Vertex vertex) {

        if(lastAddedVertex == null) {
            lastAddedVertex = vertex;
        } else {
            edges.add(new Edge(lastAddedVertex, vertex));
            lastAddedVertex =  vertex;
        }
    }

    private int getIndexOfClosestEdgeTo(Point point) {
        if(!edges.isEmpty()) {

            Point closestPointOnEdge = edges.get(0).getClosestPointTo(point);
            double minD = point.getDistanceTo(closestPointOnEdge);
            int index = 0;

            for(int i = 0; i < edges.size(); i++) {

                closestPointOnEdge = edges.get(i).getClosestPointTo(point);
                double d    = point.getDistanceTo(closestPointOnEdge);
                minD        = (d <= minD ? d : minD);
                index       = (d <= minD ? i : index);

            }

            return index;
        }

        return -1;
    }

    public Edge getClosestEdgeTo(Point point) {
        int index = getIndexOfClosestEdgeTo(point);
        return (index >= 0 ? edges.get(index) : null);
    }

    public ArrayList<Edge> getCarrotPathFrom(Point point, double lookAheadDistance) {
        ArrayList<Edge> carrotPath = new ArrayList<>();

        int indexOfStartEdge = getIndexOfClosestEdgeTo(point);
        if(indexOfStartEdge < 0) {
            return carrotPath;
        }
        Edge startEdge = edges.get(indexOfStartEdge);
        Point start = startEdge.getClosestPointTo(point);

        double remainingDistance = lookAheadDistance;

        // Loop until remaining distance is 0 (carrot point has been reached) or until the end of the path has been reached
        for(int i = indexOfStartEdge; i < edges.size() && remainingDistance > 0; i++) {

            Edge currentEdge = i == indexOfStartEdge ? new Edge(new Vertex(start), startEdge.end) : edges.get(i);
            Point end = currentEdge.getPointAlongEdgeAtDistance(remainingDistance);

            // If the distance to the supposed end-point from the current start point exceeds the current edges end point
            if(start.getDistanceTo(end) > start.getDistanceTo(currentEdge.end.toPoint())) {
                end = currentEdge.end.toPoint();

                // if the current edge is the last one, we have arrived
                if(i == edges.size() - 1) { // if last edge of path
                    remainingDistance = 0;
                    // else subtract the distance from the current start point to the end of the current edge
                } else {
                    remainingDistance-= start.getDistanceTo(end);

                }
                // else we have arrived
            } else {
                if(!start.equals(end)) {
                    remainingDistance = 0;
                }
            }

            // Add the edge to the carrotPath
            carrotPath.add(new Edge(new Vertex(start), new Vertex(end)));

            start = currentEdge.end.toPoint();
        }

        return carrotPath;
    }

    public void clear() {
        edges.clear();
        lastAddedVertex = null;
    }

    public CopyOnWriteArrayList<Edge> getEdges() {
        return edges;
    }

    public Vertex getStart() {
        if(edges.size() > 0) {
            return edges.get(0).start;
        } else {
            return lastAddedVertex;
        }
    }

    public Vertex getEnd() {
        if(edges.size() > 0) {

            return edges.get(edges.size() - 1).end;
        } else {
            return null;
        }
    }
}
