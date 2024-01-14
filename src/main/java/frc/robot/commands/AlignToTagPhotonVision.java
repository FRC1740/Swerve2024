// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotShared;
import frc.robot.constants.DriveCommandConstants;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.PhotonVision;

public class AlignToTagPhotonVision extends Command {
  /** Creates a new AlignToTagPhotonVision. */
  DriveSubsystem m_drive;
  PhotonVision m_photonVision;
  Transform3d camToTarget;
  private RobotShared m_robotShared;

  double x_error;
  double y_error;
  double theta_error;
  boolean XFinished;
  boolean YFinished;
  boolean ThetaFinished;

  public AlignToTagPhotonVision() {
    m_robotShared = RobotShared.getInstance();
    m_drive = m_robotShared.getDriveSubsystem();
    m_photonVision = m_robotShared.getPhotonVision();
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    camToTarget = m_photonVision.getCamToTarget();
    x_error = DriveCommandConstants.xGoal - camToTarget.getX() ;
    y_error = DriveCommandConstants.yGoal - camToTarget.getY() ;
    theta_error = camToTarget.getRotation().getAngle();


    
    m_drive.drive(
      DriveCommandConstants.kXP * x_error, 
      DriveCommandConstants.kYP * y_error, 
      DriveCommandConstants.kThetaP * theta_error, false, true);

    if(Math.abs(x_error) < DriveCommandConstants.kXToleranceMeters){
      XFinished = true;
    }
    if(Math.abs(y_error) < DriveCommandConstants.kYToleranceMeters){
      YFinished = true;
    }
    if(Math.abs(theta_error) < DriveCommandConstants.kXToleranceMeters){
      ThetaFinished = true;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return XFinished && YFinished && ThetaFinished;
  }
}
