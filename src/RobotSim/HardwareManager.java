package RobotSim;

import java.util.HashMap;
import java.util.Map;

public class HardwareManager {
    private static HardwareManager instance;
    public static HardwareManager getInstance() {
        if (instance == null) instance = new HardwareManager();
        return instance;
    }

    Map<Integer, Counter> encoders;
    Map<Integer, Integer> speedController;

    private HardwareManager() {
        encoders = new HashMap<>();
        speedController = new HashMap<>();
    }

    public void addEncoder(int port) {
        this.encoders.put(port, new Counter(0));
    }
    public void setEncoderCount(int port, double count) {
        this.encoders.get(port).setCount(count);
    }
    public void setEncoderCountDelta(int port, double delta) {
        this.encoders.get(port).setCount(this.encoders.get(port).getCount() + delta);
    }

    public void addSpeedGroup(int pwm1, int pwm2) {
        System.out.println(pwm1 + " " + pwm2);
        if (pwm1 == 0 || pwm1 == 1) {
            this.speedController.put(pwm1, 2);
        }
        else if (pwm1 == 2 || pwm1 == 3) {
            this.speedController.put(pwm1, 0);
        }
    }

    public void setSpeedGroup(int pwm1, int pwm2, double speed) {
        this.setEncoderCountDelta(speedController.get(pwm1), 1.0 * speed);
    }

    public int getEncoderCount(int port) {
        return (int) encoders.get(port).getCount();
    }
}
