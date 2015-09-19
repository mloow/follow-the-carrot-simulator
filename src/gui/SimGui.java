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
    private JButton clearEdgesButton;

    public SimGui() {

        fieldPanel.addMouseListener(new FieldMouseListener(fieldPanel, edgeList));
        fieldPanel.addMouseMotionListener(new FieldMouseListener(fieldPanel, edgeList));
        clearEdgesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                fieldPanel.getField().clear();
                edgeList.removeAll();
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
