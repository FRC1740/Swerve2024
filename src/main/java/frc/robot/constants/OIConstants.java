package frc.robot.constants;


public final class OIConstants {
  public static final int kDriverControllerPort = 0;
  public static final int kCoDriverControllerPort = 1;

  public static final double kDriveDeadband = 0.05;
  
  public static final boolean kUseQuadraticInput = true;
  
  /**
   * This is the flag for using absolute angles for joystick rotations 
   * <br></br>
   * ie.  <code>(→) </code> on the joystick makes the robot go 
   * <code>[↑]</code> to <code>/↗/</code> to  <code>[→]</code>
   * <br></br>
   * Until it reaches the specified angle
   * See {@link frc.robot.commands.AlignAndDrive.AlignToJoystickAndDrive}
  */
  public static final boolean kUseFieldRelativeRotation = false;

  // Rumble Constants
  public static final double kTimeToRumbleController = 500;
  public static final double kRumbleStrength = 1;
}


