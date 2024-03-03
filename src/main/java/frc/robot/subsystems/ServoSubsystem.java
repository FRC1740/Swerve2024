package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ServoSubsystem extends SubsystemBase{
  Servo climberServo = new Servo(7);

  private double servoAngle = climberServo.getAngle();

  public void climberUnlock() {
    // servoAngle = 90;
    climberServo.setSpeed(1);
  }

  @Override
  public void periodic() {
    // climberServo.setAngle(servoAngle);
    climberServo.setSpeed(1);
  }
}
