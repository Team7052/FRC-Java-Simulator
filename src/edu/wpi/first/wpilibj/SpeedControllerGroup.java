package edu.wpi.first.wpilibj;

import RobotSim.HardwareManager;

public class SpeedControllerGroup {
    private double speed = 0.0;
    private Spark motor1, motor2;
    public SpeedControllerGroup(Spark motor1, Spark motor2) {
        this.motor1 = motor1;
        this.motor2 = motor2;
        HardwareManager.getInstance().addSpeedGroup(motor1.pwm, motor2.pwm);
    }
    public void setInverted(boolean inverted) {

    }
    public void set(double speed) {
        this.speed = speed;
        HardwareManager.getInstance().setSpeedGroup(motor1.pwm, motor2.pwm, speed);
    }

    public double get() {
        return this.speed;
    }
}