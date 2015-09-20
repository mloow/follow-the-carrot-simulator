package gui;

import controller.FieldMouseListener;

import javax.swing.*;
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
    private JCheckBox drawPathVerticesCheckBox;
    private JCheckBox drawPathCheckBox;
    private JSlider slider1;

    public SimGui() {

        fieldPanel.addMouseListener(new FieldMouseListener(fieldPanel, edgeList));
        fieldPanel.addMouseMotionListener(new FieldMouseListener(fieldPanel, edgeList));
        clearPathButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                fieldPanel.getField().clear();
                edgeList.removeAll();
                fieldPanel.repaint();
            }
        });

        drawTriangleCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                fieldPanel.setDrawTriangle(drawTriangleCheckBox.isSelected());
                fieldPanel.repaint();
            }
        });
        drawClosestLineCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                fieldPanel.setDrawClosestLine(drawClosestLineCheckBox.isSelected());
                fieldPanel.repaint();
            }
        });

        drawPathVerticesCheckBox.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                fieldPanel.setDrawPathVertices(drawPathVerticesCheckBox.isSelected());
                fieldPanel.repaint();
            }
        });
        drawPathCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                fieldPanel.setDrawPath(drawPathCheckBox.isSelected());
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
