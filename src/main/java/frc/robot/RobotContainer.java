// Copyright (c) FIRST and other WPILib contributors.
//
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj.XboxController;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.constants.OIConstants;
import frc.robot.constants.SubsystemConstants.HornConstants;
import frc.Board.DriveTrainTab;
import frc.Board.DriverTab;
import frc.Board.GroundIntakeTab;
import frc.Board.HornTab;
import frc.robot.commands.VisionAlign;
import frc.robot.commands.AlignAndDrive.AlignToJoystickAndDrive;
import frc.robot.commands.AlignAndDrive.AlignToNearestAngleAndDrive;
import frc.robot.commands.AlignAndDrive.DriveWhileAligning;
import frc.robot.commands.basic.GroundEject;
import frc.robot.commands.basic.GroundIntake;
import frc.robot.commands.basic.GroundIntakeDefault;
import frc.robot.commands.basic.GroundIntakeNoHorn;
import frc.robot.commands.basic.Horn.HornAmpShoot;
import frc.robot.commands.basic.Horn.HornAmpShootWithDeflector;
import frc.robot.commands.basic.Horn.HornIntake;
import frc.robot.commands.basic.Horn.HornShoot;
import frc.robot.commands.basic.Horn.HornShootShuffleboard;
import frc.robot.commands.basic.Horn.ShootAndIntake;
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.DeflectorSubsytem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.GroundIntakeSubsystem;
import frc.robot.subsystems.HornSubsystem;
import frc.robot.subsystems.LimelightSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import java.util.List;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.PathPlannerAuto;
import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.path.PathPlannerTrajectory;

