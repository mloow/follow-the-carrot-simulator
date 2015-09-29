package simulation;

import geometry.Edge;
import geometry.Vertex;
import graphing.*;
import graphing.Point;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by marcus on 2015-09-19.
 */
public class Field {

    private Path path;
    private Robot robot;
    private Point carrotPoint;

    public Field(Robot robot) {
        this.path = new Path();
    }

    public void setRobot(Robot robot) {
        this.robot = robot;
    }

    public Path getPath() {
        return this.path;
    }

    public void clear() {
        path.clear();
        robot = null;
    }

    public double getSteeringAngle() {
        return 0;
    }

    public Robot getRobot() {
        return this.robot;
    }

    public void updateCarrotPoint() {
        try {
            if(path.getEdges().size() > 1) {
                carrotPoint = path.getCarrotPointFrom(robot.getPosition());
            } else {
                carrotPoint = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            carrotPoint = null;
        }
    }

    public Point getCarrotPoint() {
        return carrotPoint;
    }
}
