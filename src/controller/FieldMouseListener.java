package controller;

import geometry.Vertex;
import graphing.Point;
import gui.FieldPanel;
import simulation.Field;

import javax.swing.*;
import java.awt.event.*;

/**
 * Created by marcus on 2015-09-19.
 */
public class FieldMouseListener implements MouseListener, MouseMotionListener, MouseWheelListener {

    private static final double EDGE_LENGTH = 20.0;
    private final Field field;
    private final FieldPanel fieldPanel;
    private Vertex lastAdded = null;
    private boolean robotPosLocked = false;

    public FieldMouseListener(FieldPanel fieldPanel) {
        this.fieldPanel = fieldPanel;
        this.field = fieldPanel.getField();
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

        if(SwingUtilities.isLeftMouseButton(mouseEvent)) {
            Vertex mousePos = new Vertex(mouseEvent.getX(), mouseEvent.getY());
            if (!mousePos.equals(lastAdded)) {
                field.getPath().concat(mousePos);
                fieldPanel.repaint();
                lastAdded = mousePos;
            }
        } else if(SwingUtilities.isRightMouseButton(mouseEvent)) {
            robotPosLocked = !robotPosLocked;
            fieldPanel.setRunning(false);
            fieldPanel.setDrawRobotEnabled(robotPosLocked);
            field.getRobot().setTrackPathEnabled(robotPosLocked);
            if(robotPosLocked) {
                field.getRobot().resetTraveledPath();
            }
            field.getRobot().setPosition(new Point(mouseEvent.getX(), mouseEvent.getY()));
            fieldPanel.repaint();
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

        if(!robotPosLocked){
            boolean oldDrawPath = fieldPanel.isDrawPathEnabled();
            Point mousePos = new Point(mouseEvent.getX(), mouseEvent.getY());

            fieldPanel.setDrawPathEnabled(false);

            field.getRobot().setPosition(mousePos);

            fieldPanel.updateCarrotPoint();

            fieldPanel.repaint();

            fieldPanel.setDrawPathEnabled(oldDrawPath);
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {

        if(mouseWheelEvent.getWheelRotation() < 0) {
            field.getRobot().turn(Math.PI / 16);
        } else {
            field.getRobot().turn(-Math.PI / 16);
        }
        fieldPanel.repaint();

    }
}
