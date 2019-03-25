package edu.wpi.first.wpilibj;

import edu.wpi.first.networktables.NetworkTableInstance;

public class TimedRobot {
    public void robotInit() {
        NetworkTableInstance instance = NetworkTableInstance.getDefault();
        instance.startServer();
    }

    public void robotPeriodic() {}
    public void teleopInit() {}
    public void teleopPeriodic() {}
    public void autonomousInit() {}
    public void autonomousPeriodic() {}
    public void testInit() {}
    public void testPeriodic() {}
    public void disabledInit() {}
    public void disabledPeriodic() {}
}
