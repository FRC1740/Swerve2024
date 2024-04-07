// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.basic.Horn;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotShared;
import frc.robot.constants.SubsystemConstants.HornConstants;
import frc.robot.subsystems.HornSubsystem;
import frc.robot.subsystems.LimelightSubsystem;
import frc.robot.constants.VisionConstants;

public class HornShootVision extends Command {

  private HornSubsystem m_horn;
  private RobotShared m_robotShared;
  private LimelightSubsystem m_limelight;

  private boolean isSpeakerShot;
  private Command m_hornShoot;

  /** Creates a new Shoot Command. 
   * This determines whether to use the Amp score or Speaker based off the last seen scoring apriltag seen by the Limelight
   * This is how HornShootShuffleboard should be written, but this does not actually work because the deflector does not work
   * The reason is that calling execute for some reason doesn't update the timer accurately. I could use schedule, however that doesn't allow canceling the command.
   */
  public HornShootVision() {
    m_robotShared = RobotShared.getInstance();
    m_horn = m_robotShared.getHornSubsystem();
    m_limelight = m_robotShared.getLimelight();
    addRequirements(m_horn);
    addRequirements(m_limelight);
  }

  public static boolean isShotSpeaker(){ // chance that these could be null
    return VisionConstants.isSpeakerID((int) RobotShared.getInstance().getLimelight().getLastSeenScoringAprilTag());
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    isSpeakerShot = VisionConstants.isSpeakerID((int) m_limelight.getLastSeenScoringAprilTag());
    if(isSpeakerShot){
      m_hornShoot = new HornShoot(HornConstants.kHornSpeakerShotMotorRPM);
    } else {
      m_hornShoot = new HornAmpShootWithDeflector();
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_hornShoot.execute();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_hornShoot.end(true);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
