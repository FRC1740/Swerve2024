// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import frc.Board.HornTab;
import frc.robot.constants.HornConstants;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class HornSubsystem extends SubsystemBase {
  private final CANSparkMax m_HornRightMotor = new CANSparkMax(HornConstants.kHornRightMotorPort, CANSparkMax.MotorType.kBrushless);
  private final CANSparkMax m_HornLeftMotor = new CANSparkMax(HornConstants.kHornLeftMotorPort, CANSparkMax.MotorType.kBrushless);
  private final RelativeEncoder m_HornLeftEncoder;
  private final RelativeEncoder m_HornRightEncoder;
  
  HornTab m_HornTab = HornTab.getInstance();

  /** Creates a new GroundIntake. */
  public HornSubsystem() {
    m_HornRightMotor.setInverted(false);
    m_HornLeftMotor.setInverted(true);
    m_HornLeftEncoder = m_HornRightMotor.getEncoder();
    m_HornRightEncoder = m_HornRightMotor.getEncoder();
    m_HornLeftEncoder.setVelocityConversionFactor(HornConstants.kVelocityConversionFactor);
    m_HornRightEncoder.setVelocityConversionFactor(HornConstants.kVelocityConversionFactor);
    burnFlash();
  }

  public void Shoot(double speed) {
    setHornSpeed(speed);
  }

  public void Intake(double speed) {
    setHornSpeed(-speed);
  }

  public double getHornVelocity() {
    return (m_HornRightEncoder.getVelocity() + m_HornLeftEncoder.getVelocity()) / 2;
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
    m_HornTab.setHornSpeed(getHornVelocity());
  }

  private void burnFlash() {
    m_HornRightMotor.burnFlash();
    m_HornLeftMotor.burnFlash();
  }
}
