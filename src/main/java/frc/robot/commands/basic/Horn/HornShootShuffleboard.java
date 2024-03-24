// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.basic.Horn;

import edu.wpi.first.wpilibj2.command.Command;
import frc.Board.HornTab;
import frc.robot.RobotShared;
import frc.robot.constants.SubsystemConstants.HornConstants;
import frc.robot.subsystems.HornSubsystem;

public class HornShootShuffleboard extends Command {

  private HornSubsystem m_horn;
  private RobotShared m_robotShared;

  private boolean isSpeakerShot;
  private Command m_hornShoot;
  private HornTab m_HornTab = HornTab.getInstance();

  /** Creates a new Shoot. Takes in an RPM*/
  public HornShootShuffleboard() {
    m_robotShared = RobotShared.getInstance();
    m_horn = m_robotShared.getHornSubsystem();
    addRequirements(m_horn);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    isSpeakerShot = m_HornTab.getHornTargetSpeed() == HornConstants.kHornSpeakerShotMotorRPM;

    if(isSpeakerShot){
      m_hornShoot = new HornShoot(HornConstants.kHornSpeakerShotMotorRPM);
    } else {
      m_hornShoot = new HornAmpShootWithDeflector();
    }
    // m_hornShoot.schedule();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_hornShoot.execute();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_hornShoot.end(true);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
