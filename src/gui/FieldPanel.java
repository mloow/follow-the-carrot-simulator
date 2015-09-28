package gui;

import geometry.Edge;
import geometry.Vertex;
import graphing.Vector;
import simulation.Field;
import graphing.Point;
import simulation.Path;
import simulation.Robot;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

/**
 * Created by marcus on 2015-09-19.
 */
public class FieldPanel extends JPanel {

    private static final int POINT_RADIUS = 4;
    private final Field field;
    private final Robot robot;
    private boolean drawPathEnabled = true;
    private boolean drawClosestLineEnabled = true;
    private boolean drawCarrotPointEnabled = true;
    private boolean drawRobotEnabled = false;
    private Point carrotPoint;
    private boolean running;

    public FieldPanel() {
        super();
        field = new Field();
        robot = field.getRobot();
        updateCarrotPoint();
    }


    public void run() {

        running = true;
        while(running && field.getPath().getEdges().size() > 0 && !robot.isAt(field.getPath().getEnd().toPoint())) {

            updateCarrotPoint();
            robot.moveFor(0.03);
            repaint();
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateCarrotPoint() {
        try {
            if(field.getPath().getEdges().size() > 1) {
                carrotPoint = field.getPath().getCarrotPointFrom(robot.getPosition(), robot.getLookAheadDistance());
                robot.setCarrotPoint(carrotPoint);
            } else {
                carrotPoint = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            carrotPoint = null;
        }
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        drawBackground(graphics);
        drawDebug(graphics);

        if(drawClosestLineEnabled && field.getPath().getEdges().size() > 0 && robot.getPosition() != null) {
            drawClosestLine(graphics);
        }

        if(drawPathEnabled && field.getPath().getStart() != null) {
            drawPath(graphics);
        }

        if(drawCarrotPointEnabled && carrotPoint != null && robot.getPosition() != null) {
            drawCarrotPoint(graphics);
        }

        if(drawRobotEnabled && robot.getPosition() != null) {
            drawRobot(graphics);
        }

        drawRobotPath(graphics);
    }

    private void drawRobotPath(Graphics graphics) {

        Graphics2D g2d = (Graphics2D) graphics;
        g2d.setColor(new Color(27, 153, 78));
        g2d.setStroke(new BasicStroke(2));

        Path robotPath = robot.getTraveledPath();
        for(Edge e : robotPath.getEdges()) {
            graphics.drawLine((int) e.start.x, (int) e.start.y, (int) e.end.x, (int) e.end.y);
        }
    }

    private void drawRobot(Graphics graphics) {
        Point robotPos = robot.getPosition();
        int x1 = (int) robotPos.getX();
        int y1 = (int) robotPos.getY();
        drawPoint(x1, y1, new Color(128, 255, 0), graphics);

        Vector orientation = robot.getVelocity().normalize().scale(20);

        int x2 = (int) (x1 + orientation.getX());
        int y2 = (int) (y1 + orientation.getY());

        ((Graphics2D) graphics).setStroke(new BasicStroke(2));
        graphics.drawLine(x1, y1, x2, y2);
        double steeringAngle = Math.toDegrees(robot.getSteeringAngle());
        String s = String.format("%.1f\u00B0", steeringAngle);
        graphics.drawString(s, (int) robotPos.getX() + 15, (int) robotPos.getY() - 15);
    }

    private void drawCarrotPoint(Graphics graphics) {

        Point robotPos = robot.getPosition();
        Graphics2D g2d = (Graphics2D) graphics;
        drawPoint((int) carrotPoint.getX(), (int) carrotPoint.getY(), new Color(255, 128, 0), graphics);
        g2d.setStroke(new BasicStroke(1));
        g2d.drawLine((int) robotPos.getX(), (int) robotPos.getY(), (int) carrotPoint.getX(), (int) carrotPoint.getY());

        String s = String.format("(%.1f, %.1f)", carrotPoint.getX(), carrotPoint.getY());
        graphics.drawString(s, (int) carrotPoint.getX() + 25, (int) carrotPoint.getY() - 25);

    }

    private void drawBackground(Graphics graphics) {
        graphics.setColor(new Color(32, 32, 32));
        graphics.fillRect(0, 0, getWidth(), getHeight());
    }

    private void drawPath(Graphics graphics) {

        Vertex start = field.getPath().getStart();
        drawPoint((int) start.x, (int) start.y, Color.green, graphics);

        Graphics2D g2d = (Graphics2D) graphics;
        g2d.setColor(new Color(128, 128, 128));
        g2d.setStroke(new BasicStroke(3));

        for(Edge e : field.getPath().getEdges()) {
            g2d.draw(new Line2D.Float((int) e.start.x, (int) e.start.y, (int) e.end.x, (int) e.end.y));
        }

        if(field.getPath().getEnd() != null) {
            Vertex end = field.getPath().getEnd();
            drawPoint((int) end.x, (int) end.y, Color.red, graphics);
        }
    }

    private void drawClosestLine(Graphics graphics) {
        Point robotPos = robot.getPosition();
        if(field.getPath().getEdges().size() > 0) {

            Edge e = field.getPath().getClosestEdgeTo(robotPos);
            Point closest = e.getClosestPointTo(robotPos);

            graphics.setColor(new Color(96, 96, 96));
            graphics.drawLine((int) robotPos.getX(), (int) robotPos.getY(), (int) closest.getX(), (int) closest.getY());
        }

    }

    private void drawDebug(Graphics graphics) {

        Point   position = robot.getPosition();
        double orientation = Math.toDegrees(robot.getOrientation());
        double steeringAngle = Math.toDegrees(robot.getSteeringAngle());

        String positionString       = "Position: (- , -)";
        String orientationString    = String.format("Orientation: %.1f°", orientation);
        String carrotAngleString    = "Carrot angle: -";
        String steeringString       = String.format("Steering angle: %.1f°", steeringAngle);
        String cpDisString          = "C.P distance: -";

        if (position != null) {
           positionString           = "Position: " +position.toString() ;
        }

        if (position != null && carrotPoint != null) {
             cpDisString      = String.format("C.P distance: %.1f", position.getDistanceTo(carrotPoint));
            carrotAngleString = String.format("Carrot angle: %.1f°", Math.toDegrees(position.getAngleTo(carrotPoint)));
        }

        graphics.setColor(Color.white);

        StringDrawer stringDrawer = new StringDrawer(graphics);
        stringDrawer.setVerticalSpacing(2);
        stringDrawer.drawStringsDescending(10, 30,  positionString,
                                                    orientationString,
                                                    carrotAngleString,
                                                    steeringString,
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

    public boolean isDrawPathEnabled() {
        return drawPathEnabled;
    }


    public void setRunning(boolean running) {
        this.running = running;
    }
}
