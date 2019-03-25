package main;

import util.HardwareConstants;
import util.HardwareWorld;

import javax.swing.*;
import java.awt.*;

public class SideViewGraphicsEnginePanel extends JPanel {

    double height;
    double width;
    public SideViewGraphicsEnginePanel(HardwareWorld world, int width, int height) {
        this.currentWorld = world;
        this.width = width;
        this.height = height;
    }

    HardwareWorld currentWorld;

    public void updateWorld(HardwareWorld world) {
        this.currentWorld = world;
    }

    @Override
    public void paintComponent(Graphics g) {
        this.width = this.getWidth();
        this.height = this.getHeight();
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
        this.drawArm(g2d, world);

        this.drawClimber(g2d, world);

        // drive base and arm
    
        /* motion path points
        if (this.motionPathPoints.length > 0) {
            /*let originPoint = this.transformPoint({x: this.motionPathPoints[0].x * ratio, y: this.motionPathPoints[0].y * ratio});
            ctx.moveTo(originPoint.x, originPoint.y);
            for (let point of this.motionPathPoints) {
                let newPoint = this.transformPoint({x: point.x * ratio, y: point.y * ratio});
                ctx.lineTo(newPoint.x, newPoint.y);
            }
            let last = this.motionPathPoints[this.motionPathPoints.length - 1];
            let lastTransformed = this.transformPoint({x: last.x * ratio, y: last.y * ratio});
            ctx.lineTo(lastTransformed.x, lastTransformed.y);
            ctx.strokeStyle="#DADADA";
            ctx.stroke();
        }*/

    }

    public double convertY(double value) {
        return this.convertY(value, 0, true);
    }


    public double convertY(double value, double height, boolean withGround) {
        if (withGround) return this.height - value - HardwareConstants.ground * HardwareConstants.ratio - height;
        return this.height - value - height;
    }

    public void drawGround(Graphics2D g2d) {
        g2d.setColor(new Color(0,0,0));
        g2d.setStroke(new BasicStroke(3.0f));
        double convertedGround = this.height - HardwareConstants.ground * HardwareConstants.ratio;
        g2d.drawLine(0, (int) convertedGround, (int) this.width, (int) convertedGround);
    }

    public void drawDriveBase(Graphics2D g2d, HardwareWorld physicsWorld) {
        g2d.setPaint(new Color(34,34,34));
        double height = (HardwareConstants.baseHeight - HardwareConstants.baseOffset) * HardwareConstants.ratio;
        double width = HardwareConstants.baseLength * HardwareConstants.ratio;
        double baseX = physicsWorld.baseX * HardwareConstants.ratio;
        double baseY = this.convertY((HardwareConstants.baseOffset + physicsWorld.baseZ) * HardwareConstants.ratio, height, true);
        g2d.fillRect((int) baseX, (int) baseY, (int) width, (int) height);
    }

