package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.Board.CurrentDrawTab;
import frc.robot.constants.CanIds;
import frc.robot.constants.SubsystemConstants.GroundIntakeConstants;

public class GroundIntakeSubsystem extends SubsystemBase{
  private final CANSparkMax m_GroundIntakeMotor = 
    new CANSparkMax(CanIds.kGroundIntakeMotorPort, CANSparkMax.MotorType.kBrushless);

  private CurrentDrawTab m_CurrentDrawTab = CurrentDrawTab.getInstance();

  /** Creates a new GroundIntake. */
  public GroundIntakeSubsystem() {
    m_GroundIntakeMotor.setInverted(false);
    m_GroundIntakeMotor.getEncoder();
    m_GroundIntakeMotor.setSmartCurrentLimit(GroundIntakeConstants.kGroundIntakeCurrentLimit);

    m_GroundIntakeMotor.burnFlash();
  }
  public void setGroundIntakeSpeed(double speed) {
    m_GroundIntakeMotor.set(speed);
  }

  @Override
  public void periodic() {
    m_CurrentDrawTab.setGroundIntakeCurrentDraw(m_GroundIntakeMotor.getOutputCurrent());
  }
}
