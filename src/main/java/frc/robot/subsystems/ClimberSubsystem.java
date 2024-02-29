package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.CanIds;
import frc.robot.constants.SubsystemConstants.GroundIntakeConstants;

public class ClimberSubsystem extends SubsystemBase{
  private final CANSparkMax m_ClimberMotor = 
  new CANSparkMax(CanIds.kClimberMotorCanId, CANSparkMax.MotorType.kBrushless);

  /** Creates a new GroundIntake. */
  public ClimberSubsystem() {
    m_ClimberMotor.setInverted(false);
    m_ClimberMotor.getEncoder();
    m_ClimberMotor.setSmartCurrentLimit(GroundIntakeConstants.kGroundIntakeCurrentLimit);

    m_ClimberMotor.burnFlash();
  }
  public void setClimberMotorSpeed(double speed) {
    m_ClimberMotor.set(speed);
  }
}
