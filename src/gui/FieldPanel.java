package gui;

import geometry.Edge;
import geometry.Vertex;
import graphing.Vector;
import simulation.DifferentialDriveRobot;
import simulation.Field;
import simulation.Robot;
import graphing.Point;
import simulation.Path;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

/**
 * Created by marcus on 2015-09-19.
 */
public class FieldPanel extends JPanel {

    private static final int POINT_RADIUS = 4;
    
    private final Field field;
    
    private boolean drawPathEnabled = true;
    private boolean drawClosestLineEnabled = true;
    private boolean drawCarrotPointEnabled = true;
    private boolean drawRobotEnabled = true;
    private boolean drawRobotPathEnabled = true;
    
    private boolean running;

    public FieldPanel() {
        super();
        field = new Field();
    }

    public void run() {

        DifferentialDriveRobot robot = (DifferentialDriveRobot) field.getRobot();
        if(robot == null) return;

        running = true;
        while(running) {


            robot.setTargetLinearSpeed(50.0);
            robot.driveFor(0.03);

            field.updateCarrotPoint();
            repaint();
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        drawBackground(graphics);
        drawDebug(graphics);

        if(drawPathEnabled) {
            drawPath(graphics);
        }

        if(drawClosestLineEnabled) {
            drawClosestLine(graphics);
        }

        if(drawCarrotPointEnabled) {
            drawCarrotPoint(graphics);
        }

        if(drawRobotEnabled) {
            drawRobot(graphics);
        }

        if(drawRobotPathEnabled) {
            drawRobotPath(graphics);
        }
    }

    private void drawRobotPath(Graphics graphics) {
        Robot robot = field.getRobot();

        if(robot == null) return;


        Graphics2D g2d = (Graphics2D) graphics;
        g2d.setColor(new Color(27, 153, 78));
        g2d.setStroke(new BasicStroke(2));

        Path robotPath = robot.getTraveledPath();
        for(Edge e : robotPath.getEdges()) {
            int x1 = (int) e.start.x;
            int y1 = (int) (this.getHeight() - e.start.y);
            int x2 = (int) e.end.x;
            int y2 = (int) (this.getHeight() - e.end.y);
            graphics.drawLine(x1, y1, x2, y2);
        }
    }

    private void drawRobot(Graphics graphics) {
        Robot robot = field.getRobot();

        if(robot == null) return;


        Point robotPos = robot.getPosition();
        int x1 = (int) robotPos.getX();
        int y1 = (int) (this.getHeight() - robotPos.getY());
        drawPoint(x1, y1, new Color(128, 255, 0), graphics);

        Vector orientation = new Vector(Math.cos(robot.getOrientation()), Math.sin(robot.getOrientation()));
        int x2 = x1 + (int) orientation.scale(20).getX();
        int y2 = y1 - (int) orientation.scale(20).getY();

        graphics.drawLine(x1, y1, x2, y2);
    }

    private void drawCarrotPoint(Graphics graphics) {
        Robot robot = field.getRobot();
        Point carrotPoint = field.getCarrotPoint();

        if(robot == null || carrotPoint == null) return;

        Point robotPos = robot.getPosition();
        Graphics2D g2d = (Graphics2D) graphics;

        int cX = (int) carrotPoint.getX();
        int cY = (int) (this.getHeight() - carrotPoint.getY());

        drawPoint(cX, cY, new Color(255, 128, 0), graphics);

        int rX = (int) robotPos.getX();
        int rY = (int) (this.getHeight() - robotPos.getY());


        g2d.setStroke(new BasicStroke(1));
        g2d.drawLine(rX, rY, cX, cY);

        String s = String.format("(%.1f, %.1f)", carrotPoint.getX(), carrotPoint.getY());
        graphics.drawString(s, cX + 25, cY + 25);

    }

    private void drawBackground(Graphics graphics) {
        graphics.setColor(new Color(32, 32, 32));
        graphics.fillRect(0, 0, getWidth(), getHeight());
    }

    private void drawPath(Graphics graphics) {

        Path path = field.getPath();
        if(field.getPath().getEdges().size() < 1) return;

        Vertex start = path.getStart();
        drawPoint((int) start.x, (int) (this.getHeight() - start.y), Color.green, graphics);

        Graphics2D g2d = (Graphics2D) graphics;
        g2d.setColor(new Color(128, 128, 128));
        g2d.setStroke(new BasicStroke(3));

        for(Edge e : path.getEdges()) {

            int x1 = (int) e.start.x;
            int y1 = (int) (this.getHeight() - e.start.y);
            int x2 = (int) e.end.x;
            int y2 = (int) (this.getHeight() - e.end.y);
            g2d.draw(new Line2D.Float(x1, y1, x2, y2));
        }

        if(path.getEnd() != null) {
            Vertex end = path.getEnd();
            drawPoint((int) end.x, (int)(this.getHeight() - end.y) , Color.red, graphics);
        }
    }

    private void drawClosestLine(Graphics graphics) {
        Robot robot = field.getRobot();

        if(robot == null) return;

        Graphics2D g2d = (Graphics2D) graphics;
        g2d.setColor(new Color(96, 96, 96));
        g2d.setStroke(new BasicStroke(1));

        Point robotPos = robot.getPosition();
        if(field.getPath().getEdges().size() > 0) {

            Edge e = field.getPath().getClosestEdgeTo(robotPos);
            Point closest = e.getClosestPointTo(robotPos);

            int rX = (int) robotPos.getX();
            int rY = (int) (this.getHeight() - robotPos.getY());
            int clX = (int) closest.getX();
            int clY = (int) (this.getHeight() - closest.getY());

            graphics.drawLine(rX, rY, clX, clY);
        }

    }

    private void drawDebug(Graphics graphics) {

        Robot robot = field.getRobot();

        if(robot == null) return;

        Point   position = robot.getPosition();
        double orientation = Math.toDegrees(robot.getOrientation());
        double steeringAngle = Math.toDegrees(field.getSteeringAngle());

        String positionString       = "Position: " +position.toString();
        String speedString          = String.format("Linear speed: %.1f", ((DifferentialDriveRobot) robot).getLinearSpeed());
        String orientationString    = String.format("Orientation: %.1f°", orientation);
        String steeringString       = String.format("Steering angle: %.1f°", steeringAngle);
        String carrotAngleString    = "Carrot angle: -";
        String cpDisString          = "C.P distance: -";

        if (field.getCarrotPoint() != null) {
             cpDisString      = String.format("C.P distance: %.1f", position.getDistanceTo(field.getCarrotPoint()));
            carrotAngleString = String.format("Carrot angle: %.1f°", Math.toDegrees(position.getAngleTo(field.getCarrotPoint())));
        }

        graphics.setColor(Color.white);

        StringDrawer stringDrawer = new StringDrawer(graphics);
        stringDrawer.setVerticalSpacing(2);
        stringDrawer.drawStringsDescending(10, 30,
                positionString,
                speedString,
                orientationString,
                steeringString,
                carrotAngleString,
                cpDisString);

    }

    private void drawPoint(int  x, int y, Color color, Graphics graphics) {
        graphics.setColor(color);
        graphics.fillOval(x - POINT_RADIUS, y - POINT_RADIUS, POINT_RADIUS * 2, POINT_RADIUS * 2);

    }

    public Field getField() {
        return field;
    }

    public void setDrawPathEnabled(boolean drawPathEnabled) {
        this.drawPathEnabled = drawPathEnabled;
    }

    public void setDrawClosestLineEnabled(boolean drawClosestLineEnabled) {
        this.drawClosestLineEnabled = drawClosestLineEnabled;
    }

    public void setDrawCarrotPointEnabled(boolean drawCarrotPointEnabled) {
        this.drawCarrotPointEnabled = drawCarrotPointEnabled;
    }

    public void setDrawRobotEnabled(boolean drawRobotEnabled) {
        this.drawRobotEnabled = drawRobotEnabled;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void setDrawRobotPathEnabled(boolean drawRobotPathEnabled) {
        this.drawRobotPathEnabled = drawRobotPathEnabled;
    }
}
