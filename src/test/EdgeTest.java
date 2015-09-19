package test;

import geometry.Vertex;
import geometry.Edge;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by marcus on 2015-09-18.
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
        assertTrue(e1.equals(e1));
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
    public void shouldReturnCorrectVertexFromGetClosestVertexOnEdge() throws Exception {

        Vertex start = new Vertex(-1, 0);
        Vertex end = new Vertex(1, 0);
        Vertex vertex = new Vertex(-1, -1);

        Edge edge = new Edge(start, end);

        assertEquals(new Vertex(-1, 0), edge.getClosestVertexOnEdge(vertex));
    }
}