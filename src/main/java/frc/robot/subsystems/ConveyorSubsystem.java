package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import frc.Board.HornTab;
import frc.robot.constants.SubsystemConstants;

public class ConveyorSubsystem {
  private final CANSparkMax m_HornConveyorMotor = new CANSparkMax(SubsystemConstants.ConveyorConstants.kConveyorMotorPort, CANSparkMax.MotorType.kBrushless);
  HornTab m_HornTab = HornTab.getInstance();

  /** Creates a new GroundIntake. */
  public ConveyorSubsystem() {
    m_HornConveyorMotor.setInverted(false);
    m_HornConveyorMotor.getEncoder();
    m_HornConveyorMotor.burnFlash();
  }
  public void setConveyorSpeed(double speed) {
    m_HornConveyorMotor.set(speed);
  }
}
