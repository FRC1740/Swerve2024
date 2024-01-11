// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import frc.robot.constants.HornConstants;

// Comment to force commit
// Using "import static an.enum.or.constants.inner.class.*;" helps reduce verbosity
// this replaces "DoubleSolenoid.Value.kForward" with just kForward
// further reading is available at https://www.geeksforgeeks.org/static-import-java/

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class HornSubsystem extends SubsystemBase {
  private final CANSparkMax m_HornMotor = new CANSparkMax(HornConstants.kHornMotorPort, CANSparkMax.MotorType.kBrushless);
  private final RelativeEncoder m_HornMotorEncoder;
  private double m_intakeSetSpeed = HornConstants.kHornMotorSpeed;

  /** Creates a new GroundIntake. */
  public HornSubsystem() {
    m_HornMotorEncoder = m_HornMotor.getEncoder();
  }

  public void Shoot(double speed) {
    setHornSpeed(speed);
  }

  public double getHornvelocity() {
    return m_HornMotorEncoder.getVelocity(); 
    }

  public void setHornSpeed(double speed) {
    m_intakeSetSpeed = speed;
    m_HornMotor.set(m_intakeSetSpeed);
  }

  public void stopHorn() {
    m_HornMotor.set(0.0);
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
