// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
// more info on limelight networking here 
// https://docs.limelightvision.io/docs/docs-limelight/apis/complete-networktables-api

// This should not be called directly, but abstracted through limelight subsystem
package frc.Networking; // changed from network

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.HttpCamera;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

public class LimelightTable {

  private static String TableName = "limelight";

  private static String X_Offset_Entry = "tx";
  private static Double X_Offset_Default = 0.0;
  
  private static String Y_Offset_Entry = "ty";
  private static Double Y_Offset_Default = 0.0;

  private static String Target_Area_Entry = "ta";
  private static Double Target_Area_Default = 0.0;

  private static String CurrentTag = "tid";
  private static Double CurrentTag_Default = -1.0;

  private static String CamMode_Entry = "camMode";
  private static Double CamMode_Default = 0.0;

  private static String LedMode_Entry = "ledMode";
  private static Double LedMode_Default = 0.0;

  private static String Pipeline_Entry = "pipeline";
  private static Double Pipeline_Default = 0.0;

  NetworkTable m_nt = null;

  private static LimelightTable instance = null;

  private LimelightTable() {
    initNetworkTableInstance();
  }

  public static LimelightTable getInstance() {
    if(instance == null) {
      instance = new LimelightTable();
    }
    return instance;
  }

  private void initNetworkTableInstance() {
    m_nt = NetworkTableInstance.getDefault().getTable(TableName);
    //http://photonvision.local:5801
    HttpCamera httpCamera = new HttpCamera("limelight", "http://limelight.local:5801"); 
    httpCamera.setFPS(1);
    CameraServer.addCamera(httpCamera);
    Shuffleboard.getTab("Driver")
        .add(httpCamera);
  }

  public double getTx() {
    return m_nt.getEntry(X_Offset_Entry).getDouble(X_Offset_Default);
  }

  public void setTx(double value) {
    m_nt.getEntry(X_Offset_Entry).setNumber(value);
  }
  
  public double getTy() {
    return m_nt.getEntry(Y_Offset_Entry).getDouble(Y_Offset_Default);
  }

  public void setTy(double value) {
    m_nt.getEntry(Y_Offset_Entry).setNumber(value);
  }
  
  public double getTa() {
    return m_nt.getEntry(Target_Area_Entry).getDouble(Target_Area_Default);
  }

  public void setTa(double value) {
    m_nt.getEntry(Target_Area_Entry).setNumber(value);
  }
  public double[] getTranslationToAprilTag() {//x y z rot
    return m_nt.getEntry("botpose_targetspace").getDoubleArray(new double[6]); 
  }
  public double getCurrentApriltagId() {
    return m_nt.getEntry(CurrentTag).getDouble(CurrentTag_Default);
  }

  public double getCamMode() {
    return m_nt.getEntry(CamMode_Entry).getDouble(CamMode_Default);
  }
  
  public void setCamMode(double value) {
    m_nt.getEntry(CamMode_Entry).setNumber(value);
  }
  
  public double getLedMode() {
    return m_nt.getEntry(LedMode_Entry).getDouble(LedMode_Default);
  }
  
  public void setLedMode(double value) {
    m_nt.getEntry(LedMode_Entry).setNumber(value);
  }
  
  public double getPipeline() {
    return m_nt.getEntry(Pipeline_Entry).getDouble(Pipeline_Default);
  }
  
  public void setPipeline(double value) {
    m_nt.getEntry(Pipeline_Entry).setNumber(value);
  }

  public double[] getBotPose() {
    //print
    return m_nt.getEntry("botpose_wpiblue").getDoubleArray(new double[6]);
  }
}
