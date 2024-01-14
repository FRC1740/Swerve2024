//RobotShared is the class that holds all the instances of subsystems and other useful systems.
package frc.robot;

import java.util.Optional;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.constants.OIConstants;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.HornSubsystem;
import frc.robot.subsystems.LimelightSubsystem;
import frc.robot.subsystems.PhotonVision;

public class RobotShared {

  private Optional<Alliance> m_alliance = DriverStation.getAlliance();

  protected DriveSubsystem m_robotDrive = null;
  protected HornSubsystem m_horn = null;

  protected final CommandXboxController m_driverController = new CommandXboxController(OIConstants.kDriverControllerPort);

  protected LimelightSubsystem m_limelight = null;
  protected PhotonVision m_photonVision = null;

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
  public CommandXboxController getDriverController() {
    return m_driverController;
  }
  public LimelightSubsystem getLimelight() {
    if(m_limelight == null) {
      m_limelight = new LimelightSubsystem();
    }
    return m_limelight;
  }
  public PhotonVision getPhotonVision() {
    if(m_photonVision == null) {
      m_photonVision = new PhotonVision();
    }
    return m_photonVision;
  }
  public Alliance getAlliance(){ // blue is default for the path planner (paths are made on the blue side)
    m_alliance = DriverStation.getAlliance();
    if(m_alliance.isPresent()){
      if(m_alliance.get() == Alliance.Blue){
        return Alliance.Blue;
      }else{
        return Alliance.Red;
      }
    }else{
      return Alliance.Blue;
    }
  }
}
