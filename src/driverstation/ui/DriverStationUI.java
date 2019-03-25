package driverstation.ui;

import driverstation.states.DSState;
import driverstation.states.StateManager;
import util.Constraint;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class DriverStationUI extends DSStateManagedPanel {
    MenuPanel menuPanel;

    DSStateManagedPanel controlPanel;

    Map<DSState, DSStateManagedPanel> panelsMap = new HashMap<>();

    SpringLayout sl;

    public DriverStationUI() {
        sl = new SpringLayout();
        this.setLayout(sl);

        panelsMap.put(DSState.main, new MainDSPanel());
        panelsMap.put(DSState.joystick, new JoystickPanel(sl));
        panelsMap.put(DSState.test, new TempPanel());
        panelsMap.put(DSState.settings, new TempPanel());
        panelsMap.put(DSState.lightning, new TempPanel());

        controlPanel = panelsMap.get(DSState.main);
        this.add(controlPanel);

        menuPanel = new MenuPanel();

        this.add(menuPanel);
        sl.putConstraint(Constraint.left, menuPanel, 0, Constraint.left, this);
        sl.putConstraint(Constraint.top, menuPanel, 0, Constraint.top, this);
        sl.putConstraint(Constraint.bottom, menuPanel, 0, Constraint.bottom, this);
        sl.getConstraints(menuPanel).setWidth(Spring.constant(50));

    }

    @Override
    public void stateManagerWillUpdate(DSState newState) {
        this.remove(controlPanel);
        if (panelsMap.get(newState) != this.controlPanel) this.controlPanel = panelsMap.get(newState);
        System.out.println(newState);
        this.add(controlPanel);
        sl.putConstraint(Constraint.left, controlPanel, 0, Constraint.right, menuPanel);
        sl.putConstraint(Constraint.top, controlPanel, 0, Constraint.top, this);
        sl.putConstraint(Constraint.bottom, controlPanel, 0, Constraint.bottom, this);
        sl.putConstraint(Constraint.right, controlPanel, 0, Constraint.right, this);
        this.invalidate();
        this.repaint();
    }
}