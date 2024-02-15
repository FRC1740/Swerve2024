package frc.robot.constants.SubsystemConstants;

import frc.robot.constants.CurrentLimitDefaults;

public class DeflectorConstants {
  public static double kEncoderMinOutput = 0;
  public static double kEncoderMaxOutput = 18;

  public static double kAmpRetractedPosition = kEncoderMinOutput; // in 0-18 encoder
  public static double kAmpNotePopPosition = kEncoderMaxOutput; // in 0-18 encoder
  public static double kAmpScoringPosition = 3; // in 0-18 encoder
  
  public static int kDeflectorMotorCurrentLimit = CurrentLimitDefaults.kSmallBrushedMotor;

  public static double kP = 0.01;
  public static double kI = 0.0;
  public static double kD = 0.0;
  public static double kFF = 0.0;

  public static float kDeflectorMotorForwardSoftLimit = 16f;
  public static float kDeflectorMotorBackwardSoftLimit = 1.5f;

  public static double kDeflectorMotorMaxOutput = 1.0;
  public static double kDeflectorMotorMinOutput = -1.0;
}
