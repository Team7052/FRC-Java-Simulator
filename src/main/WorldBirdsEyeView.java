package main;

import driverstation.states.StateManager;
import util.HardwareWorld;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WorldBirdsEyeView extends JFrame {
    BirdsEyeViewGraphicsEnginePanel panel;
    public WorldBirdsEyeView() {
        super("World Birds Eye View");
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds(0, 0, dim.width / 2, dim.height - 350);
        Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(this.getGraphicsConfiguration());
        int taskBarSize = scnMax.bottom;
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        panel = new BirdsEyeViewGraphicsEnginePanel(HardwareWorld.getInstance(), dim.width / 2, dim.height - 350 - taskBarSize);
        this.add(panel);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                StateManager.updateDSUIState(false, StateManager.getDSUIState().isSideViewVisible);
            }
        });
    }
}
