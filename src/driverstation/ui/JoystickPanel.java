package driverstation.ui;

import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import util.Constraint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class JoystickPanel extends DSStateManagedPanel implements ActionListener {
    JComboBox comboBox;

    public Controller selectedController;
    private JPanel axisPanel = new JPanel();
    private JPanel buttonsPanel = new JPanel();

    public List<Component> axis = new ArrayList<>();
    public List<Component> buttons = new ArrayList<>();

    private List<JLabel> axisLabels = new ArrayList<>();
    private List<JLabel> buttonsLabels = new ArrayList<>();
    private SpringLayout sl;


    public boolean joystickInitialized = false;


    public JoystickPanel(SpringLayout sl) {
        super();
        this.sl = new SpringLayout();
        this.setLayout(sl);

        Controller controllers[] = ControllerEnvironment.getDefaultEnvironment().getControllers();
        comboBox = new JComboBox(controllers);
        ((JLabel) comboBox.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        comboBox.addActionListener(this);

        if (controllers.length > 2) this.selectedController = controllers[2];

        this.updatePanelUI();
        this.setVisible(true);

    }

    private void updatePanelUI() {
        this.axisLabels.clear();
        this.axis.clear();
        this.buttonsLabels.clear();
        this.buttons.clear();

        this.axisPanel.removeAll();
        this.buttonsPanel.removeAll();
        this.removeAll();
        this.repaint();
        this.revalidate();

        // get components
        if (selectedController == null) return;
        Component components[] = selectedController.getComponents();
        for (Component c : components) {
            if (c.isAnalog()) {
                axis.add(c);
            } else {
                buttons.add(c);
            }
        }

        this.add(comboBox);

        SpringLayout.Constraints comboConstraints = sl.getConstraints(comboBox);
        comboConstraints.setHeight(Spring.constant(30));
        sl.putConstraint(Constraint.left,comboBox,10,Constraint.left, this);
        sl.putConstraint(Constraint.right, comboBox, -10, Constraint.right, this);
        sl.putConstraint(Constraint.top, comboBox, 10, Constraint.top, this);

        axisPanel.setLayout(new FlowLayout());
        for (int i = 0; i < axis.size(); i++) {
            axisLabels.add(new JLabel("<html> <strong> " + axis.get(i).toString() + "</strong>: " + 0.0 + "</html>"));
            axisPanel.add(axisLabels.get(i));
        }
        this.add(axisPanel);

        sl.putConstraint(Constraint.left, axisPanel, 10, Constraint.left, this);
        sl.putConstraint(Constraint.right, axisPanel, 0, Constraint.right, comboBox);
        sl.putConstraint(Constraint.top, axisPanel, 10, Constraint.bottom, comboBox);

        buttonsPanel.setLayout(new FlowLayout());
        for (int i = 0; i < buttons.size(); i++) {
            buttonsLabels.add(new JLabel("<html><font color='red'> <strong> " + buttons.get(i).toString() + "</strong></font></html>"));
            buttonsPanel.add(buttonsLabels.get(i));
        }

        this.add(buttonsPanel);

        sl.putConstraint(Constraint.left, buttonsPanel, 10, Constraint.left, this);
        sl.putConstraint(Constraint.right, buttonsPanel, 0, Constraint.right, comboBox);
        sl.putConstraint(Constraint.top, buttonsPanel, 10, Constraint.bottom, axisPanel);

        this.repaint();
        this.revalidate();

    }

    public void setAxisAndButtonLabels() {
        if (axis.size() == axisLabels.size() && buttons.size() == buttonsLabels.size()) {
            for (int i = 0; i < axis.size(); i++) {
                DecimalFormat format = new DecimalFormat("#.##");
                axisLabels.get(i).setText("<html> <strong> " + axis.get(i).toString() + "</strong>: " + format.format(axis.get(i).getPollData()) + "</html>");
            }
            for (int i = 0; i < buttons.size(); i++) {
                String color = buttons.get(i).getPollData() == 0 ? "red" : "green";
                buttonsLabels.get(i).setText("<html><font color='" + color + "'> <strong> " + buttons.get(i).toString() + "</strong></font></html>");
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // start thread
        selectedController = (Controller) comboBox.getSelectedItem();
        updatePanelUI();

        this.joystickInitialized = true;
    }
}
