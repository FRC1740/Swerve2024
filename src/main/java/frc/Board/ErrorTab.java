// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.Board;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class ErrorTab{
  Shuffleboard m_sb;

  ShuffleboardTab m_sbt_Errors;

  GenericEntry m_nte_Errors;

  // private String ErrorsLastFrame = "";
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
    // Create and get reference to the tab
    m_sbt_Errors = Shuffleboard.getTab("Errors");

    m_nte_Errors = m_sbt_Errors.add("Errors", CurrentErrors).getEntry();
  }
  public void update() {
    // // Check all errors previously
    // // If there is an error last frame that no longer exists remove it
    // String[] lastFrameSubErrors = ErrorsLastFrame.split(":");

    // // Loop over the substrings
    // for (String lastFrameSubError : lastFrameSubErrors) {
    //   if(!errorExists(CurrentErrors, lastFrameSubError)){
    //     m_nte_Errors = m_sbt_Errors.add("Drive", "").getEntry();
    //   }
    // }
    // ErrorsLastFrame = CurrentErrors;
    // CurrentErrors = "";
  }

  public void logError(String nameOfCaller, String newError) {
    if(!UsedNames.contains(nameOfCaller)){
      UsedNames += nameOfCaller;
      m_nte_Errors = m_sbt_Errors.add(nameOfCaller, newError).getEntry();
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
