package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.Board.OutputSB.CurrentDrawTab;
import frc.Board.OutputSB.HornTab;
import frc.robot.constants.CanIds;
import frc.robot.constants.SubsystemConstants.ConveyorConstants;

public class ConveyorSubsystem extends SubsystemBase {
  private final CANSparkMax m_HornConveyorMotor = new CANSparkMax(CanIds.kConveyorMotorPort, CANSparkMax.MotorType.kBrushless);
  HornTab m_HornTab = HornTab.getInstance();
  CurrentDrawTab m_CurrentDrawTab = CurrentDrawTab.getInstance();

  /** Creates a new GroundIntake. */
  public ConveyorSubsystem() {
    m_HornConveyorMotor.setInverted(false);
    m_HornConveyorMotor.getEncoder();
    m_HornConveyorMotor.setSmartCurrentLimit(ConveyorConstants.kConveyorMotorCurrentLimit);
    // m_HornConveyorMotor.setOpenLoopRampRate(.2f);
    // m_HornConveyorMotor.setClosedLoopRampRate(.2f);
    m_HornConveyorMotor.burnFlash();
  }
  public void setConveyorSpeed(double speed) {
    m_HornConveyorMotor.set(speed);
  }

  @Override
  public void periodic() {
    m_CurrentDrawTab.setConveyorCurrentDraw(m_HornConveyorMotor.getOutputCurrent());
  }
}
