// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
// BTOKEJEN DOES NOT WORK
package frc.robot.commands.AlignAndDrive;

import edu.wpi.first.wpilibj2.command.Command;

public class AlignToJoystickAndDrive extends Command {
  /** Creates a new AlignToJoystickAndDrive. */
  private Command DriveAlignCommand;
  private double XInput, YInput;

  public AlignToJoystickAndDrive(double newXInput, double newYInput) {
    XInput = newXInput;
    YInput = newYInput;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  @Override
  public void initialize() {
    double angleToDriveTo = Math.toDegrees(Math.atan2(YInput, XInput));
    DriveAlignCommand = new DriveWhileAligning(angleToDriveTo, true, true);
    DriveAlignCommand.schedule();
  }
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return DriveAlignCommand.isFinished();
  }
}
