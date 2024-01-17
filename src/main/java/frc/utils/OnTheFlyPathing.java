package frc.utils;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathConstraints;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotShared;
import frc.robot.constants.AutoConstants;
import frc.robot.subsystems.DriveSubsystem;

public class OnTheFlyPathing {
  static RobotShared m_robotShared = RobotShared.getInstance();
  private static DriveSubsystem m_drive = m_robotShared.getDriveSubsystem();
  /**
    Returns a command for the path to a point
  */
  public static Command getOnTheFlyPath(double x, double y, double rot){
    // Since we are using a holonomic drivetrain, the rotation component of this pose
    // represents the goal holonomic rotation
    Pose2d targetPose = new Pose2d(x, y, Rotation2d.fromDegrees(rot));

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
    return pathfindingCommand;
  }
  /**
    Returns a command for the path to a point
  */
  public static Command getOnTheFlyPath(Translation2d targetPosition){
    return getOnTheFlyPath(targetPosition.getX(), targetPosition.getY(), m_drive.getHeading());
  }
  /**
    Returns a command for the path to a point
  */
  public static Command getOnTheFlyPath(Pose2d targetPosition){
    return getOnTheFlyPath(targetPosition.getX(), targetPosition.getY(), targetPosition.getRotation().getDegrees());
  }
  /**
    Returns a command for the path to a point
  */
  public static Command getOnTheFlyPath(double x, double y){
    return getOnTheFlyPath(x, y, m_drive.getHeading());
  }
}
