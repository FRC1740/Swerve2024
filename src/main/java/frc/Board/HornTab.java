
// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.Board;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class HornTab {
  Shuffleboard m_sb;

  ShuffleboardTab m_sbt_Horn;

  GenericEntry m_nte_HornSpeed;


  private static HornTab instance = null;

  private HornTab() {
    initShuffleboardTab();
  }

  public static HornTab getInstance() {
    if(instance == null) {
      instance = new HornTab();
    }
    return instance;
  }

  private void initShuffleboardTab() {
    // Create and get reference to the tab
    m_sbt_Horn = Shuffleboard.getTab("Horn");

    m_nte_HornSpeed = m_sbt_Horn.addPersistent("Horn RPM", 0.0)
      .withSize(2,1).withPosition(4,1).getEntry();
  }
  public Double getHornSpeed() {
    return m_nte_HornSpeed.getDouble(0.0);
  }

  public void setHornSpeed(Double value) {
    m_nte_HornSpeed.setDouble(value);
  }
}