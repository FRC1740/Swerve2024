package frc.Board;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class CurrentDrawTab {
  ShuffleboardTab m_sbt_CurrentDrawTab;

  GenericEntry m_nte_GroundIntakeCurrentDraw;
  GenericEntry m_nte_ConveyorCurrentDraw;
  GenericEntry m_nte_DeflectorCurrentDraw;
  GenericEntry m_nte_ClimberCurrentDraw;
  GenericEntry m_nte_HornRightCurrentDraw;
  GenericEntry m_nte_HornLeftCurrentDraw;

  GenericEntry m_nte_DrivingFrontLeft;
  GenericEntry m_nte_DrivingFrontRight;
  GenericEntry m_nte_DrivingRearLeft;
  GenericEntry m_nte_DrivingRearRight;

  GenericEntry m_nte_TurningFrontLeft;
  GenericEntry m_nte_TurningFrontRight;
  GenericEntry m_nte_TurningRearLeft;
  GenericEntry m_nte_TurningRearRight;

  private static CurrentDrawTab instance = null;

  private CurrentDrawTab() {
    initShuffleboardTab();
  }

  public static CurrentDrawTab getInstance() {
    if(instance == null) {
      instance = new CurrentDrawTab();
    }
    return instance;
  }

  private void initShuffleboardTab() {
    // Create and get reference to SB tab
    m_sbt_CurrentDrawTab = Shuffleboard.getTab("CurrentDraw");

    m_nte_GroundIntakeCurrentDraw = m_sbt_CurrentDrawTab.add("GroundIntakeCurrentDraw", 0.0)
      .withSize(2, 1).getEntry();

    m_nte_ConveyorCurrentDraw = m_sbt_CurrentDrawTab.add("ConveyorCurrentDraw", 0.0)
      .withSize(2, 1).getEntry();

    m_nte_DeflectorCurrentDraw = m_sbt_CurrentDrawTab.add("DeflectorCurrentDraw", 0.0)
      .withSize(2, 1).getEntry();
    
    m_nte_ClimberCurrentDraw = m_sbt_CurrentDrawTab.add("ClimberCurrentDraw", 0.0)
      .withSize(2, 1).getEntry();
    
    m_nte_HornRightCurrentDraw = m_sbt_CurrentDrawTab.add("HornRightCurrentDraw", 0.0)
      .withSize(2, 1).getEntry();

    m_nte_HornLeftCurrentDraw = m_sbt_CurrentDrawTab.add("HornLeftCurrentDraw", 0.0)
      .withSize(2, 1).getEntry();

    m_nte_DrivingFrontLeft = m_sbt_CurrentDrawTab.add("DrivingFrontLeft", 0.0)
      .withSize(2, 1).getEntry();
    
    m_nte_DrivingFrontRight = m_sbt_CurrentDrawTab.add("DrivingFrontRight", 0.0)
      .withSize(2, 1).getEntry();

    m_nte_DrivingRearLeft = m_sbt_CurrentDrawTab.add("DrivingRearLeft", 0.0) 
      .withSize(2, 1).getEntry();
    
    m_nte_DrivingRearRight = m_sbt_CurrentDrawTab.add("DrivingRearRight", 0.0)
      .withSize(2, 1).getEntry();
    
    m_nte_TurningFrontLeft = m_sbt_CurrentDrawTab.add("TurningFrontLeft", 0.0)
      .withSize(2, 1).getEntry();
    
    m_nte_TurningFrontRight = m_sbt_CurrentDrawTab.add("TurningFrontRight", 0.0) 
      .withSize(2, 1).getEntry();
    
    m_nte_TurningRearLeft = m_sbt_CurrentDrawTab.add("TurningRearLeft", 0.0)
      .withSize(2, 1).getEntry();
    
    m_nte_TurningRearRight = m_sbt_CurrentDrawTab.add("TurningRearRight", 0.0)
      .withSize(2, 1).getEntry();
  }

  public void setGroundIntakeCurrentDraw(double currentDraw) {
    m_nte_GroundIntakeCurrentDraw.setDouble(currentDraw);
  }
  public void setConveyorCurrentDraw(double currentDraw) {
    m_nte_ConveyorCurrentDraw.setDouble(currentDraw);
  }
  public void setDeflectorCurrentDraw(double currentDraw) {
    m_nte_DeflectorCurrentDraw.setDouble(currentDraw);
  }
  public void setClimberCurrentDraw(double currentDraw) {
    m_nte_ClimberCurrentDraw.setDouble(currentDraw);
  }
  public void setHornRightCurrentDraw(double currentDraw) {
    m_nte_HornRightCurrentDraw.setDouble(currentDraw);
  }
  public void setHornLeftCurrentDraw(double currentDraw) {
    m_nte_HornLeftCurrentDraw.setDouble(currentDraw);
  }
  public void setDrivingFrontLeftCurrentDraw(double currentDraw) {
    m_nte_DrivingFrontLeft.setDouble(currentDraw);
  }
  public void setDrivingFrontRightCurrentDraw(double currentDraw) {
    m_nte_DrivingFrontRight.setDouble(currentDraw);
  }
  public void setDrivingRearLeftCurrentDraw(double currentDraw) {
    m_nte_DrivingRearLeft.setDouble(currentDraw);
  }
  public void setDrivingRearRightCurrentDraw(double currentDraw) {
    m_nte_DrivingRearRight.setDouble(currentDraw);
  }
  public void setTurningFrontLeftCurrentDraw(double currentDraw) {
    m_nte_TurningFrontLeft.setDouble(currentDraw);
  }
  public void setTurningFrontRightCurrentDraw(double currentDraw) {
    m_nte_TurningFrontRight.setDouble(currentDraw);
  }
  public void setTurningRearLeftCurrentDraw(double currentDraw) {
    m_nte_TurningRearLeft.setDouble(currentDraw);
  }
  public void setTurningRearRightCurrentDraw(double currentDraw) {
    m_nte_TurningRearRight.setDouble(currentDraw);
  }
}
