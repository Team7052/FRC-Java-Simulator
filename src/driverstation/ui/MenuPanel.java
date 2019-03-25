package driverstation.ui;


import driverstation.states.DSRobotEnabledState;
import driverstation.states.DSState;
import driverstation.states.StateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MenuPanel extends DSStateManagedPanel implements ActionListener {

    ArrayList<JButton> buttons;
    DSState currentState;

    public MenuPanel() {
        super();
        DSState[] states = DSState.values();
        this.setLayout(new GridLayout(states.length, 1));
        if (states.length > 0) {
            currentState = states[0];
        }
        buttons = new ArrayList<>();
        for (DSState state: states) {
            JButton button = new JButton("" + state.toString().charAt(0));
            button.setName(state.toString());
            button.addActionListener(this);
            buttons.add(button);
            this.add(button);
        }
        if (states.length > 0) {
            buttons.get(0).setEnabled(false);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton selectedButton = (JButton) e.getSource();
        StateManager.updateDSState(DSState.toState(selectedButton.getName()));
    }

    @Override
    public void stateManagerWillUpdate(DSState newState) {
        for (JButton button: this.buttons) {
            button.setEnabled(!button.getName().equals(newState.toString()));
        }
    }
}
