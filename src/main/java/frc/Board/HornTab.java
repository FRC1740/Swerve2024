
// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.Board;

import java.util.Map;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.constants.SubsystemConstants.HornConstants;

public class HornTab {
  Shuffleboard m_sb;

  ShuffleboardTab m_sbt_Horn;

  GenericEntry m_nte_RightHornSpeed;
  GenericEntry m_nte_LeftHornSpeed;
  GenericEntry m_nte_IntakeFromHornMode;
  GenericEntry m_nte_HornSpeedSetter;

  GenericEntry m_nte_RightVelocityOffset;

  GenericEntry m_nte_AmpVelocity;

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
    

    m_nte_HornSpeedSetter = m_sbt_Horn.add("Horn Speed Setter", 0.0)
      .withWidget(BuiltInWidgets.kNumberSlider)
      .withProperties(Map.of("min", 0, "max", 4000)) // specify widget properties here
      .getEntry();
    m_nte_P = m_sbt_Horn.add("P", HornConstants.kP)
      .withSize(1, 1).withPosition(8, 1).getEntry();

    m_nte_I = m_sbt_Horn.add("I", HornConstants.kI)
      .withSize(1, 1).withPosition(8, 2).getEntry();
    
    m_nte_D = m_sbt_Horn.add("D", HornConstants.kD)
      .withSize(1, 1).withPosition(8, 3).getEntry();

    m_nte_RightVelocityOffset = m_sbt_Horn.add("RightMotorRPMOffset", 1)
      .withWidget(BuiltInWidgets.kNumberSlider)
      .withProperties(Map.of("min", 0, "max", 400)) // specify widget properties here
      .getEntry();
    
    m_nte_FF = m_sbt_Horn.add("FF", 0)
      .withSize(1, 1).withPosition(8, 4).getEntry();

    m_nte_IntakeFromHornMode = m_sbt_Horn.add("IntakeFromHorn", false)
      .withSize(1, 1).withPosition(8, 3).getEntry();

    m_nte_AmpVelocity = m_sbt_Horn.add("AmpShotRPM", HornConstants.kHornAmpShotMotorRPM)
      .withWidget(BuiltInWidgets.kNumberSlider)
      .withProperties(Map.of("min", 0, "max", 600)) // specify widget properties here
      .getEntry();
 
  }
  public Double getHornTargetSpeed() {
    return m_nte_HornSpeedSetter.getDouble(0.0);
  }
  public Double getAmpTargetSpeed() {
    return m_nte_HornSpeedSetter.getDouble(HornConstants.kHornAmpShotMotorRPM);
  }


  public void setHornTargetSpeed(Double value) {
    m_nte_HornSpeedSetter.setDouble(value);
  }
  public Double getHornSpeed() {
    return m_nte_RightHornSpeed.getDouble(0.0);
  }

  public void setRightHornVelocity(Double value) {
    m_nte_RightHornSpeed.setDouble(truncate(value, 2));
  }

  public void setLeftHornVelocity(Double value) {
    m_nte_LeftHornSpeed.setDouble(truncate(value, 2));
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
    return m_nte_RightVelocityOffset.getDouble(0) + 
    m_nte_HornSpeedSetter.getDouble(0);
  }
  public double getLeftVelocitySetPoint(){
    return m_nte_HornSpeedSetter.getDouble(0);
  }
  public boolean getIntakeFromHornMode(){
    return m_nte_IntakeFromHornMode.getBoolean(false);
  }
  private double truncate(double input, int decimalPlaces){
    return ((int)(input * Math.pow(10, decimalPlaces))) / (1.0 * Math.pow(10, decimalPlaces));
  }
}
