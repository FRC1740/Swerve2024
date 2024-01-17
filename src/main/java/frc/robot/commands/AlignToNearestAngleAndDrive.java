// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotShared;
import frc.robot.subsystems.DriveSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class AlignToNearestAngleAndDrive extends Command {
  /** Creates a new DriveWhileAligning. */
  
  static RobotShared m_robotShared = RobotShared.getInstance();
  private static DriveSubsystem m_drive = m_robotShared.getDriveSubsystem();
  private Command DriveAlignCommand;
  public AlignToNearestAngleAndDrive(boolean fieldRelative, boolean rateLimit) {
    
  }
  @Override
  public void initialize() {
    DriveAlignCommand = new DriveWhileAligning((((int)(((m_drive.getHeading()) / (360 / 8)) + .5)) * 45), true, true);
    DriveAlignCommand.schedule();
  }
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return DriveAlignCommand.isFinished();
  }
}
