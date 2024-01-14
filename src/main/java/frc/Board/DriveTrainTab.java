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
//import frc.robot.constants.ShuffleboardConstants;

/** Add your docs here. */
public class DriveTrainTab {
  Field2d m_Field = new Field2d();

  Shuffleboard m_sb;

  // Shuffleboard DriveTrain entries
  // Create and get reference to SB tab
  ShuffleboardTab m_sbt_DriveTrain;

  GenericEntry m_nte_Testing;

  // Autonomous Variables
  GenericEntry m_nte_a_DriveDelay;
  GenericEntry m_nte_b_DriveDistance;
  GenericEntry m_nte_c_DriveTurnAngle;
  GenericEntry m_nte_autoDriveMode;

  // Encoders/PID Feedback sensors
  GenericEntry m_nte_LeftEncoder;
  GenericEntry m_nte_RightEncoder;
  GenericEntry m_nte_IMU_ZAngle;
  GenericEntry m_nte_IMU_PitchAngle; // USES IMU ROLL AXIS!!!

  // PID Tuning
  GenericEntry m_nte_DriveSpeedFilter;
  GenericEntry m_nte_DriveRotationFilter;

  GenericEntry m_nte_kPAutoBalance;
  GenericEntry m_nte_kIAutoBalance;
  GenericEntry m_nte_kDAutoBalance;

  GenericEntry m_nte_kPTurn;
  GenericEntry m_nte_kITurn;
  GenericEntry m_nte_kDTurn;

  // Create widget for non-linear input
  GenericEntry m_nte_InputExponent;

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
      .withSize(2,1).withPosition(4,2).getEntry();

    m_nte_IMU_PitchAngle = m_sbt_DriveTrain.addPersistent("IMU Pitch", 0.0)
      .withSize(2,1).withPosition(4,3).getEntry();

    m_sbt_DriveTrain.add(m_Field)
      .withSize(4, 2).withPosition(5, 0);

    // Create widgets for PID Controllers
  }


  public Double getIMU_ZAngle() {
    return m_nte_IMU_ZAngle.getDouble(0.0);
  }

  public void setIMU_ZAngle(Double value) {
    m_nte_IMU_ZAngle.setDouble(value);
  }

  public Double getIMU_PitchAngle() {
    return m_nte_IMU_PitchAngle.getDouble(0.0);
  }

  public void setIMU_PitchAngle(Double value) {
    m_nte_IMU_PitchAngle.setDouble(value);
  }

  public Pose2d getRobotPose() {
    return m_Field.getRobotPose();
  }

  public void setRobotPose(Pose2d pose2d) {
    m_Field.setRobotPose(pose2d);
  }

  public void setTrajectory(Trajectory traj){
    m_Field.getObject("trajectory").setTrajectory(traj);
  }
}
