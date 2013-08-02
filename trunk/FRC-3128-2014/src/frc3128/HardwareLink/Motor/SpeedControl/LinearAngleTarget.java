package frc3128.HardwareLink.Motor.SpeedControl;

import frc3128.HardwareLink.Motor.MotorSpeedControl;
import frc3128.Util.RobotMath;

/**
 *
 * @author Noah Sutton-Smolin
 */
public class LinearAngleTarget extends MotorSpeedControl {
	private double minSpeed;
	private double targetAngle;
	
	public LinearAngleTarget(double minSpeed) {
		if(!RobotMath.isValidPower(minSpeed)) throw new IllegalArgumentException("The minimum power is incorrect!"); 
		this.minSpeed = Math.abs(minSpeed);
	}
	
	public void setControlTarget(double val) {this.targetAngle = val;}

	public double speedTimestep(double dt) {
		double currentAngle = RobotMath.normalizeAngle(this.getLinkedEncoderAngle());
		targetAngle = RobotMath.normalizeAngle(this.getLinkedEncoderAngle());
		double angleDiff = RobotMath.normalizeAngle(Math.abs(currentAngle - targetAngle));
		return (angleDiff / 180.0) * ((1.0 - minSpeed)/1.0) + minSpeed;
	}

	public void clearControlRun() {this.targetAngle = 0;}
}