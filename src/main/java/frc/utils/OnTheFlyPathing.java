package frc.utils;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathConstraints;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StructArrayPublisher;
import edu.wpi.first.networktables.StructPublisher;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotShared;
import frc.robot.constants.AutoConstants;
import frc.robot.subsystems.DriveSubsystem;
import com.pathplanner.lib.util.PathPlannerLogging;

public class OnTheFlyPathing {
  static RobotShared m_robotShared = RobotShared.getInstance();
  private static DriveSubsystem m_drive = m_robotShared.getDriveSubsystem();

  //Data Logging
  NetworkTable OnTheFlyPathingTable = NetworkTableInstance.getDefault().getTable("On The Fly Pathing");

  StructPublisher<Pose2d> TargetPose = OnTheFlyPathingTable.getStructTopic("Target Pose", Pose2d.struct).publish();
  StructPublisher<Pose2d> CurrentPose = OnTheFlyPathingTable.getStructTopic("Current Pose", Pose2d.struct).publish();
  StructArrayPublisher<Pose2d> ActivePath = OnTheFlyPathingTable.getStructArrayTopic("Active Path", Pose2d.struct).publish();

  public OnTheFlyPathing(){
    PathPlannerLogging.setLogTargetPoseCallback((Pose) -> {
      TargetPose.set(Pose);
    });
    PathPlannerLogging.setLogCurrentPoseCallback((Pose) -> {
      CurrentPose.set(Pose);
    });
    PathPlannerLogging.setLogActivePathCallback((Poses) -> {
      ActivePath.set(Poses.toArray(new Pose2d[0]));
    });
  }

  /**
    Returns a command for the path to a point
  */
  public Command getOnTheFlyPath(double x, double y, double rot){
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
    Returns a command for the path to a point and sets the angle to the current heading
  */
  public Command getOnTheFlyPath(Translation2d targetPosition){
    return getOnTheFlyPath(targetPosition.getX(), targetPosition.getY(), m_drive.getHeading());
  }
  /**
    Returns a command for the path to a point
  */
  public Command getOnTheFlyPath(Pose2d targetPosition){
    return getOnTheFlyPath(targetPosition.getX(), targetPosition.getY(), targetPosition.getRotation().getDegrees());
  }
  /**
    Returns a command for the path to a point and sets the angle to the current heading
  */
  public Command getOnTheFlyPath(double x, double y){
    return getOnTheFlyPath(x, y, m_drive.getHeading());
  }
}
