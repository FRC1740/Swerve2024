// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathConstraints;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotShared;
import frc.robot.constants.AutoConstants;
import frc.robot.subsystems.DriveSubsystem;

public class DriveToPointOnTheFly extends Command {
  /** Creates a new DriveToPointHolo. */
  static RobotShared m_robotShared = RobotShared.getInstance();
  private static DriveSubsystem m_drive = m_robotShared.getDriveSubsystem();

  public DriveToPointOnTheFly(Translation2d targetPosition) {
    // Since we are using a holonomic drivetrain, the rotation component of this pose
    // represents the goal holonomic rotation
    Pose2d targetPose = new Pose2d(10, 5, Rotation2d.fromDegrees(180));

    // Create the constraints to use while pathfinding
    PathConstraints constraints = new PathConstraints(
      AutoConstants.kMaxSpeedDriveToPointMetersPerSecond,
      AutoConstants.kMaxAccelerationDriveToPointMetersPerSecond,
      Math.toRadians(540), Math.toRadians(720));

    // Since AutoBuilder is configured, we can use it to build pathfinding commands
    Command pathfindingCommand = AutoBuilder.pathfindToPose(
      targetPose,
      constraints,
      0.0, // Goal end velocity in meters/sec
      0.0 // Rotation delay distance in meters. This is how far the robot should travel before attempting to rotate.
    );
    pathfindingCommand.schedule();
    addRequirements(m_drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
