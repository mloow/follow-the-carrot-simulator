package simulation;

import graphing.Point;
import graphing.Vector;

public class Mover2D {
    public static final double TICK_PERIOD = 0.001;

    private Vector velocity;
    private Vector acceleration;
    private Point position;
    private double maxSpeed;


    public Mover2D() {

        position = new Point(0, 0);
        velocity = new Vector(0, 0);
        acceleration = new Vector(0, 0);
        maxSpeed = 0;
    }

    public Mover2D(Point position, Vector velocity, Vector acceleration, double maxSpeed) {
        this.acceleration = acceleration;
        this.maxSpeed = maxSpeed;
        this.velocity = velocity;
        this.position = position;
    }


    public void tick() {

        double vX = velocity.getX() + acceleration.getX() * TICK_PERIOD;
        double vY = velocity.getY() + acceleration.getY() * TICK_PERIOD;

        if(maxSpeed >= 0 &&Math.hypot(vX, vY) > maxSpeed) {

            double ratio = maxSpeed/Math.hypot(vX, vY);
             vX *= ratio;
             vY *= ratio;

        }

        velocity = new Vector(vX, vY);

        position.setX(position.getX() + velocity.getX() * TICK_PERIOD);
        position.setY(position.getY() + velocity.getY() * TICK_PERIOD);

    }

    public double getOrientation() {
        return (new Point(0, 0)).getAngleTo(velocity.pointsTo());
    }

    public Vector getVelocity() {
        return velocity;
    }

    public Point getPosition() {
        return position;
    }

    public void setVelocity(double x, double y) {

        if(maxSpeed >= 0 && Math.hypot(x, y) > maxSpeed) {

            double ratio = maxSpeed/Math.hypot(x, y);
            x *= ratio;
            y *= ratio;

        }

        velocity = new Vector(x, y);

    }

    public void setVelocity(Vector velocity) {
        setVelocity(velocity.getX(), velocity.getY());
    }

    public void setAcceleration(Vector acceleration) {
        this.acceleration = acceleration;
    }

    public void setAcceleration(double x, double y) {
        acceleration = new Vector(x, y);
    }

    public Vector getAcceleration() {
        return acceleration;
    }

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public void setPosition(Point position) {
        this.position = position;
    }
}
