package util;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.helpers.Pair;
import frc.robot.motionProfiling.Point;

import java.util.ArrayList;

public class HardwareWorld {
    /* all measurements in inches, degrees in radians */
    private static HardwareWorld instance;
    public static HardwareWorld getInstance() {
        if (instance == null) instance = new HardwareWorld();
        return instance;
    }
    public double baseX = 0;
    public double baseY = 0;
    public double baseZ = 0;
    public double baseAngle = 0;

    private double prevTimestamp = -1;

    public double theta1 = 20 / 180 * Math.PI;
    public double theta2 = 70 / 180 * Math.PI;

    public Point shoulderJoint = new Point(0,0);
    public Point elbowJoint = new Point(0,0);
    public Point wristJoint = new Point(0,0);
    public Point fingerTip = new Point(0,0);

    public double climberClawAngle = 0;
    public double climberLegHeight = 0;


    private HardwareWorld() {
        // initialize moving parts on the arm
    }

    public void updateWorld(double driveLeftSpeed, double driveRightSpeed, double climberClawAngle, double climberLegHeight, double theta1, double theta2, boolean degrees) {
        if (degrees) {
            this.theta1 = theta1 / 180 * Math.PI;
            this.theta2 = theta2 / 180 * Math.PI;
        }
        else {
            this.theta1 = theta1;
            this.theta2 = theta2;
        }
        this.baseX = 20;

        this.climberClawAngle = climberClawAngle;
        this.climberLegHeight = climberLegHeight;

        if (climberLegHeight > 0) baseZ = climberLegHeight;

        //forward kinematics for the arm joints
        this.shoulderJoint = new Point(this.baseX + HardwareConstants.baseLength - HardwareConstants.backToArm + HardwareConstants.thickness / 2, HardwareConstants.baseHeight + baseZ + HardwareConstants.armHeight);
        this.elbowJoint = new Point(this.shoulderJoint.x + HardwareConstants.upperArm * Math.sin(this.theta1), this.shoulderJoint.y - HardwareConstants.upperArm * Math.cos(this.theta1));
        this.wristJoint = new Point(this.elbowJoint.x + HardwareConstants.lowerArm * Math.sin(this.theta2), this.elbowJoint.y - HardwareConstants.lowerArm * Math.cos(this.theta2));
        this.fingerTip = new Point(this.wristJoint.x + HardwareConstants.hand, this.wristJoint.y);

        // forward kinematics for the drive base
        if (prevTimestamp == -1) {
            prevTimestamp = Timer.getFPGATimestamp();
            return;
        }

        double currentTimestamp = Timer.getFPGATimestamp();
        double dt = currentTimestamp - prevTimestamp;
        // calculate angular velocity

        double vel_left = driveLeftSpeed;
        double vel_right = driveRightSpeed;

        double radius = vel_left;

        if (vel_left != vel_right) radius = Math.abs(HardwareConstants.wheelDistance / 2 * (vel_right - vel_left) / (vel_left - vel_right));

        double w_robot = vel_left == vel_right ? 0 : vel_left / (radius + HardwareConstants.wheelDistance / 2);
        double deltaAngle = w_robot * dt;
        double magnitude = vel_left == vel_right ? vel_left : 2 * radius * Math.sin(deltaAngle / 2) * 100;
        double absoluteOrientation = baseAngle + deltaAngle;

        double deltaX = magnitude * Math.cos(Math.PI / 2 - deltaAngle / 2 - absoluteOrientation);
        double deltaY = magnitude * Math.sin(Math.PI / 2 - deltaAngle / 2 - absoluteOrientation);
        baseX += deltaX;
        baseY += deltaY;
        baseAngle = absoluteOrientation;

        this.prevTimestamp = currentTimestamp;
    }

