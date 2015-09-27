package simulation;

import graphing.Point;
import graphing.Vector;

public class Mover2D {
    public static final double TICK_PERIOD = 0.001;

    private Vector velocity;
    private Vector accelleration;
    private Point position;
    private double maxSpeed;


    public Mover2D() {

        position = new Point(0, 0);
        velocity = new Vector(0, 0);
        accelleration = new Vector(0, 0);
        maxSpeed = 0;
    }

    public Mover2D(Point position, Vector velocity, Vector accelleration, double maxSpeed) {
        this.accelleration = accelleration;
        this.maxSpeed = maxSpeed;
        this.velocity = velocity;
        this.position = position;
    }


    public void tick() {

        double vX = velocity.getX() + accelleration.getX() * TICK_PERIOD;
        double vY = velocity.getY() + accelleration.getY() * TICK_PERIOD;

        if(maxSpeed >= 0 &&Math.hypot(vX, vY) > maxSpeed) {

            double ratio = maxSpeed/Math.hypot(vX, vY);
             vX *= ratio;
             vY *= ratio;

        }

        velocity = new Vector(vX, vY);

        position.setX(position.getX() + vX * TICK_PERIOD);
        position.setY(position.getY() + vY * TICK_PERIOD);

    }

    public double getOrientation() {
        return (new Point(0, 0)).getAngleTo(new Point(velocity.getX(), velocity.getY()));
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

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public void setPosition(Point position) {
        this.position = position;
    }
}
