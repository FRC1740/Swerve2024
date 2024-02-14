// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.Board.OutputSB;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

import edu.wpi.first.wpilibj.smartdashboard.Field2d;

import frc.robot.constants.SubsystemConstants.DriveConstants;


public class DriveTrainTab {

  ShuffleboardTab m_sbt_DriveTrainTab;

  Field2d m_Field = new Field2d();
  GenericEntry m_nte_IMU_ZAngle;
  GenericEntry m_nte_IMU_PitchAngle;
  GenericEntry m_nte_MaxDrivingSpeed;
  GenericEntry m_nte_HasRotationControl;

  private static DriveTrainTab instance = null;

  private DriveTrainTab() {
    initShuffleboardTab();
  }

  public static DriveTrainTab getInstance() {
    if(instance == null) {
      instance = new DriveTrainTab();
    }
    return instance;
  }

  private void initShuffleboardTab() {
    // Create and get reference to SB tab
    m_sbt_DriveTrainTab = Shuffleboard.getTab("DriveTrainTab");
    m_sbt_DriveTrainTab.add(m_Field)
      .withSize(4, 2)
      .withPosition(8, 0);
    m_nte_IMU_ZAngle = m_sbt_DriveTrainTab.add("IMU_ZAngle", 0)
      .withSize(2, 1)
      .withPosition(4, 0)
      .getEntry();
    m_nte_IMU_PitchAngle = m_sbt_DriveTrainTab.add("IMU_PitchAngle", 0)
      .withSize(2, 1)
      .withPosition(4, 1)
      .getEntry();
    m_nte_MaxDrivingSpeed = m_sbt_DriveTrainTab.add("MaxDrivingSpeed", DriveConstants.kMaxSpeedMetersPerSecond)
      .withSize(1, 1)
      .withPosition(4, 2)
      .getEntry();
    m_nte_HasRotationControl = m_sbt_DriveTrainTab.add("HasRotationControl", false)
      .withSize(1, 1)
      .withPosition(4, 3)
      .getEntry();
  }

  public void setIMU_ZAngle(double value) {
    m_nte_IMU_ZAngle.setDouble(value);
  }
  public double getIMU_ZAngle() {
    return m_nte_IMU_ZAngle.getDouble(0);
  }

  public void setIMU_PitchAngle(double value) {
    m_nte_IMU_PitchAngle.setDouble(value);
  }
  public double getIMU_PitchAngle() {
    return m_nte_IMU_PitchAngle.getDouble(0);
  }

  public void setMaxDrivingSpeed(double value) {
    m_nte_MaxDrivingSpeed.setDouble(value);
  }
  public double getMaxDrivingSpeed() {
    return m_nte_MaxDrivingSpeed.getDouble(DriveConstants.kMaxSpeedMetersPerSecond);
  }

  public void setHasRotationControl(boolean value) {
    m_nte_HasRotationControl.setBoolean(value);
  }
  public boolean getHasRotationControl() {
    return m_nte_HasRotationControl.getBoolean(false);
  }
}
