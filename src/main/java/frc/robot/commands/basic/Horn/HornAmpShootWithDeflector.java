// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.basic.Horn;

import edu.wpi.first.wpilibj2.command.Command;
import frc.Board.HornTab;
import frc.robot.RobotShared;
import frc.robot.constants.SubsystemConstants.DeflectorConstants;
import frc.robot.constants.SubsystemConstants.HornConstants;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.HornSubsystem;

public class HornAmpShootWithDeflector extends Command {

  private HornSubsystem m_horn;
  private HornTab m_HornTab;
  private ConveyorSubsystem m_conveyorSubsystem;
  private RobotShared m_robotShared;

  private long startingTime;
  private int timeUntilPop = 2700;
  private boolean finished = false;

  /** Creates a new Amp Shot using the Deflector */
  public HornAmpShootWithDeflector() {
    m_robotShared = RobotShared.getInstance();
    m_HornTab = HornTab.getInstance();
    m_horn = m_robotShared.getHornSubsystem();
    // This is needed to make sure the deflector is initialized, 
    // but because it is not used, it is not stored in a variable
    m_robotShared.getDeflectorSubsystem(); 
    m_conveyorSubsystem = m_robotShared.getConveyorSubsystem();
    addRequirements(m_horn);
    addRequirements(m_conveyorSubsystem);
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
    long currentTime = System.currentTimeMillis();
    // wait until shooter is at speed
    if(startingTime + HornConstants.kShootConveyorDelay < currentTime){
      m_conveyorSubsystem.setConveyorSpeed(1.0);
    }
    if(startingTime + 50 > currentTime){ // run for 50 ms
      m_HornTab.setDeflectorSetpoint(DeflectorConstants.kAmpScoringPosition);

    }else if(startingTime + timeUntilPop > currentTime){
      // do nothing and keep at the resting position to hold the note
      m_HornTab.setDeflectorSetpoint(DeflectorConstants.kAmpScoringPosition);
    }else if(startingTime + 3900 > currentTime){
      // pop the note for 1500 - timeUntilPop = 500 ms
      m_HornTab.setDeflectorSetpoint(DeflectorConstants.kAmpNotePopPosition);

    }else if(startingTime + 4000 > currentTime){
      // wait for the note to enter the amp and then retract the deflector
      m_HornTab.setDeflectorSetpoint(DeflectorConstants.kAmpRetractedPosition);
      finished = true;
    }
  }

  // Called once the command ends or is interrupted
  @Override
  public void end(boolean interrupted) {
    m_horn.setRpmSetpoint(0.0);
    m_conveyorSubsystem.setConveyorSpeed(0.0);
    m_HornTab.setDeflectorSetpoint(DeflectorConstants.kAmpRetractedPosition);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return finished;
  }
}