    public void drawArm(Graphics2D g2d, HardwareWorld physicsWorld) {
        double width = HardwareConstants.thickness * HardwareConstants.ratio;
        double height = HardwareConstants.armHeight * HardwareConstants.ratio;
        double armX = (HardwareConstants.baseLength - HardwareConstants.backToArm + physicsWorld.baseX) * HardwareConstants.ratio;
        double armY = this.convertY((HardwareConstants.baseHeight + physicsWorld.baseZ) * HardwareConstants.ratio, height, true);
        g2d.setPaint(new Color(170,170,170));
        g2d.fillRect((int) armX, (int) armY, (int) width, (int) height);
        g2d.setPaint(new Color(102,102,102));
        // upper arm
        int x0 = (int) ((physicsWorld.shoulderJoint.x - HardwareConstants.thickness * 0.5 * Math.cos(physicsWorld.theta1)) * HardwareConstants.ratio);
        int y0 = (int) this.convertY((physicsWorld.shoulderJoint.y - HardwareConstants.thickness * 0.5 * Math.sin(physicsWorld.theta1)) * HardwareConstants.ratio);
        int x1 = (int) ((physicsWorld.shoulderJoint.x + HardwareConstants.thickness * 0.5 * Math.cos(physicsWorld.theta1)) * HardwareConstants.ratio);
        int y1 = (int) this.convertY((physicsWorld.shoulderJoint.y + HardwareConstants.thickness * 0.5 * Math.sin(physicsWorld.theta1)) * HardwareConstants.ratio);
        int x2 = (int) ((physicsWorld.elbowJoint.x + HardwareConstants.thickness * 0.5 * Math.cos(physicsWorld.theta1)) * HardwareConstants.ratio);
        int y2 = (int) this.convertY((physicsWorld.elbowJoint.y + HardwareConstants.thickness * 0.5 * Math.sin(physicsWorld.theta1)) * HardwareConstants.ratio);
        int x3 = (int) ((physicsWorld.elbowJoint.x - HardwareConstants.thickness * 0.5 * Math.cos(physicsWorld.theta1)) * HardwareConstants.ratio);
        int y3 = (int) this.convertY((physicsWorld.elbowJoint.y - HardwareConstants.thickness * 0.5 * Math.sin(physicsWorld.theta1)) * HardwareConstants.ratio);
        int x4 = (int) ((physicsWorld.shoulderJoint.x - HardwareConstants.thickness * 0.5 * Math.cos(physicsWorld.theta1)) * HardwareConstants.ratio);
        int y4 = (int) this.convertY((physicsWorld.shoulderJoint.y - HardwareConstants.thickness * 0.5 * Math.sin(physicsWorld.theta1)) * HardwareConstants.ratio);
        g2d.drawLine(x0, y0, x1, y1);
        g2d.drawLine(x1, y1, x2, y2);
        g2d.drawLine(x2, y2, x3, y3);
        g2d.drawLine(x3, y3, x4, y4);

        // lower arm
        g2d.setPaint(new Color(255,0,0));
        int x5 = (int) ((physicsWorld.elbowJoint.x - HardwareConstants.thickness * 0.5 * Math.cos(physicsWorld.theta2)) *HardwareConstants. ratio);
        int y5 = (int) this.convertY((physicsWorld.elbowJoint.y - HardwareConstants.thickness * 0.5 * Math.sin(physicsWorld.theta2)) * HardwareConstants.ratio);
        int x6 = (int) ((physicsWorld.elbowJoint.x + HardwareConstants.thickness * 0.5 * Math.cos(physicsWorld.theta2)) * HardwareConstants.ratio);
        int y6 = (int) this.convertY((physicsWorld.elbowJoint.y + HardwareConstants.thickness * 0.5 * Math.sin(physicsWorld.theta2)) * HardwareConstants.ratio);
        int x7 = (int) ((physicsWorld.wristJoint.x + HardwareConstants.thickness * 0.5 * Math.cos(physicsWorld.theta2)) * HardwareConstants.ratio);
        int y7 = (int) this.convertY((physicsWorld.wristJoint.y + HardwareConstants.thickness * 0.5 * Math.sin(physicsWorld.theta2)) * HardwareConstants.ratio);
        int x8 = (int) ((physicsWorld.wristJoint.x - HardwareConstants.thickness * 0.5 * Math.cos(physicsWorld.theta2)) * HardwareConstants.ratio);
        int y8 = (int) this.convertY((physicsWorld.wristJoint.y - HardwareConstants.thickness * 0.5 * Math.sin(physicsWorld.theta2)) * HardwareConstants.ratio);
        int x9 = (int) ((physicsWorld.elbowJoint.x - HardwareConstants.thickness * 0.5 * Math.cos(physicsWorld.theta2)) * HardwareConstants.ratio);
        int y9 = (int) this.convertY((physicsWorld.elbowJoint.y - HardwareConstants.thickness * 0.5 * Math.sin(physicsWorld.theta2)) * HardwareConstants.ratio);

        g2d.drawLine(x5,y5,x6,y6);
        g2d.drawLine(x6,y6,x7,y7);
        g2d.drawLine(x7,y7,x8,y8);
        g2d.drawLine(x8,y8,x9,y9);

        // hand
        int x10 = (int) (physicsWorld.wristJoint.x * HardwareConstants.ratio);
        int y10 = (int) (this.convertY(physicsWorld.wristJoint.y * HardwareConstants.ratio));
        int x11 = (int) (physicsWorld.fingerTip.x * HardwareConstants.ratio);
        int y11 = (int) (this.convertY((physicsWorld.fingerTip.y + HardwareConstants.thickness) * HardwareConstants.ratio));
        int x12 = (int) (physicsWorld.fingerTip.x * HardwareConstants.ratio);
        int y12 = (int) (this.convertY((physicsWorld.fingerTip.y - HardwareConstants.thickness) * HardwareConstants.ratio));
        int x13 = (int) (physicsWorld.wristJoint.x * HardwareConstants.ratio);
        int y13 = (int) (this.convertY(physicsWorld.wristJoint.y * HardwareConstants.ratio));

        g2d.drawLine(x10, y10, x11, y11);
        g2d.drawLine(x11, y11, x12, y12);
        g2d.drawLine(x12, y12, x13, y13);


        g2d.setPaint(new Color(248,192,54));

        // draw joints
        g2d.setPaint(new Color(188,188,188));
        int radius = (int) (HardwareConstants.jointSize * HardwareConstants.ratio);
        g2d.fillArc((int) (physicsWorld.shoulderJoint.x * HardwareConstants.ratio - radius / 2), (int) this.convertY(physicsWorld.shoulderJoint.y * HardwareConstants.ratio + radius / 2), radius, radius, 0, 360);
        g2d.fillArc((int) (physicsWorld.elbowJoint.x * HardwareConstants.ratio - radius / 2), (int) this.convertY(physicsWorld.elbowJoint.y * HardwareConstants.ratio + radius / 2), radius, radius, 0, 360);
        g2d.fillArc((int) (physicsWorld.wristJoint.x * HardwareConstants.ratio - radius / 2), (int) this.convertY(physicsWorld.wristJoint.y * HardwareConstants.ratio + radius / 2), radius, radius, 0, 360);
    }

