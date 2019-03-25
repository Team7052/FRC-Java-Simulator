package main;

import frc.robot.motionProfiling.Point;
import util.HardwareConstants;
import util.HardwareWorld;

import javax.swing.*;
import java.awt.*;

public class BirdsEyeViewGraphicsEnginePanel extends JPanel {

    double height;
    double width;
    public BirdsEyeViewGraphicsEnginePanel(HardwareWorld world, int width, int height) {
        this.currentWorld = world;
        this.width = width;
        this.height = height;
    }

    private double padding = 10;

    HardwareWorld currentWorld;

    public void updateWorld(HardwareWorld world) {
        this.currentWorld = world;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        this.renderWorld(this.currentWorld, g2d);
    }

    public void renderWorld(HardwareWorld world, Graphics2D g2d) {
        /* set physics limits */
        // outer arm limit
        g2d.setColor(new Color(221,221,221));
        //ctx.fillRect(0,0, this.canvas.width, this.canvas.height);
        g2d.setPaint(new Color(225,225,225));
        //ctx.arc(physicsWorld.shoulderJoint.x * ratio, this.convertor.canvas.height - (physicsWorld.shoulderJoint.y * ratio) - this.ground, armOuterLimitRadius * ratio, 0, 360);
        // ctx.fill();

        this.drawGround(g2d);
        this.drawDriveBase(g2d, world);

        // drive base and arm
    }

    public double convertY(double value) {
        return this.height - value;
    }

    public double convertY(double value, double height) {
        System.out.println(this.height);
        return this.height - value - height;
    }

    public void drawGround(Graphics2D g2d) {
        g2d.setColor(new Color(255,0,0));
        g2d.setStroke(new BasicStroke(3.0f));
        g2d.drawRect((int) padding, (int) padding, (int) (this.width - padding * 2), (int) (this.height - padding * 4));
    }

    public void drawDriveBase(Graphics2D g2d, HardwareWorld physicsWorld) {
        g2d.setPaint(new Color(34,34,34));

        double radius = Math.sqrt(Math.pow(HardwareConstants.baseWidth / 2, 2) + Math.pow(HardwareConstants.baseLength / 2, 2));
        double alpha1 = Math.PI / 4 - physicsWorld.baseAngle;
        double alpha2 = Math.PI / 4 + physicsWorld.baseAngle;
        Point c = new Point(physicsWorld.baseX, physicsWorld.baseY);

        double x1 = (c.x - radius * Math.cos(alpha1)) * HardwareConstants.topDownRatio + width / 2 - padding;
        double y1 = this.convertY((c.y + radius * Math.sin(alpha1) + HardwareConstants.baseLength / 2) * HardwareConstants.topDownRatio) - padding * 3;

        double x2 = (c.x + radius * Math.cos(alpha2)) * HardwareConstants.topDownRatio + width / 2 - padding;
        double y2 = this.convertY((c.y + radius * Math.sin(alpha2) + HardwareConstants.baseLength / 2) * HardwareConstants.topDownRatio) - padding * 3;

        double x3 = (c.x + radius * Math.cos(alpha1)) * HardwareConstants.topDownRatio + width / 2 - padding;
        double y3 = this.convertY((c.y - radius * Math.sin(alpha1) + HardwareConstants.baseLength / 2) * HardwareConstants.topDownRatio) - padding * 3;

        double x4 = (c.x - radius * Math.cos(alpha2)) * HardwareConstants.topDownRatio + width / 2 - padding;
        double y4 = this.convertY((c.y - radius * Math.sin(alpha2) + HardwareConstants.baseLength / 2) * HardwareConstants.topDownRatio) - padding * 3;

        g2d.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
        g2d.drawLine((int) x2, (int) y2, (int) x3, (int) y3);
        g2d.drawLine((int) x3, (int) y3, (int) x4, (int) y4);
        g2d.drawLine((int) x4, (int) y4, (int) x1, (int) y1);
    }
}

