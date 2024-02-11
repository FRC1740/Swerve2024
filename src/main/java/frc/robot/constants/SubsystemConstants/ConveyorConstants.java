package frc.robot.constants.SubsystemConstants;

import frc.robot.constants.CurrentLimitDefaults;

public class ConveyorConstants {
  public static final double kConveyorGearRatio = 1/5;
  public static final double intakeFromFloorSpeed = .5; // Should approx. match floor intake
  public static final double intakeFromHornSpeed = .5; // Should approx. match floor intake
  public static final int kConveyorMotorCurrentLimit = CurrentLimitDefaults.kNeo; // Amps
}
