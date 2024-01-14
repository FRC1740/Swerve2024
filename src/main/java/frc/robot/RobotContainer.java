// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.MathUtil;

import edu.wpi.first.wpilibj.XboxController;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

import frc.robot.constants.OIConstants;
import frc.robot.commands.AlignToTagPhotonVision;
import frc.robot.commands.basic.DriveWhileAligning;
import frc.robot.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.path.PathPlannerPath;

/*
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems
  private DriveSubsystem m_robotDrive;

  private RobotShared m_robotShared = RobotShared.getInstance();

  PathPlannerPath m_ExamplePath = PathPlannerPath.fromPathFile("Example Path");

  // The driver's controller
  CommandXboxController m_driverController;

  SendableChooser<Command> autoChooser;

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    initSubsystems();
    initInputDevices();


    //Creates sendable chooser for use with PathPlanner autos
    autoChooser = AutoBuilder.buildAutoChooser();

    //Must register commands used in PathPlanner autos
    NamedCommands.registerCommand("AlignToTagPhotonVision", new AlignToTagPhotonVision());

    // Configure the button bindings
    configureButtonBindings();

    // Configure default commands
    m_robotDrive.setDefaultCommand(
      // The left stick controls translation of the robot.
      // Turning is controlled by the X axis of the right stick.
      // If any changes are made to this, please update DPad driver controls
      new RunCommand(
        () -> m_robotDrive.drive(
          -MathUtil.applyDeadband(m_driverController.getLeftY(), OIConstants.kDriveDeadband),
          -MathUtil.applyDeadband(m_driverController.getLeftX(), OIConstants.kDriveDeadband),
          -MathUtil.applyDeadband(m_driverController.getRightX(), OIConstants.kDriveDeadband),
          true, true),
        m_robotDrive));
  }

  private void initSubsystems() {
    // New ------------------------------------------------------------------------------------------
    m_robotShared = RobotShared.getInstance();

    m_robotDrive = m_robotShared.getDriveSubsystem();
    // ----------------------------------------------------------------------------------------------
  }
  private void initInputDevices() {
    m_driverController = m_robotShared.getDriverController();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by
   * instantiating a {@link edu.wpi.first.wpilibj.GenericHID} or one of its
   * subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then calling
   * passing it to a
   * {@link JoystickButton}.
   */
  private void configureButtonBindings() {
    m_driverController.x()
      .whileTrue(new RunCommand(() -> m_robotDrive.setXFormation()));

    m_driverController.a()
      .onTrue(new InstantCommand(() -> m_robotDrive.zeroHeading()));

    m_driverController.b()
      .onTrue(new InstantCommand(() -> m_robotDrive.resetOdometry(m_ExamplePath.getPreviewStartingHolonomicPose())));
    m_driverController.b()
      .whileTrue(AutoBuilder.followPath(m_ExamplePath));

    for(int angleForDPad = 0; angleForDPad <= 7; angleForDPad++) { // Sets all the DPad to rotate to an angle
      new POVButton(m_driverController.getHID(), angleForDPad * 45)
        .onTrue(new SequentialCommandGroup(
          new DriveWhileAligning(angleForDPad * -45, true, true).withTimeout(3),
          new WaitCommand(.1))); // small delay to prevent reinput after angled DPad input
    }
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return autoChooser.getSelected();
  }
}
