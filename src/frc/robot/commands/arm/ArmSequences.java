package frc.robot.commands.arm;

import frc.robot.util.physics.PhysicsConstants;
import frc.robot.util.physics.PhysicsWorld;

import frc.robot.helpers.Pair;
import frc.robot.helpers.Triplet;
import frc.robot.motionProfiling.MotionTriplet;
import frc.robot.motionProfiling.FilterOutputModifier;
import frc.robot.motionProfiling.TrapezoidShape;
import frc.robot.motionProfiling.TrapezoidalFunctions;
import frc.robot.motionProfiling.TrapezoidalProfileFilter;
import frc.robot.sequencing.Sequence;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.ArmSubsystem.Motor;

public class ArmSequences {
    public static final double shoulderMaxVelocity = Math.PI * 2 / 5;
    public static final double shoulderMaxAcceleration = Math.PI * 2 / 3;

    public static final double elbowMaxVelocity = Math.PI;
    public static final double elbowMaxAcceleration = Math.PI;
    
    public static Triplet<Sequence<MotionTriplet>> homeSequence() {
        return toSequence(generateProfiles(radians(25), radians(180), radians(180)));
    }
    public static Triplet<Sequence<MotionTriplet>> intakeHatchSequence() {
        return toSequence(setDistances(16, 20, radians(180)));
    }
    public static Triplet<Sequence<MotionTriplet>> lowerRocketHatchSequence() {
        return toSequence(setDistances(16, 26, radians(180)));
    }
    public static Triplet<Sequence<MotionTriplet>> midRocketHatchSequence() {
        return toSequence(setDistances(14, 52, radians(180)));
    }
    public static Triplet<Sequence<MotionTriplet>> highRocketHatchSequence() {
        return toSequence(setDistances(2, 78, radians(180)));
    }

    public static Triplet<Sequence<MotionTriplet>> raiseArmSequence() {
        // get current displacements
        Pair<Double> currentDisplacements = PhysicsWorld.getInstance().solveArmKinematics();
        double normalized_l = currentDisplacements.a - PhysicsConstants.backToArm - PhysicsConstants.thickness / 2 + PhysicsConstants.hand;
        double normalized_h = PhysicsConstants.armHeight + PhysicsConstants.baseHeight - currentDisplacements.b;
        System.out.println("Raise the arm");
        return toSequence(setDistances(normalized_l, normalized_h + 3, radians(180)));
    }
    public static Triplet<Sequence<MotionTriplet>> lowerArmSequence() {
        // get current displacements
        Pair<Double> currentDisplacements = PhysicsWorld.getInstance().solveArmKinematics();
        double normalized_l = currentDisplacements.a - PhysicsConstants.backToArm - PhysicsConstants.thickness / 2 + PhysicsConstants.hand;
        double normalized_h = PhysicsConstants.armHeight + PhysicsConstants.baseHeight - currentDisplacements.b;
        System.out.println("Lower the arm");
        return toSequence(setDistances(normalized_l, normalized_h - 2, radians(180)));
    }
    public static Triplet<Sequence<MotionTriplet>> intakeCargoSequence() {
        return toSequence(generateProfiles(radians(40),  radians(160), radians(270)));
    }
    public static Triplet<Sequence<MotionTriplet>> lowRocketCargoSequence() {
        return toSequence(generateProfiles(radians(60), radians(70), radians(270)));
    }
    public static Triplet<Sequence<MotionTriplet>> midRocketCargoSequence() {
        return toSequence(generateProfiles(radians(60), radians(70), radians(270)));
    }
    private static Triplet<TrapezoidalProfileFilter> setDistances(double l, double h, double wristRadians) {
        Pair<Double> angles = PhysicsWorld.getInstance().armInverseKinematics(l + PhysicsConstants.backToArm + PhysicsConstants.thickness / 2 - PhysicsConstants.hand, PhysicsConstants.armHeight + PhysicsConstants.baseHeight - h);
        return generateProfiles(angles.a, angles.b, wristRadians);
    }

    private static double radians(double degrees) {
        return degrees / 180.0 * Math.PI;
    }

    private static Triplet<Sequence<MotionTriplet>> toSequence(Triplet<TrapezoidalProfileFilter> triplet) {
        Triplet<Sequence<MotionTriplet>> sequence = new Triplet<>(new Sequence<>(), new Sequence<>(), new Sequence<>());
        sequence.a.addStep(triplet.a);
        sequence.b.addStep(triplet.b);
        sequence.c.addStep(triplet.c);
        return sequence;
    }

