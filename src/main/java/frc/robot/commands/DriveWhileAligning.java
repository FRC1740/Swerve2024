// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.RobotShared;
import frc.robot.constants.DriveConstants;
import frc.robot.constants.OIConstants;
import frc.robot.subsystems.DriveSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class DriveWhileAligning extends PIDCommand {
  /** Creates a new DriveWhileAligning. */
  
  static RobotShared m_robotShared = RobotShared.getInstance();
  private static DriveSubsystem m_drive = m_robotShared.getDriveSubsystem();
  private static CommandXboxController m_driverController = m_robotShared.getDriverController();
  public DriveWhileAligning(double angle, boolean fieldRelative, boolean rateLimit) {
    super(
      // The controller that the command will use
      new PIDController(DriveConstants.kDriveWhileAligningP, 0, 0),
      // This should return the measurement
      () -> m_drive.getHeading(), // replacing this with getRotation2d could mean I don't have to reverse the for loop
      // This should return the setpoint (can also be a constant)
      () -> angle,
      // This uses the output
      output -> {
        m_drive.drive(
          -MathUtil.applyDeadband(m_driverController.getLeftY(), OIConstants.kDriveDeadband), 
          -MathUtil.applyDeadband(m_driverController.getLeftX(), OIConstants.kDriveDeadband), 
          output, fieldRelative, rateLimit, OIConstants.kUseQuadraticInput);
      });
    // Use addRequirements() here to declare subsystem dependencies.
    // Configure additional PID options by calling `getController` here.
    addRequirements(m_drive);
    getController().enableContinuousInput(-180, 180);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return getController().atSetpoint();
  }
}
