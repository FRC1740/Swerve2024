package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import frc.robot.constants.CanIds;
import frc.robot.constants.SubsystemConstants.ConveyorConstants;

public class DeflectorSubsytem {
  private final CANSparkMax m_DeflectorMotor = new CANSparkMax(CanIds.kDeflectorMotorCanId, CANSparkMax.MotorType.kBrushless);

  /** Creates a new GroundIntake. */
  public DeflectorSubsytem() {
    m_DeflectorMotor.setInverted(false);
    m_DeflectorMotor.getEncoder();
    m_DeflectorMotor.setSmartCurrentLimit(ConveyorConstants.kConveyorMotorCurrentLimit);
    m_DeflectorMotor.burnFlash();
  }
  public void setDeflectorSpeed(double speed) {
    m_DeflectorMotor.set(speed);
  }
}
