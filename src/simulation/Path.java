package simulation;

import geometry.Edge;
import geometry.Vertex;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by marcus on 2015-09-20.
 */
public class Path {

    private CopyOnWriteArrayList<Edge> edges;
    private Vertex lastAddedVertex = null;

    public Path() {
        edges = new CopyOnWriteArrayList<Edge>();
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

    private int getIndexOfClosestEdgeToVertex(Vertex vertex) {
        if(!edges.isEmpty()) {

            Vertex closestVertexOnEdge = edges.get(0).getClosestVertexOnEdge(vertex);
            double minD = vertex.distanceTo(closestVertexOnEdge);
            int index = 0;

            for(int i = 0; i < edges.size(); i++) {

                closestVertexOnEdge = edges.get(i).getClosestVertexOnEdge(vertex);
                double d    = vertex.distanceTo(closestVertexOnEdge);
                minD        = (d <= minD ? d : minD);
                index       = (d <= minD ? i : index);

            }

            return index;
        }

        return -1;
    }

    public Edge getClosestEdgeToVertex(Vertex vertex) {
        int index = getIndexOfClosestEdgeToVertex(vertex);
        return (index >= 0 ? edges.get(index) : null);
    }

    public ArrayList<Edge> getCarrotPathFrom(Vertex vertex, double lookAheadDistance) {
        ArrayList<Edge> carrotPath = new ArrayList<Edge>();

        int indexOfStartEdge = getIndexOfClosestEdgeToVertex(vertex);
        if(indexOfStartEdge < 0) {
            return carrotPath;
        }
        Edge startEdge = edges.get(indexOfStartEdge);
        Vertex start = startEdge.getClosestVertexOnEdge(vertex);

        double remainingDistance = lookAheadDistance;

        // Loop until remaining distance is 0 (carrot point has been reached) or until the end of the path has been reached
        for(int i = indexOfStartEdge; i < edges.size() && remainingDistance > 0; i++) {

            Edge currentEdge = i == indexOfStartEdge ? new Edge(start, startEdge.end) : edges.get(i);
            Vertex end = currentEdge.getVertexAlongEdgeAtDistance(remainingDistance);

            // If the distance to the supposed end-point from the current start point exceeds the current edges end point
            if(start.distanceTo(end) > start.distanceTo(currentEdge.end)) {
                end = currentEdge.end;

                // if the current edge is the last one, we have arrived
                if(i == edges.size() - 1) { // if last edge of path
                    remainingDistance = 0;
                    // else subtract the distance from the current start point to the end of the current edge
                } else {
                    remainingDistance-= start.distanceTo(end);

                }
                // else we have arrived
            } else {
                if(!start.equals(end)) {
                    remainingDistance = 0;
                }
            }

            // Add the edge to the carrotPath
            carrotPath.add(new Edge(start, end));

            start = currentEdge.end;

        }

        return carrotPath;


    }

    public String[] getPathAsStringArray() {
        String[] strings = new String[edges.size()];

        for (int i = 0; i < edges.size(); i++) {
            strings[i] = edges.get(i).toString();
        }

        return strings;
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
            return null;
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
