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

    private final Field field;
    private final JList<String> edgeList;
    private final FieldPanel fieldPanel;

    public FieldMouseListener(FieldPanel fieldPanel, JList<String> edgeList) {
        this.fieldPanel = fieldPanel;
        this.field = fieldPanel.getField();
        this.edgeList = edgeList;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

        field.getPath().concatPath(new Vertex(mouseEvent.getX(), mouseEvent.getY()));
        edgeList.setListData(field.getPath().getPathAsStringArray());
        fieldPanel.repaint();

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

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {

        boolean oldDrawPath = fieldPanel.isDrawPathEnabled();

        fieldPanel.setDrawPathEnabled(false);

        fieldPanel.getField().setRobotPosition(new Vertex(mouseEvent.getX(), mouseEvent.getY()));
        fieldPanel.repaint();

        fieldPanel.setDrawPathEnabled(oldDrawPath);
    }
}
