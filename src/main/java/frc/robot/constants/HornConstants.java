package frc.robot.constants;

public class HornConstants {  
  public static final int kHornLeftMotorPort = 12;
  public static final int kHornRightMotorPort = 13;
  
  public static final double HornMotorSpeed = .1;

  public static final int HornPeakCurrentLimit = 35; // Amps
  public static final int HornPeakDurationLimit = 200; // mSec
  public static final int HornContinuousCurrentLimit = 20; // Amps

  public static final double kPostionConversionFactor = 0; //Revolutions to meters
  public static final double kVelocityConversionFactor = kPostionConversionFactor / 60; //RPM to meters/second

  public static final double kP = 0;

  public static final double kI = 0;

  public static final double kD = 0;
}