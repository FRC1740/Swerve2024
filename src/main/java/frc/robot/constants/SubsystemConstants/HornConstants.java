package frc.robot.constants.SubsystemConstants;

import com.revrobotics.CANSparkBase.ControlType;

import frc.robot.constants.CurrentLimitDefaults;

public class HornConstants {  
  public static final double HornMotorSpeed = .1;

  public static final int HornPeakCurrentLimit = 35; // Amps
  public static final int HornPeakDurationLimit = 200; // mSec
  public static final int HornContinuousCurrentLimit = 20; // Amps

  public static final double kPostionConversionFactor = 16 / 24; //Revolutions to meters
  public static final double kVelocityConversionFactor = kPostionConversionFactor / 60; //RPM to meters/second

  public static final ControlType kDefaultControlType = ControlType.kVelocity;

  public static final double kMaxHornRPM = 2800; // TODO: rough estimate

  public static final double kP = 0;
  public static final double kI = 0;
  public static final double kD = 0;


  public static final int kShootConveyorDelay = 250; // TODO: tune

  public static final int kHornCurrentLimit = CurrentLimitDefaults.kNeo;

  public static final double kHornAmpShotMotorRPM = 350; // tuned
  public static final double kHornSpeakerShotMotorRPM = kMaxHornRPM ; // 2800 RPM  = 1 ie. full speed
}