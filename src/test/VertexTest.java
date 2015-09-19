package test;

import geometry.Vertex;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by marcus on 2015-09-18.
 */
public class VertexTest {

    @Test
    public void shouldReturnTrueFromEqualsWhenEqualsPositions() throws Exception {

        Vertex v1 = new Vertex(0, 0);
        Vertex v2 = new Vertex(0, 0);

        assertTrue(v1.equals(v2));

    }

    @Test
    public void shouldReturnFalseFromEqualsWhenNonEqualPositions() throws Exception {
        Vertex v1 = new Vertex(0, 0);
        Vertex v2 = new Vertex(0, 1);

        assertFalse(v1.equals(v2));
    }

    @Test
    public void shouldReturnCorrectValueFromDifferenceTo() throws Exception {
        Vertex v1 = new Vertex(5, -3);
        Vertex v2 = new Vertex(-3, 1);

        assertEquals(new Vertex(8, -4), v1.differenceTo(v2));
    }

    @Test
    public void shouldReturnCorrectValueFromDotProduct() throws Exception {
        Vertex v1 = new Vertex(3, 1);
        Vertex v2 = new Vertex(-0.5, 3);

        assertTrue(1.5 == v1.dotProduct(v2));
    }

    @Test
    public void shouldReturnCorrectValueFromScale() throws Exception {
        Vertex v1 = new Vertex(2, -1);
        assertEquals(new Vertex(5, -2.5), v1.scale(2.5));
    }

    @Test
    public void shouldReturnCorrectValueFromConcat() throws Exception {
        Vertex v1 = new Vertex(-1,-1);
        Vertex v2 = new Vertex(0, 2);

        assertEquals(new Vertex(-1 ,1), v1.concat(v2));
    }
}