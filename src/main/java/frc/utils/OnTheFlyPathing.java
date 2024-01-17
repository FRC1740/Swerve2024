package frc.utils;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathConstraints;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.AutoConstants;

public class OnTheFlyPathing {
  /**
    Returns a command for the path to a point
  */
  public static Command getOnTheFlyPath(Translation2d targetPosition){
    // Since we are using a holonomic drivetrain, the rotation component of this pose
    // represents the goal holonomic rotation
    Pose2d targetPose = new Pose2d(targetPosition.getX(), targetPosition.getY(), Rotation2d.fromDegrees(180));

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
  public static <T> Command getOnTheFlyPath(T x, T y){
    // Since we are using a holonomic drivetrain, the rotation component of this pose
    // represents the goal holonomic rotation
    Pose2d targetPose = new Pose2d((double)x, (double)y, Rotation2d.fromDegrees(180));

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
}
