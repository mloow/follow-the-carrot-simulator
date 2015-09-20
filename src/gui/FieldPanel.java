package gui;

import geometry.Edge;
import geometry.Vertex;
import simulation.Field;

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
    private double lookAheadDistance = 100;

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

        if(drawTriangleEnabled || drawClosestLineEnabled) {
            drawClosestLine(graphics);
        }

        if(drawPathEnabled) {
            drawPath(graphics);
        }

        if(drawCarrotPathEnabled) {
            drawCarrotPath(graphics);
        }
    }

    private void drawCarrotPath(Graphics graphics) {

        Graphics2D g2d = (Graphics2D) graphics;
        g2d.setColor(new Color(255, 128, 0));
        g2d.setStroke(new BasicStroke(4));
        ArrayList<Edge> carrotPath = field.getPath().getCarrotPathFrom(field.getRobotPosition(), lookAheadDistance);

        for(Edge e : carrotPath) {
            g2d.draw(new Line2D.Float((int) e.start.x, (int) e.start.y, (int) e.end.x, (int) e.end.y));
        }

        if(!carrotPath.isEmpty())
        {
            Vertex lastVertex = carrotPath.get(carrotPath.size()-1).end;
            drawPoint((int) lastVertex.x, (int) lastVertex.y, new Color(255, 128, 0), graphics);
        }

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

            if(i > 0) {
                drawPoint((int)e.start.x, (int)e.start.y, Color.cyan, graphics);
            } else {
            }
            drawPoint((int) e.end.x, (int) e.end.y, Color.red, graphics);
        }
    }

    private void drawClosestLine(Graphics graphics) {
        Vertex robotPos = field.getRobotPosition();
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

    public boolean isDrawCarrotPathEnabled() {
        return drawCarrotPathEnabled;
    }
}
