package simulation;

import geometry.Vertex;
import graphing.Point;
import graphing.Vector;

/**
 * Created by marcus on 2015-09-24.
 */
public class Robot extends Mover2D {

    public static final double DEFAULT_LOOK_AHEAD_DISTANCE = 50.0;
    private double lookAheadDistance;
    private Point carrotPoint;
    private Path traveledPath;
    private boolean trackPathEnabled;

    public Robot(double lookAheadDistance) {
        super();
        this.lookAheadDistance = lookAheadDistance;
        setPosition(null);
        //setMaxSpeed(60);
        //setAccelleration(25, 0);
        setMaxSpeed(-1);
        setVelocity(45, 45);
        traveledPath = new Path();
    }

    public void turn(double rad) {

        setVelocity(getVelocity().turn(rad));

    }


    public void setCarrotPoint(Point carrotPoint) {

        this.carrotPoint = carrotPoint;

    }

    public double getSteeringAngle() {

        double orientation = getOrientation();
        if(carrotPoint == null) {
            return orientation;
        } else {
            double carrotAngle = getPosition().getAngleTo(carrotPoint);
            double error =  carrotAngle - orientation;
            if(error > Math.PI) {
                return error - 2*Math.PI;
            } else if (error < -Math.PI) {
                return error + 2*Math.PI;
            } else {
                return error;
            }
        }

    }

    @Override
    public void setPosition(Point position) {
        super.setPosition(position);
        if(trackPathEnabled) {
            traveledPath.concat(new Vertex(position));
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
        double steeringAngle = getSteeringAngle();
        //setAngularVelocity(steeringAngle);

        for(int i = 0; i < ticks; i++) {
            tick();
            turn(steeringAngle * TICK_PERIOD);
        }

        if(trackPathEnabled) {
            traveledPath.concat(new Vertex(getPosition()));
        }
    }

    public void setTrackPathEnabled(boolean trackPathEnabled) {
        this.trackPathEnabled = trackPathEnabled;
    }

    public Path getTraveledPath() {
        return traveledPath;
    }

    public void resetTraveledPath() {
        traveledPath.clear();
    }

}
