package driverstation.ui;

import driverstation.states.*;
import edu.wpi.first.wpilibj.Joystick;
import util.Constraint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainDSPanel extends DSStateManagedPanel implements ActionListener {
    RobotControlsPanel robotControlsPanel;

    JPanel uiSelectorPanel = new JPanel();
    JLabel uiSelectorTitle = new JLabel("World UI View Selector");
    JLabel sideViewLabel = new JLabel("Side view");
    JLabel birdsViewLabel = new JLabel("Birds Eye View");

    JButton sideViewToggler = new JButton("Hidden");
    JButton birdsViewToggler = new JButton("Hidden");

    JoystickPanel joystickPanel;


    public MainDSPanel() {
        super();
        SpringLayout sl = new SpringLayout();
        robotControlsPanel = new RobotControlsPanel();
        this.setLayout(sl);
                                         
        this.add(robotControlsPanel);
        sl.putConstraint(Constraint.left, robotControlsPanel, 10, Constraint.left, this);
        sl.putConstraint(Constraint.top, robotControlsPanel, 10, Constraint.top, this);
        sl.putConstraint(Constraint.bottom, robotControlsPanel, -10, Constraint.bottom, this);
        sl.getConstraints(robotControlsPanel).setWidth(Spring.constant(300));

        uiSelectorPanel.setLayout(new GridLayout(0, 1));
        uiSelectorPanel.add(uiSelectorTitle);
        uiSelectorPanel.add(sideViewLabel);
        uiSelectorPanel.add(sideViewToggler);
        uiSelectorPanel.add(birdsViewLabel);
        uiSelectorPanel.add(birdsViewToggler);

        sideViewToggler.setActionCommand("Side View");
        birdsViewToggler.setActionCommand("Birds View");
        sideViewToggler.addActionListener(this);
        birdsViewToggler.addActionListener(this);

        this.add(uiSelectorPanel);
        sl.putConstraint(Constraint.left, uiSelectorPanel, 10, Constraint.right, robotControlsPanel);
        sl.putConstraint(Constraint.top, uiSelectorPanel, 10, Constraint.top, this);
        sl.putConstraint(Constraint.bottom, uiSelectorPanel, -10, Constraint.bottom, this);
        sl.getConstraints(uiSelectorPanel).setWidth(Spring.constant(200));

        joystickPanel = new JoystickPanel(sl);

        /*this.add(joystickPanel);
        sl.putConstraint(Constraint.left, joystickPanel, 10, Constraint.right, uiSelectorPanel);
        sl.putConstraint(Constraint.top, joystickPanel, 10, Constraint.top, this);
        sl.putConstraint(Constraint.bottom, joystickPanel, -10, Constraint.bottom, this);
        sl.getConstraints(joystickPanel).setWidth(Spring.constant(500));*/
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        DSUIState current = StateManager.getDSUIState();
        if (e.getActionCommand().equals(birdsViewToggler.getActionCommand())) current.isBirdsEyeViewVisible = !current.isBirdsEyeViewVisible;
        if (e.getActionCommand().equals(sideViewToggler.getActionCommand())) current.isSideViewVisible = !current.isSideViewVisible;
        StateManager.updateDSUIState(current.isBirdsEyeViewVisible, current.isSideViewVisible);
    }

    @Override
    public void stateManagerWillUpdate(DSUIState newState) {
        if (newState.isBirdsEyeViewVisible) {
            birdsViewToggler.setText("Visible");
            birdsViewToggler.setBackground(new Color(78,233,217));
            birdsViewToggler.setOpaque(true);
        }
        else {
            birdsViewToggler.setText("Hidden");
            birdsViewToggler.setOpaque(false);
        }
        if (newState.isSideViewVisible) {
            sideViewToggler.setText("Visible");
            sideViewToggler.setBackground(new Color(78,233,217));
            sideViewToggler.setOpaque(true);
        }
        else {
            sideViewToggler.setText("Hidden");
            sideViewToggler.setOpaque(false);
        }
    }
}