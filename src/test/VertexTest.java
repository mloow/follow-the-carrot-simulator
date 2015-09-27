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


}