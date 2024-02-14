// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.Board.OutputSB;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

import java.util.Map;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import frc.robot.constants.SubsystemConstants.HornConstants;


public class HornTab {

  ShuffleboardTab m_sbt_HornTab;

  GenericEntry m_nte_RightHornSpeed;
  GenericEntry m_nte_LeftHornSpeed;
  GenericEntry m_nte_HornSpeedSetter;
  GenericEntry m_nte_P;
  GenericEntry m_nte_I;
  GenericEntry m_nte_D;
  GenericEntry m_nte_FF;
  GenericEntry m_nte_RightVelocityOffset;
  GenericEntry m_nte_IntakeFromHornMode;
  GenericEntry m_nte_AmpVelocity;
  GenericEntry m_nte_DeflectorSetpoint;
  GenericEntry m_nte_DeflectorEncoder;

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
    // Create and get reference to SB tab
    m_sbt_HornTab = Shuffleboard.getTab("HornTab");
    m_nte_RightHornSpeed = m_sbt_HornTab.add("RightHornSpeed", 0)
      .withSize(2, 1)
      .withPosition(4, 1)
      .getEntry();
    m_nte_LeftHornSpeed = m_sbt_HornTab.add("LeftHornSpeed", 0)
      .withSize(2, 1)
      .withPosition(4, 3)
      .getEntry();
    m_nte_HornSpeedSetter = m_sbt_HornTab.add("HornSpeedSetter", 0)
      .withWidget(BuiltInWidgets.kNumberSlider)
      .withProperties(Map.of("min", 0, "max", 4000))
      .getEntry();
    m_nte_P = m_sbt_HornTab.add("P", HornConstants.kP)
      .withSize(1, 1)
      .withPosition(8, 1)
      .getEntry();
    m_nte_I = m_sbt_HornTab.add("I", HornConstants.kI)
      .withSize(1, 1)
      .withPosition(8, 2)
      .getEntry();
    m_nte_D = m_sbt_HornTab.add("D", HornConstants.kD)
      .withSize(1, 1)
      .withPosition(8, 3)
      .getEntry();
    m_nte_FF = m_sbt_HornTab.add("FF", 0)
      .withSize(1, 1)
      .withPosition(8, 4)
      .getEntry();
    m_nte_RightVelocityOffset = m_sbt_HornTab.add("RightVelocityOffset", 0.0)
      .withWidget(BuiltInWidgets.kNumberSlider)
      .withProperties(Map.of("min", 0, "max", 400))
      .getEntry();
    m_nte_IntakeFromHornMode = m_sbt_HornTab.add("IntakeFromHornMode", false)
      .withSize(1, 1)
      .withPosition(8, 3)
      .getEntry();
    m_nte_AmpVelocity = m_sbt_HornTab.add("AmpVelocity", HornConstants.kHornAmpShotMotorRPM)
      .withWidget(BuiltInWidgets.kNumberSlider)
      .withProperties(Map.of("min", 0, "max", 600))
      .getEntry();
    m_nte_DeflectorSetpoint = m_sbt_HornTab.add("DeflectorSetpoint", 0)
      .withWidget(BuiltInWidgets.kNumberSlider)
      .withProperties(Map.of("min", 0, "max", 1))
      .getEntry();
    m_nte_DeflectorEncoder = m_sbt_HornTab.add("DeflectorEncoder", 0)
      .getEntry();
  }
}
