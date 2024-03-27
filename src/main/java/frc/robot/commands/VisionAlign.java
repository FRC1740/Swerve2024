// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.ProfiledPIDCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.LimelightSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class VisionAlign extends ProfiledPIDCommand {
  /** Creates a new VisionAlign. Aligns the robot's heading to the Apriltag using the limelight. */
  public VisionAlign(DriveSubsystem m_drive, LimelightSubsystem m_limelight) {
    super(
        // The ProfiledPIDController used by the command
        new ProfiledPIDController(
            // The PID gains
            0.0123,
            0.001,
            0,
            // The motion profile constraints
            new TrapezoidProfile.Constraints(0, 0)),
        // This should return the measurement
        m_limelight::getXdeviation,
        // This should return the goal (can also be a constant)
        0,
        // This uses the output
        (output, setpoint) -> {m_drive.drive(0, 0, 
            output, false, false, false);
          // Use the output (and setpoint, if desired) here
        });
    // Use addRequirements() here to declare subsystem dependencies.
    // Configure additional PID options by calling `getController` here.
    getController().setTolerance(0.3);
    addRequirements(m_drive);
    addRequirements(m_limelight);
  }


  @Override
  public void end(boolean interrupted) {
    super.end(interrupted);
   
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return getController().atGoal();
  }
}
