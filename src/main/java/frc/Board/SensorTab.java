
// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.Board;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class SensorTab {
  Shuffleboard m_sb;

  ShuffleboardTab m_sbt_SensorTab;

  GenericEntry m_nte_SensorState;


  private static SensorTab instance = null;

  private SensorTab() {
    initShuffleboardTab();
  }

  public static SensorTab getInstance() {
    if(instance == null) {
      instance = new SensorTab();
    }
    return instance;
  }

  private void initShuffleboardTab() {
    // Create and get reference to the tab
    m_sbt_SensorTab = Shuffleboard.getTab("Sensor");

    m_nte_SensorState = m_sbt_SensorTab.addPersistent("Port 0 DIO", true)
      .withSize(2,1).withPosition(4,1).getEntry();
  }
  public Boolean getSensorStatePort0() {
    return m_nte_SensorState.getBoolean(true);
  }

  public void setSensorStatePort0(Boolean value) {
    m_nte_SensorState.setBoolean(value);
  }
}