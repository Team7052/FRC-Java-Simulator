package edu.wpi.first.wpilibj;

import main.Main;

public class RobotState {
    public static boolean isEnabled() {
        return Main.robotState != Main.State.DISABLED;
    }
    public static boolean isDisabled() {
        return !RobotState.isEnabled();
    }
    public static boolean isOperatorControl() {
        return Main.robotState == Main.State.TELEOP;
    }
    public static boolean isTest() {
        return Main.robotState == Main.State.TEST;
    }
    public static boolean isAutonomous() {
        return Main.robotState == Main.State.TEST;
    }
}