    private ArrayList<Point> _interpolate(Point currentPos, Point newPos) {
        double delta_h = newPos.y - currentPos.y;
        double delta_l = newPos.x - currentPos.x;
        ArrayList<Point> points = new ArrayList<>();
        int numberOfLines = 20;
        for (int i = 0; i <= numberOfLines; i++) {
            double h = currentPos.y + delta_h * (i / numberOfLines);
            double l = currentPos.x + delta_l * (i / numberOfLines);
            points.add(new Point(l, h));
        }
        return points;
    }

    public Pair<Double> inverseKinematics(double delta_l, double delta_h) {
        // get the delta h and delta l
        double alpha = Math.atan(delta_h / delta_l);
        if (delta_l < 0) alpha += Math.PI;
        double p = Math.sqrt(delta_l * delta_l + delta_h * delta_h);
        //console.log(delta_l + " " + delta_h);
        double d1 = HardwareConstants.upperArm, d2 = HardwareConstants.lowerArm;

        double theta1 = Math.asin((-Math.pow(d2, 2) + Math.pow(d1, 2) + delta_l * delta_l + delta_h * delta_h) / (2 * d1 * p)) - alpha;
        double theta2 = Math.PI - Math.asin((-Math.pow(d1, 2) + Math.pow(d2, 2) + delta_l * delta_l + delta_h * delta_h) / (2 * d2 * p)) - alpha;
        //console.log(theta1 + " " + theta2);
        // console.log((this.wristJoint.x - this.shoulderJoint.x) + " " + (this.lowerArm + this.upperArm));
        return new Pair<Double>(theta1, theta2);
    }

    private boolean isInfinity(double value) {
        return value == Double.POSITIVE_INFINITY || value == Double.NEGATIVE_INFINITY;
    }

    public Point getLengthAndHeight() {
        double current_l = this.wristJoint.x - this.shoulderJoint.x;
        double current_h = this.shoulderJoint.y - this.wristJoint.y;
        return new Point(current_l, current_h);
    }

    public Pair<Double> displaceLengthAndHeight(double delta_l, double delta_h) {
        Point currentDistance = this.getLengthAndHeight();
        double new_l = currentDistance.x + delta_l;
        double new_h = currentDistance.y - delta_h;

        return inverseKinematics(new_l, new_h);
    }

    public ArrayList<Pair<Double>> displaceSequentially(ArrayList<Pair<Double>> deltas) {
        ArrayList<Pair<Double>> angleSequence = new ArrayList<>();
        double counting_l = this.get_l();
        double counting_h = this.get_h();
        for (int i = 0; i < deltas.size(); i++) {
            counting_l += deltas.get(i).a;
            counting_h -= deltas.get(i).b;
            angleSequence.add(inverseKinematics(counting_l, counting_h));
        }
        return angleSequence;
    }

    public double get_l() {
        return this.wristJoint.x - this.shoulderJoint.x;
    }

    public double get_h() {
        return this.shoulderJoint.y - this.wristJoint.y;
    }


    public ArrayList<Pair<Double>> generateTrajectory(double x, double y) {
        // linear interpolate between 
        double new_l = x - this.shoulderJoint.x - HardwareConstants.hand;
        double new_h = this.shoulderJoint.y - y;

        double current_l = this.wristJoint.x - this.shoulderJoint.x;
        double current_h = this.shoulderJoint.y - this.wristJoint.y;

        ArrayList<Point> length_height_trajectory = this._interpolate(new Point(current_l, current_h), new Point(new_l, new_h));
        ArrayList<Pair<Double>> displacementProfiles = this._generateDisplacementProfiles(length_height_trajectory);
        return displacementProfiles;
    }

    private ArrayList<Pair<Double>> _generateDisplacementProfiles(ArrayList<Point> trajectory) {
        ArrayList<Pair<Double>> profiles = new ArrayList<>();

        for (Point point: trajectory) {
            Pair<Double> anglePair = this.inverseKinematics(point.x, point.y);
            if (!isInfinity(anglePair.a) && !isInfinity(anglePair.b)) {
                profiles.add(new Pair<Double>(anglePair.a, anglePair.b));
            }
        }
        return profiles;
    }

}
