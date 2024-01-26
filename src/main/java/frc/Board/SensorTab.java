
// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.Board;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.constants.SensorConstants;

public class SensorTab {
  Shuffleboard m_sb;

  ShuffleboardTab m_sbt_SensorTab;

  GenericEntry[] m_nte_SensorStates;

  private boolean filledPorts[];


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
    m_nte_SensorStates = new GenericEntry[SensorConstants.kDIOPorts];
    filledPorts = new boolean[SensorConstants.kDIOPorts];
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
        .withSize(2,1).withPosition((port * 2), 1).getEntry();
    }else{
      DriverStation.reportError("Port " + port + " could not be created, port already exists!", false);
    }
  }
  public boolean sensorStatePortExists(int port) {
    if(filledPorts[port] == true){
      return true;
    }
    return false;
  }
}