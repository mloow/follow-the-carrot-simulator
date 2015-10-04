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
    private FieldPanel fieldPanel;
    private JButton clearPathButton;
    private JCheckBox drawClosestLineCheckBox;
    private JCheckBox drawPathCheckBox;
    private JSlider carrotLengthSlider;
    private JCheckBox drawCarrotPathCheckBox;
    private JButton runButton;
    private JComboBox comboBox1;
    private Thread simulationThread;

    public SimGui() {

        FieldMouseListener allInOneMouseListener = new FieldMouseListener(fieldPanel);

        fieldPanel.addMouseListener(allInOneMouseListener);
        fieldPanel.addMouseMotionListener(allInOneMouseListener);
        fieldPanel.addMouseWheelListener(allInOneMouseListener);
        clearPathButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                fieldPanel.getField().getPath().clear();
                fieldPanel.getField().updateCarrotPoint();
                fieldPanel.repaint();
            }
        });

        addCheckboxListeners();
        carrotLengthSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                fieldPanel.getField().getPath().setLookAheadDistance(carrotLengthSlider.getValue());
                fieldPanel.getField().updateCarrotPoint();
                fieldPanel.repaint();
            }
        });
        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                if(runButton.getText().equals("Run")) {
                    simulationThread = new Thread(() -> {
                        runButton.setText("Stop");
                        fieldPanel.run();
                        runButton.setText("Run");
                    });
                    simulationThread.start();
                } else {
                    fieldPanel.setRunning(false);
                    runButton.setText("Run");
                }

            }
        });
    }

    private void addCheckboxListeners() {

        drawClosestLineCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                fieldPanel.setDrawClosestLineEnabled(drawClosestLineCheckBox.isSelected());
                fieldPanel.repaint();
            }
        });
        drawPathCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                fieldPanel.setDrawPathEnabled(drawPathCheckBox.isSelected());
                fieldPanel.repaint();
            }
        });
        drawCarrotPathCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                fieldPanel.setDrawCarrotPointEnabled(drawCarrotPathCheckBox.isSelected());
                fieldPanel.repaint();
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Follow the carrot simulator");
        frame.setContentPane(new SimGui().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
