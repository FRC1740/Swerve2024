// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.Board.OutputSB;

import java.util.Map;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

import  java.util.Map;
import  edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import  frc.robot.constants.SubsystemConstants.HornConstants;

public class HornTab {

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

    m_nte_RightHornSpeed = m_sbt_DriverTab.add("RightHornSpeed", 0)
      .withSize(2, 1).withPosition(4, 1).getEntry();

    m_nte_LeftHornSpeed = m_sbt_DriverTab.add("LeftHornSpeed", 0.0
)
      .getEntry();
.withPosition(4, 3).getEntry();

    m_nte_HornSpeedSetter = m_sbt_DriverTab.add("HornSpeedSetter", 0.0
)
      .getEntry();

    m_nte_HornSpeedSetter.properties = m_sbt_DriverTab.add("HornSpeedSetter.properties", {min,)
      .getEntry();

    m_nte_P = m_sbt_DriverTab.add("P", HornConstants.kP
)
      .getEntry();
.withPosition(8, 1).getEntry();

    m_nte_I = m_sbt_DriverTab.add("I", HornConstants.kI
)
      .getEntry();
.withPosition(8, 2).getEntry();

    m_nte_D = m_sbt_DriverTab.add("D", HornConstants.kD
)
      .getEntry();
.withPosition(8, 3).getEntry();

    m_nte_FF = m_sbt_DriverTab.add("FF", 0.0
)
      .getEntry();
.withPosition(8, 4).getEntry();

    m_nte_RightVelocityOffset = m_sbt_DriverTab.add("RightVelocityOffset", 0.0
)
      .getEntry();

    m_nte_RightVelocityOffset.properties = m_sbt_DriverTab.add("RightVelocityOffset.properties", {min,)
      .getEntry();

    m_nte_IntakeFromHornMode = m_sbt_DriverTab.add("IntakeFromHornMode", false
)
      .withSize(1, 1).withPosition(8, 3).getEntry();

    m_nte_AmpVelocity = m_sbt_DriverTab.add("AmpVelocity", HornConstants.kHornAmpShotMotorRPM
)
      .getEntry();

    m_nte_AmpVelocity.properties = m_sbt_DriverTab.add("AmpVelocity.properties", {min,)
      .getEntry();

    m_nte_DeflectorSetpoint = m_sbt_DriverTab.add("DeflectorSetpoint", 0
)
      
    m_nte_DeflectorSetpoint.widget = m_sbt_DriverTab.add("DeflectorSetpoint.widget", "kNumberSlider"
)
      
    m_nte_DeflectorSetpoint.properties = m_sbt_DriverTab.add("DeflectorSetpoint.properties", {min,)
      .getEntry();

    m_nte_DeflectorEncoder = m_sbt_DriverTab.add("DeflectorEncoder", 0
)
        }

ShuffleboardTab m_sbt_DriverTab;

}