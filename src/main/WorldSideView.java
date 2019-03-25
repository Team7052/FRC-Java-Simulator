package main;

import driverstation.states.StateManager;
import util.HardwareWorld;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WorldSideView extends JFrame {
    SideViewGraphicsEnginePanel panel;
    public WorldSideView() {
        super("World Side View");
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds(dim.width / 2, 0, dim.width / 2, dim.height - 350);
        panel = new SideViewGraphicsEnginePanel(HardwareWorld.getInstance(), dim.width / 2, dim.height / 2);

        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        this.add(panel);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                StateManager.updateDSUIState(StateManager.getDSUIState().isBirdsEyeViewVisible, false);
            }
        });
    }
}