// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.Board.OutputSB;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

import edu.wpi.first.wpilibj.DriverStation;


public class ErrorTab {

  ShuffleboardTab m_sbt_ErrorTab;

  GenericEntry m_nte_Errors;
  private String CurrentErrors;
  private String UsedNames;

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
    m_sbt_ErrorTab = Shuffleboard.getTab("ErrorTab");
    m_nte_Errors = m_sbt_ErrorTab.add("Errors", "")
      .getEntry();
    CurrentErrors = "";
    UsedNames = "";
  }

  public void setErrors(String value) {
    m_nte_Errors.setString(value);
  }
  public String getErrors() {
    return m_nte_Errors.getString("");
  }

  public void logError(String nameOfCaller, String newError) {
    if(!UsedNames.contains(nameOfCaller)){
      UsedNames += nameOfCaller;
      m_nte_Errors = m_sbt_ErrorTab.add(nameOfCaller, newError).getEntry();
      DriverStation.reportError(newError, false);
    }
  }
  public boolean errorExists(String stringToCheck, String error) { // this is seperate because I want it to be able to be called
    if(stringToCheck.contains(error)){
      return true;
    }
    return false;
  }
  public boolean handleError(String nameOfCaller, String newError){
    boolean errorExists = errorExists(CurrentErrors, newError);
    if(!errorExists){ // meaning it hasn't been logged yet
      CurrentErrors += newError + ":"; // update list
      logError(nameOfCaller, newError);
    }
    return errorExists;
  }
}
