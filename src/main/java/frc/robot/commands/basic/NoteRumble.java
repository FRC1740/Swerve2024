package frc.robot.commands.basic;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.RobotShared;
import frc.robot.constants.SensorConstants;

public class NoteRumble extends Command{
  double start;
  RobotShared m_robotShared;
  CommandXboxController m_driverController;
  @Override
  public void initialize() {
    start = System.currentTimeMillis();
    m_robotShared = RobotShared.getInstance();
    m_driverController = m_robotShared.getDriverController();
    m_driverController.getHID().setRumble(RumbleType.kLeftRumble, SensorConstants.kRumbleStrength);
    m_driverController.getHID().setRumble(RumbleType.kRightRumble, SensorConstants.kRumbleStrength);
  }
  @Override
  public void execute() {
    // if(start + SensorConstants.kTimeToRumbleController < System.currentTimeMillis()){
    //   end(true);
    // }
    // System.err.println("OEFIH");
  }

  @Override
  public boolean isFinished() {
    if(start + SensorConstants.kTimeToRumbleController < System.currentTimeMillis()){
      return true;
    }
    return false;
  }
  @Override
  public void end(boolean interrupted) {
    m_driverController.getHID().setRumble(RumbleType.kLeftRumble, 0);
    m_driverController.getHID().setRumble(RumbleType.kRightRumble, 0);
  }
}
