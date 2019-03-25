package main;

import driverstation.DriverStationDelegate;
import driverstation.DriverStationManager;
import driverstation.states.*;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.commands.arm.ArmControllerCommand;
import frc.robot.subsystems.ArmSubsystem;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main implements Runnable, StateManager.StateManagerDelegate {
    public enum State {
        TELEOP, AUTO, TEST, PRACTICE, DISABLED
    }
    public static State robotState = State.DISABLED;
    public static OI oi;
    Thread t;
    ScheduledExecutorService timer;

    UserInterfaceManager uiManager;

    Robot robot;



    public Main() {
        uiManager = new UserInterfaceManager();
        robot = new Robot();
        robot.robotInit();
    }

    public void start() {
        if (t == null) {
            t = new Thread(this,"thread");
            timer = Executors.newSingleThreadScheduledExecutor();
            timer.scheduleAtFixedRate(this, 0, 20, TimeUnit.MILLISECONDS);
            t.start();
        }
    }

    public static void main(String args[]) {
        Main main = new Main();
        main.start();
    }

    @Override
    public void run() {
        robot.robotPeriodic();
        if (StateManager.getDSRobotEnabledState() == DSRobotEnabledState.disabled) {
            robot.disabledPeriodic();
            return;
        }
        if (StateManager.getDSRobotState() == DSRobotState.teleop) {
            robot.teleopPeriodic();
        }
        else if (StateManager.getDSRobotState() == DSRobotState.autonomous) {
            robot.autonomousPeriodic();
        }
        else if (StateManager.getDSRobotState() == DSRobotState.test) {
            robot.testPeriodic();
        }

        uiManager.updateViews();
    }

    @Override
    public void stateManagerWillUpdate(DSState newState) {

    }

    @Override
    public void stateManagerWillUpdate(DSRobotState newState) {
    }

    @Override
    public void stateManagerWillUpdate(DSRobotEnabledState newState) {
        if (newState == DSRobotEnabledState.enabled) {
            DSRobotState robotState = StateManager.getDSRobotState();
            if (robotState == DSRobotState.teleop) robot.teleopInit();
            else if (robotState == DSRobotState.autonomous) robot.autonomousInit();
            else if (robotState == DSRobotState.test) robot.testInit();
        }
        else {
            robot.disabledInit();
        }

    }

    @Override
    public void stateManagerWillUpdate(DSUIState newState) {

    }

    @Override
    public void stateManagerDidUpdate(DSState oldState) {

    }

    @Override
    public void stateManagerDidUpdate(DSRobotState oldState) {

    }

    @Override
    public void stateManagerDidUpdate(DSRobotEnabledState oldState) {

    }

    @Override
    public void stateManagerDidUpdate(DSUIState newState) {

    }
}
