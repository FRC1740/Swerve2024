package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Servo;

public class ServoSubsystem {
  Servo climberServo = new Servo(9);

  public void climberUnlock() {
    climberServo.setAngle(90);
  }
}
