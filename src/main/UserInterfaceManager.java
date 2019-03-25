package main;

import driverstation.DriverStationDelegate;
import driverstation.DriverStationManager;
import util.HardwareWorld;

public class UserInterfaceManager implements DriverStationDelegate {
    DriverStationManager dsManager;
    WorldBirdsEyeView birdsEyeView;
    WorldSideView worldSideView;


    public UserInterfaceManager() {
        dsManager = new DriverStationManager(this);
        birdsEyeView = new WorldBirdsEyeView();
        worldSideView = new WorldSideView();
        dsManager.setVisible(true);
    }

    @Override
    public void toggleWorldSideView(boolean isVisible) {
        this.worldSideView.setVisible(isVisible);
    }

    @Override
    public void toggleWorldBirdView(boolean isVisible) {
        this.birdsEyeView.setVisible(isVisible);
    }

    public void updateViews() {
        this.birdsEyeView.repaint();
        this.worldSideView.repaint();
    }
}
