// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.basic.Horn;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotShared;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.HornSubsystem;

public class HornIntake extends Command {

  private HornSubsystem m_horn;
  private ConveyorSubsystem m_conveyorSubsystem;
  private RobotShared m_robotShared;
  private double m_intakeSpeed;

  /** Creates a new IntakeDeploy. */
  public HornIntake(double intakeSpeed) {
    m_robotShared = RobotShared.getInstance();
    m_horn = m_robotShared.getHornSubsystem();
    m_conveyorSubsystem = m_robotShared.getConveyorSubsystem();
    m_intakeSpeed = intakeSpeed;
    addRequirements(m_horn);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_horn.setHornSpeed(m_intakeSpeed); // TODO: make this a set target so that way PID is always in control
    m_conveyorSubsystem.setConveyorSpeed(m_intakeSpeed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_horn.setHornSpeed(0.0);
    m_conveyorSubsystem.setConveyorSpeed(0.0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