    private void drawClimber(Graphics2D g2d, HardwareWorld physicsWorld) {
        double theta = physicsWorld.climberClawAngle;
        double alpha = theta - Math.PI / 2;
        double l = HardwareConstants.climberArmLength;
        double w = HardwareConstants.climberArmWidth;
        double climberClawX = physicsWorld.baseX + HardwareConstants.climberArmBaseXOffset;
        double climberClawY = (HardwareConstants.baseHeight + physicsWorld.baseZ) + HardwareConstants.climberArmBaseYOffset;
        double climberClawLengthTipX = climberClawX - Math.cos(alpha) * l;
        double climberClawLengthTipY = climberClawY + Math.sin(alpha) * l;

        double beta = alpha - Math.atan(w / l);
        double hypotenuse = Math.sqrt(l * l + w * w);
        double climberClawTipX = climberClawX - Math.cos(beta) * hypotenuse;
        double climberClawTipY = climberClawY + Math.sin(beta) * hypotenuse;

        // draw lines
        double x1 = climberClawX * HardwareConstants.ratio;
        double y1 = this.convertY(climberClawY * HardwareConstants.ratio);

        double x2 = climberClawLengthTipX * HardwareConstants.ratio;
        double y2 = this.convertY(climberClawLengthTipY * HardwareConstants.ratio);

        double x3 = climberClawTipX * HardwareConstants.ratio;
        double y3 = this.convertY(climberClawTipY * HardwareConstants.ratio);
        g2d.setStroke(new BasicStroke(10));

        g2d.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
        g2d.drawLine((int) x2, (int) y2, (int) x3, (int) y3);


        // rack
        double rackX = HardwareConstants.climberLegToBack;
        double rackY = HardwareConstants.baseOffset + physicsWorld.climberLegHeight;

    }
}

