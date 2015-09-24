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
    private boolean drawTriangleEnabled = false;
    private boolean drawPathEnabled = true;
    private boolean drawClosestLineEnabled = false;
    private boolean drawCarrotPathEnabled = false;
    private boolean drawCarrotPointEnabled = false;
    private double lookAheadDistance = 100;
    private boolean drawRobotEnabled;

    public FieldPanel() {
        super();
        this.field = new Field();
    }

    public boolean isDrawPathEnabled() {
        return drawPathEnabled;
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        drawBackground(graphics);

        if(drawRobotEnabled) {
            drawRobot(graphics);
        }

        if(drawTriangleEnabled || drawClosestLineEnabled) {
            drawClosestLine(graphics);
        }

        if(drawPathEnabled) {
            drawPath(graphics);
        }

        if(drawCarrotPathEnabled || drawCarrotPointEnabled) {
            drawCarrotPath(graphics);
        }
    }

    private void drawRobot(Graphics graphics) {
        Point robotPos = field.getRobot().getPosition();
        if(robotPos != null) {
            int x1 = (int) robotPos.getX();
            int y1 = (int) robotPos.getY();
            drawPoint(x1, y1, Color.pink, graphics);

            Vector orientation = field.getRobot().getVelocity().normalize().scale(2).scale(10);


            int x2 = (int) (x1 + orientation.getX());
            int y2 = (int) (y1 + orientation.getY());


            graphics.drawLine(x1, y1, x2, y2);
        }
    }

    private void drawCarrotPath(Graphics graphics) {

        Graphics2D g2d = (Graphics2D) graphics;
        g2d.setColor(new Color(255, 128, 0));
        g2d.setStroke(new BasicStroke(3));

        Vertex robotPos = Vertex.fromPoint(field.getRobot().getPosition());

        ArrayList<Edge> carrotPath = field.getPath().getCarrotPathFrom(robotPos, lookAheadDistance);

        if(drawCarrotPathEnabled) {
            for(Edge e : carrotPath) {
                g2d.draw(new Line2D.Float((int) e.start.x, (int) e.start.y, (int) e.end.x, (int) e.end.y));
            }
        }

        if(!carrotPath.isEmpty())
        {
            Vertex carrotPoint = carrotPath.get(carrotPath.size()-1).end;
            drawPoint((int) carrotPoint.x, (int) carrotPoint.y, new Color(255, 128, 0), graphics);
            if(drawCarrotPointEnabled) {
                drawCarrotPointCoordinates(carrotPoint, graphics);
            }
        }
    }

    private void drawCarrotPointCoordinates(Vertex carrotPoint, Graphics graphics) {

        String s = String.format("(%.1f, %.1f)", carrotPoint.x, carrotPoint.y);

        graphics.drawString(s, (int) carrotPoint.x + 25, (int) carrotPoint.y + 25);
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
        Vertex robotPos = Vertex.fromPoint(field.getRobot().getPosition());
        if(robotPos != null) {
            Edge e = field.getPath().getClosestEdgeToVertex(robotPos);
            if(e != null) {

                Vertex closest = e.getClosestVertexOnEdge(robotPos);

                if(drawTriangleEnabled) {

                    graphics.setColor(new Color(64, 64, 64));
                    graphics.drawLine((int) robotPos.x, (int) robotPos.y, (int) e.start.x, (int) e.start.y);
                    graphics.drawLine((int) robotPos.x, (int) robotPos.y, (int) e.end.x, (int) e.end.y);
                }

                if(drawClosestLineEnabled) {
                    graphics.setColor(new Color(128, 128, 128));
                    graphics.drawLine((int) robotPos.x, (int) robotPos.y, (int) closest.x, (int) closest.y);
                }
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

    public void setDrawTriangleEnabled(boolean drawTriangleEnabled) {
        this.drawTriangleEnabled = drawTriangleEnabled;
    }

    public void setDrawPathEnabled(boolean drawPathEnabled) {
        this.drawPathEnabled = drawPathEnabled;
    }

    public void setDrawClosestLineEnabled(boolean drawClosestLineEnabled) {
        this.drawClosestLineEnabled = drawClosestLineEnabled;
    }

    public void setLookAheadDistance(double lookAheadDistance) {
        this.lookAheadDistance = lookAheadDistance;
    }

    public void setDrawCarrotPathEnabled(boolean drawCarrotPathEnabled) {
        this.drawCarrotPathEnabled = drawCarrotPathEnabled;
    }

    public void setDrawCarrotPointEnabled(boolean drawCarrotPointEnabled) {
        this.drawCarrotPointEnabled = drawCarrotPointEnabled;
    }


    public void setDrawRobotEnabled(boolean drawRobotEnabled) {
        this.drawRobotEnabled = drawRobotEnabled;
    }
}
