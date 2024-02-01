package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import frc.robot.constants.CanIds;
import frc.robot.constants.SubsystemConstants.GroundIntakeConstants;

public class GroundIntakeSubsystem {
  private final CANSparkMax m_GroundIntakeMotor = 
  new CANSparkMax(CanIds.kGroundIntakeMotorPort, CANSparkMax.MotorType.kBrushless);

  /** Creates a new GroundIntake. */
  public GroundIntakeSubsystem() {
    m_GroundIntakeMotor.setInverted(false);
    m_GroundIntakeMotor.getEncoder();
    m_GroundIntakeMotor.setSmartCurrentLimit(GroundIntakeConstants.kGroundIntakeLimit);

    m_GroundIntakeMotor.burnFlash();
  }
  public void setGroundIntakeSpeed(double speed) {
    m_GroundIntakeMotor.set(speed);
  }
}
