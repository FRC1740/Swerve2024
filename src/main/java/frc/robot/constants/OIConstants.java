package frc.robot.constants;


public final class OIConstants {
  public static final int kDriverControllerPort = 0;
  public static final int kCoDriverControllerPort = 1;

  public static final double kDriveDeadband = 0.15;
  
  public static final boolean kUseQuadraticInput = true;
  /**
   * This is the flag for using absolute angles for joystick rotations 
   * <br></br>
   * ie.  <code>(→) </code> on the joystick makes the robot go 
   * <code>[↑]</code> to <code>/↗/</code> to  <code>[→]</code>
   * <br></br>
   * Until it reaches the specified angle
  */
  public static final boolean kUseFieldRelitiveRotation = false;
}


