package frc.robot.commands.basic;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotShared;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.GroundIntakeSubsystem;
import frc.robot.subsystems.SensorSubsystem;

public class GroundIntakeNoHorn extends Command{

  private ConveyorSubsystem m_conveyorSubsystem;
  private GroundIntakeSubsystem m_groundIntakeSubsystem;
  private SensorSubsystem m_sensorSubsystem;
  private RobotShared m_robotShared;
  private double m_intakeSpeed;

  /** Creates a new Ground Intake. 
   * Intakes from the ground intake without the horn flywheels.
   * @param intakeSpeed The speed at which the intake should run in the range of [-1, 1], where 1 is full speed intaking.
  */
  public GroundIntakeNoHorn(double intakeSpeed) {
    m_robotShared = RobotShared.getInstance();
    m_conveyorSubsystem = m_robotShared.getConveyorSubsystem();
    m_groundIntakeSubsystem = m_robotShared.getGroundIntakeSubsystem();
    m_sensorSubsystem = m_robotShared.getSensorSubsystem();
    m_intakeSpeed = intakeSpeed;
    addRequirements(m_groundIntakeSubsystem);
    addRequirements(m_conveyorSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_conveyorSubsystem.setConveyorSpeed(m_intakeSpeed - .6f);
    m_groundIntakeSubsystem.setGroundIntakeSpeed(m_intakeSpeed);
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
    if(m_sensorSubsystem.checkHornSensors()) {
      return true;
    }
    return false;
  }
}