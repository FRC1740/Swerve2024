// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.basic.Horn;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotShared;
import frc.robot.constants.SubsystemConstants.HornConstants;
import frc.robot.subsystems.HornSubsystem;

public class HornShoot extends Command {

  private HornSubsystem m_horn;
  private RobotShared m_robotShared;
  private double m_shootSpeed;

  /** Creates a new IntakeDeploy. Takes in a normalized -1 - 1 input and runs shoot at that speed*/
  public HornShoot(double shootSpeed) {
    m_robotShared = RobotShared.getInstance();
    m_horn = m_robotShared.getHornSubsystem();
    m_shootSpeed = shootSpeed * HornConstants.kMaxHornRPM;
    addRequirements(m_horn);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_horn.setRpmSetpoint(m_shootSpeed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_horn.setRpmSetpoint(0.0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
