package simulation;

import geometry.Edge;
import geometry.Vertex;

import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by marcus on 2015-09-19.
 */
public class Field {

    private Rectangle bounds;
    private CopyOnWriteArrayList<Edge> path;
    private Vertex robotPosition = null;
    private Vertex lastAddedVertex = null;

    public Field(Rectangle bounds) {
        this.bounds = bounds;
        path = new CopyOnWriteArrayList<Edge>();
    }

    public void concatPath(Vertex vertex) throws Exception {

        if(bounds.contains(vertex.x, vertex.y)) {

            if(lastAddedVertex == null) {
                lastAddedVertex = vertex;
            } else {
                path.add(new Edge(lastAddedVertex, vertex));
                lastAddedVertex =  vertex;
            }
        } else {
            throw new Exception("Vertex " + vertex.toString() + " not inside of field bounds");
        }
    }

    public void setRobotPosition(Vertex vertex) {
        this.robotPosition = vertex;
    }

    public Vertex getRobotPosition() {
        return this.robotPosition;
    }

    public CopyOnWriteArrayList<Edge> getPath() {
        return this.path;
    }

    public void clear() {
        path.clear();
        lastAddedVertex = null;
        robotPosition = null;
    }

    public Rectangle getBounds() {
        return this.bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public String[] getPathAsStringArray() {
        String[] strings = new String[path.size()];

        for (int i = 0; i < path.size(); i++) {
            strings[i] = path.get(i).toString();
        }

        return strings;
    }

    public Vertex getLastAddedVertex() {
        return lastAddedVertex;
    }

    public Edge getClosestEdge() {
        if(!path.isEmpty()) {
            Edge closestEdge = path.get(0);
            Vertex closestVertexOnEdge = path.get(0).getClosestVertexOnEdge(robotPosition);
            double minD = robotPosition.distanceTo(closestVertexOnEdge);

            for(Edge e : path) {

                closestVertexOnEdge = e.getClosestVertexOnEdge(robotPosition);
                double d = robotPosition.distanceTo(closestVertexOnEdge);
                minD = (d <= minD ? d : minD);
                closestEdge = (d <= minD ? e : closestEdge);
            }

            return closestEdge;
        }

        return null;
    }
}
