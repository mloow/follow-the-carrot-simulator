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
    private boolean drawAll = true;

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

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Vertex robotPos = field.getRobotPosition();
        if(robotPos != null) {
            Edge e =field.getClosestEdge();
            if(e != null) {

                Vertex closest = e.getClosestVertexOnEdge(robotPos);
                graphics.setColor(Color.lightGray);
                graphics.drawLine((int) robotPos.x, (int) robotPos.y, (int) e.start.x, (int) e.start.y);
                graphics.drawLine((int) robotPos.x, (int) robotPos.y, (int) e.end.x, (int) e.end.y);
                graphics.setColor(Color.green);
                graphics.drawLine((int) robotPos.x, (int) robotPos.y, (int) closest.x, (int) closest.y);
            }

        }

        if(drawAll) {
            graphics.setColor(Color.black);
            if(field.getPath().isEmpty()) {
                Vertex v = field.getLastAddedVertex();
                if(v != null) {
                    drawPoint((int)v.x, (int)v.y, graphics);
                }
            }

            for(int i = 0; i < field.getPath().size(); i++) {
                Edge e = field.getPath().get(i);
                if(i == 0) {
                    drawPoint((int) e.start.x, (int) e.start.y, graphics);
                }
                drawPoint((int)e.end.x, (int)e.end.y, graphics);
                graphics.drawLine((int) e.start.x, (int) e.start.y, (int) e.end.x, (int) e.end.y);
            }

        }
    }

    private void drawPoint(int  x, int y, Graphics graphics) {

        graphics.fillOval(x - POINT_RADIUS, y - POINT_RADIUS, POINT_RADIUS * 2, POINT_RADIUS * 2);

    }

    public Field getField() {
        return field;
    }

    public void setDrawAll(boolean drawAll) {
        this.drawAll = drawAll;
    }
}
