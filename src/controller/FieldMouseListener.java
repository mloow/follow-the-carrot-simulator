package controller;

import geometry.Vertex;
import graphing.Point;
import gui.FieldPanel;
import simulation.DifferentialDriveRobot;
import simulation.Field;

import javax.swing.*;
import java.awt.event.*;

/**
 * Created by marcus on 2015-09-19.
 */
public class FieldMouseListener implements MouseListener, MouseMotionListener, MouseWheelListener {

    private static final double EDGE_LENGTH = 10.0;
    private final FieldPanel fieldPanel;
    private Vertex lastAdded = null;

    public FieldMouseListener(FieldPanel fieldPanel) {
        this.fieldPanel = fieldPanel;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

        Field field = fieldPanel.getField();

        if(SwingUtilities.isLeftMouseButton(mouseEvent)) {
            Vertex mousePos = new Vertex(mouseEvent.getX(), mouseEvent.getY());
            if (!mousePos.equals(lastAdded)) {
                field.getPath().concat(mousePos);
                fieldPanel.repaint();
                lastAdded = mousePos;
            }
        } else if(SwingUtilities.isRightMouseButton(mouseEvent)) {
            if(field.getRobot() == null) {
                field.setRobot(new DifferentialDriveRobot());
                field.getRobot().setPosition(new Point(mouseEvent.getX(), mouseEvent.getY()));
                field.updateCarrotPoint();
                fieldPanel.repaint();
            } else {
                field.setRobot(null);
                fieldPanel.repaint();
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

        Field field = fieldPanel.getField();

        if(SwingUtilities.isLeftMouseButton(mouseEvent)) {
            Vertex mousePos = new Vertex(mouseEvent.getX(), mouseEvent.getY());
            if (lastAdded != null) {

                if (lastAdded.distanceTo(mousePos) >= EDGE_LENGTH) {
                    field.getPath().concat(mousePos);
                    fieldPanel.repaint();
                    lastAdded = mousePos;
                }
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {


    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {

    }
}
