//RobotShared is the class that holds all the instances of subsystems and other useful systems.
package frc.robot;

import java.util.Optional;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.constants.OIConstants;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.DeflectorSubsytem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.GroundIntakeSubsystem;
import frc.robot.subsystems.HornSubsystem;
import frc.robot.subsystems.LedSubsystem;
import frc.robot.subsystems.LimelightSubsystem;
import frc.utils.Inspired;
import frc.robot.subsystems.SensorSubsystem;
import frc.robot.subsystems.ClimberSubsystem;

public class RobotShared {

  private Optional<Alliance> m_alliance = DriverStation.getAlliance();

  protected DriveSubsystem m_robotDrive = null;
  protected HornSubsystem m_horn = null;
  protected ConveyorSubsystem m_conveyor = null;
  protected DeflectorSubsytem m_deflector = null;
  protected ClimberSubsystem m_climberSubsystem = null;
  
  protected final CommandXboxController m_driverController = new CommandXboxController(OIConstants.kDriverControllerPort);
  protected final CommandXboxController m_coDriverController = new CommandXboxController(OIConstants.kCoDriverControllerPort);


  protected LimelightSubsystem m_limelight = null;
  protected LedSubsystem m_ledSubsystem = null;
  protected SensorSubsystem m_sensorSubsystem = null;
  protected GroundIntakeSubsystem m_groundIntakeSubsystem = null;
  protected Inspired m_Inspiration = null;

  private static RobotShared instance;

  public static RobotShared getInstance() {
    if(instance == null) {
      instance = new RobotShared();
    }
    return instance;
  }

  public DriveSubsystem getDriveSubsystem() {
    if(m_robotDrive == null) {
      m_robotDrive = new DriveSubsystem();
    }
    return m_robotDrive;
  }
  public HornSubsystem getHornSubsystem() {
    if(m_horn == null) {
      m_horn = new HornSubsystem();
    }
    return m_horn;
  }
  public ConveyorSubsystem getConveyorSubsystem() {
    if(m_conveyor == null) {
      m_conveyor = new ConveyorSubsystem();
    }
    return m_conveyor;
  }
  public ClimberSubsystem getClimberSubsystem() {
    if(m_climberSubsystem == null) {
      m_climberSubsystem = new ClimberSubsystem();
    }
    return m_climberSubsystem;
  }
  public DeflectorSubsytem getDeflectorSubsystem() {
    if(m_deflector == null) {
      m_deflector = new DeflectorSubsytem();
    }
    return m_deflector;
  } 
  public GroundIntakeSubsystem getGroundIntakeSubsystem() {
    if(m_groundIntakeSubsystem == null) {
      m_groundIntakeSubsystem = new GroundIntakeSubsystem();
    }
    return m_groundIntakeSubsystem;
  }
  public SensorSubsystem getSensorSubsystem() {
    if(m_sensorSubsystem == null) {
      m_sensorSubsystem = new SensorSubsystem();
    }
    return m_sensorSubsystem;
  }
  public CommandXboxController getDriverController() {
    return m_driverController;
  }
  public CommandXboxController getCoDriverController() {
    return m_coDriverController;
  }
  public Inspired getInspiration() {
    if(m_Inspiration == null) {
      m_Inspiration = new Inspired();
    }
    return m_Inspiration;
  }
  public LedSubsystem getLedSubsystem() {
    if(m_ledSubsystem == null) {
      m_ledSubsystem = new LedSubsystem();
    }
    return m_ledSubsystem;
  }
  public LimelightSubsystem getLimelight() {
    if(m_limelight == null) {
      m_limelight = new LimelightSubsystem();
    }
    return m_limelight;
  }
  /** blue is default */
  public Alliance getAlliance() { // blue is default for the path planner (paths are made on the blue side)
    if(DriverStation.isFMSAttached()){
      m_alliance = DriverStation.getAlliance();
      if(m_alliance.isPresent()){
        if(m_alliance.get() == Alliance.Blue){
          return Alliance.Blue;
        }else{
          return Alliance.Red;
        }
      }else{
        System.err.println("No alliance found!");
        return Alliance.Blue;
      }
    }else{
      System.err.println("No FMS found!");
      return Alliance.Blue;
    }
  }
}
