// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.utils;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import com.pathplanner.lib.util.GeometryUtil;

enum Location{
  BlueSpeakerAmp(new Pose2d(0.70,6.59, Rotation2d.fromDegrees(-120))), //Amp-side of speaker
  BlueSpeakerCenter(new Pose2d(1.24,5.56, Rotation2d.fromDegrees(180))),
  BlueSpeakerSource(new Pose2d(0.70,4.55, Rotation2d.fromDegrees(120))), //Source-side of Speaker
  BlueSourceClose(new Pose2d(14.89, 0.54, Rotation2d.fromDegrees(59))),
  BlueSourceFar(new Pose2d(16.02, 1.22, Rotation2d.fromDegrees(59))),
  BlueAmp(new Pose2d(1.69, 0.54, Rotation2d.fromDegrees(90)));

  private final Pose2d pose;
  Location(Pose2d Pose){
    this.pose = Pose;
  }

  public Pose2d getPose2d(){
    return this.pose;
  }
  public Pose2d getRedPose2d(){
    return GeometryUtil.flipFieldPose(this.pose);
  }
}
/** Add your docs here. */
public class Field {
  //Blue Location
  public Pose2d getBlueSpeakerAmp(){
    return Location.BlueSpeakerAmp.getPose2d();
  }

  public Pose2d getBlueSpeakerCenter(){
    return Location.BlueSpeakerCenter.getPose2d();
  }

  public Pose2d getBlueSpeakerSource(){
    return Location.BlueSpeakerSource.getPose2d();
  }

  public Pose2d getBlueSourceClose(){
    return Location.BlueSourceClose.getPose2d();
  }

  public Pose2d getBlueSourceFar(){
    return Location.BlueSourceClose.getPose2d();
  }

  public Pose2d getBlueAmp(){
    return Location.BlueSpeakerAmp.getPose2d();
  }

  //Red Locations
  public Pose2d getRedSpeakerAmp(){
    return Location.BlueSpeakerAmp.getRedPose2d();
  }

  public Pose2d getRedSpeakerCenter(){
    return Location.BlueSpeakerCenter.getRedPose2d();
  }

  public Pose2d getRedSpeakerSource(){
    return Location.BlueSpeakerSource.getRedPose2d();
  }

  public Pose2d getRedSourceClose(){
    return Location.BlueSourceClose.getRedPose2d();
  }

  public Pose2d getRedSourceFar(){
    return Location.BlueSourceClose.getRedPose2d();
  }

  public Pose2d getRedAmp(){
    return Location.BlueSpeakerAmp.getRedPose2d();
  }
}
