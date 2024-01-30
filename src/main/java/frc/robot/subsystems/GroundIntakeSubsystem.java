package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import frc.robot.constants.SubsystemConstants;

public class GroundIntakeSubsystem {
  private final CANSparkMax m_GroundIntakeMotor = 
  new CANSparkMax(SubsystemConstants.GroundIntakeConstants.kGroundIntakeMotorPort, CANSparkMax.MotorType.kBrushless);

  /** Creates a new GroundIntake. */
  public GroundIntakeSubsystem() {
    m_GroundIntakeMotor.setInverted(false);
    m_GroundIntakeMotor.getEncoder();
    m_GroundIntakeMotor.burnFlash();
  }
  public void setGroundIntakeSpeed(double speed) {
    m_GroundIntakeMotor.set(speed);
  }
}
