package frc.robot;

import java.util.Optional;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.constants.OIConstants;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.HornSubsystem;

public class RobotShared {

  private Optional<Alliance> m_alliance = DriverStation.getAlliance();

  protected DriveSubsystem m_robotDrive = null;
  protected HornSubsystem m_horn = null;

  protected final CommandXboxController m_driverController = new CommandXboxController(OIConstants.kDriverControllerPort);

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

  public Alliance GetAlliance(){
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
