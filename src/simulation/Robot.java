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
    private double angularSpeed;

    public Robot(double lookAheadDistance) {
        super();
        this.lookAheadDistance = lookAheadDistance;
        setPosition(null);
        setMaxSpeed(-1);
        setVelocity(45, 0);
    }

    public void turn(double rad) {

        setVelocity(getVelocity().turn(rad));

    }

    public void setCarrotPoint(Point carrotPoint) {

        this.carrotPoint = carrotPoint;

    }

    public double getSteeringAngle() {

        double angle1 = getOrientation();
        if(carrotPoint == null) {
            return angle1;
        } else {
            double angle2 = getPosition().getAngleTo(carrotPoint);
            double error =  angle2 - angle1;
            if(error > 180.0) {
                return error - 360.0;
            } else if (error < -180.0) {
                return error + 360.0;
            } else {
                return error;
            }
        }

    }

    public double getLookAheadDistance() {
        return lookAheadDistance;
    }

    public void setLookAheadDistance(double lookAheadDistance) {
        this.lookAheadDistance = lookAheadDistance;
    }

    public boolean isAt(Point point) {
        return getPosition().getDistanceTo(point) < 2.0;
    }
    public void moveFor(double seconds) {
        int ticks = (int) (seconds / Mover2D.TICK_PERIOD);
        for(int i = 0; i < ticks; i++) {
            tick();
            turn(getSteeringAngle() * Mover2D.TICK_PERIOD);
        }
    }


}
