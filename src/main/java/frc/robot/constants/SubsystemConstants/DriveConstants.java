package frc.robot.constants.SubsystemConstants;

import com.pathplanner.lib.util.HolonomicPathFollowerConfig;
import com.pathplanner.lib.util.PIDConstants;
import com.pathplanner.lib.util.ReplanningConfig;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.util.Units;

public final class DriveConstants {
  // Driving Parameters - Note that these are not the maximum capable speeds of
  // the robot, rather the allowed maximum speeds
  public static final double kMaxSpeedMetersPerSecond = 4.8; // 4.8 is the top speed possible with our modules 
  public static final double kMaxAngularSpeed = 2 * Math.PI; // radians per second

  public static final double kDirectionSlewRate = 4; // radians per second was 1.2 4
  public static final double kMagnitudeSlewRate = 3; // percent per second (1 = 100%) 2.5
  public static final double kRotationalSlewRate = 5; // percent per second (1 = 100%) 10

  // Chassis configuration
  public static final double kTrackWidth = Units.inchesToMeters(20.625);
  // Distance between centers of right and left wheels on robot
  public static final double kWheelBase = Units.inchesToMeters(20.625);
  // Distance between front and back wheels on robot

  public static final double kDriveRadius = Math.hypot(kTrackWidth / 2, kWheelBase / 2);

  public static final SwerveDriveKinematics kDriveKinematics = new SwerveDriveKinematics(
    new Translation2d(kWheelBase / 2, kTrackWidth / 2),
    new Translation2d(kWheelBase / 2, -kTrackWidth / 2),
    new Translation2d(-kWheelBase / 2, kTrackWidth / 2),
    new Translation2d(-kWheelBase / 2, -kTrackWidth / 2));

  // Angular offsets of the modules relative to the chassis in radians
  public static final double kFrontLeftChassisAngularOffset = -Math.PI / 2;
  public static final double kFrontRightChassisAngularOffset = 0;
  public static final double kBackLeftChassisAngularOffset = Math.PI;
  public static final double kBackRightChassisAngularOffset = Math.PI / 2;

  public static final HolonomicPathFollowerConfig kPathFollowerConfig = new HolonomicPathFollowerConfig( // HolonomicPathFollowerConfig, this should likely live in your Constants class
    new PIDConstants(5.0, 0.0, 0.0), // Translation PID constants
    new PIDConstants(5.0, 0.0, 0.0), // Rotation PID constants
    4.8, // Max module speed, in m/s
    kDriveRadius, // Drive base radius in meters. Distance from robot center to furthest module.
    new ReplanningConfig()); // Default path replanning config. See the API for the options here) TODO: enable replanning
}

