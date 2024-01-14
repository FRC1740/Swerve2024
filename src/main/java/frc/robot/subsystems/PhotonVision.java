// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.io.IOException;
import java.util.Optional;

import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.PhotonPoseEstimator.PoseStrategy;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.VisionConstants;

public class PhotonVision extends SubsystemBase {
  /** Creates a new PhotonVision. */
  PhotonCamera cam;
  AprilTagFieldLayout aprilTagFieldLayout;
  PhotonPoseEstimator PoseEstimator;

  public PhotonVision() {
    cam = new PhotonCamera(VisionConstants.camName);
    try{
      aprilTagFieldLayout = AprilTagFieldLayout.loadFromResource(AprilTagFields.k2024Crescendo.m_resourceFile); }
    catch(IOException IOE){
      IOE.printStackTrace();
    }

    PoseEstimator = new PhotonPoseEstimator(
      aprilTagFieldLayout, PoseStrategy.MULTI_TAG_PNP_ON_COPROCESSOR, VisionConstants.RobotToCam);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public PhotonPipelineResult getLatestResult(){
    return cam.getLatestResult();
  }

  public Optional<EstimatedRobotPose> getEstimatedVisionPose(){
    return PoseEstimator.update();
  }

  public PhotonTrackedTarget getBestTarget(){
    return getLatestResult().getBestTarget();
  }

  public Transform3d getCamToTarget(){
    return getBestTarget().getBestCameraToTarget();
  }
}
