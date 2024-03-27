// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.basic.Horn;

import edu.wpi.first.wpilibj2.command.Command;
import frc.Board.HornTab;
import frc.robot.RobotShared;
import frc.robot.constants.SubsystemConstants.HornConstants;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.HornSubsystem;

public class HornAmpShoot extends Command {

  private HornSubsystem m_horn;
  private HornTab m_HornTab;
  private ConveyorSubsystem m_conveyorSubsystem;
  private RobotShared m_robotShared;

  private long startingTime;

  /** Creates a new Amp Shot without using the Deflector */
  public HornAmpShoot() {
    m_robotShared = RobotShared.getInstance();
    m_HornTab = HornTab.getInstance();
    m_horn = m_robotShared.getHornSubsystem();
    m_conveyorSubsystem = m_robotShared.getConveyorSubsystem();
    addRequirements(m_horn);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    startingTime = System.currentTimeMillis();

    if(!HornShootVision.isShotSpeaker()) {
      System.out.println("Correct Guess");
      m_HornTab.setVisionGuessCorrect(true);
    }else{
      System.out.println("Incorrect Guess");
      m_HornTab.setVisionGuessCorrect(false);
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_horn.setRpmSetpoint(m_HornTab.getAmpTargetSpeed());
    // wait until shooter is at speed
    if(startingTime + HornConstants.kShootConveyorDelay < System.currentTimeMillis()){
      m_conveyorSubsystem.setConveyorSpeed(1.0);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_horn.setRpmSetpoint(0.0);
    m_conveyorSubsystem.setConveyorSpeed(0.0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
