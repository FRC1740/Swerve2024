// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.Board;

import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;

/** Add your docs here. */
public class DriverTab{
  Field2d m_Field = new Field2d();
  ShuffleboardTab m_sbt_DriverTab;
  GenericEntry m_nte_HasNote;
  GenericEntry m_nte_TimeLeft;

  private static DriverTab instance = null;

  private DriverTab() {
    initShuffleboardTab();
  }

  public static DriverTab getInstance() {
    if(instance == null) {
      instance = new DriverTab();
    }
    return instance;
  }

  private void initShuffleboardTab() {
    // Create and get reference to SB tab
    m_sbt_DriverTab = Shuffleboard.getTab("Driver");

    m_sbt_DriverTab.add(m_Field)
      .withSize(4, 2).withPosition(0, 0);

    m_nte_HasNote = m_sbt_DriverTab.add("HasNote", false)
      .withSize(1, 1).withPosition(8, 2).getEntry();

    m_nte_TimeLeft = m_sbt_DriverTab.add("Time Left", 0)
      .withSize(1, 1).withPosition(0, 4).getEntry();
  }

  public void setTrajectory(Trajectory traj){
    m_Field.getObject("trajectory").setTrajectory(traj);
  }

  public void setHasNote(boolean hasNote) {
    m_nte_HasNote.setBoolean(hasNote);
  }
  // @Override
  // public void periodic() {
  //   m_nte_TimeLeft.setDouble(DriverStation.getMatchTime());
  // }
}
