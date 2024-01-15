// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
import frc.robot.constants.VisionConstants.AprilTagIDs;

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

  //Returns list of IDs currently being tracked
  public List<Integer> getAprilTagIDs(){
    List<PhotonTrackedTarget> targets = getLatestResult().getTargets();
    List<Integer> tagIDs = new ArrayList<>();
    targets.forEach(target -> tagIDs.add(target.getFiducialId()));

    return tagIDs;
  }

  //Returns true if an the ID is being tracked
  public boolean containsID(Integer ID){
    return getAprilTagIDs().contains(ID);
  }

// Returns true if tracking the CENTER tag of a speaker (Red or Blue). Will not return true if only tracking the side tag
  public boolean containsSpeaker(){
    return 
    containsID(VisionConstants.AprilTagIDs.BlueSpeakerCenter.getID()) || 
    containsID(VisionConstants.AprilTagIDs.RedSpeakerCenter.getID());
  }

//Returns true if tracking a Source April Tag
  public boolean containsSource(){
    return
    containsID(VisionConstants.AprilTagIDs.BlueSourceDriverStationClose.getID()) ||
    containsID(VisionConstants.AprilTagIDs.BlueSourceDriverStationFar.getID()) ||
    containsID(VisionConstants.AprilTagIDs.RedSourceDriverStationClose.getID()) ||
    containsID(VisionConstants.AprilTagIDs.RedSourceDriverStationFar.getID());
  }
//Returns true if tracking an Amp April Tag
  public boolean containsAmp(){
    return
    containsID(VisionConstants.AprilTagIDs.BlueAmp.getID()) ||
    containsID(VisionConstants.AprilTagIDs.RedAmp.getID());
  }

//Returns true if the best target is a speaker april tag (Best target refers to a user-customizable strategy 
//in PhotonVision for "ranking" targets when there are more than one in view, likely whichever is closest to the center)
  public boolean bestTargetisSpeaker(){
    return 
    getBestTarget().getFiducialId() == VisionConstants.AprilTagIDs.BlueSpeakerCenter.getID() ||
    getBestTarget().getFiducialId() == VisionConstants.AprilTagIDs.BlueSpeakerSide.getID() ||
    getBestTarget().getFiducialId() == VisionConstants.AprilTagIDs.RedSpeakerCenter.getID() ||
    getBestTarget().getFiducialId() == VisionConstants.AprilTagIDs.RedSpeakerSide.getID();
  }

//Returns true if the best target is a source april tag
  public boolean bestTargetisSource(){
    return
    getBestTarget().getFiducialId() == VisionConstants.AprilTagIDs.BlueSourceDriverStationClose.getID() ||
    getBestTarget().getFiducialId() == VisionConstants.AprilTagIDs.BlueSourceDriverStationFar.getID() ||
    getBestTarget().getFiducialId() == VisionConstants.AprilTagIDs.RedSourceDriverStationClose.getID() ||
    getBestTarget().getFiducialId() == VisionConstants.AprilTagIDs.RedSourceDriverStationFar.getID();
  }

//Returns true if the best target is an Amp april tag
  public boolean bestTargetisAmp(){
    return 
    getBestTarget().getFiducialId() == VisionConstants.AprilTagIDs.BlueAmp.getID() ||
    getBestTarget().getFiducialId() == VisionConstants.AprilTagIDs.RedAmp.getID();
  }
}