package frc.robot.constants;

import edu.wpi.first.math.trajectory.TrapezoidProfile;

public final class AutoConstants {
  // Constants for On The Fly pathing
  public static final double kMaxSpeedDriveToPointMetersPerSecond = 1; // This is different because it is not a path
  public static final double kMaxAccelerationDriveToPointMetersPerSecond = 1; // This is different because it is not a path

  //constant for aligning to an angle while driving
  public static final double kDriveWhileAligningP = .01;

  public static final double kMaxSpeedMetersPerSecond = 3;
  public static final double kMaxAccelerationMetersPerSecondSquared = 3;
  public static final double kMaxAngularSpeedRadiansPerSecond = Math.PI;
  public static final double kMaxAngularSpeedRadiansPerSecondSquared = Math.PI;

  public static final double kPXController = 1;
  public static final double kPYController = 1;
  public static final double kPThetaController = 1;

  // Constraint for the motion profiled robot angle controller
  public static final TrapezoidProfile.Constraints kThetaControllerConstraints = new TrapezoidProfile.Constraints(
    kMaxAngularSpeedRadiansPerSecond, kMaxAngularSpeedRadiansPerSecondSquared);
}
