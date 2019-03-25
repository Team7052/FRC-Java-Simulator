package driverstation;

import driverstation.states.*;
import driverstation.ui.DriverStationUI;

import javax.swing.*;
import java.awt.*;

public class DriverStationManager extends JFrame implements StateManager.StateManagerDelegate {
    DriverStationUI driverStationUIPanel;

    public DriverStationDelegate delegate;

    public DriverStationManager(DriverStationDelegate delegate) {
        super("Driver Station");
        this.delegate = delegate;

        StateManager.addDelegate(this);

        Dimension screenDimensions = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds(0, screenDimensions.height - 300, screenDimensions.width, 300);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch(Exception e) {}
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        driverStationUIPanel = new DriverStationUI();

        this.add(driverStationUIPanel);
        StateManager.initialize();
    }

    @Override
    public void stateManagerDidUpdate(DSState dsState) {

    }

    @Override
    public void stateManagerDidUpdate(DSRobotState dsRobotState) {

    }

    @Override
    public void stateManagerDidUpdate(DSRobotEnabledState dsRobotEnabledState) {

    }

    @Override
    public void stateManagerDidUpdate(DSUIState newState) {

    }

    @Override
    public void stateManagerWillUpdate(DSState newState) {

    }

    @Override
    public void stateManagerWillUpdate(DSRobotState newState) {
        if (newState != StateManager.getDSRobotState()) {
            StateManager.updateDSRobotEnabledState(DSRobotEnabledState.disabled);
        }
    }

    @Override
    public void stateManagerWillUpdate(DSRobotEnabledState newState) {

    }

    @Override
    public void stateManagerWillUpdate(DSUIState newState) {
        this.delegate.toggleWorldBirdView(newState.isBirdsEyeViewVisible);
        this.delegate.toggleWorldSideView(newState.isSideViewVisible);
    }
}
