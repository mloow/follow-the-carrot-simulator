package test;

import geometry.Vertex;
import geometry.Edge;
import geometry.Triangle;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by marcus on 2015-09-18.
 */
public class TriangleTest {

    Vertex v1 = new Vertex(3, 0);
    Vertex v2 = new Vertex(0, 0);
    Vertex v3 = new Vertex(0, -3);

    Triangle triangle = new Triangle(v1, v2, v3);

    @Test
    public void shouldReturnTrueOnContainsWhenContainsVertex() throws Exception {
        assertTrue(triangle.contains(v1));
    }

    @Test
    public void shouldReturnFalseOnContainsWhenNotContainsVertex() throws Exception {
        assertFalse(triangle.contains(new Vertex(23, 1)));
    }

    @Test
    public void shouldReturnCorrectArrayOnToArray() throws Exception {

        List<Edge> list = Arrays.asList(triangle.toArray());

        Edge e1 = new Edge(v3, v2);
        Edge e2 = new Edge(v2, v1);
        Edge e3 = new Edge(v1, v3);

        assertTrue(list.contains(e1) && list.contains(e2) && list.contains(e3));
    }

    @Test
    public void shouldReturnCorrectEdgesOnGetSegmentsFromVertex() throws Exception {


        Edge e1 = new Edge(v3, v2);
        Edge e2 = new Edge(v2, v1);

        List<Edge> list = Arrays.asList(triangle.getAdjacentEdges(v2));

        assertTrue(list.contains(e1) && list.contains(e2));

    }

    @Test(expected = Exception.class)
    public void shouldThrowExceptionOnGetAdjacentEdgesWhenNotContainsVertex() throws Exception {
        triangle.getAdjacentEdges(new Vertex(123, 24));
    }
    
    @Test
    public void shouldReturnCorrectEdgeOnGetOpposingEdges() throws Exception {
        Edge edge = new Edge(v2, v3);
        assertEquals(edge, triangle.getOpposingEdge(v1));
    }

    @Test(expected = Exception.class)
    public void shouldThrowExceptionOnGetOpposingEdgeWhenNotContainsVertex() throws Exception {
        triangle.getOpposingEdge(new Vertex(12, 44));
    }

    @Test
    public void shouldReturnCloseToCorrectAngleOnGetAngleInVertex() throws Exception {

        System.out.println(triangle.getAngleInVertex(v2, Triangle.SUGGESTED_ERROR));
        assertTrue(90.0 == triangle.getAngleInVertex(v2, Triangle.SUGGESTED_ERROR));
    }

}