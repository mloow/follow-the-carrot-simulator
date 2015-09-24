package simulation;

import geometry.Edge;
import geometry.Vertex;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by marcus on 2015-09-19.
 */
public class Field {

    private Path path;
    private Robot robot;

    public Field() {
        this.path = new Path();
        this.robot = new Robot(Robot.DEFAULT_LOOK_AHEAD_DISTANCE);
    }

    public Path getPath() {
        return this.path;
    }

    public void clear() {
        path.clear();
        robot = new Robot(Robot.DEFAULT_LOOK_AHEAD_DISTANCE);
    }

    public Robot getRobot() {
        return this.robot;
    }

}
