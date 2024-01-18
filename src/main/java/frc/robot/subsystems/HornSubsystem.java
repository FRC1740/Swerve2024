// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import frc.robot.constants.HornConstants;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkBase.ControlType;

import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class HornSubsystem extends SubsystemBase {
  private final CANSparkMax m_HornMotorLeader = new CANSparkMax(HornConstants.kHornLeaderMotorPort, CANSparkMax.MotorType.kBrushless);
  private final CANSparkMax m_HornMotorFollower = new CANSparkMax(HornConstants.kHornFollowerMotorPort, CANSparkMax.MotorType.kBrushless);
  private final RelativeEncoder m_HornMotorEncoder;
  private SparkPIDController m_PidController;


  NetworkTable HornTable = NetworkTableInstance.getDefault().getTable("Horn");

  //PID network tables publishers
  DoublePublisher P_Pub = HornTable.getDoubleTopic("P").publish();
  DoublePublisher I_Pub = HornTable.getDoubleTopic("I").publish();
  DoublePublisher D_Pub = HornTable.getDoubleTopic("D").publish();

  //Flywheel Velocity publishers (Meters/second)
  DoublePublisher Velocity_Pub = HornTable.getDoubleTopic("Velocity meters/sec").publish();
  /** Creates a new GroundIntake. */
  public HornSubsystem() {
    m_HornMotorFollower.follow(m_HornMotorLeader, false); //invert might have to be changed to true
    m_HornMotorEncoder = m_HornMotorLeader.getEncoder();
    m_PidController = m_HornMotorLeader.getPIDController();

    setPID(HornConstants.kP, HornConstants.kI, HornConstants.kD);

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

    publishPIDvalues();
    publishVelocity();

  }

  private void burnFlash() {
    m_HornMotorLeader.burnFlash();
    m_HornMotorFollower.burnFlash();
  }

  private void publishPIDvalues(){
    P_Pub.set(m_PidController.getP());
    I_Pub.set(m_PidController.getI());
    D_Pub.set(m_PidController.getD());
  }

  private void setPID(double P, double I, double D){
    m_PidController.setP(P);
    m_PidController.setI(I);
    m_PidController.setD(D);
  }

  private void publishVelocity(){
    Velocity_Pub.set(m_HornMotorEncoder.getVelocity());
  }
}
