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
        field = new Field();
    }

    @Test
    public void shouldSetRobotPositionToNullWhenClear() throws Exception {
        field.setRobotPosition(new Vertex(0, 0));
        field.clear();
        assertNull(field.getRobotPosition());
    }

}