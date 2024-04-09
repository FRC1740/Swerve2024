package frc.robot.commands.basic;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotShared;
import frc.robot.constants.SubsystemConstants.HornConstants;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.GroundIntakeSubsystem;
import frc.robot.subsystems.HornSubsystem;
import frc.robot.subsystems.SensorSubsystem;

public class GroundIntake extends Command{

  private HornSubsystem m_horn;
  private ConveyorSubsystem m_conveyorSubsystem;
  private GroundIntakeSubsystem m_groundIntakeSubsystem;
  private SensorSubsystem m_sensorSubsystem;
  private RobotShared m_robotShared;
  private double m_intakeSpeed;

  /** Creates a new IntakeDeploy. 
   * Intakes from the Ground Intake.
   * @param intakeSpeed The speed at which the intake should run in the range of [-1, 1], where 1 is full speed intaking.
  */
  public GroundIntake(double intakeSpeed) {
    m_robotShared = RobotShared.getInstance();
    m_horn = m_robotShared.getHornSubsystem();
    m_conveyorSubsystem = m_robotShared.getConveyorSubsystem();
    m_groundIntakeSubsystem = m_robotShared.getGroundIntakeSubsystem();
    m_sensorSubsystem = m_robotShared.getSensorSubsystem();
    m_intakeSpeed = intakeSpeed;
    addRequirements(m_horn);
    addRequirements(m_groundIntakeSubsystem);
    addRequirements(m_conveyorSubsystem);
    addRequirements(m_sensorSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_horn.setRpmSetpoint(-m_intakeSpeed * HornConstants.kMaxHornRPM * .1);
    m_conveyorSubsystem.setConveyorSpeed(m_intakeSpeed - .6f);
    m_groundIntakeSubsystem.setGroundIntakeSpeed(m_intakeSpeed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_horn.setRpmSetpoint(0.0);
    m_conveyorSubsystem.setConveyorSpeed(0.0);
    m_groundIntakeSubsystem.setGroundIntakeSpeed(0.0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(m_sensorSubsystem.checkHornSensors()){
      return true;
    }
    return false;
  }
}