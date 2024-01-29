// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.HornConstants;
import frc.robot.constants.SubsystemConstants.ConveyorConstants;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

public class Conveyor extends SubsystemBase {
  /** Creates a new Conveyor. */
  private final CANSparkMax m_ConveyorMotor = new CANSparkMax(ConveyorConstants.kConveyorMotorPort, CANSparkMax.MotorType.kBrushless);
  private final RelativeEncoder m_ConveyorMotorEncoder;
  private double m_ConveyorSpeed = ConveyorConstants.intakeFromFloorSpeed;

  public Conveyor() {
    m_ConveyorMotorEncoder = m_ConveyorMotor.getEncoder();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void stopConveyor() {
    m_ConveyorMotor.stopMotor();
  }

  public void conveyFromFloor() {
    m_ConveyorMotor.set(m_ConveyorSpeed);
  }
  public void conveyFromHorn() {
    m_ConveyorMotor.set(-m_ConveyorSpeed);

  }
}
