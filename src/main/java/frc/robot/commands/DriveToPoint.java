// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.RobotShared;
import frc.robot.constants.AutoConstants;
import frc.robot.subsystems.DriveSubsystem;

public class DriveToPoint extends PIDCommand {
  static RobotShared m_robotShared = RobotShared.getInstance();
  private static DriveSubsystem m_drive = m_robotShared.getDriveSubsystem();
  /** Creates a new DriveToPoint. 
   * This command drives the robot to a specified point
   * This is the janky homemade drive to point function, consider using {@link frc.utils.OnTheFlyPathing}.
   * It also doesn't work. So OnTheFlyPathing is better.
  */
  public DriveToPoint(double targetX, double targetY) { 
    super(
        // The controller that the command will use
        new PIDController(1, 0, 0),
        // This should return the measurement
        () -> m_drive.getPose().getTranslation().getDistance(new Translation2d(targetX, targetY)), //Error X and Y
        // This should return the setpoint (can also be a constant)
        () -> 0, // 0 for 0 error
        // This uses the output
        output -> {
          double desiredAngle = Math.atan2((m_drive.getPose().getY() - targetY), (m_drive.getPose().getX() - targetX));
          desiredAngle = Math.toDegrees(desiredAngle);
          // desiredAngle = Math.abs(desiredAngle); // normalize it
          double XSpeed = Math.cos(Math.toRadians(desiredAngle)) * 190000; //Aribtrarily large tesing value
          double YSpeed = Math.sin(Math.toRadians(desiredAngle)) * 100000;
          //Janky clamp because Java doesn't have a native one
          double clampedXSpeed = (Math.max(0.0, Math.min(AutoConstants.kMaxSpeedDriveToPointMetersPerSecond, XSpeed)));
          double clampedYSpeed = (Math.max(0.0, Math.min(AutoConstants.kMaxSpeedDriveToPointMetersPerSecond, YSpeed)));
          m_drive.drive(clampedXSpeed, clampedYSpeed, 0, 
            true, true, false);
        });
      addRequirements(m_drive);
      // atSetpoint error tolerance
      getController().setTolerance(.1, 1);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return getController().atSetpoint();
  }
}
