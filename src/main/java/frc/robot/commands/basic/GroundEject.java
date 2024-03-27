package frc.robot.commands.basic;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotShared;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.GroundIntakeSubsystem;

public class GroundEject extends Command{
  private ConveyorSubsystem m_conveyorSubsystem;
  private GroundIntakeSubsystem m_groundIntakeSubsystem;
  private RobotShared m_robotShared;
  private double m_ejectSpeed;

  /** Creates a new Eject Command. 
   * Ejects from the Ground Intake.
   * Currently, you also need to drive the robot away from the note to eject it.
  */
  public GroundEject(double ejectSpeed) {
    m_robotShared = RobotShared.getInstance();
    m_conveyorSubsystem = m_robotShared.getConveyorSubsystem();
    m_groundIntakeSubsystem = m_robotShared.getGroundIntakeSubsystem();
    m_ejectSpeed = ejectSpeed;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_conveyorSubsystem.setConveyorSpeed(m_ejectSpeed);
    m_groundIntakeSubsystem.setGroundIntakeSpeed(m_ejectSpeed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_conveyorSubsystem.setConveyorSpeed(0.0);
    m_groundIntakeSubsystem.setGroundIntakeSpeed(0.0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}