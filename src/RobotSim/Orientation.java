package RobotSim;

public class Orientation {
    private double angle;
    public Orientation(double initAngle) {
        this.angle = initAngle;
    }

    public void changeOrientation(double deltaAngle) {
        this.angle += deltaAngle;
    }

    public void setOrientation(double angle) {
        this.angle = angle;
    }

    public double getOrientation() {
        return angle;
    }
}
