/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.ArrayList;
import java.util.Arrays;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import frc.joysticks.*;
import frc.robot.commands.FollowSplineCommand;
import frc.robot.motionProfiling.Point;
import frc.robot.states.ArmSuperState;
import frc.robot.states.substates.ArmState;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.Climber;
import frc.robot.util.loops.Loop;
import frc.robot.util.loops.Looper;
import util.HardwareWorld;


public class Robot extends TimedRobot {

    public static OI oi;

    Looper globalLooper;
    Looper teleopLooper;
    Looper autoLooper;
    LoopsManager loopsManager;
    FollowSplineCommand autoCommand;
    @Override
    public void robotInit() {
        // change Logitech to newly extended class
        teleopLooper = new Looper();
        autoLooper = new Looper();
        globalLooper = new Looper();
        oi = new Logitech(0);

        loopsManager = new LoopsManager();

        teleopLooper.register(loopsManager.hardwareLoop);
        teleopLooper.register(loopsManager.stateManagerLoop);

        globalLooper.register(loopsManager.networkLoop);
        globalLooper.register(loopsManager.physicsWorldLoop);

        Point[] path = {
                new Point (0, 0), new Point (27,56.5), new Point (45, 81.5), new Point (60, 89.5)
                //new Point (0,0), new Point (27,56.5)
        };
        autoCommand = new FollowSplineCommand(new ArrayList<>(Arrays.asList(path)), 3.5);

        autoLooper.register(loopsManager.autoLoop);


    }
    @Override
    public void robotPeriodic() {
    }

    @Override
    public void disabledInit() {
        this.teleopLooper.stop();
        this.autoLooper.stop();
    }

    @Override
    public void disabledPeriodic() {
        this.teleopLooper.stop();
        this.autoLooper.stop();
    }

    @Override
    public void autonomousInit() {
        autoLooper.start();
    }

    @Override
    public void autonomousPeriodic() {
        autoLooper.start();
    }

    @Override
    public void teleopInit() {
        teleopLooper.start();
    }

    @Override
    public void teleopPeriodic() {
        HardwareWorld.getInstance().updateWorld(0,0, Climber.getInstance().getClaw().getDegrees() / 180 * Math.PI, Climber.getInstance().getRack().getLinearPosition(), ArmSubsystem.getInstance().getDegrees(ArmSubsystem.Motor.SHOULDER_JOINT), ArmSubsystem.getInstance().getAbsoluteDegrees(ArmSubsystem.Motor.ELBOW_JOINT), true);
        teleopLooper.start();
    }

    @Override
    public void testInit() {
    }

    @Override
    public void testPeriodic() {
    }
}
