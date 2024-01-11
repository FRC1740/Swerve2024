// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.Networking.LimelightTable;

public class LimelightSubsystem extends SubsystemBase {

  private LimelightTable m_LimelightTable;

  public LimelightSubsystem() {
    m_LimelightTable = LimelightTable.getInstance();
    enableDriverCamera();
  }

  public double getXdeviation() {
    return m_LimelightTable.getTx();
  }
  public double getYdeviation() {
    return m_LimelightTable.getTy();
  }

  public void enableVisionProcessing() {
    m_LimelightTable.setCamMode(0);
    m_LimelightTable.setPipeline(1);
    m_LimelightTable.setLedMode(3);
    System.out.println("Vision processing enabled");
  }
  public void enableDriverCamera() {
    m_LimelightTable.setCamMode(1);
    m_LimelightTable.setLedMode(1);
  }

  public double getCamMode() {
    return m_LimelightTable.getCamMode();
  }
  //Toggle led on and off
  public void toggleLED() {
    if (m_LimelightTable.getLedMode() == 1){
      m_LimelightTable.setLedMode(3);
    }else{
      m_LimelightTable.setLedMode(1);
    }
  }

  //Returns true if Limelight is in vision processing mode
  public Boolean isVisionProcessing() {
    return m_LimelightTable.getCamMode() == 0 && m_LimelightTable.getLedMode() == 3;
  }

  // public void targetMidNode(){
  //   m_LimelightTable.setPipeline(0);
  // }

  // public void targetHighNode(){
  //   m_LimelightTable.setPipeline(1);
  // }

  // public void getAprilTagPosition(){
  //   m_LimelightTable.setPipeline(1);
  // }

  public double getTargetedID() {
    return m_LimelightTable.getCurrentApriltagId();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}