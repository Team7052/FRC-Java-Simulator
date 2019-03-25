package main;

import util.HardwareWorld;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class UserInterface extends JFrame implements ActionListener {
    SideViewGraphicsEnginePanel mainPanel;
    HardwareWorld physicsWorld;
    String buttonNames[] = {
            "A", "X", "B", "Y", "L1", "L2", "L3"
    };

    public static HashMap<String, Boolean> buttons = new HashMap<>();

    int panelWidth = 550, panelHeight = 600;

    JPanel buttonPanel = new JPanel();
    public boolean started = false;

    public UserInterface(HardwareWorld world) {
        super("Frame");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e) {}

        this.physicsWorld = world;

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800,700);
        mainPanel = new SideViewGraphicsEnginePanel(world, panelWidth, panelHeight);
        mainPanel.setSize(panelWidth, panelHeight);
        this.setLayout(new BorderLayout());

        for (int i = 0; i < buttonNames.length; i++) {
            JButton button = new JButton(buttonNames[i]);
            button.addActionListener(this);
            buttonPanel.add(button);
            buttons.put(buttonNames[i], false);
        }

        this.add(buttonPanel, BorderLayout.NORTH);
        this.add(mainPanel, BorderLayout.CENTER);

        this.setVisible(true);
    }

    public void resetButtons() {
        for (Map.Entry<String, Boolean> entry: buttons.entrySet()) {
            buttons.put(entry.getKey(), false);
        }
    }

    public void update() {
        this.mainPanel.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (Map.Entry<String, Boolean> entry: buttons.entrySet()) {
            if (!entry.getKey().equals(e.getActionCommand())) buttons.put(entry.getKey(), false);
            else buttons.put(entry.getKey(), true);
        }
    }

}
