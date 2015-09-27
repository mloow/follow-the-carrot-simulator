package simulation;

import graphing.Point;
import graphing.Vector;

/**
 * Created by marcus on 2015-09-24.
 */
public class Robot extends Mover2D {

    public static final double DEFAULT_LOOK_AHEAD_DISTANCE = 50.0;
    private double lookAheadDistance;
    private Point carrotPoint;

    public Robot(double lookAheadDistance) {
        super();
        this.lookAheadDistance = lookAheadDistance;
        setPosition(null);
        setMaxSpeed(-1);
        setVelocity(1, 1);
    }

    public void turn(double rad) {

        setVelocity(getVelocity().turn(rad));

    }

    public void setCarrotPoint(Point carrotPoint) {

        this.carrotPoint = carrotPoint;

    }

    public double getSteeringAngle() {

        double angle1 = getOrientation();
        double angle2 = new Point(0, 0).getAngleTo(carrotPoint);
        return angle1 - angle2;

    }

    public double getLookAheadDistance() {
        return lookAheadDistance;
    }

    public void setLookAheadDistance(double lookAheadDistance) {
        this.lookAheadDistance = lookAheadDistance;
    }
}
