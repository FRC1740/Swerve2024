// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.DriveCommandConstants;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.PhotonVision;

public class AlignToTagPhotonVision extends Command {
  /** Creates a new AlignToTagPhotonVision. */
  DriveSubsystem m_drive;
  PhotonVision m_vision;
  Transform3d camToTarget;
  double x_error;
  double y_error;
  double theta_error;
  boolean XFinished;
  boolean YFinished;
  boolean ThetaFinished;

  public AlignToTagPhotonVision(DriveSubsystem drive, PhotonVision Vision) {
    addRequirements(drive);
    m_drive = drive;
    m_vision = Vision;

    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    camToTarget = m_vision.getCamToTarget();
    x_error = camToTarget.getX() - DriveCommandConstants.xGoal;
    y_error = camToTarget.getY() - DriveCommandConstants.yGoal;
    theta_error = camToTarget.getRotation().getAngle();


    
    m_drive.drive(
      -DriveCommandConstants.kXP * x_error, 
      -DriveCommandConstants.kYP * y_error, 
      -DriveCommandConstants.kThetaP * theta_error, false, true);

    if(x_error < DriveCommandConstants.kXToleranceMeters){
      XFinished = true;
    }
    if(y_error < DriveCommandConstants.kXToleranceMeters){
      YFinished = true;
    }
    if(theta_error < DriveCommandConstants.kXToleranceMeters){
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
