package frc.robot.subsystems;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkAbsoluteEncoder.Type;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.CanIds;
import frc.robot.constants.SubsystemConstants.ConveyorConstants;

public class DeflectorSubsytem extends SubsystemBase{
  private final CANSparkMax m_DeflectorMotor = new CANSparkMax(CanIds.kDeflectorMotorCanId, CANSparkMax.MotorType.kBrushed);

  private final AbsoluteEncoder m_deflectorEncoder;

  /** Creates a new GroundIntake. */
  public DeflectorSubsytem() {
    m_DeflectorMotor.setInverted(false);
    m_deflectorEncoder = m_DeflectorMotor.getAbsoluteEncoder(Type.kDutyCycle);
    m_DeflectorMotor.setSmartCurrentLimit(ConveyorConstants.kConveyorMotorCurrentLimit);
    m_DeflectorMotor.burnFlash();
  }
  public void setDeflectorSpeed(double speed) {
    m_DeflectorMotor.set(speed);
  }
  public void stopDeflector() {
    m_DeflectorMotor.stopMotor();
  }
  public void getEncoderPosition() {
    m_deflectorEncoder.getPosition();
  }
}
