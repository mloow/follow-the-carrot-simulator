package gui;

import geometry.Edge;
import geometry.Vertex;
import graphing.Vector;
import simulation.Field;
import graphing.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;

/**
 * Created by marcus on 2015-09-19.
 */
public class FieldPanel extends JPanel {

    private static final int POINT_RADIUS = 4;
    private final Field field;
    private boolean drawPathEnabled = true;
    private boolean drawClosestLineEnabled = true;
    private boolean drawCarrotPathEnabled = true;
    private boolean drawRobotEnabled = false;
    private ArrayList<Edge> carrotPath;
    private Point carrotPoint;
    private boolean running;

    public FieldPanel() {
        super();
        this.field = new Field();
        updateCarrotPath();
    }

    public boolean isDrawPathEnabled() {
        return drawPathEnabled;
    }

    public void run() {

        running = true;
        while(running && !field.getRobot().isAt(field.getPath().getEnd().toPoint())) {

            updateCarrotPath();
            field.getRobot().moveFor(0.03);
            repaint();
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateCarrotPath() {
        try {
            carrotPath = field.getPath().getCarrotPathFrom(field.getRobot().getPosition(), field.getRobot().getLookAheadDistance());
            carrotPoint = carrotPath.get(carrotPath.size() - 1).end.toPoint();
            field.getRobot().setCarrotPoint(carrotPoint);
        } catch (Exception e) {
            // no path
        }
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);



        drawBackground(graphics);


        if(drawClosestLineEnabled) {
            drawClosestLine(graphics);
        }

        if(drawPathEnabled) {
            drawPath(graphics);
        }

        if(drawCarrotPathEnabled) {
            drawCarrotPath(graphics);
        }

        if(drawRobotEnabled) {
            drawRobot(graphics);
        }
    }

    private void drawRobot(Graphics graphics) {
        Point robotPos = field.getRobot().getPosition();
        if(robotPos != null) {
            int x1 = (int) robotPos.getX();
            int y1 = (int) robotPos.getY();
            drawPoint(x1, y1, Color.pink, graphics);

            Vector orientation = field.getRobot().getVelocity().normalize().scale(20);


            int x2 = (int) (x1 + orientation.getX());
            int y2 = (int) (y1 + orientation.getY());

            ((Graphics2D) graphics).setStroke(new BasicStroke(2));
            graphics.drawLine(x1, y1, x2, y2);
            double steeringAngle = Math.toDegrees(field.getRobot().getSteeringAngle());
            String s = String.format("%.1f\u00B0", steeringAngle);
            graphics.drawString(s, (int) robotPos.getX() + 15, (int) robotPos.getY() - 15);
        }
    }

    private void drawCarrotPath(Graphics graphics) {

        Graphics2D g2d = (Graphics2D) graphics;
        g2d.setColor(new Color(255, 128, 0));
        g2d.setStroke(new BasicStroke(3));

        Point robotPos = field.getRobot().getPosition();


        for(Edge e : carrotPath) {
            g2d.draw(new Line2D.Float((int) e.start.x, (int) e.start.y, (int) e.end.x, (int) e.end.y));
        }

        if (!carrotPath.isEmpty())
        {
            drawPoint((int) carrotPoint.getX(), (int) carrotPoint.getY(), new Color(255, 128, 0), graphics);
            drawCarrotPointCoordinates(carrotPoint, graphics);
            g2d.setStroke(new BasicStroke(1));
            g2d.drawLine((int) robotPos.getX(), (int) robotPos.getY(), (int) carrotPoint.getX(), (int) carrotPoint.getY());
        }


    }

    private void drawCarrotPointCoordinates(Point carrotPoint, Graphics graphics) {

        String s = String.format("(%.1f, %.1f)", carrotPoint.getX(), carrotPoint.getY());

        graphics.drawString(s, (int) carrotPoint.getX() + 25, (int) carrotPoint.getY() - 25);
    }

    private void drawBackground(Graphics graphics) {
        graphics.setColor(new Color(32, 32, 32));
        graphics.fillRect(0, 0, getWidth(), getHeight());
    }

    private void drawPath(Graphics graphics) {


        Vertex start = field.getPath().getStart();
        if(start != null) {
            drawPoint((int)start.x, (int)start.y, Color.green, graphics);
        }

        for(int i = 0; i < field.getPath().getEdges().size(); i++) {

            Edge e = field.getPath().getEdges().get(i);
            Graphics2D g2d = (Graphics2D) graphics;
            g2d.setColor(Color.cyan);
            g2d.setStroke(new BasicStroke(4));
            g2d.draw(new Line2D.Float((int) e.start.x, (int) e.start.y, (int) e.end.x, (int) e.end.y));

        }
        Vertex end = field.getPath().getEnd();
        if(end != null) {

            drawPoint((int) end.x, (int) end.y, Color.red, graphics);
        }
    }

    private void drawClosestLine(Graphics graphics) {
        Point robotPos = field.getRobot().getPosition();
        if(robotPos != null) {
            Edge e = field.getPath().getClosestEdgeTo(robotPos);
            if(e != null) {

                Point closest = e.getClosestPointTo(robotPos);

                graphics.setColor(new Color(128, 128, 128));
                graphics.drawLine((int) robotPos.getX(), (int) robotPos.getY(), (int) closest.getX(), (int) closest.getY());
            }

        }
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

    public void setDrawCarrotPathEnabled(boolean drawCarrotPathEnabled) {
        this.drawCarrotPathEnabled = drawCarrotPathEnabled;
    }

    public void setDrawRobotEnabled(boolean drawRobotEnabled) {
        this.drawRobotEnabled = drawRobotEnabled;
    }


    public void setRunning(boolean running) {
        this.running = running;
    }
}
