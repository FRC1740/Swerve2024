package frc.robot.subsystems;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkAbsoluteEncoder.Type;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkBase.SoftLimitDirection;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.Board.DriverTab;
import frc.Board.HornTab;
import frc.robot.constants.CanIds;
import frc.robot.constants.ModuleConstants;
import frc.robot.constants.SubsystemConstants.DeflectorConstants;

public class DeflectorSubsytem extends SubsystemBase{
  private final CANSparkMax m_DeflectorMotor = new CANSparkMax(CanIds.kDeflectorMotorCanId, CANSparkMax.MotorType.kBrushless);

  private final RelativeEncoder m_deflectorEncoder;
  private final SparkPIDController m_deflectorPidController;

  private double setpoint = .1;

  private final HornTab m_hornTab;

  /** Creates a new GroundIntake. */
  public DeflectorSubsytem() {
    m_DeflectorMotor.setInverted(false);
    m_DeflectorMotor.setSoftLimit(SoftLimitDirection.kForward, 16);
    m_DeflectorMotor.setSoftLimit(SoftLimitDirection.kReverse, 1);
    m_deflectorEncoder = m_DeflectorMotor.getEncoder();
    m_deflectorEncoder.setPosition(0);
    m_DeflectorMotor.setSmartCurrentLimit(DeflectorConstants.kDeflectorMotorCurrentLimit);
    m_DeflectorMotor.burnFlash(); 

    m_deflectorPidController = m_DeflectorMotor.getPIDController();

    m_deflectorPidController.setPositionPIDWrappingEnabled(true);
    m_deflectorPidController.setPositionPIDWrappingMinInput(ModuleConstants.kTurningEncoderPositionPIDMinInput);
    m_deflectorPidController.setPositionPIDWrappingMaxInput(18);



    m_deflectorPidController.setP(.1);
    m_deflectorPidController.setI(0);
    m_deflectorPidController.setD(0);
    m_deflectorPidController.setFF(0);
    m_deflectorPidController.setOutputRange(ModuleConstants.kTurningMinOutput,
      ModuleConstants.kTurningMaxOutput);

    m_deflectorPidController.setFeedbackDevice(m_deflectorEncoder);

    m_hornTab = HornTab.getInstance();
  }
  @Override
  public void periodic() {
    setpoint = m_hornTab.getDeflectorSetpoint();
    m_hornTab.setDeflectorEncoder(getEncoderPosition());
  }
  public void setDeflectorSpeed(double speed) {
    m_DeflectorMotor.set(speed);
  }
  public void stopDeflector() {
    m_DeflectorMotor.stopMotor();
  }
  public double getEncoderPosition() {
    return m_deflectorEncoder.getPosition();
  }
  public void seekSetpoint() {
    m_deflectorPidController.setReference(setpoint, CANSparkMax.ControlType.kPosition);
  }
}
