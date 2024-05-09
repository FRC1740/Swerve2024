package frc.robot.constants.SubsystemConstants;

import frc.robot.constants.CurrentLimitDefaults;

public class DeflectorConstants {
  public static double kEncoderMinOutput = 0;
  public static double kEncoderMaxOutput = 14.5;
  public static double kPostionConversionFactor = 1 / 12;

  public static double kAmpRetractedPosition = kEncoderMinOutput; // in 0-kEncoderMaxOutput encoder
  public static double kAmpNotePopPosition = 4; // in 0-kEncoderMaxOutput encoder
  public static double kAmpScoringPosition = 1; // in 0-kEncoderMaxOutput encoder

  // This is not a brushed motor but I don't want to break the gear on the licker
  public static int kDeflectorMotorCurrentLimit = CurrentLimitDefaults.kSmallBrushedMotor + 4; 

  public static double kP = .3;
  public static double kI = 0.0;
  public static double kD = 0.0;
  public static double kFF = 0.0;

  public static float kDeflectorMotorForwardSoftLimit = 3.0f; // 11.5f
  public static float kDeflectorMotorBackwardSoftLimit = 1.5f / 3.5f;

  public static double kDeflectorMotorMaxOutput = 1.0;
  public static double kDeflectorMotorMinOutput = -1.0;
}
