// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import frc.Board.HornTab;
import frc.robot.constants.HornConstants;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkBase.ControlType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class HornSubsystem extends SubsystemBase {
  private final CANSparkMax m_HornRightMotor = new CANSparkMax(HornConstants.kHornRightMotorPort, CANSparkMax.MotorType.kBrushless);
  private final CANSparkMax m_HornLeftMotor = new CANSparkMax(HornConstants.kHornLeftMotorPort, CANSparkMax.MotorType.kBrushless);
  private final RelativeEncoder m_HornLeftEncoder;
  private final RelativeEncoder m_HornRightEncoder;
  private SparkPIDController m_RightPidController;
  private SparkPIDController m_LeftPidController;
  HornTab m_HornTab = HornTab.getInstance();

  private double currentP; // stores the current P without checking m_RightPidController
  private double currentI; // stores the current I without checking m_RightPidController
  private double currentD; // stores the current D without checking m_RightPidController
  private double currentFF; // stores the current FF without checking m_RightPidController

  /** Creates a new GroundIntake. */
  public HornSubsystem() {
    m_HornRightMotor.setInverted(false);
    m_HornLeftMotor.setInverted(true);
    m_HornLeftEncoder = m_HornLeftMotor.getEncoder();
    m_HornRightEncoder = m_HornRightMotor.getEncoder();
    m_HornLeftEncoder.setVelocityConversionFactor(HornConstants.kVelocityConversionFactor);
    m_HornRightEncoder.setVelocityConversionFactor(HornConstants.kVelocityConversionFactor);

    m_RightPidController = m_HornRightMotor.getPIDController();
    m_LeftPidController = m_HornLeftMotor.getPIDController();

    // m_RightPidController.setOutputRange(0, 1);
    // m_LeftPidController.setOutputRange(0, 1);
    burnFlash();

  }

  public void Shoot(double speed) {
    setHornSpeed(speed);
  }

  public void setVelocity(double rightVelocity, double leftVelocity){
    m_RightPidController.setReference(rightVelocity, ControlType.kVelocity);
    m_LeftPidController.setReference(leftVelocity, ControlType.kVelocity);
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

  public void Intake(double speed) {
    setHornSpeed(-speed);
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

  public void setHornSpeed(double speed) {
    m_HornRightMotor.set(speed);
    m_HornLeftMotor.set(speed);
  }

  public void stopHorn() {
    m_HornRightMotor.set(0.0);
    m_HornLeftMotor.set(0.0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    // Report the actual speed to the shuffleboard
    // m_GroundIntakeTab.setIntakeSpeed(getIntakeVelocity());
    // m_intakeSetSpeed = m_GroundIntakeTab.getIntakeSetSpeed();
  }

  public void burnFlash() {
    m_HornMotor.burnFlash();
  }
}
