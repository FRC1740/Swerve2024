// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import frc.Board.OutputSB.HornTab;
import frc.robot.constants.CanIds;
import frc.robot.constants.SubsystemConstants.HornConstants;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class HornSubsystem extends SubsystemBase {
  private final CANSparkMax m_HornRightMotor = new CANSparkMax(CanIds.kHornRightMotorCanId, CANSparkMax.MotorType.kBrushless);
  private final CANSparkMax m_HornLeftMotor = new CANSparkMax(CanIds.kHornLeftMotorCanId, CANSparkMax.MotorType.kBrushless);
  private final RelativeEncoder m_HornLeftEncoder;
  private final RelativeEncoder m_HornRightEncoder;
  private SparkPIDController m_RightPidController;
  private SparkPIDController m_LeftPidController;
  HornTab m_HornTab = HornTab.getInstance();

  private double currentP; // stores the current P without checking m_RightPidController
  private double currentI; // stores the current I without checking m_RightPidController
  private double currentD; // stores the current D without checking m_RightPidController
  private double currentFF; // stores the current FF without checking m_RightPidController
  // Checking the PIDController is extremely slow

  /** Creates a new GroundIntake. */
  public HornSubsystem() {
    m_HornRightMotor.setInverted(false);
    m_HornLeftMotor.setInverted(true);
    m_HornLeftMotor.setSmartCurrentLimit(HornConstants.kHornCurrentLimit);
    m_HornRightMotor.setSmartCurrentLimit(HornConstants.kHornCurrentLimit);

    m_HornLeftEncoder = m_HornLeftMotor.getEncoder();
    m_HornRightEncoder = m_HornRightMotor.getEncoder();
    m_HornLeftEncoder.setVelocityConversionFactor(HornConstants.kVelocityConversionFactor);
    m_HornRightEncoder.setVelocityConversionFactor(HornConstants.kVelocityConversionFactor);

    m_RightPidController = m_HornRightMotor.getPIDController();
    m_LeftPidController = m_HornLeftMotor.getPIDController();

    // m_RightPidController.setOutputRange(-1, 1); // do not touch!
    // m_LeftPidController.setOutputRange(-1, 1);
    burnFlash();

  }

  public void Shoot(double speed) {
    setHornSpeed(speed);
  }

  public void setVelocity(double rightVelocity, double leftVelocity){
    m_RightPidController.setReference(rightVelocity, HornConstants.kDefaultControlType);
    m_LeftPidController.setReference(leftVelocity, HornConstants.kDefaultControlType);
  }

  public void setP(double gain){
    m_RightPidController.setP(gain);
    m_LeftPidController.setP(gain);
    currentP = gain;
  }

  public void setI(double gain){
    m_RightPidController.setI(gain);
    m_LeftPidController.setI(gain);
    currentI = gain;
  }

  public void setD(double gain){
    m_RightPidController.setD(gain);
    m_LeftPidController.setD(gain);
    currentD = gain;
  }

  public void setFF(double gain){
    m_RightPidController.setFF(gain);
    m_LeftPidController.setFF(gain);
    currentFF = gain;
  }

  @Deprecated
  public void Intake(double speed) {
    setHornSpeed(-speed);
  }
  @Deprecated
  public void setHornSpeed(double speed) {
    m_HornRightMotor.set(speed);
    m_HornLeftMotor.set(speed);
  }

  public void setRpmSetpoint(double RPMSptpoint) {
    m_RightPidController.setReference(RPMSptpoint, HornConstants.kDefaultControlType);
    m_LeftPidController.setReference(RPMSptpoint, HornConstants.kDefaultControlType);
    m_HornTab.setHornTargetSpeed(RPMSptpoint);
  }

  public double getHornVelocity() {
    return (m_HornRightEncoder.getVelocity() + m_HornLeftEncoder.getVelocity()) / 2;
  }

  public double getRightVelocity(){
    return m_HornRightEncoder.getVelocity();
  }

  public double getLeftVelocity(){
    return m_HornLeftEncoder.getVelocity();
  }

  public void stopHorn() {
    m_HornRightMotor.set(0.0);
    m_HornLeftMotor.set(0.0);
  }

  @Override
  public void periodic() {
    setVelocity(m_HornTab.getHornTargetSpeed(), m_HornTab.getHornTargetSpeed());
    m_HornTab.setRightHornSpeed(getRightVelocity());
    m_HornTab.setLeftHornSpeed(getLeftVelocity());
    if(m_HornTab.getP() != currentP){
      setP(m_HornTab.getP());
    }
    if(m_HornTab.getI() != currentI){
      setI(m_HornTab.getI());
    }
    if(m_HornTab.getD() != currentD){
      setD(m_HornTab.getD());
    }
    if(m_HornTab.getFF() != currentFF){
      setFF(m_HornTab.getFF());
    }
  }

  public void burnFlash() {
    m_HornRightMotor.burnFlash();
    m_HornLeftMotor.burnFlash();
  }
}
