package frc.robot.constants;

public class SubsystemConstants {

  public final class ConveyorConstants {
    public static final int kConveyorMotorPort = 11;
    public static final double kConveyorGearRatio = 1/5;
    public static final double intakeFromFloorSpeed = .5; // Should approx. match floor intake
    public static final double intakeFromHornSpeed = .5; // Should approx. match floor intake

  }

  public final class GroundIntakeConstants {
    public static final int kGroundIntakeMotorPort = 10;
    public static final double kGroundIntakeGearRatio = 1/3;
    public static final double GroundIntakeMotorSpeed = .3; // Should approx. match conveyor
  }

  public class HornConstants {  
    public static final int kHornLeftMotorPort = 12;
    public static final int kHornRightMotorPort = 13;
    public static final double HornMotorSpeed = .1;
  
    public static final int HornPeakCurrentLimit = 35; // Amps
    public static final int HornPeakDurationLimit = 200; // mSec
    public static final int HornContinuousCurrentLimit = 20; // Amps
  }

  public final class DriveConstants {
    // Driving Parameters - Note that these are not the maximum capable speeds of
    // the robot, rather the allowed maximum speeds
    public static final double kMaxSpeedMetersPerSecond = 1;
    public static final double kMaxAngularSpeed = 2 * Math.PI; // radians per second
  
    public static final double kDirectionSlewRate = 1.2; // radians per second
    public static final double kMagnitudeSlewRate = 1.8; // percent per second (1 = 100%)
    public static final double kRotationalSlewRate = 2.0; // percent per second (1 = 100%)
  }
}

