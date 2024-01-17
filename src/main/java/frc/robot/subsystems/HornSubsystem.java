// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import frc.robot.constants.HornConstants;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkBase.ControlType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class HornSubsystem extends SubsystemBase {
  private final CANSparkMax m_HornMotorLeader = new CANSparkMax(HornConstants.kHornLeaderMotorPort, CANSparkMax.MotorType.kBrushless);
  private final CANSparkMax m_HornMotorFollower = new CANSparkMax(HornConstants.kHornFollowerMotorPort, CANSparkMax.MotorType.kBrushless);
  private final RelativeEncoder m_HornMotorEncoder;
  private SparkPIDController m_PidController;

  //FIXME: Add datalogging

  /** Creates a new GroundIntake. */
  public HornSubsystem() {
    m_HornMotorFollower.follow(m_HornMotorLeader, false); //invert might have to be changed to true
    m_HornMotorEncoder = m_HornMotorLeader.getEncoder();
    m_PidController = m_HornMotorLeader.getPIDController();
  }

  public void Shoot(double speed) {
    setHornSpeed(speed);
  }

  public void Intake(double speed) {
    setHornSpeed(-speed);
  }

  public double getHornvelocity() {
    return m_HornMotorEncoder.getVelocity(); 
    }

  public void setHornSpeed(double speed) {
    m_PidController.setReference(speed, ControlType.kVelocity);
  }

  public void stopHorn() {
    m_PidController.setReference(0, ControlType.kVelocity);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    // Report the actual speed to the shuffleboard
    // m_GroundIntakeTab.setIntakeSpeed(getIntakeVelocity());
    // m_intakeSetSpeed = m_GroundIntakeTab.getIntakeSetSpeed();
  }

  public void burnFlash() {
    m_HornMotorLeader.burnFlash();
    m_HornMotorFollower.burnFlash();
  }

}
