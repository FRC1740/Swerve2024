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

public class HornShootShuffleboard extends Command {

  private HornSubsystem m_horn;
  private ConveyorSubsystem m_conveyorSubsystem;
  private RobotShared m_robotShared;

  private boolean isSpeakerShot;
  private long startingTime;
  private int timeUntilPop = 1000; // TODO: CHANGE THIS IN EVERY FILE THAT USES IT
  private HornTab m_HornTab = HornTab.getInstance();

  /** Creates a new Shoot command based on whether the Horn has been spun up 
   * This command is very poorly written, however I have not found a better way to do this
  */
  public HornShootShuffleboard() {
    m_robotShared = RobotShared.getInstance();
    m_horn = m_robotShared.getHornSubsystem();
    m_conveyorSubsystem = m_robotShared.getConveyorSubsystem();
    addRequirements(m_horn);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    startingTime = System.currentTimeMillis();

    // Is true if the shooter is spun up. 
    // There is no worry of the shuffleboard responding slowly, because if it's that soon, it's not spun up
    isSpeakerShot = m_HornTab.getHornTargetSpeed() == HornConstants.kHornSpeakerShotMotorRPM;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    long currentTime = System.currentTimeMillis();
    if(isSpeakerShot){
      m_HornTab.setHornTargetSpeed(HornConstants.kHornSpeakerShotMotorRPM);
      // wait until shooter is at speed
      if(startingTime + HornConstants.kShootConveyorDelay < currentTime){
        m_conveyorSubsystem.setConveyorSpeed(1.0);
      }
    }else{
      // Ignores offset
      m_horn.setRpmSetpoint(m_HornTab.getAmpTargetSpeed());
      // wait until shooter is at speed
      if(startingTime + HornConstants.kShootConveyorDelay < currentTime){
        m_conveyorSubsystem.setConveyorSpeed(1.0);
      }
      if(startingTime + 50 > currentTime){ // run for .25 second
        m_HornTab.setDeflectorSetpoint(DeflectorConstants.kAmpScoringPosition);

      }else if(startingTime + timeUntilPop > currentTime){
        // do nothing and keep at .3
        m_HornTab.setDeflectorSetpoint(DeflectorConstants.kAmpScoringPosition);
      }else if(startingTime + 1500 > currentTime){ // run for .25 second
        m_HornTab.setDeflectorSetpoint(DeflectorConstants.kAmpNotePopPosition);

      }else if(startingTime + 3000 > currentTime){ // run for .25 second
        m_HornTab.setDeflectorSetpoint(DeflectorConstants.kAmpRetractedPosition);
      }
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // m_hornShoot.end(true);    
    m_horn.setRpmSetpoint(0.0);
    m_conveyorSubsystem.setConveyorSpeed(0.0);
    m_HornTab.setDeflectorSetpoint(DeflectorConstants.kAmpRetractedPosition);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
