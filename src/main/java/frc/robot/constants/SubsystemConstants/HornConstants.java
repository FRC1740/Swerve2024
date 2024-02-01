package frc.robot.constants.SubsystemConstants;

import com.revrobotics.CANSparkBase.ControlType;

public class HornConstants {  
  public static final double HornMotorSpeed = .1;

  public static final int HornPeakCurrentLimit = 35; // Amps
  public static final int HornPeakDurationLimit = 200; // mSec
  public static final int HornContinuousCurrentLimit = 20; // Amps

  public static final double kPostionConversionFactor = 16 / 24; //Revolutions to meters
  public static final double kVelocityConversionFactor = kPostionConversionFactor / 60; //RPM to meters/second

  public static final ControlType kDefaultControlType = ControlType.kVelocity;

  public static final int kMaxHornRPM = 2800; // TODO: rough estimate

  public static final double kP = 0;

  public static final double kI = 0;

  public static final double kD = 0;
}