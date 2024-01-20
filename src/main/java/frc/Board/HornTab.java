
// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.Board;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.networktables.GenericSubscriber;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.constants.HornConstants;
import frc.robot.constants.NeoMotorConstants;

public class HornTab {
  Shuffleboard m_sb;

  ShuffleboardTab m_sbt_Horn;

  GenericEntry m_nte_RightHornSpeed;
  GenericEntry m_nte_LeftHornSpeed;

  GenericEntry m_nte_HornSpeedSetter;

  GenericEntry m_nte_RightVelocitySetter;
  GenericEntry m_nte_LeftVelocitySetter;

  GenericEntry m_nte_P;
  GenericEntry m_nte_I;
  GenericEntry m_nte_D;

  GenericEntry m_nte_FF;


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

    m_nte_RightHornSpeed = m_sbt_Horn.addPersistent("Right Horn RPM", 0.0)
      .withSize(2,1).withPosition(4,1).getEntry();
    
    m_nte_LeftHornSpeed = m_sbt_Horn.addPersistent("Left Horn RPM", 0.0)
      .withSize(2,1).withPosition(4,3).getEntry();
    

    m_nte_HornSpeedSetter = m_sbt_Horn.addPersistent("Horn Speed Setter", 0.0)
      .withSize(2,1).withPosition(6,1).getEntry();

    m_nte_P = m_sbt_Horn.addPersistent("P", HornConstants.kP)
      .withSize(1, 1).withPosition(8, 1).getEntry();

    m_nte_I = m_sbt_Horn.addPersistent("I", HornConstants.kI)
      .withSize(1, 1).withPosition(8, 2).getEntry();
    
    m_nte_D = m_sbt_Horn.addPersistent("D", HornConstants.kD)
      .withSize(1, 1).withPosition(8, 3).getEntry();

    m_nte_RightVelocitySetter = m_sbt_Horn.addPersistent("RightVelocitySetPoint", 0)
      .withSize(1, 1).withPosition(6, 2).getEntry();
    
    m_nte_LeftVelocitySetter = m_sbt_Horn.addPersistent("LeftVelocitySetPoint", 0)
      .withSize(1, 1).withPosition(6, 3).getEntry();
    
    m_nte_FF = m_sbt_Horn.addPersistent("FF", 0)
      .withSize(1, 1).withPosition(8, 4).getEntry();
  }
  public Double getHornMaxSpeed() {
    return m_nte_HornSpeedSetter.getDouble(0.0);
  }

  public void setHornMaxSpeed(Double value) {
    m_nte_HornSpeedSetter.setDouble(value);
  }
  public Double getHornSpeed() {
    return m_nte_RightHornSpeed.getDouble(0.0);
  }

  public void setRightHornVelocity(Double value) {
    m_nte_RightHornSpeed.setDouble(value);
  }

  public void setLeftHornVelocity(Double value) {
    m_nte_LeftHornSpeed.setDouble(value);
  }

  public double getP(){
    return m_nte_P.getDouble(0);
  }

  public double getI(){
    return m_nte_I.getDouble(0);
  }
  public double getD(){
    return m_nte_D.getDouble(0);
  }

  public double getFF(){
    return m_nte_FF.getDouble(0);
  }

  public double getRightVelocitySetPoint(){
    return m_nte_RightVelocitySetter.getDouble(0);
  }
  public double getLeftVelocitySetPoint(){
    return m_nte_LeftVelocitySetter.getDouble(0);
  }
}