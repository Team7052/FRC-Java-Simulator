package edu.wpi.first.wpilibj;

public class Timer {
    public static double getFPGATimestamp() {
        return (double) System.currentTimeMillis() / 1000.0;
    }
}
