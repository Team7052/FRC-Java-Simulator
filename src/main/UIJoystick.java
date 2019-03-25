package main;

import frc.robot.OI;

public class UIJoystick extends OI {
    public UIJoystick(int port) {
        super(port);
    }

    @Override
    public double axisLeft_X() {
        return 0;
    }

    @Override
    public double axisLeft_Y() {
        return 0;
    }

    @Override
    public double axisRight_X() {
        return 0;
    }

    @Override
    public double axisRight_Y() {
        return 0;
    }

    @Override
    public double axisTrigger_L2() {
        return 0;
    }

    @Override
    public double axisTrigger_R2() {
        return 0;
    }

    @Override
    public boolean button_X() {
        return false;
    }

    @Override
    public boolean button_Y() {
        return false;
    }

    @Override
    public boolean button_A() {
        return false;
    }

    @Override
    public boolean button_B() {
        return false;
    }

    @Override
    public boolean button_L1() {
        return false;
    }

    @Override
    public boolean button_L2() {
        return false;
    }

    @Override
    public boolean button_L3() {
        return false;
    }

    @Override
    public boolean button_R1() {
        return false;
    }

    @Override
    public boolean button_R2() {
        return false;
    }

    @Override
    public boolean button_R3() {
        return false;
    }

    @Override
    public boolean button_back() {
        return false;
    }

    @Override
    public boolean button_start() {
        return false;
    }

    @Override
    public boolean dPad_UP() {
        return false;
    }

    @Override
    public boolean dPad_RIGHT() {
        return false;
    }

    @Override
    public boolean dPad_DOWN() {
        return false;
    }

    @Override
    public boolean dPad_LEFT() {
        return false;
    }

    @Override
    public boolean dPad_UP_RIGHT() {
        return false;
    }

    @Override
    public boolean dPad_UP_LEFT() {
        return false;
    }

    @Override
    public boolean dPad_DOWN_RIGHT() {
        return false;
    }

    @Override
    public boolean dPad_DOWN_LEFT() {
        return false;
    }
}
