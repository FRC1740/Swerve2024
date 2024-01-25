package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.Board.SensorTab;
import frc.robot.constants.SensorConstants;

public class SensorSubsystem extends SubsystemBase{
  private DigitalInput m_timeOfFlightSensor;
  private SensorTab m_sensorTab;
  public SensorSubsystem() {
    m_sensorTab = SensorTab.getInstance();
    m_timeOfFlightSensor = new DigitalInput(SensorConstants.kTimeOfFlightInputPort);
  }

  @Override
  public void periodic() {
    m_sensorTab.setSensorStatePort0(m_timeOfFlightSensor.get());
  }
}
