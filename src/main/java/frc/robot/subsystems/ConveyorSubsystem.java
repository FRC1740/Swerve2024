package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.Board.HornTab;
import frc.robot.constants.CanIds;
import frc.robot.constants.SubsystemConstants.ConveyorConstants;

public class ConveyorSubsystem extends SubsystemBase {
  private final CANSparkMax m_HornConveyorMotor = new CANSparkMax(CanIds.kConveyorMotorPort, CANSparkMax.MotorType.kBrushless);
  HornTab m_HornTab = HornTab.getInstance();

  /** Creates a new GroundIntake. */
  public ConveyorSubsystem() {
    m_HornConveyorMotor.setInverted(false);
    m_HornConveyorMotor.getEncoder();
    m_HornConveyorMotor.setSmartCurrentLimit(ConveyorConstants.kConveyorMotorCurrentLimit);
    m_HornConveyorMotor.burnFlash();
  }
  public void setConveyorSpeed(double speed) {
    m_HornConveyorMotor.set(speed);
  }
}
