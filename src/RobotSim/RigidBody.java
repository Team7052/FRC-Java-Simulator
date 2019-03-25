package RobotSim;

import java.util.ArrayList;

public class RigidBody {
    public enum Geometry {
        prism, cylinder
    }
    private Point3D location;
    private double width, length, height;

    public ArrayList<RigidBodyConstraint> constraints;

    private ArrayList<RigidBody> children;

    private Orientation orientation;

    public RigidBody(double width, double length, double height) {
        this.width = width;
        this.length = length;
        this.height = height;

        children = new ArrayList<>();
    }

    public void add(RigidBody rigidBody) {

    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getLength() {
        return length;
    }

    public Point3D getLocation() {
        return location;
    }

    public double getMinX() {
        return location.x - width / 2;
    }
    public double getMaxX() {
        return location.x + width / 2;
    }
    public double getMinY() {
        return location.x - length / 2;
    }
    public double getMaxY() {
        return location.y - length / 2;
    }
    public double getMinZ() {
        return location.z;
    }
    public double getMaxZ() {
        return location.z + height;
    }

    public double getOrientation() {
        return orientation.getOrientation();
    }

    public void addAnchor(Anchor anchor, RigidBody toBody, Anchor toAnchor, double constant) {
        
    }
    public void addAnchor(Anchor anchor, RigidBody toBody, Anchor toAnchor, double constant, double multiplier) {

    }
}
