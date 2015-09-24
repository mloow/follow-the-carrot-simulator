package simulation;

import graphing.Point;
import graphing.Vector;

/**
 * Created by marcus on 2015-09-24.
 */
public class Robot extends Mover2D {

    public static final double DEFAULT_LOOK_AHEAD_DISTANCE = 50.0;
    private double lookAheadDistance;

    public Robot(double lookAheadDistance) {
        super();
        this.lookAheadDistance = lookAheadDistance;
        setPosition(null);
        setMaxSpeed(-1);
        setVelocity(1, 1);
    }

    public void turn(double rad) {
        Vector orientation = getVelocity();

        double x = orientation.getX() * Math.cos(rad) - orientation.getY() * Math.sin(rad);
        double y = orientation.getX() * Math.sin(rad) + orientation.getY() * Math.cos(rad);

        setVelocity(x, y);

    }


}
