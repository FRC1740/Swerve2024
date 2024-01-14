// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.Board;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class ErrorTab {

  Shuffleboard m_sb;

  // Shuffleboard DriveTrain entries
  // Create and get reference to SB tab

  ShuffleboardTab m_sbt_Errors;

  GenericEntry m_nte_Errors;

  private String CurrentErrors = "";
  private String UsedNames = "";


  private static ErrorTab instance = null;

  private ErrorTab() {
    initShuffleboardTab();
  }

  public static ErrorTab getInstance() {
    if(instance == null) {
      instance = new ErrorTab();
    }
    return instance;
  }

  private void initShuffleboardTab() {
    // Create and get reference to SB tab
    m_sbt_Errors = Shuffleboard.getTab("Errors");

    m_nte_Errors = m_sbt_Errors.add("Errors", CurrentErrors).getEntry();
    // Create widgets for PID Controllers
  }


  public void logError(String nameOfCaller, String newError) {
    if(!UsedNames.contains(nameOfCaller)){
      UsedNames += nameOfCaller;
      m_nte_Errors = m_sbt_Errors.add(nameOfCaller, newError).getEntry();
    }
  }
  public boolean errorExists(String nameOfCaller, String newError){
    if(CurrentErrors.contains(newError)){
      return true;
    }else{
      CurrentErrors += newError;
      logError(nameOfCaller, newError);
    }
    return false;
  }
}
