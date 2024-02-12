// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.Board.OutputSB;

import java.util.Map;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

import  edu.wpi.first.math.trajectory.Trajectory;

public class DriverTab {

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
      .withPosition(0, 3).withSize(1, 1).getEntry();

    m_nte_Field = m_sbt_DriverTab.add(m_Field)
      .withSize(4, 2).withPosition(0, 0).getEntry();

    m_nte_TimeLeft = m_sbt_DriverTab.add("TimeLeft", true)
      .withPosition(0, 4).withSize(1, 1).getEntry();
  }

ShuffleboardTab m_sbt_DriverTab;

}