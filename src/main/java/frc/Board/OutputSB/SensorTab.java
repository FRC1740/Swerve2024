// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.Board.OutputSB;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

import frc.robot.constants.SensorConstants;

import edu.wpi.first.wpilibj.DriverStation;


public class SensorTab {

  ShuffleboardTab m_sbt_SensorTab;

  boolean[] filledPorts;
  GenericEntry[] m_nte_SensorStates;

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
    // Create and get reference to SB tab
    m_sbt_SensorTab = Shuffleboard.getTab("SensorTab");
    filledPorts = new boolean[3];
    m_nte_SensorStates = new GenericEntry[SensorConstants.kDIOPorts];
  }
  public Boolean getSensorStatePort(int port) {
    return m_nte_SensorStates[port].getBoolean(true);
  }

  public void setSensorStatePort(Boolean value, int port) {
    m_nte_SensorStates[port].setBoolean(value);
  }

  public void addSensorStatePort(int port) {
    if(filledPorts[port] != true){
      filledPorts[port] = true;
      m_nte_SensorStates[port] = m_sbt_SensorTab.add("Port " + port +" DIO", true)
        .withSize(2, 1).withPosition((port * 2), 1).getEntry();
    }else{
      DriverStation.reportError("Port " + port + " could not be created, port already exists!", false);
    }
  }
  public boolean sensorStatePortExists(int port) {
    return filledPorts[port];
  }
}
