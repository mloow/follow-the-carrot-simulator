package test;

import geometry.Edge;
import geometry.Vertex;
import org.junit.Before;
import org.junit.Test;
import simulation.Field;

import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.junit.Assert.*;

/**
 * Created by marcus on 2015-09-19.
 */
public class FieldTest {

    Field field;

    @Before
    public void setup() {
        field = new Field(new Rectangle(0, 0, 100, 100));
    }

    @Test
    public void shouldReturnEmptyPathFromGetPathWhenNotConcat() throws Exception {
        assertTrue(field.getPath().isEmpty());
    }

    @Test(expected = Exception.class)
    public void shouldThrowExceptionInConcatPathWhenVertexOutOfBounds() throws Exception {
        field.concatPath(new Vertex(101, 101));
    }
    @Test
    public void shouldReturnEmptyPathFromGetPathWhenConcatOnce() throws Exception {
        field.concatPath(new Vertex(1, 10));
        assertTrue(field.getPath().isEmpty());
    }

    @Test
    public void shouldReturnCorrectPathFromGetPathWhenConcatMoreThanOnce() throws Exception {

        Vertex vertex1 = new Vertex(5, 10);
        Vertex vertex2 = new Vertex(6, 12);
        Vertex vertex3 = new Vertex(7, 14);
        field.concatPath(vertex1);
        field.concatPath(vertex2);
        field.concatPath(vertex3);

        CopyOnWriteArrayList<Edge> actualPath = field.getPath();
        Edge[] expectedPath = new Edge[]{ new Edge(vertex1, vertex2), new Edge(vertex2, vertex3) };
        for (int i = 0; i < expectedPath.length; i++) {
            assertEquals(expectedPath[i], actualPath.get(i));
        }
    }

    @Test
    public void shouldSetRobotPositionToNullWhenClear() throws Exception {
        field.setRobotPosition(new Vertex(0, 0));
        field.clear();
        assertNull(field.getRobotPosition());
    }

    @Test
    public void shouldEmptyPathWhenClear() throws Exception {

        Vertex vertex1 = new Vertex(5, 10);
        Vertex vertex2 = new Vertex(6, 12);
        Vertex vertex3 = new Vertex(7, 14);
        field.concatPath(vertex1);
        field.concatPath(vertex2);
        field.concatPath(vertex3);

        field.clear();

        assertTrue(field.getPath().isEmpty());
    }

    @Test
    public void shouldReturnEmptyPathAfterConcatClearConcat() throws Exception {
        field.concatPath(new Vertex(1, 1));
        field.clear();
        field.concatPath(new Vertex(2, 3));
        assertTrue(field.getPath().isEmpty());
    }

}