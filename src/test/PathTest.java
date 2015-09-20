package test;

import geometry.Edge;
import geometry.Vertex;
import org.junit.Before;
import org.junit.Test;
import simulation.Path;

import java.util.concurrent.CopyOnWriteArrayList;

import static org.junit.Assert.*;

/**
 * Created by marcus on 2015-09-20.
 */
public class PathTest {

    Path path;

    @Before
    public void setUp() {
        path = new Path();
    }

    @Test
    public void shouldReturnEmptyEdgesFromGetPathWhenNotConcat() throws Exception {
        assertTrue(path.getEdges().isEmpty());
    }

    @Test
    public void shouldReturnEmptyEdgesFromGetPathWhenConcatOnce() throws Exception {
        path.concatPath(new Vertex(1, 10));
        assertTrue(path.getEdges().isEmpty());
    }

    @Test
    public void shouldReturnCorrectEdgesFromGetPathWhenConcatMoreThanOnce() throws Exception {

        Vertex vertex1 = new Vertex(5, 10);
        Vertex vertex2 = new Vertex(6, 12);
        Vertex vertex3 = new Vertex(7, 14);
        path.concatPath(vertex1);
        path.concatPath(vertex2);
        path.concatPath(vertex3);

        CopyOnWriteArrayList<Edge> actualPath = path.getEdges();
        Edge[] expectedPath = new Edge[]{ new Edge(vertex1, vertex2), new Edge(vertex2, vertex3) };
        for (int i = 0; i < expectedPath.length; i++) {
            assertEquals(expectedPath[i], actualPath.get(i));
        }
    }


    @Test
    public void shouldEmptyPathWhenClear() throws Exception {

        Vertex vertex1 = new Vertex(5, 10);
        Vertex vertex2 = new Vertex(6, 12);
        Vertex vertex3 = new Vertex(7, 14);
        path.concatPath(vertex1);
        path.concatPath(vertex2);
        path.concatPath(vertex3);

        path.clear();

        assertTrue(path.getEdges().isEmpty());
    }

    @Test
    public void shouldReturnEmptyPathAfterConcatClearConcat() throws Exception {
        path.concatPath(new Vertex(1, 1));
        path.clear();
        path.concatPath(new Vertex(2, 3));
        assertTrue(path.getEdges().isEmpty());
    }

    @Test
    public void shouldSetLastAddedToNullAfterClear() throws Exception {

        Vertex vertex1 = new Vertex(5, 10);
        Vertex vertex2 = new Vertex(6, 12);
        Vertex vertex3 = new Vertex(7, 14);
        path.concatPath(vertex1);
        path.concatPath(vertex2);
        path.concatPath(vertex3);

        path.clear();

        assertNull(path.getLastAddedVertex());
    }

    @Test
    public void shouldNotReturnNullFromLastAddedAfterConcatClearConcat() throws Exception {

        path.concatPath(new Vertex(1, 1));
        path.clear();
        path.concatPath(new Vertex(2, 3));
        assertNotNull(path.getLastAddedVertex());
    }

    @Test
    public void testGetClosestEdgeToVertex() throws Exception {

    }

    @Test
    public void testGetCarrotPathFrom() throws Exception {

    }

    @Test
    public void testClear() throws Exception {

    }

}