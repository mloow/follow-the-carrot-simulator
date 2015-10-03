package simulation;

import graphing.Point;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

/**
 * Created by marcus on 2015-09-29.
 *
 * Mathematics from http://chess.eecs.berkeley.edu/eecs149/documentation/differentialDrive.pdf
 *
 */
public class DifferentialDriveRobot extends Robot {

    private static final double TICK_PERIOD = 0.01;

    // physical constants
    private final double wheelRadius = 10d;
    private final double wheelAxisLength = 15d;
    private final double wheelMaxAngularVelocity = 2d * Math.PI;
    private final double wheelAngularAcceleration = 2d * Math.PI;

    // variables
    private double leftWheelAngularVelocity;
    private double rightWheelAngularVelocity;

    private double targetLinearSpeed;
    private double targetAngularSpeed;

    public DifferentialDriveRobot() {
        setOrientation(Math.PI/4);
        leftWheelAngularVelocity =  Math.PI;
        rightWheelAngularVelocity = Math.PI;
    }

    public void setTargetLinearSpeed(double targetLinearSpeed) {

        leftWheelAngularVelocity = targetLinearSpeed / wheelRadius;
        rightWheelAngularVelocity = leftWheelAngularVelocity;

    }

    public void setTargetAngularSpeed(double targetAngularSpeed) {

        double change = ((targetAngularSpeed / wheelAxisLength) / (rightWheelSpeed() - leftWheelSpeed())) / 2d;
        double changeInAngularWheelVelocity = change / wheelRadius;

        rightWheelAngularVelocity += changeInAngularWheelVelocity;
        leftWheelAngularVelocity  -= changeInAngularWheelVelocity;
    }

    public void driveFor(double seconds) {

        int ticks = (int) (seconds / TICK_PERIOD);
        for(int i = 0; i < ticks; i++) {
            tick();
        }

    }

    private void tick() {

        updateWheelSpeeds();

        if(leftWheelAngularVelocity == rightWheelAngularVelocity) {
            double x = getPosition().getX() + leftWheelAngularVelocity * Math.cos(getOrientation()) * TICK_PERIOD;
            double y = getPosition().getY() + leftWheelAngularVelocity * Math.sin(getOrientation()) * TICK_PERIOD;
            setPosition(new Point(x, y));
        } else if(rightWheelAngularVelocity == -leftWheelAngularVelocity) {
            setOrientation(getOrientation() + 2 * rightWheelAngularVelocity * TICK_PERIOD / wheelAxisLength);
        } else {

            RealMatrix rotationMatrix = getRotationMatrix();
            RealMatrix distanceMatrix = getDistanceMatrix();
            RealMatrix iccMatrix      = getIccMatrix();

            RealMatrix poseMatrix = rotationMatrix.multiply(distanceMatrix).add(iccMatrix);

            double[] pose = poseMatrix.getColumn(0);

            setPosition(new Point(pose[0], pose[1]));
            setOrientation(pose[2]);
        }

    }

    private void updateWheelSpeeds() {

    }

    private RealMatrix getRotationMatrix() {
        double m11 = Math.cos(rateOfRotation()*TICK_PERIOD);
        double m12 = -Math.sin(rateOfRotation()*TICK_PERIOD);

        double[][] matrix = {{ m11, m12, 0d},
                             {-m12, m11, 0d},
                             {  0d,  0d, 1d}};

        return MatrixUtils.createRealMatrix(matrix);
    }

    private RealMatrix getDistanceMatrix() {
        double m1 = getPosition().getX() - iccLocation().getX();
        double m2 = getPosition().getY() - iccLocation().getY();
        double m3 = getOrientation();

        double[] matrix = {m1, m2, m3};

        return MatrixUtils.createColumnRealMatrix(matrix);
    }

    private RealMatrix getIccMatrix() {

        double m1 = iccLocation().getX();
        double m2 = iccLocation().getY();
        double m3 = rateOfRotation() * TICK_PERIOD;

        double[] matrix = {m1, m2, m3};

        return MatrixUtils.createColumnRealMatrix(matrix);

    }

    private Point iccLocation() {

        double orientation = getOrientation();
        double x = getPosition().getX() - distanceToIcc() * Math.sin(orientation);
        double y = getPosition().getY() + distanceToIcc() * Math.cos(orientation);

        return new Point(x, y);

    }

    private double distanceToIcc() {
        return (wheelAxisLength / 2d) * ((leftWheelSpeed()  + rightWheelSpeed()) /
                                         (rightWheelSpeed() -  leftWheelSpeed()));
    }

    private double rateOfRotation() {
        return (rightWheelSpeed() - leftWheelSpeed()) / wheelAxisLength;
    }

    private double leftWheelSpeed() {
        return leftWheelAngularVelocity * wheelRadius;
    }

    private double rightWheelSpeed() {
        return rightWheelAngularVelocity * wheelRadius;
    }

    public double getLinearSpeed() {
        return rateOfRotation() * distanceToIcc();
    }

}
