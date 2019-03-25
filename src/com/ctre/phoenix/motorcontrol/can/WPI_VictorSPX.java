package com.ctre.phoenix.motorcontrol.can;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

public class WPI_VictorSPX {

    private int quadraturePosition = 0;

    private int prevQuadraturePosition = 0;
    private double prevQuadraturePositionTime = 0;

    private double k_p = 0.0;
    private double k_i = 0.0;
    private double k_d = 0.0;

    private double percentOutput = 0.0;

    private boolean isInverted = false;

    private final double kPercentToDeltaPositionRatio = 200;

    private boolean inPositionControl = false;

    private PIDController pidController;

    double nominalOutputForward = 0.0;
    double nominalReverseForward = 0.0;
    double peakOutputForward = 1.0;
    double peakOutputReverse = -1.0;

    public WPI_VictorSPX(int can_id) {
        pidController = new PIDController(k_p, k_i, k_d);

    }
    public void configSelectedFeedbackSensor(FeedbackDevice feedbackDevice, int slodIdX, int timeoutMs) {

    }

    public void setSelectedSensorPosition(int position, int idx, int timeoutMs) {
        this.prevQuadraturePosition = quadraturePosition;
        this.prevQuadraturePositionTime = (double) System.currentTimeMillis() / 1000.0;
        this.quadraturePosition = position;
    }

    public double getMotorOutputPercent() {
        return this.percentOutput;
    }
    public double getOutputCurrent() {
        return 0.0;
    }
    public double getMotorOutputVoltage() {
        return 0.0;
    }

    public int getSelectedSensorPosition(int slotIdx) {
        return this.quadraturePosition;
    }


    public void set(ControlMode mode, double value) {
        this.inPositionControl = false;
        double output = value;
        if (output > 0 && output > this.peakOutputForward) output = this.peakOutputForward;
        if (output < 0 && output < this.peakOutputReverse) output = this.peakOutputReverse;
        if (this.isInverted) output *= -1;
        this.percentOutput = output;
        this.quadraturePosition += output * kPercentToDeltaPositionRatio;
    }

    public void setInverted(boolean inverted) {
        this.isInverted = inverted;
    }

    public void configNominalOutputForward(double value) {
        this.nominalOutputForward = value;
    }
    public void configPeakOutputForward(double value) {
        this.peakOutputForward = value;
    }

    public void configNominalOutputReverse(double value) {
        this.nominalReverseForward = value;
    }
    public void configPeakOutputReverse(double value) {
        this.peakOutputReverse = value;
    }

    public void configNominalOutputForward(double value, int timeoutMs) {
        this.nominalOutputForward = value;
    }
    public void configPeakOutputForward(double value, int timeoutMs) {
        this.peakOutputForward = value;
    }

    public void configNominalOutputReverse(double value, int timeoutMs) {
        this.nominalReverseForward = value;
    }
    public void configPeakOutputReverse(double value, int timeoutMs) {
        this.peakOutputReverse = value;
    }

    public void setIntegralAccumulator(int value) {
        this.pidController.setIntegral(value);
    }

    public double getSelectedSensorVelocity(int slotIdX) {
        return (this.quadraturePosition - this.prevQuadraturePosition) / (((double) System.currentTimeMillis() / 1000) - this.prevQuadraturePositionTime);
    }

    public boolean getInverted() {
        return false;
    }
    public void setSensorPhase(boolean PhaseSensor) {
    }

    public void config_kP(int slotIdX, double value, int timeoutMs) {
        this.k_p = value;
        this.pidController.setGains(k_p, k_i, k_d);
    }
    public void config_kI(int slotIdX, double value, int timeoutMs) {
        this.k_i = value;
        this.pidController.setGains(k_p, k_i, k_d);
    }
    public void config_kD(int slotIdX, double value, int timeoutMs) {
        this.k_d = value;
        this.pidController.setGains(k_p, k_i, k_d);
    }


}
