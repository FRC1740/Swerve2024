package frc.robot.subsystems;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.constants.CanIds;

import frc.robot.constants.SubsystemConstants.DeflectorConstants;

public class DeflectorSubsystem extends SubsystemBase {
  private final AbsoluteEncoder m_DeflectorEncoder;
}

public class DeflectorSubsystem {
  private final CANSparkMax m_DeflectorMotor = new CANSparkMax(CanIds.kDeflectorMotorTalonId, CANSparkMax.MotorType.kBrushless);

  /** Creates a new GroundIntake. */
  public DeflectorSubsystem() {
    m_DeflectorMotor.setInverted(false);
    m_DeflectorMotor.getEncoder();
    m_DeflectorMotor.setSmartCurrentLimit(DeflectorConstants.kDeflectorMotorCurrentLimit);
    m_DeflectorMotor.burnFlash();
  }
  public void setDeflectorPosition(double position) {
    m_DeflectorMotor.set(position);
  }
  public void setDeflectorZeroPosition() {
    
  }
}
