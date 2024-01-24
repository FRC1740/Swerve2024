package frc.robot.constants;

public class HornConstants { // all values are based on assumptions and lies 
  public static final double kHornMotorSpeed = .1;

  public static final int kHornPeakCurrentLimit = 35; // Amps
  public static final int kHornPeakDurationLimit = 200; // mSec
  public static final int kHornContinuousCurrentLimit = 20; // Amps

  public static final int kHornRightMotorPort = 8;
  public static final int kHornLeftMotorPort = 9;

  public static final double kVelocityConversionFactor = 1.0 / 2; // one revolution is 1/2 of a turn on the horn

  public static final double kP = 0;

  public static final double kI = 0;

  public static final double kD = 0;

  public static final double kTolerance = 50;  //Right now unit is RPM
}