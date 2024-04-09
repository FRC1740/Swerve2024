package frc.robot.commands.basic;

import edu.wpi.first.wpilibj2.command.Command;
import frc.Board.GroundIntakeTab;
import frc.robot.RobotShared;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.GroundIntakeSubsystem;
import frc.robot.subsystems.SensorSubsystem;

public class GroundIntakeDefault extends Command{

  private ConveyorSubsystem m_conveyorSubsystem;
  private GroundIntakeSubsystem m_groundIntakeSubsystem;
  private SensorSubsystem m_sensorSubsystem;
  private RobotShared m_robotShared;
  private double m_intakeSpeed;

  private GroundIntakeTab m_GroundIntakeTab = GroundIntakeTab.getInstance();

  /** Creates a new IntakeDeploy. 
   * Intakes from the Ground Intake.
   * This is the default command for the Ground Intake, it runs continuously while enabled.
   * @param intakeSpeed The speed at which the intake should run in the range of [-1, 1], where 1 is full speed intaking.
  */
  public GroundIntakeDefault(double intakeSpeed) {
    m_robotShared = RobotShared.getInstance();
    m_conveyorSubsystem = m_robotShared.getConveyorSubsystem();
    m_groundIntakeSubsystem = m_robotShared.getGroundIntakeSubsystem();
    m_sensorSubsystem = m_robotShared.getSensorSubsystem();
    m_intakeSpeed = intakeSpeed;
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
    if(m_sensorSubsystem.checkHornSensors() ||
      m_GroundIntakeTab.getGroundIntakeDefaultEnabled() == false){
        
      m_conveyorSubsystem.setConveyorSpeed(0);
      m_groundIntakeSubsystem.setGroundIntakeSpeed(0);
    }else{
      m_conveyorSubsystem.setConveyorSpeed(m_intakeSpeed - .6f);
      m_groundIntakeSubsystem.setGroundIntakeSpeed(m_intakeSpeed);
    }
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