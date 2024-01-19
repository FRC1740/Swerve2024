// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import frc.robot.constants.HornConstants;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class HornSubsystem extends SubsystemBase {
  private final CANSparkMax m_HornMotorLeader = new CANSparkMax(HornConstants.kHornLeaderMotorPort, CANSparkMax.MotorType.kBrushless);
  private final CANSparkMax m_HornMotorFollower = new CANSparkMax(HornConstants.kHornFollowerMotorPort, CANSparkMax.MotorType.kBrushless);
  private final RelativeEncoder m_HornMotorEncoder;

  /** Creates a new GroundIntake. */
  public HornSubsystem() {
    m_HornMotorFollower.follow(m_HornMotorLeader, false); //invert might have to be changed to true
    m_HornMotorEncoder = m_HornMotorLeader.getEncoder();

    burnFlash();
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
    m_HornMotorLeader.set(speed);
  }

  public void stopHorn() {
    m_HornMotorLeader.set(0.0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    // Report the actual speed to the shuffleboard
    // m_GroundIntakeTab.setIntakeSpeed(getIntakeVelocity());
    // m_intakeSetSpeed = m_GroundIntakeTab.getIntakeSetSpeed();
  }

  private void burnFlash() {
    m_HornMotorLeader.burnFlash();
    m_HornMotorFollower.burnFlash();
  }
}
