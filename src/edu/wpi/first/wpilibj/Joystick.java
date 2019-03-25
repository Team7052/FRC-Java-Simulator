package edu.wpi.first.wpilibj;

import net.java.games.input.*;

import java.util.ArrayList;
import java.util.List;

public class Joystick {
    private Controller controller;
    private Event event = new Event();

    private List<Component> axis = new ArrayList<>();
    private List<Component> buttons = new ArrayList<>();


    public Joystick(int port) {
        Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
        int index = -1;
        int counter = 0;
        for (Controller c: controllers) {
            if(c.getName().equals("Logitech Dual Action")) {
                index = counter;
            }
            counter++;
        }

        if (index != -1) {

            this.controller = controllers[index];
            this.controller.poll();

            for (Component component: this.controller.getComponents()) {
                System.out.println(component.getName());
                if (component.isAnalog()) axis.add(component);
                else buttons.add(component);
            }
        }
    }

    public double getRawAxis(int port) {
        if (controller == null) return 0.0;
        else if (axis.size() <= port) return 0.0;
        controller.poll();
        return axis.get(port).getPollData();
    }

    public boolean getRawButton(int port) {
        if (controller == null) return false;
        else if (buttons.size() <= port) return false;
        controller.poll();
        return buttons.get(port - 1).getPollData() == 1.0;
    }
    public int getPOVCount() {
        return 0;
    }

    public int getPOV(int num) {
        return 0;
    }

}
