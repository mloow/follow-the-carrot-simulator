package gui;

import controller.FieldMouseListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.*;

/**
 * Created by marcus on 2015-09-19.
 */
public class SimGui {

    private JPanel panel;
    private JList<String> edgeList;
    private FieldPanel fieldPanel;
    private JScrollPane edgesScrollPane;
    private JButton clearPathButton;
    private JCheckBox drawTriangleCheckBox;
    private JCheckBox drawClosestLineCheckBox;
    private JCheckBox drawPathCheckBox;
    private JSlider carrotLengthSlider;
    private JCheckBox drawCarrotPathCheckBox;

    public SimGui() {

        fieldPanel.addMouseListener(new FieldMouseListener(fieldPanel, edgeList));
        fieldPanel.addMouseMotionListener(new FieldMouseListener(fieldPanel, edgeList));
        clearPathButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                fieldPanel.getField().clear();
                edgeList.removeAll();
                edgeList.revalidate();
                fieldPanel.repaint();
            }
        });

        addCheckboxListeners();
        carrotLengthSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                fieldPanel.setLookAheadDistance(carrotLengthSlider.getValue());
            }
        });
    }

    private void addCheckboxListeners() {
        drawTriangleCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                fieldPanel.setDrawTriangleEnabled(drawTriangleCheckBox.isEnabled());
                fieldPanel.repaint();
            }
        });
        drawClosestLineCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                fieldPanel.setDrawClosestLineEnabled(drawClosestLineCheckBox.isEnabled());
                fieldPanel.repaint();
            }
        });
        drawPathCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                fieldPanel.setDrawPathEnabled(drawPathCheckBox.isEnabled());
                fieldPanel.repaint();
            }
        });
        drawCarrotPathCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                fieldPanel.setDrawCarrotPathEnabled(drawCarrotPathCheckBox.isEnabled());
                fieldPanel.repaint();
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("SimGui");
        frame.setContentPane(new SimGui().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
