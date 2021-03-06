package test;

import geometry.Vertex;
import geometry.Edge;
import graphing.Point;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Marcus on 2015-09-18.
 */
public class EdgeTest {

    Vertex v1 = new Vertex(0, 0);
    Vertex v2 = new Vertex(2, 0);
    Edge e1 = new Edge(v1, v2);
    Edge e2 = new Edge(v2, v1);

    @Test
    public void shouldReturnCorrectLengthFromGetLength() throws Exception {

        assertTrue(2.0 == e1.getLength());
    }

    @Test
    public void shouldReturnTrueFormContainsWhenContainsVertex() throws Exception {

        assertTrue(e1.contains(v1));
    }

    @Test
    public void shouldReturnFalseFromContainsWhenNotContainsVertex() throws Exception {
        assertFalse(e1.contains(new Vertex(-1, -1)));
    }

    @Test
    public void shouldReturnTrueFromEqualsWhenEqualEdge() throws Exception {
        assertTrue(e1.equals(new Edge(new Vertex(0, 0), new Vertex(2, 0))));
    }

    @Test
    public void shouldReturnTrueFromEqualsWhenEdgeReversed() throws Exception {
        assertTrue(e1.equals(e2));
    }

    @Test
    public void shouldReturnFalseFromEqualsWhenNotEqualEdge() throws Exception {
        assertFalse(e1.equals(new Edge(v1, v1)));
    }

    @Test
    public void shouldReturnCorrectPointFromGetClosestPointTo() throws Exception {

        Vertex start = new Vertex(-1, 0);
        Vertex end = new Vertex(1, 0);
        Point point = new Point(-1, -1);

        Edge edge = new Edge(start, end);

        assertEquals(new Point(-1, 0), edge.getClosestPointTo(point));
    }

    @Test
    public void shouldReturnCorrectPointFromGetPointAlongEdgeAtDistance() throws Exception {

        Vertex start = new Vertex(-1, -1);
        Vertex end = new Vertex(5, 7);
        Edge e = new Edge(start, end);

        Point expected = new Point(2, 3);

        Point actual = e.getPointAlongEdgeAtDistance(5);

        assertEquals(expected, actual);
    }
}