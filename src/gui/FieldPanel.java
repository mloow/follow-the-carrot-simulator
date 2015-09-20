package gui;

import geometry.Edge;
import geometry.Vertex;
import simulation.Field;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * Created by marcus on 2015-09-19.
 */
public class FieldPanel extends JPanel {

    private static final int POINT_RADIUS = 2;
    private final Field field;
    private boolean drawTriangle = false;
    private boolean drawPath = true;
    private boolean drawClosestLine = false;
    private boolean drawPathVertices = true;

    public FieldPanel() {
        super();
        this.field = new Field(this.getBounds());
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent componentEvent) {
                getField().setBounds(getBounds());
            }
        });
    }

    public boolean isDrawPathVertices() {
        return drawPathVertices;
    }

    public boolean isDrawClosestLine() {
        return drawClosestLine;
    }

    public boolean isDrawPath() {
        return drawPath;
    }

    public boolean isDrawTriangle() {
        return drawTriangle;
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        drawBackground(graphics);

        if(drawTriangle || drawClosestLine) {
            drawClosestLine(graphics);
        }

        if(drawPath || drawPathVertices) {
            drawPath(graphics);
        }
    }

    private void drawBackground(Graphics graphics) {
        graphics.setColor(new Color(32, 32, 32));
        graphics.fillRect(field.getBounds().x, field.getBounds().y, field.getBounds().width, field.getBounds().height);
    }

    private void drawPath(Graphics graphics) {
        if(drawPathVertices) {
            if(field.getPath().isEmpty()) {
                Vertex v = field.getLastAddedVertex();
                if(v != null) {
                    drawPoint((int)v.x, (int)v.y, Color.green, graphics);
                }
            }
        }

        for(int i = 0; i < field.getPath().size(); i++) {
            Edge e = field.getPath().get(i);
            if(drawPathVertices) {
                if(i > 0) {
                    drawPoint((int)e.start.x, (int)e.start.y, new Color(255, 128, 0), graphics);
                } else {
                    drawPoint((int)e.start.x, (int)e.start.y, Color.green, graphics);
                }
                drawPoint((int)e.end.x, (int)e.end.y, Color.red, graphics);
            }

            if(drawPath) {
                graphics.setColor(new Color(255, 128, 0));
                graphics.drawLine((int) e.start.x, (int) e.start.y, (int) e.end.x, (int) e.end.y);
            }
        }
    }

    private void drawClosestLine(Graphics graphics) {
        Vertex robotPos = field.getRobotPosition();
        if(robotPos != null) {
            Edge e =field.getClosestEdge();
            if(e != null) {

                Vertex closest = e.getClosestVertexOnEdge(robotPos);

                if(drawTriangle) {

                    graphics.setColor(new Color(64, 64, 64));
                    graphics.drawLine((int) robotPos.x, (int) robotPos.y, (int) e.start.x, (int) e.start.y);
                    graphics.drawLine((int) robotPos.x, (int) robotPos.y, (int) e.end.x, (int) e.end.y);
                }

                if(drawClosestLine) {
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

    public void setDrawTriangle(boolean drawTriangle) {
        this.drawTriangle = drawTriangle;
    }

    public void setDrawPath(boolean drawPath) {
        this.drawPath = drawPath;
    }

    public void setDrawClosestLine(boolean drawClosestLine) {
        this.drawClosestLine = drawClosestLine;
    }

    public void setDrawPathVertices(boolean drawPathVertices) {
        this.drawPathVertices = drawPathVertices;
    }
}
