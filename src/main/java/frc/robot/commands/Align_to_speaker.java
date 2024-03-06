// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotShared;
import frc.robot.constants.AutoConstants;
import frc.robot.constants.DriveCommandConstants;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.LimelightSubsystem;

public class Align_to_speaker extends Command {
  /** Creates a new Align_to_speaker. */
  private DriveSubsystem m_drive;
  private LimelightSubsystem m_limelight;

  private RobotShared m_robotShared;

  double angle_to_speaker;
  double[] translation_to_speaker;
  double distance_to_speaker;
  double distance_error;
  double drive_speed;
  double x_speed;
  double y_speed;
  double rot;

  public Align_to_speaker() {
    // Use addRequirements() here to declare subsystem dependencies.
    m_robotShared = RobotShared.getInstance();
    m_drive = m_robotShared.getDriveSubsystem();
    m_limelight = m_robotShared.getLimelight();
    addRequirements(m_drive);
    addRequirements(m_limelight);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

    //Set priority id to the speaker tag so that we target the right tag
    if (m_robotShared.getAlliance() == DriverStation.Alliance.Red){
      m_limelight.setPriorityID(4); //Red center apriltag
    }
    else{
      m_limelight.setPriorityID(7); //Blue center apriltag
    }

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //The idea is we aim the robot towards the speaker, and drive towards it. 
    //If we are aimed at the speaker and a certain distance from it, we can shoot. 
    //Regardless if we are lined up with the base of the speaker. 
    //May need the shooter to be aimed down, but I heard discussions about doing that already
    //May NOT need the shooter to be aimed down. (May be able to tune distance_from_speaker_goal to work with the current configuration)
    angle_to_speaker = m_limelight.getXdeviation();
    translation_to_speaker = m_limelight.getTranslationToAprilTag();
    distance_to_speaker = Math.hypot(translation_to_speaker[0], translation_to_speaker[1]);
    distance_error = distance_to_speaker - DriveCommandConstants.distance_from_speaker_goal;
    drive_speed = distance_error * DriveCommandConstants.kTranslationP;
    rot = angle_to_speaker * DriveCommandConstants.kThetaP;
    
    //Robot needs to drive towards speaker regardless of where it is pointed so have to do some trig
    x_speed = Math.cos(angle_to_speaker) * drive_speed; //Forward velocity. Using cos not sin because we want full speed forward when angle_to_speaker = 0
    y_speed = Math.sin(angle_to_speaker) * drive_speed; //Lateral velocity.

    m_drive.drive(x_speed, y_speed, rot, false, false, false);



  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Math.abs(distance_error) < DriveCommandConstants.kDistanceTolerance && Math.abs(angle_to_speaker) < DriveCommandConstants.kThetaToleranceDegrees;
  }
}
