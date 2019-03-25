package edu.wpi.first.wpilibj;

import RobotSim.HardwareManager;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;

public class Encoder {
    private int port;


    public Encoder(int valueA, int valueB) {
        // do nothing, ports don't matter in simulation
        port = valueA;
        HardwareManager.getInstance().addEncoder(valueA);
    }
    public Encoder(int valueA, int valueB, boolean inverted, EncodingType type) {
        port = valueA;
        HardwareManager.getInstance().addEncoder(valueA);
    }

    public int get() {
        return HardwareManager.getInstance().getEncoderCount(port);
    }

    public int getRate() {
        return 0;
    }

    public void reset() {
        HardwareManager.getInstance().setEncoderCount(port, 0);
    }
}
