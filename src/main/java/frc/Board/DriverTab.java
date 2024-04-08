// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.Board;

import java.util.ArrayList;
import java.util.List;

import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.path.PathPlannerTrajectory;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.Trajectory.State;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;

/** Add your docs here. */
public class DriverTab{
  Field2d m_Field = new Field2d();
  ShuffleboardTab m_sbt_DriverTab;
  GenericEntry m_nte_HasNote;
  GenericEntry m_nte_TimeLeft;
  Trajectory m_Trajectory;

  private static DriverTab instance = null;

  private DriverTab() {
    initShuffleboardTab();
  }

  public static DriverTab getInstance() {
    if(instance == null) {
      instance = new DriverTab();
    }
    return instance;
  }

  private void initShuffleboardTab() {
    // Create and get reference to SB tab
    m_sbt_DriverTab = Shuffleboard.getTab("Driver");

    m_sbt_DriverTab.add(m_Field)
      .withSize(4, 2).withPosition(0, 0);

    m_nte_HasNote = m_sbt_DriverTab.add("HasNote", false)
      .withSize(1, 1).withPosition(8, 2).getEntry();

    m_nte_TimeLeft = m_sbt_DriverTab.add("Time Left", 0)
      .withSize(1, 1).withPosition(0, 4).getEntry();
  }

  public void setRobotPose(Pose2d pose2d) {
    m_Field.setRobotPose(pose2d);
  }

  public void setTrajectory(Trajectory traj){
    m_Trajectory = traj;
    m_Field.getObject("trajectory").setTrajectory(m_Trajectory);
  }

  public void setTrajectory(List<PathPlannerPath> paths) {
    List<edu.wpi.first.math.trajectory.Trajectory.State> states = new java.util.ArrayList<>();

    for (PathPlannerPath path : paths) {
      List<PathPlannerTrajectory.State> pathplannerStates = path.getTrajectory(
        new ChassisSpeeds(0, 0, 0), 
        path.getStartingDifferentialPose().getRotation()).getStates();

      //loop over states
      for(int i = 0; i < pathplannerStates.size(); i++){
        edu.wpi.first.math.trajectory.Trajectory.State state = new edu.wpi.first.math.trajectory.Trajectory.State(
          pathplannerStates.get(i).timeSeconds,
          pathplannerStates.get(i).velocityMps,
          pathplannerStates.get(i).accelerationMpsSq,
          new Pose2d(
            pathplannerStates.get(i).getTargetHolonomicPose().getTranslation().getX(),
            pathplannerStates.get(i).getTargetHolonomicPose().getTranslation().getY(),
            pathplannerStates.get(i).getTargetHolonomicPose().getRotation()
          ),
          pathplannerStates.get(i).curvatureRadPerMeter
        );
        states.add(state);
      }
    }

    if(states == null || states.size() == 0){
      System.out.println("No states found");
      return;
    }

    Trajectory traj = new Trajectory(states);
    setTrajectory(traj);
  }

  public void flipTrajectory(boolean flip){
    if(m_Trajectory == null) {
      return;
    }
    if(!flip) {
      m_Field.getObject("trajectory").setTrajectory(m_Trajectory);
      return;
    }

    List<State> states = m_Trajectory.getStates();
    List<State> flippedStates = new ArrayList<>();
    for(State state : states) {
      // 16.46 meters is the length of the field, by 8.21055
      // meters is the width of the field
      Translation2d translation = state.poseMeters.getTranslation();
      Translation2d newTranslation = new Translation2d(16.46 - translation.getX(), translation.getY());
      Rotation2d rotation = state.poseMeters.getRotation();
      Rotation2d newRotation = rotation.plus(new Rotation2d(Math.PI));
      Pose2d newPose = new Pose2d(newTranslation, newRotation);
      State newState = new State(state.timeSeconds, state.velocityMetersPerSecond, state.accelerationMetersPerSecondSq, 
      newPose, state.curvatureRadPerMeter);
      flippedStates.add(newState);
    }
    if(states == null) {
      return;
    }
    m_Field.getObject("trajectory").setTrajectory(new Trajectory(flippedStates));
  }

  public void setHasNote(boolean hasNote) {
    m_nte_HasNote.setBoolean(hasNote);
  }

  /** Gets the distance between 2 pose 2ds 
   * @param pose1 the first pose
   * @param pose2 the second pose
  */
  public double getDistance(Pose2d pose1, Pose2d pose2){
    return pose1.getTranslation().getDistance(pose2.getTranslation());
  }
}
