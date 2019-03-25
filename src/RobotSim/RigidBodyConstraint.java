package RobotSim;

public class RigidBodyConstraint {
    public enum Type {
        joint, fixed
    }
    Type constraintType;
    RigidBody independentBody;
    RigidBody dependentBody;

    Point3D dependentBodyRelativeLocation;
    Point3D independentBodyRelativeLocation;

    public RigidBodyConstraint(RigidBody dependentBody, RigidBody independentBody, Point3D dependentBodyRelativeLocation, Point3D independentBodyRelativeLocation, Type constraintType) {
        this.dependentBody = dependentBody;
        this.independentBody = independentBody;
        this.dependentBodyRelativeLocation = dependentBodyRelativeLocation;
        this.independentBodyRelativeLocation = independentBodyRelativeLocation;

        this.constraintType = constraintType;
    }
}
