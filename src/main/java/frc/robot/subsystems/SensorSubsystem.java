package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.Board.SensorTab;
import frc.robot.constants.SensorConstants;

public class SensorSubsystem extends SubsystemBase{
  /* example usage:
    if(m_SensorSubsystem,getSensorValue(1) == true){
      //do something
    }  
  */
  private DigitalInput[] m_breakBeamSensors;
  private SensorTab m_sensorTab;
  public SensorSubsystem() {
    m_breakBeamSensors = new DigitalInput[SensorConstants.kDIOPorts]; // ten ports total
    m_sensorTab = SensorTab.getInstance();
    getSensorValue(1);
  }

  @Override
  public void periodic() {
    // Loop over every port and update them if they exist
    for(int index = 0; index < SensorConstants.kDIOPorts; index++){
      if(m_sensorTab.sensorStatePortExists(index)){
        m_sensorTab.setSensorStatePort(m_breakBeamSensors[index].get(), index);
      }
    }
  }
  /**
   * Returns the value at a port, if it does not exist, it is created
   * @param port The port to check 
  */
  public boolean getSensorValue(int port){
    if(!m_sensorTab.sensorStatePortExists(port)){
      createSensor(port);
    }
    return m_sensorTab.getSensorStatePort(port);
  }
  /**
   * Returns the value at a port, if it does not exist, it is *NOT* created
   * @param port The port to check 
  */
  @Deprecated
  public boolean getSensorValueUnsafe(int port){
    return m_sensorTab.getSensorStatePort(port);
  }
  /**
   * Creates a new Senor with DigitalInput(port); and posts it to the shuffleboard
   * This is not a safe method, improper usage can crash the robot so it is private
   * You need to make sure port does not already exist via sensorStatePortExists
   * @param port The port to make 
  */
  private void createSensor(int port){
    m_breakBeamSensors[port] = new DigitalInput(port);
    m_sensorTab.addSensorStatePort(port);
  }
}
