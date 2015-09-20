package controller;

import geometry.Vertex;
import gui.FieldPanel;
import simulation.Field;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Created by marcus on 2015-09-19.
 */
public class FieldMouseListener implements MouseListener, MouseMotionListener {

    private static final double EDGE_LENGTH = 20.0;
    private final Field field;
    private final FieldPanel fieldPanel;
    private Vertex lastAdded = new Vertex(0, 0);

    public FieldMouseListener(FieldPanel fieldPanel) {
        this.fieldPanel = fieldPanel;
        this.field = fieldPanel.getField();
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

        if(SwingUtilities.isRightMouseButton(mouseEvent)) {
            field.invertRobotPositionLock();
            if(field.isRobotPositionLocked()) {
                fieldPanel.repaint();
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        if(SwingUtilities.isLeftMouseButton(mouseEvent)) {
            lastAdded = new Vertex(mouseEvent.getX(), mouseEvent.getY());
            field.getPath().concatPath(lastAdded);
            fieldPanel.repaint();
        }

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if(SwingUtilities.isLeftMouseButton(mouseEvent)) {
            Vertex mousePos = new Vertex(mouseEvent.getX(), mouseEvent.getY());

            if (!mousePos.equals(lastAdded)) {
                field.getPath().concatPath(mousePos);
                fieldPanel.repaint();
            }
        }

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
                    field.getPath().concatPath(mousePos);
                    fieldPanel.repaint();
                    lastAdded = mousePos;
                }
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {

        if(!field.isRobotPositionLocked()){
            boolean oldDrawPath = fieldPanel.isDrawPathEnabled();
            Vertex mousePos = new Vertex(mouseEvent.getX(), mouseEvent.getY());

            fieldPanel.setDrawPathEnabled(false);

            field.setRobotPosition(mousePos);
            fieldPanel.repaint();

            fieldPanel.setDrawPathEnabled(oldDrawPath);
        }
    }
}
