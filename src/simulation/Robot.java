package simulation;

import geometry.Vertex;
import graphing.Point;

/**
 * Created by marcus on 2015-09-24.
 */
public class Robot {

    private Point position;
    private double orientation;
    private Path traveledPath;
    private boolean trackPathEnabled;

    public Robot() {
        setPosition(new Point (0, 0));
        setOrientation(0);
        setTrackPathEnabled(true);
        traveledPath = new Path();
    }

    public void setPosition(Point position) {
        this.position = position;
        if(trackPathEnabled) {
            traveledPath.concat(new Vertex(position));
        }
    }

    public Point getPosition() {
        return position;
    }

    public double getOrientation() {
        return orientation;
    }

    public void setOrientation(double rad) {

        if(rad > 2 * Math.PI) {
            setOrientation(rad - 2 * Math.PI);
        } else if (rad < -2 * Math.PI) {
            setOrientation(rad + 2 * Math.PI);
        } else {
            this.orientation = rad;
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
