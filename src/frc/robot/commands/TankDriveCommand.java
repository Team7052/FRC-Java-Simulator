package frc.robot.commands;


import frc.robot.Robot;
import frc.robot.states.substates.ClimberState;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.DriveTrain;

public class TankDriveCommand {
    // declare subsystem variable
	DriveTrain driveTrain;
	double deadBand = 0.1;
	double kp = 0;//0.00001;
    
    public TankDriveCommand() {
        // get drive train subsystem instance
		driveTrain = DriveTrain.getInstance();
	}
	
    public void execute() {
		// your code goes here:
		double v = Robot.oi.axisTrigger_R2();
		double w = Robot.oi.axisTrigger_L2();
		double x = Robot.oi.axisRight_X();
		double y = Robot.oi.axisLeft_Y();
		double rightSpeed = 0;
		double leftSpeed = 0;
		double difference = 0;
		double theta = 0;

		double multiplier = 0.4;

		if (Robot.oi.button_L2()) multiplier = 0.85;
		if (Math.abs(y) < deadBand) y = 0;
		if (Math.abs(x) < deadBand) x = 0;
		if (y != 0) {
			theta = Math.atan(Math.abs(x / y));
		} 
		else {
			theta = 0;
		}
		double ratio = Math.cos(theta);	
		if (y == 0) ratio = -ratio;

		if (x >= 0) {
			leftSpeed = y;
			rightSpeed = y * ratio;
		}
		else if (x < 0) {
			rightSpeed = y;
			leftSpeed=y*ratio;
		}

		if(y==0){
			leftSpeed=x*0.8;
			rightSpeed = leftSpeed * ratio;
		}

		//System.out.println("encoder: " + driveTrain.getLeftDisplacement() + " " + driveTrain.getRightDisplacement());
		if (Climber.getInstance().getSuperState().getState() == ClimberState.safelyStowed) {
			if (Math.abs(y) > 0.2) rightSpeed += difference * kp;
			driveTrain.setLeftGroupSpeed(leftSpeed * multiplier);
			driveTrain.setRightGroupSpeed(rightSpeed * multiplier);
			Climber.getInstance().driveWheelsStop();
		}
		else {
			if (y < -0.2) {
				Climber.getInstance().driveWheelsForward();
				driveTrain.setLeftGroupSpeed(-0.2);
				driveTrain.setRightGroupSpeed(-0.2);
			}
			else {
				Climber.getInstance().driveWheelsStop();
				driveTrain.setLeftGroupSpeed(-0.0);
				driveTrain.setRightGroupSpeed(-0.0);
			}
		}
		
    }
}