    private static Triplet<TrapezoidalProfileFilter> generateProfiles(double shoulderAngle, double elbowAngle, double wristAngle) {
        double initShoulder = ArmSubsystem.getInstance().getDegrees(Motor.SHOULDER_JOINT) / 180 * Math.PI;
        double endShoulder = shoulderAngle;
        double initElbow = ArmSubsystem.getInstance().getAbsoluteDegrees(Motor.ELBOW_JOINT) / 180 * Math.PI;
        double endElbow =  elbowAngle;
        double initWrist = ArmSubsystem.getInstance().getAbsoluteDegrees(Motor.WRIST_JOINT) / 180.0 * Math.PI;
        double endWrist = wristAngle;

        return generateProfiles(initShoulder, initElbow, initWrist, endShoulder, endElbow, endWrist);
    }
    private static Triplet<TrapezoidalProfileFilter> generateProfiles(double initShoulder, double initElbow, double initWrist, double endShoulder, double endElbow, double endWrist) {
        // trapezoidal motion profiling.
        FilterOutputModifier<MotionTriplet> shoulderFilter = (dt, endTime, triplet) -> {
            return new MotionTriplet(triplet.a + initShoulder, triplet.b, triplet.c);
        };


        TrapezoidShape initShoulderShape = TrapezoidalFunctions.generateTrapezoidShape(initShoulder, endShoulder, shoulderMaxVelocity, shoulderMaxAcceleration);
        TrapezoidShape initElbowShape = TrapezoidalFunctions.generateTrapezoidShape(initElbow, endElbow, elbowMaxVelocity, elbowMaxAcceleration);
        Pair<TrapezoidShape> newShapes = TrapezoidalFunctions.syncTrapezoidShapes(initShoulderShape, initElbowShape);
        // generate nonsensical elbow shape at beginning
        TrapezoidalProfileFilter shoulderProfile = new TrapezoidalProfileFilter(newShapes.a, shoulderFilter);


        FilterOutputModifier<MotionTriplet> elbowFilter = (dt, endTime, triplet) -> {
            double absoluteElbowPosition = triplet.getPosition() + initElbow;
            double absoluteShoulderPosition = shoulderProfile.getUpdateForDeltaTime(dt).getPosition();
            double relativeElbow = radiansElbowRelativeToShoulder(absoluteElbowPosition, absoluteShoulderPosition);
            return new MotionTriplet(relativeElbow, triplet.getVelocity(), triplet.getAcceleration());
        };
        TrapezoidalProfileFilter elbowProfile = new TrapezoidalProfileFilter(newShapes.b, elbowFilter);

        // interpolate wrist points
        FilterOutputModifier<MotionTriplet> wristFilter = (dt, endTime, triplet) -> {
            double absoluteWristPosition = initWrist + (endWrist - initWrist) * (dt / endTime);
            double absoluteShoulderPosition = shoulderProfile.getUpdateForDeltaTime(dt).getPosition();
            double relativeElbowPosition = elbowProfile.getUpdateForDeltaTime(dt).getPosition();
            double absoluteElbowPosition = relativeElbowToAbsolute(relativeElbowPosition, absoluteShoulderPosition);
            double relativeWristPosition = radiansWristRelativeToElbow(absoluteWristPosition, absoluteElbowPosition);
            return new MotionTriplet(relativeWristPosition, 0.0, 0.0);
        };
        // take elbow absolute angles and transform the wrist to match those angles
        TrapezoidalProfileFilter wristProfile = new TrapezoidalProfileFilter(wristFilter, () -> newShapes.a.totalTime());

        return new Triplet<>(shoulderProfile, elbowProfile, wristProfile);
    }

    private static double radiansElbowRelativeToShoulder(double absoluteRadians, double shoulderRadians) {
        //System.out.println("Radians: " + absoluteRadians + " " + shoulderRadians);
        return absoluteRadians - shoulderRadians + ArmSubsystem.getInstance().getHomeDegrees(Motor.SHOULDER_JOINT) / 180.0 * Math.PI;
    }

    private static double radiansWristRelativeToElbow(double absoluteRadians, double elbowRadiansAbsolute) {
        //System.out.println("Radians: " + absoluteRadians + " " + shoulderRadians);
        return absoluteRadians - elbowRadiansAbsolute + ArmSubsystem.getInstance().getHomeDegrees(Motor.ELBOW_JOINT) / 180.0 * Math.PI;
    }

    private static double relativeElbowToAbsolute(double relativeElbow, double absoluteShoulder) {
        return relativeElbow + absoluteShoulder - ArmSubsystem.getInstance().getHomeDegrees(Motor.SHOULDER_JOINT);
    }
}