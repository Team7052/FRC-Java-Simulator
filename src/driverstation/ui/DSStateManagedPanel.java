package driverstation.ui;

import driverstation.states.*;

import javax.swing.*;

public abstract class DSStateManagedPanel extends JPanel implements StateManager.StateManagerDelegate {
    public DSStateManagedPanel() {
        StateManager.addDelegate(this);
    }

    @Override
    public void stateManagerDidUpdate(DSState oldState) {

    }

    @Override
    public void stateManagerDidUpdate(DSRobotState oldState) {

    }

    @Override
    public void stateManagerDidUpdate(DSRobotEnabledState oldState) {

    }

    @Override
    public void stateManagerDidUpdate(DSUIState newState) {

    }

    @Override
    public void stateManagerWillUpdate(DSState newState) {

    }

    @Override
    public void stateManagerWillUpdate(DSRobotState newState) {

    }

    @Override
    public void stateManagerWillUpdate(DSRobotEnabledState newState) {

    }

    @Override
    public void stateManagerWillUpdate(DSUIState newState) {

    }
}