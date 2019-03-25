package RobotSim;

public class DriveBase extends RigidBody {
    public double width;
    public double height;
    public int numberOfWheels;

    RigidBody leftSide;
    RigidBody rightSide;
    RigidBody frontSide;
    RigidBody backSide;

    public DriveBase(double width, double length, double height, double thickness) {
        super(width, length, height);
        leftSide = new RigidBody(thickness,length, height);
        rightSide = new RigidBody(thickness, length, height);
        frontSide = new RigidBody(width, thickness, height);
        backSide = new RigidBody(width, thickness, height);
    }
}
