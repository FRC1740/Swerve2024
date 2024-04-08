// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.Board;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import frc.robot.constants.SubsystemConstants.DriveConstants;

/** Add your docs here. */
public class DriveTrainTab {
  Field2d m_Field = new Field2d();

  Shuffleboard m_sb;

  // Shuffleboard DriveTrain entries
  // Create and get reference to SB tab
  ShuffleboardTab m_sbt_DriveTrain;

  // Encoders/PID Feedback sensors
  GenericEntry m_nte_LeftEncoder;
  GenericEntry m_nte_RightEncoder;
  GenericEntry m_nte_IMU_ZAngle;
  GenericEntry m_nte_IMU_PitchAngle; // USES IMU ROLL AXIS!!!

  // PID Tuning
  GenericEntry m_nte_DriveSpeedFilter;
  GenericEntry m_nte_DriveRotationFilter;

  GenericEntry m_nte_kPTurn;
  GenericEntry m_nte_kITurn;
  GenericEntry m_nte_kDTurn;

  // Create widget for non-linear input
  GenericEntry m_nte_InputExponent;

  GenericEntry m_nte_MaxDrivingSpeed;
  GenericEntry m_nte_CurrentSpeed;

  GenericEntry m_nte_HasRotationControl; // whether the driver has control over rotation or not

  GenericEntry m_nte_AutoRotationOffset;
  GenericEntry m_nte_IsPathFlipped;

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
    m_sbt_DriveTrain = Shuffleboard.getTab("DriveTrain");

    // Encoder outputs
    // Display current encoder values

    m_nte_IMU_ZAngle = m_sbt_DriveTrain.addPersistent("IMU Z-Axis Angle", 0.0)
      .withSize(2,1).withPosition(4,0).getEntry();

    m_nte_IMU_PitchAngle = m_sbt_DriveTrain.addPersistent("IMU Pitch", 0.0)
      .withSize(2,1).withPosition(4,1).getEntry();

    m_nte_MaxDrivingSpeed = m_sbt_DriveTrain.addPersistent("Max Speed MPS", DriveConstants.kMaxSpeedMetersPerSecond)
    .withSize(2,1).withPosition(4, 2).getEntry();

    m_nte_HasRotationControl = m_sbt_DriveTrain.addPersistent("Has Rotation Control", true)
    .withSize(3,3).withPosition(4, 3).getEntry();

    m_nte_AutoRotationOffset = m_sbt_DriveTrain.addPersistent("AutoRotationOffset", 0.0)
    .withSize(1,1).withPosition(1, 3).getEntry();

    m_nte_IsPathFlipped = m_sbt_DriveTrain.addPersistent("IsPathFlipped", (int)0)
    .withSize(1,1).withPosition(1, 4).getEntry();

    m_sbt_DriveTrain.add(m_Field)
      .withSize(4, 2).withPosition(0, 0);

    m_nte_CurrentSpeed = m_sbt_DriveTrain.addPersistent("Speed MPS", 0.0)
    .withSize(2,1).withPosition(1, 5).getEntry();

  }


  public Double getIMU_ZAngle() {
    return m_nte_IMU_ZAngle.getDouble(0.0);
  }

  public void setIMU_ZAngle(Double value) {
    m_nte_IMU_ZAngle.setDouble(truncate(value, 2));
  }

  public Double getIMU_PitchAngle() {
    return m_nte_IMU_PitchAngle.getDouble(0.0);
  }

  public void setIMU_PitchAngle(Double value) {
    m_nte_IMU_PitchAngle.setDouble(truncate(value, 2));
  }

  public Pose2d getRobotPose() {
    return m_Field.getRobotPose();
  }

  public void setRobotPose(Pose2d pose2d) {
    m_Field.setRobotPose(pose2d);
  }

  public void setMaxDrivingSpeed(Double value) {
    m_nte_MaxDrivingSpeed.setDouble(value);
  } 

  public Double getMaxDrivingSpeed() {
    return m_nte_MaxDrivingSpeed.getDouble(DriveConstants.kMaxSpeedMetersPerSecond);
  }

  public void setHasRotationControl(Boolean hasRotationControl) {
    m_nte_HasRotationControl.setBoolean(hasRotationControl);
  } 

  public Boolean getHasRotationControl() {
    return m_nte_HasRotationControl.getBoolean(true);
  }   

  public void setTrajectory(Trajectory traj){
    m_Field.getObject("trajectory").setTrajectory(traj);
  }

  public double getAutoRotationOffset() {
    return m_nte_AutoRotationOffset.getDouble(0.0);
  }

  public void setSpeed(double value) {
    m_nte_CurrentSpeed.setDouble(value);
  }

  public double getSpeed() {
    return m_nte_CurrentSpeed.getDouble(0.0);
  }

  /** one is flipped */
  public double getIsPathFlipped() {
    return m_nte_IsPathFlipped.getInteger(0);
  }

  public void setIsPathFlipped(int value) {
    m_nte_IsPathFlipped.setInteger(value);
  }

  /** Reduces the number of decmial places to reduce noise */
  private double truncate(double input, int decimalPlaces){
    return ((int)(input * Math.pow(10, decimalPlaces))) / (1.0 * Math.pow(10, decimalPlaces));
  }
}
