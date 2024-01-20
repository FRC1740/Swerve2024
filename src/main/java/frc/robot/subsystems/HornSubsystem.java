// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import frc.robot.constants.HornConstants;

import java.io.Console;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class HornSubsystem extends SubsystemBase {
  private final CANSparkMax m_HornRightMotor = new CANSparkMax(HornConstants.kHornLeaderMotorPort, CANSparkMax.MotorType.kBrushless);
  private final CANSparkMax m_HornLeftMotor = new CANSparkMax(HornConstants.kHornFollowerMotorPort, CANSparkMax.MotorType.kBrushless);
  private final RelativeEncoder m_HornLeftEncoder;
  private final RelativeEncoder m_HornRightEncoder;

  /** Creates a new GroundIntake. */
  public HornSubsystem() {
    m_HornRightMotor.setInverted(false);
    m_HornLeftMotor.setInverted(true);
    m_HornLeftEncoder = m_HornRightMotor.getEncoder();
    m_HornRightEncoder = m_HornRightMotor.getEncoder();
    burnFlash();
  }

  public void Shoot(double speed) {
    setHornSpeed(speed);
  }

  public void Intake(double speed) {
    setHornSpeed(-speed);
  }

  public double getHornvelocity() {
    return m_HornRightEncoder.getVelocity(); 
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
    // m_DriveTab.setIMU_ZAngle(10);
    // This method will be called once per scheduler run
    // Report the actual speed to the shuffleboard
    // m_GroundIntakeTab.setIntakeSpeed(getIntakeVelocity());
    // m_intakeSetSpeed = m_GroundIntakeTab.getIntakeSetSpeed();
  }

  private void burnFlash() {
    m_HornRightMotor.burnFlash();
    m_HornLeftMotor.burnFlash();
  }
}
