package frc.robot.subsystems;

import com.revrobotics.CANSparkBase.SoftLimitDirection;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.Board.CurrentDrawTab;
import frc.robot.constants.CanIds;
import frc.robot.constants.SubsystemConstants.ClimberConstants;
import frc.robot.constants.SubsystemConstants.GroundIntakeConstants;

public class ClimberSubsystem extends SubsystemBase{
  private final CANSparkMax m_ClimberMotor = 
  new CANSparkMax(CanIds.kClimberMotorCanId, CANSparkMax.MotorType.kBrushless);
  RelativeEncoder m_climberEncoder;

  CurrentDrawTab m_CurrentDrawTab = CurrentDrawTab.getInstance();

  /** Creates a new ClimberSubsystem. */
  public ClimberSubsystem() {
    m_ClimberMotor.setInverted(false);
    m_climberEncoder = m_ClimberMotor.getEncoder();
    m_climberEncoder.setPosition(0);

    m_ClimberMotor.enableSoftLimit(SoftLimitDirection.kForward, true);
    m_ClimberMotor.enableSoftLimit(SoftLimitDirection.kReverse, true);
    m_ClimberMotor.setSoftLimit(SoftLimitDirection.kForward, ClimberConstants.kClimberMotorForwardSoftLimit);
    m_ClimberMotor.setSoftLimit(SoftLimitDirection.kReverse, ClimberConstants.kClimberMotorReverseSoftLimit);


    m_ClimberMotor.setSmartCurrentLimit(GroundIntakeConstants.kGroundIntakeCurrentLimit);

    m_ClimberMotor.burnFlash();
  }
  public void setClimberMotorSpeed(double speed) {
    m_ClimberMotor.set(speed);
  }
  @Override
  public void periodic() {
    // System.out.println("Climber Encoder: " + m_climberEncoder.getPosition());
    m_CurrentDrawTab.setClimberCurrentDraw(m_ClimberMotor.getOutputCurrent());
  }
  public void toggleSoftLimit(){
    if(m_ClimberMotor.isSoftLimitEnabled(SoftLimitDirection.kForward)){
      m_ClimberMotor.enableSoftLimit(SoftLimitDirection.kForward, false);
      m_ClimberMotor.enableSoftLimit(SoftLimitDirection.kReverse, false);
    } else {
      m_ClimberMotor.enableSoftLimit(SoftLimitDirection.kForward, true);
      m_ClimberMotor.enableSoftLimit(SoftLimitDirection.kReverse, true);
    }
  }
}
