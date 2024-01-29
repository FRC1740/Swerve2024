// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import frc.robot.constants.HornConstants;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class HornSubsystem extends SubsystemBase {
  private final CANSparkMax m_HornRightMotor = new CANSparkMax(HornConstants.kHornRightMotorPort, CANSparkMax.MotorType.kBrushless);
  private final CANSparkMax m_HornLeftMotor = new CANSparkMax(HornConstants.kHornLeftMotorPort, CANSparkMax.MotorType.kBrushless);
  private final RelativeEncoder m_HornLeftMotorEncoder;
  private final RelativeEncoder m_HornRightMotorEncoder;
  private double m_intakeSetSpeed = HornConstants.HornMotorSpeed;

  /** Creates a new GroundIntake. */
  public HornSubsystem() {
    m_HornLeftMotor.setInverted(true);
    m_HornRightMotor.setInverted(false);
    m_HornLeftMotorEncoder = m_HornLeftMotor.getEncoder();
    m_HornRightMotorEncoder = m_HornRightMotor.getEncoder();
  }

  public void Shoot(double speed) {
    setHornSpeed(speed);
  }

  public void Intake(double speed) {
    setHornSpeed(-speed);
  }

  public double getHornvelocity() {
    return (m_HornLeftMotorEncoder.getVelocity() + m_HornRightMotorEncoder.getVelocity()) / 2.0; 
    }

  public void setHornSpeed(double speed) {
    m_intakeSetSpeed = speed;
    m_HornLeftMotor.set(m_intakeSetSpeed);
    m_HornRightMotor.set(m_intakeSetSpeed);
  }

  public void stopHorn() {
    m_HornRightMotor.set(0.0);
    m_HornLeftMotor.set(0.0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    // Report the actual speed to the shuffleboard
  }

  public void burnFlash() {
    m_HornRightMotor.burnFlash();
    m_HornLeftMotor.burnFlash();
  }

}
