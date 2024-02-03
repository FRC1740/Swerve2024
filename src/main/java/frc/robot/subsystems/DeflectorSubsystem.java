package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import frc.robot.constants.CanIds;

import frc.robot.constants.SubsystemConstants.DeflectorConstants;

public class DeflectorSubsystem {
  private final CANSparkMax m_DeflectorMotor = new CANSparkMax(CanIds.kDeflectorMotorTalonId, CANSparkMax.MotorType.kBrushless);

  /** Creates a new GroundIntake. */
  public DeflectorSubsystem() {
    m_DeflectorMotor.setInverted(false);
    m_DeflectorMotor.getEncoder();
    m_DeflectorMotor.setSmartCurrentLimit(DeflectorConstants.kDeflectorMotorCurrentLimit);
    m_DeflectorMotor.burnFlash();
  }
  public void setDeflectorSpeed(double speed) {
    m_DeflectorMotor.set(speed);
  }
}
