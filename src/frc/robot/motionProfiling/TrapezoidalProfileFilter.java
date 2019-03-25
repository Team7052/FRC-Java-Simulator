package frc.robot.motionProfiling;

import frc.robot.sequencing.DynamicEndTime;
import frc.robot.sequencing.Step;
import frc.robot.sequencing.StepState;

public class TrapezoidalProfileFilter extends Step<MotionTriplet> {
    private FilterOutputModifier<MotionTriplet> modifier;
    private TrapezoidShape velocityShape;
    public TrapezoidalProfileFilter(TrapezoidShape velocityShape, FilterOutputModifier<MotionTriplet> modifier){
        super();
        this.totalRunningTime = () -> velocityShape.isValidShape() ? velocityShape.totalTime() : 0.0;
        this.modifier = modifier;
        if (velocityShape.isValidShape()) this.velocityShape = velocityShape;
    }
    public TrapezoidalProfileFilter(TrapezoidShape velocityShape){
        super();
        this.totalRunningTime = () -> velocityShape.isValidShape() ? velocityShape.totalTime() : 0.0;
        if (velocityShape.isValidShape()) this.velocityShape = velocityShape;
    }

    public TrapezoidalProfileFilter(FilterOutputModifier<MotionTriplet> modifier, DynamicEndTime endTime) {
        super(endTime);
        this.modifier = modifier;
    }

    public double index = 0;
    public MotionTriplet update(double timeStamp) {
        if (state == StepState.RUNNING) {
            if (this.totalRunningTime.get() == 0) return null;
            if (timeStamp - startTime > this.totalRunningTime.get()) {
                this.endStep();
                return null;
            }
            double dt = timeStamp - this.startTime;
            return this.getUpdateForDeltaTime(dt);
        }
        return null;
    }

    @Override
    public MotionTriplet getUpdateForDeltaTime(double dt) {
            // calculate on the trapezoidal shape the current velocity
            if (this.velocityShape == null) return modifier.transform(dt, this.totalRunningTime.get(), null);
            double velocity = this.velocityShape.getVelocityForTime(dt);
            double position = this.velocityShape.getIntegralForTime(dt);
            double acceleration = this.velocityShape.getDerivativeForTime(dt);
            MotionTriplet currentTriplet = new MotionTriplet(position, velocity, acceleration);
            if (modifier == null) return currentTriplet;
            
            return modifier.transform(dt, this.totalRunningTime.get(), currentTriplet);
    }

    @Override
    public MotionTriplet getLastUpdate() {
        return this.getUpdateForDeltaTime(this.totalRunningTime.get());
    }
}