/*
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems
  private DriveSubsystem m_robotDrive;
  private HornSubsystem m_hornSubsystem;
  private ConveyorSubsystem m_conveyorSubsystem;
  private ClimberSubsystem m_climberSubsystem;
  private DeflectorSubsytem m_deflectorSubsystem;
  private GroundIntakeSubsystem m_groundIntakeSubsystem;
  private LimelightSubsystem m_limelight;
  
  private RobotShared m_robotShared = RobotShared.getInstance();

  // PathPlannerPath m_ExamplePath = PathPlannerPath.fromPathFile("Example Path");

  // The driver's controller
  CommandXboxController m_driverController;
  CommandXboxController m_coDriverController;


  SendableChooser<Command> autoChooser;

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    initSubsystems();
    initInputDevices();

    //Must register commands used in PathPlanner autos
    NamedCommands.registerCommand("GroundIntake", new GroundIntakeNoHorn(1).withTimeout(2));
    NamedCommands.registerCommand("GroundIntakeMedium", new GroundIntakeNoHorn(1).withTimeout(5));
    NamedCommands.registerCommand("GroundIntakeLong", new GroundIntakeNoHorn(1).withTimeout(10));
    NamedCommands.registerCommand("ShootSpeaker", new HornShoot(HornConstants.kHornSpeakerShotMotorRPM).withTimeout(.5));
    NamedCommands.registerCommand("ShootAmp", new HornAmpShoot().withTimeout(1)); // We don't use the amp so deflector not needed
    NamedCommands.registerCommand("ShootAmpWithDeflector", new HornAmpShootWithDeflector().withTimeout(3)); // We use the amp so deflector needed
    NamedCommands.registerCommand("SpinupShooter", new InstantCommand(() -> m_hornSubsystem.setRpmSetpoint(7000.0)));
    NamedCommands.registerCommand("ResetGyro", new InstantCommand(() -> m_robotDrive.zeroHeading()));
    NamedCommands.registerCommand("ShootAndIntake", new ShootAndIntake(.6).withTimeout(15)); // .6 is so it doesn't enter our zone

    //Creates sendable chooser for use with PathPlanner autos
    autoChooser = AutoBuilder.buildAutoChooser();
    
    autoChooser.onChange((command) -> {
      List<edu.wpi.first.math.trajectory.Trajectory.State> states = new java.util.ArrayList<>();
      for (PathPlannerPath path : PathPlannerAuto.getPathGroupFromAutoFile(command.getName())) {
        ChassisSpeeds speeds = new ChassisSpeeds(0, 0, 0);
        List<PathPlannerTrajectory.State> pathplannerStates = path.getTrajectory(speeds, path.getStartingDifferentialPose().getRotation()).getStates();
        
        //loop over states
        for(int i = 0; i < pathplannerStates.size(); i++){
          edu.wpi.first.math.trajectory.Trajectory.State state = new edu.wpi.first.math.trajectory.Trajectory.State(
            pathplannerStates.get(i).timeSeconds,
            pathplannerStates.get(i).velocityMps,
            pathplannerStates.get(i).accelerationMpsSq,
            new Pose2d(
              pathplannerStates.get(i).getTargetHolonomicPose().getTranslation().getX(),
              pathplannerStates.get(i).getTargetHolonomicPose().getTranslation().getY(),
              pathplannerStates.get(i).getTargetHolonomicPose().getRotation()
            ),
            pathplannerStates.get(i).curvatureRadPerMeter
          );
          states.add(state);
        }
      }
      if(states == null || states.size() == 0){
        System.out.println("No states found for " + command.getName());
        return;
      }

      Trajectory traj = new Trajectory(states);

      DriverTab.getInstance().setTrajectory(traj);
      SmartDashboard.putString("Selected Auto", command.getName());
    });

    SendableChooser<Command> isPathFlippedSendableChooser = new SendableChooser<>();

    isPathFlippedSendableChooser.setDefaultOption("Not Flipped", new InstantCommand(() -> DriveTrainTab.getInstance().setIsPathFlipped(0)));
    isPathFlippedSendableChooser.addOption("Flipped", new RunCommand(() -> DriveTrainTab.getInstance().setIsPathFlipped(1)));


    isPathFlippedSendableChooser.onChange((command) -> {
      System.out.println("Is Path Flipped: " + isPathFlippedSendableChooser.getSelected().getName());
      
      if(isPathFlippedSendableChooser.getSelected().getName().contentEquals("RunCommand")) { // SO SO SO JANKY
        DriverTab.getInstance().flipTrajectory(true);
        DriveTrainTab.getInstance().setIsPathFlipped(1);
      }else{
        DriverTab.getInstance().flipTrajectory(false);
        DriveTrainTab.getInstance().setIsPathFlipped(0);
      }
    });
    
    SmartDashboard.putData("Is Path Flipped", isPathFlippedSendableChooser);
    SmartDashboard.putData("Auto Mode", autoChooser);

    // Configure the button bindings
    configureButtonBindings();
    buttonBoardControls();
    // flightStickControls();

    // Configure default commands
      // The left stick controls translation of the robot.
      // Turning is controlled by the X axis of the right stick.
      // If any changes are made to this, please update DPad driver controls
    if(OIConstants.kUseFieldRelativeRotation){
      m_robotDrive.setDefaultCommand(new RunCommand(() -> 
        new AlignToJoystickAndDrive(
          m_driverController.getRightX(),
          m_driverController.getRightY(),
          true, true, 
          (-MathUtil.applyDeadband(m_driverController.getRightX(), OIConstants.kDriveDeadband) + 
          -MathUtil.applyDeadband(m_driverController.getRightY(), OIConstants.kDriveDeadband) != 0) ? 1 : 0).execute(), m_robotDrive));
    }else{
      m_robotDrive.setDefaultCommand(
        new RunCommand(() -> m_robotDrive.drive(
            -MathUtil.applyDeadband(m_driverController.getLeftY(), OIConstants.kDriveDeadband),
            -MathUtil.applyDeadband(m_driverController.getLeftX(), OIConstants.kDriveDeadband),
            -MathUtil.applyDeadband(m_driverController.getRightX(), OIConstants.kDriveDeadband),
            true, true, OIConstants.kUseQuadraticInput),
          m_robotDrive));
    }

    m_deflectorSubsystem.setDefaultCommand(
      new RunCommand(() -> m_deflectorSubsystem.seekSetpoint(),
      m_deflectorSubsystem));

    // buttonBoardSwitches[1][1] // bottom right
    m_conveyorSubsystem.setDefaultCommand(new GroundIntakeDefault(1));
  }

  private void initSubsystems() {
    m_robotShared = RobotShared.getInstance();

    m_robotDrive = m_robotShared.getDriveSubsystem();
    m_robotShared.getSensorSubsystem(); // no setting because not used
    m_limelight = m_robotShared.getLimelight();
    m_hornSubsystem = m_robotShared.getHornSubsystem();
    m_conveyorSubsystem = m_robotShared.getConveyorSubsystem();
    m_groundIntakeSubsystem = m_robotShared.getGroundIntakeSubsystem();
    m_climberSubsystem = m_robotShared.getClimberSubsystem();
    // m_robotShared.getPhotonVision();
    m_robotShared.getLedSubsystem();
    
    m_deflectorSubsystem = m_robotShared.getDeflectorSubsystem();

    m_robotShared.getInspiration();

    DriverTab.getInstance();
  }
  private void initInputDevices() {
    m_coDriverController = m_robotShared.getCoDriverController();
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
    m_driverController.start()
      .onTrue(new InstantCommand(() -> m_robotDrive.zeroHeading()));

    //Robot relative mode
    m_driverController.leftBumper()
      .whileTrue(
        new HornIntake(-0.4)
      );
    m_driverController.y()
      .whileTrue(
        new VisionAlign(m_robotDrive, m_robotShared.getLimelight())
      );
    //Half Speed mode
    m_driverController.rightBumper()
      .whileTrue( 
        new SequentialCommandGroup(
          new GroundIntake(1),
          new HornIntake(-0.2))
      );

    m_driverController.rightTrigger()
      .whileTrue( 
        new HornShootShuffleboard()
      );
    m_driverController.leftTrigger()
      .whileTrue(new RunCommand(
        () -> m_robotDrive.drive(
          -MathUtil.applyDeadband(m_driverController.getLeftY(), OIConstants.kDriveDeadband) / 2,
          -MathUtil.applyDeadband(m_driverController.getLeftX(), OIConstants.kDriveDeadband) / 2,
          -MathUtil.applyDeadband(m_driverController.getRightX(), OIConstants.kDriveDeadband) / 2,
          true, true, OIConstants.kUseQuadraticInput),
        m_robotDrive));
      // .whileTrue(
      //   new HornAmpShootWithDeflector()
      // );
    // // Testing path following
    // m_driverController.b()
    //   .whileTrue(new SequentialCommandGroup(
    //     new InstantCommand(() -> m_robotDrive.resetOdometry(m_ExamplePath.getPreviewStartingHolonomicPose())),
    //     autoChooser.getSelected()
    //   ));
    m_driverController.b()
      .whileTrue(new RunCommand(
        () -> m_robotShared.getHornSubsystem().Shoot(.5),
        m_robotDrive));
    m_driverController.a()
      .whileTrue(
        new RunCommand(() -> m_deflectorSubsystem.setDeflectorSpeed(-.3),
         m_deflectorSubsystem)
      )
      .onFalse(new InstantCommand(() -> m_deflectorSubsystem.setDeflectorSpeed(0)));

    m_driverController.y()
      .whileTrue(
        new RunCommand(() -> m_deflectorSubsystem.setDeflectorSpeed(.3),
         m_deflectorSubsystem)
      )
      .onFalse(new InstantCommand(() -> m_deflectorSubsystem.setDeflectorSpeed(0)));
  
    //Testing path following
    // m_driverController.b()
    //   .whileTrue(new SequentialCommandGroup(
    //     new InstantCommand(() -> m_robotDrive.resetOdometry(m_ExamplePath.getPreviewStartingHolonomicPose())),
    //     AutoBuilder.followPath(m_ExamplePath)
    //   ));
      
      // TESTED
    m_driverController.back()
      .whileTrue(
        new GroundEject(-.3)
      );

    // This is a stick click
    m_driverController.rightStick()
      .onTrue(new SequentialCommandGroup(
        // double normalizedAngle = (int)((m_robotDrive.getHeading() + 180) / (360 / 8)),  // This is the uncondensed code
        new AlignToNearestAngleAndDrive(true, true).withTimeout(3))
      );
    // Something super janky is happening here but it works so
    for(int angleForDPad = 0; angleForDPad <= 7; angleForDPad++) { // Sets all the DPad to rotate to an angle
      new POVButton(m_driverController.getHID(), angleForDPad * 45)
        .onTrue(
          new DriveWhileAligning(angleForDPad * -45, true, true).withTimeout(3)); // -45 could be 45 
    }
    m_driverController.leftStick()
      .whileTrue(new RunCommand(
        () -> m_robotDrive.drive(
          -MathUtil.applyDeadband(m_driverController.getLeftY(), OIConstants.kDriveDeadband) / 2,
          -MathUtil.applyDeadband(m_driverController.getLeftX(), OIConstants.kDriveDeadband) / 2,
          -MathUtil.applyDeadband(m_driverController.getRightX(), OIConstants.kDriveDeadband) / 2,
          true, true, OIConstants.kUseQuadraticInput),
        m_robotDrive));
  }

  void buttonBoardControls(){
    // define buttons 
    Trigger buttonBoardButtons[][] = new Trigger[3][3];
    for(int i = 0; i < 3; i++){
      for(int j = 0; j < 3; j++){
        buttonBoardButtons[i][j] = m_coDriverController.button((i * 3) + j + 1);
      }
    }

    Trigger buttonBoardSwitches[][] = new Trigger[2][2];
    for(int i = 0; i < 2; i++){
      for(int j = 0; j < 2; j++){
        buttonBoardSwitches[i][j] = m_coDriverController.button((((i * 2) + j) * 2) + 10); // starts st 10 offset
      }
    }
    buttonBoardSwitches[0][1] // top
    .whileTrue(
      new RunCommand(() -> HornTab.getInstance().setHornTargetSpeed(HornConstants.kHornSpeakerShotMotorRPM))
    )
    .onFalse(
      new InstantCommand(() -> HornTab.getInstance().setHornTargetSpeed(0.0))
    );

    buttonBoardSwitches[1][1] // bottom right
    .onTrue(
      new InstantCommand(() -> GroundIntakeTab.getInstance().setGroundIntakeDefaultEnabled(true))
    )
    .onFalse(
      new InstantCommand(() -> GroundIntakeTab.getInstance().setGroundIntakeDefaultEnabled(false))
    );
    // buttonBoardSwitches[1][1] // bottom right
    // .onTrue(
    //   new InstantCommand(() -> m_deflectorSubsystem.resetDeflectorEncoder())
    // );
    
    buttonBoardSwitches[0][0]
    .onTrue(
      new InstantCommand(() -> m_robotDrive.setAutoRotationOffset(0.0, true))
    );
    
    buttonBoardSwitches[1][0]
    .onTrue(
      new InstantCommand(() -> m_limelight.toggleLED(true))
    )
    .onFalse(
      new InstantCommand(() -> m_limelight.toggleLED(false))
    );
    
    buttonBoardButtons[0][0]
      .whileTrue( 
        new ParallelCommandGroup(
          new RunCommand(() -> m_hornSubsystem.setHornSpeed(1)),
          new RunCommand(() -> m_conveyorSubsystem.setConveyorSpeed(1))
        )
      )
      .onFalse( 
        new ParallelCommandGroup(
          new InstantCommand(() -> m_hornSubsystem.setHornSpeed(0)),
          new InstantCommand(() -> m_conveyorSubsystem.setConveyorSpeed(0))
        )
      );
    buttonBoardButtons[0][1]
      .whileTrue( 
        new ParallelCommandGroup(
          new RunCommand(() -> m_hornSubsystem.setHornSpeed(-.3)),
          new RunCommand(() -> m_conveyorSubsystem.setConveyorSpeed(-.3))
        )
      )
      .onFalse(
        new ParallelCommandGroup(
          new InstantCommand(() -> m_hornSubsystem.setHornSpeed(0)),
          new InstantCommand(() -> m_conveyorSubsystem.setConveyorSpeed(0))
        )
      );
    buttonBoardButtons[0][2]
      .whileTrue( 
        new ParallelCommandGroup(
          new RunCommand(() -> m_hornSubsystem.setHornSpeed(-.3)),
          new RunCommand(() -> m_conveyorSubsystem.setConveyorSpeed(.3)),
          new RunCommand(() -> m_groundIntakeSubsystem.setGroundIntakeSpeed(.3))
        )
      )
      .onFalse(
        new ParallelCommandGroup(
          new InstantCommand(() -> m_hornSubsystem.setHornSpeed(0)),
          new InstantCommand(() -> m_conveyorSubsystem.setConveyorSpeed(0)),
          new InstantCommand(() -> m_groundIntakeSubsystem.setGroundIntakeSpeed(0))
        )
      );

    // buttonBoardButtons[1][0]
    //   .onTrue(
    //     new InstantCommand(() -> m_robotDrive.setAutoRotationOffset(Optional.of(null)))
    //   );
    buttonBoardButtons[1][0]
      .whileTrue(
        new RunCommand(() -> m_climberSubsystem.setClimberMotorSpeed(1))
      )
      .onFalse(
        new InstantCommand(() -> m_climberSubsystem.setClimberMotorSpeed(0))
      );
    buttonBoardButtons[1][1]
      .whileTrue(
        new RunCommand(() -> m_climberSubsystem.setClimberMotorSpeed(-1))
      )
      .onFalse(
        new InstantCommand(() -> m_climberSubsystem.setClimberMotorSpeed(0))
      );
    buttonBoardButtons[1][2]
      .whileTrue(
        new GroundEject(-.3)
      );
    buttonBoardButtons[2][0]
      .whileTrue(
        new RunCommand(() -> m_climberSubsystem.setClimberMotorSpeed(.15))
      )
      .onFalse(
        new InstantCommand(() -> m_climberSubsystem.setClimberMotorSpeed(0))
      );
    buttonBoardButtons[2][1]
      .whileTrue(
        new RunCommand(() -> m_climberSubsystem.setClimberMotorSpeed(-.6))
      )
      .onFalse(
        new InstantCommand(() -> m_climberSubsystem.setClimberMotorSpeed(0))
      );
    buttonBoardButtons[2][2]
      .onTrue(
        new InstantCommand(() -> m_climberSubsystem.toggleSoftLimit())
      );
  }

  void flightStickControls(){
    m_driverController.x()
      .onTrue(
        new InstantCommand(() -> m_robotDrive.zeroHeading())
      );


    m_driverController.button(22)
      .whileTrue(
        new HornAmpShootWithDeflector()
      );
    m_driverController.button(5)
      .whileTrue(
      new SequentialCommandGroup(
        new GroundIntake(1),
        new HornIntake(-0.2))
      );

    m_driverController.a()
      .whileTrue( 
        new HornShoot(HornConstants.kHornSpeakerShotMotorRPM)
      );
    m_driverController.button(10)
      .whileTrue(
        new GroundEject(-.3)
      );
    m_driverController.rightBumper()
      .whileTrue( 
        new SequentialCommandGroup(
          new GroundIntake(1),
          new HornIntake(-0.2))
      );
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
