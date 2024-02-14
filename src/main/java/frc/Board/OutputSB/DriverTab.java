// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.Board.OutputSB;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

import edu.wpi.first.math.trajectory.Trajectory;

import edu.wpi.first.wpilibj.smartdashboard.Field2d;


public class DriverTab {

  ShuffleboardTab m_sbt_DriverTab;

  GenericEntry m_nte_HasNote;
  GenericEntry m_nte_HaDNote;
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
    m_sbt_DriverTab = Shuffleboard.getTab("DriverTab");
    m_nte_HasNote = m_sbt_DriverTab.add("HasNote", true)
      .withSize(1, 1)
      .withPosition(0, 3)
      .getEntry();
    m_nte_HaDNote = m_sbt_DriverTab.add("HaDNote", 0.0)
      .withSize(1, 1)
      .withPosition(0, 3)
      .getEntry();
    m_nte_TimeLeft = m_sbt_DriverTab.add("TimeLeft", true)
      .withSize(1, 1)
      .withPosition(0, 4)
      .getEntry();
  }
}
