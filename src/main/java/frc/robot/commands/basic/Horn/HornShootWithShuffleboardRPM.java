// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.basic.Horn;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotShared;
import frc.robot.constants.SubsystemConstants.HornConstants;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.HornSubsystem;

public class HornShootWithShuffleboardRPM extends Command {

  private HornSubsystem m_horn;
  private ConveyorSubsystem m_conveyorSubsystem;
  private RobotShared m_robotShared;

  private long startingTime;


  /** Creates a new Shoot, runs at the set shuffleboard RPM 
   * This does not work.
  */
  public HornShootWithShuffleboardRPM() {
    m_robotShared = RobotShared.getInstance();
    m_horn = m_robotShared.getHornSubsystem();
    m_conveyorSubsystem = m_robotShared.getConveyorSubsystem();
    addRequirements(m_horn);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    startingTime = System.currentTimeMillis();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // m_horn.setRpmSetpoint(m_shootSpeed);
    // wait until shooter is at speed
    if(startingTime + HornConstants.kShootConveyorDelay < System.currentTimeMillis()){
      m_conveyorSubsystem.setConveyorSpeed(1.0);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // m_horn.setRpmSetpoint(0.0);
    m_conveyorSubsystem.setConveyorSpeed(0.0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
