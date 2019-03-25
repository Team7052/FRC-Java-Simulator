package driverstation.ui;

import driverstation.states.DSRobotEnabledState;
import driverstation.states.DSRobotState;
import driverstation.states.StateManager;
import util.Constraint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RobotControlsPanel extends DSStateManagedPanel implements ActionListener {
    JPanel pickerGridPanel = new JPanel();
    ArrayList<JButton> robotStateButtons;
    JPanel enableStatePanel;
    JButton enableButton;
    JButton disableButton;

    SpringLayout sl;

    public RobotControlsPanel() {
        super();
        this.setBackground(Color.ORANGE);
        robotStateButtons = new ArrayList<>();
        sl = new SpringLayout();
        this.setLayout(sl);

        pickerGridPanel.setLayout(new GridLayout(DSRobotState.values().length, 1));

        for (DSRobotState state: DSRobotState.values()) {
            JButton newButton = new JButton(capitalizedFirst(state.toString()));
            newButton.addActionListener(this);
            this.robotStateButtons.add(newButton);
            pickerGridPanel.add(newButton);
        }

        this.add(pickerGridPanel);
        sl.putConstraint(Constraint.left, pickerGridPanel, 0, Constraint.left, this);
        sl.putConstraint(Constraint.top, pickerGridPanel, 0, Constraint.top, this);
        sl.putConstraint(Constraint.right, pickerGridPanel, 0, Constraint.right, this);
        sl.getConstraints(pickerGridPanel).setHeight(Spring.constant(180));

        enableStatePanel = new JPanel();
        enableStatePanel.setLayout(new GridLayout(1,2));

        enableButton = new JButton("Enable");
        disableButton = new JButton("Disable");

        enableButton.setBackground(new Color(148,233,78));
        disableButton.setBackground(new Color(233, 78, 78));
        enableButton.setOpaque(true);
        disableButton.setOpaque(true);

        enableButton.addActionListener(this);
        disableButton.addActionListener(this);

        enableStatePanel.add(enableButton);
        enableStatePanel.add(disableButton);

        this.add(enableStatePanel);
        sl.putConstraint(Constraint.left, enableStatePanel, 0, Constraint.left, this);
        sl.putConstraint(Constraint.right, enableStatePanel, 0, Constraint.right, this);
        sl.putConstraint(Constraint.bottom, enableStatePanel, 0, Constraint.bottom, this);
        sl.putConstraint(Constraint.top, enableStatePanel, 0, Constraint.bottom, pickerGridPanel);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Enable")) StateManager.updateDSRobotEnabledState(DSRobotEnabledState.enabled);
        else if (e.getActionCommand().equals("Disable")) StateManager.updateDSRobotEnabledState(DSRobotEnabledState.disabled);
        else StateManager.updateDSRobotState(DSRobotState.toState(e.getActionCommand().toLowerCase()));
    }

    @Override
    public void stateManagerWillUpdate(DSRobotEnabledState newState) {
        this.enableButton.setEnabled(newState == DSRobotEnabledState.disabled);
        this.disableButton.setEnabled(newState == DSRobotEnabledState.enabled);
    }

    @Override
    public void stateManagerWillUpdate(DSRobotState newState) {
        for (JButton button: robotStateButtons) {
            button.setEnabled(!newState.toString().equals(button.getText().toLowerCase()));
        }
    }


    String capitalizedFirst(String word) {
        String newStr = "";
        newStr += (word.charAt(0) + "").toUpperCase();
        newStr += word.substring(1);
        return newStr;
    }

}