package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.motionProfiling.MotionTriplet;
import frc.robot.sequencing.Sequence;
import frc.robot.states.ClimberSuperStateDelegate;
import frc.robot.subsystems.Climber;

public class ClawCommand implements ClimberSuperStateDelegate {
    private Climber climber;
    Sequence<MotionTriplet> sequence;

    public ClawCommand() {
        climber = Climber.getInstance();
        sequence = new Sequence<>();
    }
    double prev = 0;
    public void execute() {
        double timestamp = Timer.getFPGATimestamp();
        if (prev == 0) prev = timestamp;
        if (!sequence.hasBegan()) sequence.start(timestamp);
        MotionTriplet triplet = sequence.update(timestamp);
        if (sequence.isRunning() && triplet != null) {
            // a = position
            double position = triplet.a;
            climber.getClaw().setDegrees(position / Math.PI * 180);
            //System.out.println(position / Math.PI * 180 + " " + climber.getClaw().getDegrees() + " " + climber.getClaw().getPercentOutput());
        }
        if (sequence.isFinished(timestamp)) {
            MotionTriplet lastTriplet = sequence.getLastUpdate();
            if (lastTriplet != null) {
                double position = lastTriplet.a;
                climber.getClaw().setDegrees(position / Math.PI * 180);
                //System.out.println(position / Math.PI * 180 + " " + climber.getClaw().getDegrees() + " " + climber.getClaw().getPercentOutput());
            }   
        }
        prev = timestamp;
    }

    @Override
    public void updateSequence(Sequence<MotionTriplet> sequence) {
        this.sequence = sequence;
    }
}
