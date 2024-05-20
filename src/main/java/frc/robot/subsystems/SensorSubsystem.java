package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.Board.DriverTab;
import frc.Board.SensorTab;
import frc.robot.RobotShared;
import frc.robot.commands.basic.NoteRumble;
import frc.robot.constants.SensorConstants;

/** example usage:
  if(m_SensorSubsystem,getSensorValue(1) == true){
    //do something
  }  
*/
public class SensorSubsystem extends SubsystemBase{
  private DigitalInput[] m_breakBeamSensors;
  RobotShared m_robotShared;
  private LedSubsystem m_LedSubsystem;
  private SensorTab m_sensorTab;
  private DriverTab m_driverTab;
  private boolean hasNote = false;

  public SensorSubsystem() {
    m_breakBeamSensors = new DigitalInput[SensorConstants.kDIOPorts]; // ten ports total
    m_robotShared = RobotShared.getInstance();
    m_LedSubsystem = m_robotShared.getLedSubsystem();
    m_sensorTab = SensorTab.getInstance();
    m_driverTab = DriverTab.getInstance();
    getSensorValue(0);
  }

  @Override
  public void periodic() {
    boolean previousNoteState = hasNote;

    hasNote = false;
    // Loop over every port and update them if they exist
    for(int index = 0; index < SensorConstants.kDIOPorts; index++) {
      if(m_sensorTab.sensorStatePortExists(index)){
        m_sensorTab.setSensorStatePort(m_breakBeamSensors[index].get(), index);
        if(m_breakBeamSensors[index].get() == false){
          hasNote = true;
        }
      }
    }

    if(previousNoteState != hasNote){
      new NoteRumble().schedule();
      if(hasNote) {
        m_LedSubsystem.SendLedCommandGotNote();
      }else{
        m_LedSubsystem.SendLedCommandPathing();
      }
    }

    m_driverTab.setHasNote(hasNote);
    m_sensorTab.updateShuffleboard();
  }
  /**
   * Returns true if the sensors can see eachother, meaning there is nothing there, 
   * otherwise it returns false, and there is something there,
   * if it does not exist, it is created. 
   * In the event the sensors are not hooked up correctly, it will return false when they should see eachother
   * @param port The port to check 
  */
  public boolean getSensorValue(int port){
    // This checks a value in an array on the sensor tab, it does not affect performance in a meaningful way
    if(!m_sensorTab.sensorStatePortExists(port)){
      createSensor(port);
    }
    // This does not pull from the shuffleboard, it pulls from the array in the sensor tab
    return m_sensorTab.getSensorStatePort(port);
  }
  /**
   * Returns true if the sensors can see eachother, meaning there is nothing there, 
   * otherwise it returns false, and there is something there,
   * Returns the value at a port, if it does not exist, it is *<b>NOT</b>* created
   * This method is not recommened, use getSensorValue instead, because if it does not exist it will cause a crash and a memory leak
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

  /**
   * Checks the status of the horn intake sensors.
   * Returns true if either of the sensors detect an object, otherwise returns false.
   */
  public boolean checkHornSensors() {
    return getSensorValue(SensorConstants.kHornIntakeSensorPort) == false;
  }
  
  /** 
   * Checks the status of the ground intake sensor.
   * Returns true if the sensor detects an object, otherwise returns false.
   */
  public boolean checkGroundIntakeSensor() {
    return getSensorValue(SensorConstants.kGroundIntakeSensorPort) == false;
  }
}
